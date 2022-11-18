/**
 *     Copyright (C) 2019-2020 Ubiqube.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.service.mon.vim;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.openstack4j.api.OSClient.OSClientV3;
import org.openstack4j.api.client.IOSClientBuilder.V3;
import org.openstack4j.core.transport.Config;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.model.telemetry.gnocchi.Measure;
import org.openstack4j.model.telemetry.gnocchi.MeasureFilter;
import org.openstack4j.model.telemetry.gnocchi.Resource;
import org.openstack4j.openstack.OSFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.mon.dao.TelemetryMetricsResult;
import com.ubiqube.etsi.mano.service.mon.data.Metric;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class GnocchiSubTelemetry {
	private static final Logger LOG = LoggerFactory.getLogger(GnocchiSubTelemetry.class);

	private GnocchiSubTelemetry() {
		// Nothing.
	}

	public static List<TelemetryMetricsResult> getMetricsForVnfc(final VimConnectionInformation vimConnectionInformation, final String vnfcId, final List<Metric> collectedMetrics, final UUID uuid) {
		final OSClientV3 os = authenticate(vimConnectionInformation);
		return getMetrics(uuid, vnfcId, collectedMetrics, os);
	}

	private static List<TelemetryMetricsResult> getMetrics(final UUID uuid, final String vnfcId, final List<Metric> collectedMetrics, final OSClientV3 os) {
		final List<String> colls = collectedMetrics.stream().map(Metric::getName).toList();
		final Resource instanceResources = os.telemetry().resources().instance(vnfcId);
		if (null == instanceResources) {
			LOG.warn("Could not fetch resource {}", vnfcId);
			return List.of();
		}
		final List<Entry<String, String>> colMeter = instanceResources.getMetrics().entrySet().stream().filter(x -> colls.contains(x.getKey())).toList();
		return colMeter.stream().map(x -> map(x, vnfcId, uuid, os)).toList();
	}

	private static TelemetryMetricsResult map(final Entry<String, String> x, final String vnfcId, final UUID id, final OSClientV3 os) {
		final MeasureFilter mf = new MeasureFilter();
		mf.start(OffsetDateTime.now().minus(10, ChronoUnit.MINUTES));
		final List<? extends Measure> res;
		try {
			res = os.telemetry().gnocchi().mesures().read(x.getValue(), mf);
		} catch (final RuntimeException e) {
			LOG.warn("An error occured.", e);
			return new TelemetryMetricsResult(id.toString(), vnfcId, x.getKey(), Double.valueOf(0), OffsetDateTime.now(), false);
		}
		if (res.isEmpty()) {
			LOG.warn("Metric {} is empty.", x.getValue());
			return new TelemetryMetricsResult(id.toString(), vnfcId, x.getKey(), Double.valueOf(0), OffsetDateTime.now(), false);
		}
		final Double value = res.get(0).getValue();
		final OffsetDateTime ts = res.get(res.size() - 1).getTimeStamp();
		return new TelemetryMetricsResult(id.toString(), vnfcId, x.getKey(), value, ts, true);
	}

	/**
	 * This one is a copy paste of OpenStackVim.
	 *
	 * @param vci
	 * @return
	 */
	private static OSClientV3 authenticate(final VimConnectionInformation vci) {
		final Map<String, String> ii = vci.getInterfaceInfo();
		final V3 base = OSFactory.builderV3()
				.endpoint(ii.get("endpoint"));
		final Map<String, String> ai = vci.getAccessInfo();
		final String userDomain = ai.get("userDomain");
		if (null != userDomain) {
			final Identifier domainIdentifier = Identifier.byName(userDomain);
			base.credentials(ai.get("username"), ai.get("password"), domainIdentifier);
		} else {
			base.credentials(ai.get("username"), ai.get("password"));
		}
		final Config conf = Config.newConfig();
		if ("true".equals(ii.get("non-strict-ssl"))) {
			conf.withSSLVerificationDisabled();
		}
		if (null != ii.get("nat-host")) {
			conf.withEndpointNATResolution(ii.get("nat-host"));
		}
		base.withConfig(conf);
		final String project = ai.get("project");
		final String projectId = ai.get("projectId");
		if (null != project) {
			base.scopeToProject(Identifier.byName(project));
		} else if (null != projectId) {
			base.scopeToProject(Identifier.byId(projectId));
		}
		return base.authenticate();
	}

}
