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
package com.ubiqube.etsi.mano.service.repository;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.vim.PlanStatusType;
import com.ubiqube.etsi.mano.jpa.config.ServersJpa;
import com.ubiqube.etsi.mano.service.auth.model.ServerType;

@Service
public class ServersRepositoryService {
	private final ServersJpa serversJpa;

	public ServersRepositoryService(final ServersJpa serversJpa) {
		this.serversJpa = serversJpa;
	}

	public List<Servers> findAll() {
		return serversJpa.findAll();
	}

	public Optional<Servers> findById(final UUID id) {
		return serversJpa.findById(id);
	}

	public Optional<Servers> findByUrl(final URI url) {
		return serversJpa.findByUrl(url);
	}

	public Servers save(final Servers servers) {
		return serversJpa.save(servers);
	}

	public void deleteById(final UUID id) {
		serversJpa.deleteById(id);
	}

	public List<Servers> findByServerStatusIn(final List<PlanStatusType> of) {
		return serversJpa.findByServerStatusIn(of);
	}

	public List<Servers> findByServerTypeAndServerStatusIn(final ServerType vnfm, final List<PlanStatusType> asList) {
		return serversJpa.findByServerTypeAndServerStatusIn(vnfm, asList);
	}
}
