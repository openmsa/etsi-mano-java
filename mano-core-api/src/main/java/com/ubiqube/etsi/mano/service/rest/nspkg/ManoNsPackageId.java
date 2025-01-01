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
package com.ubiqube.etsi.mano.service.rest.nspkg;

import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.OnboardingStateType;
import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.service.HttpGateway;
import com.ubiqube.etsi.mano.service.rest.QueryParameters;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
public class ManoNsPackageId {

	private static final Logger LOG = LoggerFactory.getLogger(ManoNsPackageId.class);

	private final QueryParameters client;

	public ManoNsPackageId(final QueryParameters manoClient, final UUID id) {
		this.client = manoClient;
		client.setQueryType(ApiVersionType.SOL005_NSD);
		client.setFragment("/ns_descriptors/{id}");
		client.setObjectId(id);
	}

	public void onboard(final Path path, final String accept) {
		client.setFragment("/ns_descriptors/{id}/nsd_content");
		client.createQuery().upload(path, accept);
	}

	public NsdPackage waitForOnboarding() {
		NsdPackage nsd = find();
		while ((nsd.getNsdOnboardingState() == OnboardingStateType.UPLOADING)
				|| (nsd.getNsdOnboardingState() == OnboardingStateType.PROCESSING)
				|| (nsd.getNsdOnboardingState() == OnboardingStateType.CREATED)) {
			try {
				Thread.sleep(1000);
			} catch (final InterruptedException e) {
				LOG.warn("Interrupted", e);
				Thread.currentThread().interrupt();
			}
			nsd = find();
		}
		return nsd;
	}

	public NsdPackage find() {
		return (NsdPackage) client.createQuery()
				.setWireOutClass(HttpGateway::getNsdPackageClass)
				.setOutClass(HttpGateway::mapToNsdPackage)
				.getSingle();
	}

	public void delete() {
		client.createQuery().delete();
	}

	public NsdPackage patch(final String ifMatch, final Map<String, Object> patch) {
		return (NsdPackage) client.createQuery()
				.setWireOutClass(HttpGateway::getNsdPackageClass)
				.setOutClass(HttpGateway::mapToNsdPackage)
				.patch(ifMatch, patch);

	}

}
