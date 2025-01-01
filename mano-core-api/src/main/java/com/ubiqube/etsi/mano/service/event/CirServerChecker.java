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
package com.ubiqube.etsi.mano.service.event;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionType;
import com.ubiqube.etsi.mano.docker.HelmService;
import com.ubiqube.etsi.mano.docker.RegistryInformations;
import com.ubiqube.etsi.mano.exception.GenericException;

@Service
public class CirServerChecker {
	private final Map<ConnectionType, HelmService> helmServices;

	public CirServerChecker(final List<HelmService> helmServices) {
		this.helmServices = helmServices.stream().collect(Collectors.toMap(x -> ConnectionType.valueOf(x.getConnectionType()), x -> x));
	}

	public void verify(final ConnectionInformation conn) {
		final HelmService hs = helmServices.get(conn.getConnType());
		if (null == hs) {
			throw new GenericException("Unknown CIR: " + conn.getConnType());
		}
		final RegistryInformations reg = toRegistry(conn);
		hs.verifyConnection(reg);
	}

	private static RegistryInformations toRegistry(final ConnectionInformation conn) {
		return RegistryInformations.builder()
				.server(conn.getUrl().toString())
				.username(conn.getAuthentification().getAuthParamBasic().getUserName())
				.password(conn.getAuthentification().getAuthParamBasic().getPassword())
				.build();
	}

}
