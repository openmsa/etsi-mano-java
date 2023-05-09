/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
package com.ubiqube.etsi.mano.service.rest;

import java.net.URI;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.web.util.UriComponentsBuilder;

import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;

public class ManoVim {

	private final ManoClient client;

	public ManoVim(final ManoClient client) {
		this.client = client;
	}

	public VimConnectionInformation register(final VimConnectionInformation vim, final String root) {
		final ServerAdapter server = client.getServer();
		final URI uri = buildUri(root, "admin/vim/register");
		return Objects.requireNonNull(server.rest().post(uri, vim, VimConnectionInformation.class, null));
	}

	private URI buildUri(final String urlRoot, final String url) {
		final Map<String, Object> uriParams = Optional.ofNullable(client.getObjectId()).map(x -> Map.of("id", (Object) x.toString())).orElseGet(Map::of);
		return UriComponentsBuilder.fromHttpUrl(urlRoot).pathSegment(url).buildAndExpand(uriParams).toUri();
	}

}
