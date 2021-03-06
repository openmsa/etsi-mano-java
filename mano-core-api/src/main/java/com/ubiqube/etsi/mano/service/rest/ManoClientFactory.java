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
package com.ubiqube.etsi.mano.service.rest;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.service.ServerService;

import ma.glasnost.orika.MapperFacade;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class ManoClientFactory {

	private final MapperFacade mapper;
	private final ServerService serverService;

	public ManoClientFactory(final MapperFacade mapper, final ServerService serverService) {
		super();
		this.mapper = mapper;
		this.serverService = serverService;
	}

	public ManoClient getClient() {
		final ServerAdapter server = serverService.findNearestServer();
		return new ManoClient(mapper, server);
	}

	public ManoClient getClient(final Servers servers) {
		final ServerAdapter server = serverService.buildServerAdapter(servers);
		return new ManoClient(mapper, server);
	}
}
