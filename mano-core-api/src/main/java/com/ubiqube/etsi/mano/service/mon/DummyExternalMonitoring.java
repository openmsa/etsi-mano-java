/**
 * Copyright (C) 2019-2025 Ubiqube.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.service.mon;

import static com.ubiqube.etsi.mano.Constants.getSafeUUID;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.dao.mano.AccessInfo;
import com.ubiqube.etsi.mano.dao.mano.ai.KeystoneAuthV3;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.mon.dao.TelemetryMetricsResult;
import com.ubiqube.etsi.mano.service.mapping.MonitoringMapper;
import com.ubiqube.etsi.mano.service.mon.cli.MetricsRemoteService;
import com.ubiqube.etsi.mano.service.mon.cli.MonPollingRemoteService;
import com.ubiqube.etsi.mano.service.mon.data.BatchPollingJob;
import com.ubiqube.etsi.mano.service.mon.data.Metric;
import com.ubiqube.etsi.mano.service.mon.dto.ConnInfo;
import com.ubiqube.etsi.mano.service.mon.dto.KeystoneV3;
import com.ubiqube.etsi.mano.service.mon.dto.PollingJob;

/**
 *
 * @author olivier
 *
 */
@Service
public class DummyExternalMonitoring implements ExternalMonitoring {

	private static final Logger LOG = LoggerFactory.getLogger(DummyExternalMonitoring.class);
	private final MonPollingRemoteService remoteService;
	private final MetricsRemoteService metricsRemoteService;
	private final MonitoringMapper mapper;

	public DummyExternalMonitoring(final MonPollingRemoteService remoteService, final MetricsRemoteService metricsRemoteService, final MonitoringMapper mapper) {
		this.remoteService = remoteService;
		this.metricsRemoteService = metricsRemoteService;
		this.mapper = mapper;
	}

	@Override
	public UUID createBatch(final String resourceId, final Set<String> metrics, final Long pollingInterval, final VimConnectionInformation vimConnectionInformation) {
		LOG.info("registering {}", resourceId);
		final PollingJob pj = new PollingJob();
		pj.setInterval(pollingInterval);
		pj.setConnection(convert(vimConnectionInformation));
		final List<Metric> lst = metrics.stream().map(x -> new Metric(x, x)).toList();
		pj.setMetrics(lst);
		pj.setResourceId(resourceId);
		final ResponseEntity<BatchPollingJob> ret = remoteService.register(pj);
		return Objects.requireNonNull(ret.getBody()).getId();
	}

	private ConnInfo convert(final VimConnectionInformation vimConnectionInformation) {
		final ConnInfo ci = createConnInfo(vimConnectionInformation);
		// Not sure every VIMId are UUID.
		ci.setConnId(UUID.fromString(vimConnectionInformation.getVimId()));
		ci.setExtra(vimConnectionInformation.getExtra());
		ci.setIgnoreSsl(true);
		ci.setName(vimConnectionInformation.getVimId());
		ci.setType(vimConnectionInformation.getVimType());
		return ci;
	}

	private ConnInfo createConnInfo(final VimConnectionInformation vimConnectionInformation) {
		AccessInfo ai = vimConnectionInformation.getAccessInfo();
		if (ai instanceof KeystoneAuthV3 ka3) {
			KeystoneV3 obj = mapper.toKeystoneV3(vimConnectionInformation);
			mapper.toKeystoneV3(ka3, obj);
			return obj;
		}
		throw new GenericException(("Unsupported access info type: " + (ai == null ? null : ai.getClass())));
	}

	@Override
	public void deleteResources(final String resourceId) {
		LOG.info("Deleting {}", resourceId);
		remoteService.delete(getSafeUUID(resourceId));
	}

	@Override
	public List<TelemetryMetricsResult> searchMetric(final MultiValueMap<String, String> params) {
		return Optional.ofNullable(metricsRemoteService.searchApi(params))
				.map(HttpEntity::getBody)
				.orElseGet(List::of);
	}

}
