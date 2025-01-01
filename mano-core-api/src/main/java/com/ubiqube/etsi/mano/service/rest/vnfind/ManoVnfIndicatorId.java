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
package com.ubiqube.etsi.mano.service.rest.vnfind;

import java.util.UUID;

import com.ubiqube.etsi.mano.dao.mano.VnfIndicator;
import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.service.HttpGateway;
import com.ubiqube.etsi.mano.service.rest.QueryParameters;

public class ManoVnfIndicatorId {

	private QueryParameters client;

	public ManoVnfIndicatorId(final QueryParameters manoClient, final UUID id) {
		this.client = manoClient;
		client.setQueryType(ApiVersionType.SOL003_VNFIND);
		client.setFragment("/indicators/{id}");
		client.setObjectId(id);
	}

	public VnfIndicator find() {
		return (VnfIndicator) client.createQuery()
				.setWireOutClass(HttpGateway::getVnfIndicatorClass)
				.setOutClass(HttpGateway::mapToVnfIndicator)
				.getSingle();
	}
}
