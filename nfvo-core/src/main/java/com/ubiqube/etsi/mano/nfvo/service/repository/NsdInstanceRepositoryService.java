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

import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.nfvo.jpa.NsdInstanceJpa;

@Service
public class NsdInstanceRepositoryService {
	private final NsdInstanceJpa nsdInstanceJpa;

	NsdInstanceRepositoryService(final NsdInstanceJpa nsdInstanceJpa) {
		this.nsdInstanceJpa = nsdInstanceJpa;
	}

	public NsdInstance save(final NsdInstance nsInstance) {
		return nsdInstanceJpa.save(nsInstance);
	}

	public void deleteById(final UUID nsInstanceUuid) {
		nsdInstanceJpa.deleteById(nsInstanceUuid);
	}

	public Optional<NsdInstance> findById(final UUID nsUuid) {
		return nsdInstanceJpa.findById(nsUuid);
	}

	public int countByNsdInfo(final NsdPackage nsPackage) {
		return nsdInstanceJpa.countByNsdInfo(nsPackage);
	}

	public Iterable<NsdInstance> findAll() {
		return nsdInstanceJpa.findAll();
	}
}
