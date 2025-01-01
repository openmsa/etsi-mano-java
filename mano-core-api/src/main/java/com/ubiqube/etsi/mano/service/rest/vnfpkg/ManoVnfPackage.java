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
package com.ubiqube.etsi.mano.service.rest.vnfpkg;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;

import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.service.HttpGateway;
import com.ubiqube.etsi.mano.service.rest.ManoQueryBuilder;
import com.ubiqube.etsi.mano.service.rest.QueryParameters;

import org.jspecify.annotations.NonNull;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
public class ManoVnfPackage {
	@NonNull
	private final QueryParameters client;

	public ManoVnfPackage(final QueryParameters manoClient) {
		this.client = manoClient;
		client.setQueryType(ApiVersionType.SOL003_VNFPKGM);
		client.setFragment("/vnf_packages");
	}

	public ManoVnfPackageId id(final UUID id) {
		return new ManoVnfPackageId(client, id);
	}

	public List<VnfPackage> list() {
		final ManoQueryBuilder<Object, VnfPackage> q = client.createQuery();
		return q
				.setInClassList(HttpGateway::getVnfPackageClassList)
				.setOutClass(HttpGateway::mapToVnfPackage)
				.getList();
	}

	public VnfPackage create(final Map<String, String> userDefinedData) {
		final BiFunction<HttpGateway, Object, Object> request = (httpGateway, y) -> httpGateway.createVnfPackageRequest(userDefinedData);
		return (VnfPackage) client.createQuery()
				.setWireInClass(request)
				.setWireOutClass(HttpGateway::getVnfPackageClass)
				.setOutClass(HttpGateway::mapToVnfPackage)
				.post();
	}

	public ManoOnboarded onboarded(final UUID vnfdId) {
		return new ManoOnboarded(client, vnfdId);
	}

	public ManoVnfPackageSubscription subscription() {
		return new ManoVnfPackageSubscription(client);
	}
}
