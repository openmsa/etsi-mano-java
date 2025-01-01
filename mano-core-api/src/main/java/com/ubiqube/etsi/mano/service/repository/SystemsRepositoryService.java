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

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.jpa.SystemsJpa;
import com.ubiqube.etsi.mano.orchestrator.entities.Systems;

@Service
public class SystemsRepositoryService {
	private final SystemsJpa systemJpa;

	public SystemsRepositoryService(final SystemsJpa systemJpa) {
		this.systemJpa = systemJpa;
	}

	public Systems save(final Systems sys) {
		return systemJpa.save(sys);
	}

	public Iterable<Systems> findAll() {
		return systemJpa.findAll();
	}

	public void deleteByVimOrigin(final UUID id) {
		systemJpa.deleteByVimOrigin(id);
	}

	public List<Systems> findByIdAndSubSystemsModuleName(final UUID id, final String moduleName) {
		return systemJpa.findByIdAndSubSystemsModuleName(id, moduleName);
	}
}
