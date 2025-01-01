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

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.vrqan.VrQan;
import com.ubiqube.etsi.mano.jpa.VrQanJpa;

@Service
public class VrQanRepositoryService {
	private final VrQanJpa vrQanJpa;

	public VrQanRepositoryService(final VrQanJpa vrQanJpa) {
		this.vrQanJpa = vrQanJpa;
	}

	public Optional<VrQan> findByVimId(final UUID id) {
		return vrQanJpa.findById(id);
	}

	public VrQan save(final VrQan vq) {
		return vrQanJpa.save(vq);
	}
}
