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
package com.ubiqube.etsi.mano.nfvo.service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ipam.Networks;
import com.ubiqube.etsi.mano.nfvo.jpa.ipam.NetworksJpa;

@Service
public class NetworksRepositoryService {
	private final NetworksJpa networksJpa;

	NetworksRepositoryService(final NetworksJpa networksJpa) {
		this.networksJpa = networksJpa;
	}

	public Optional<Networks> findFirstFreeNetwork(final UUID id) {
		return networksJpa.findFirstFreeNetwork(id);
	}

	public void save(final Networks network) {
		networksJpa.save(network);
	}
}
