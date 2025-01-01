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
package com.ubiqube.etsi.mano.service.rest.admin;

import java.net.URI;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.web.util.UriComponentsBuilder;

import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionInformation;
import com.ubiqube.etsi.mano.service.rest.QueryParameters;
import com.ubiqube.etsi.mano.service.rest.ServerAdapter;

import org.jspecify.annotations.NonNull;

public class ManoCir {
	@NonNull
	private final QueryParameters client;

	public ManoCir(final QueryParameters client) {
		this.client = client;
	}

	public ConnectionInformation register(final ConnectionInformation cirConnection, final String root) {
		final ServerAdapter server = client.getServer();
		final URI uri = buildUri(root, "admin/cir");
		return Objects.requireNonNull(server.rest().post(uri, cirConnection, ConnectionInformation.class, null));
	}

	private URI buildUri(final String urlRoot, final String url) {
		final Map<String, Object> uriParams = Optional.ofNullable(client.getObjectId()).map(x -> Map.of("id", (Object) x.toString())).orElseGet(Map::of);
		return UriComponentsBuilder.fromUriString(urlRoot).pathSegment(url).buildAndExpand(uriParams).toUri();
	}

}
