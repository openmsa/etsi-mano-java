/**
 *     Copyright (C) 2019-2024 Ubiqube.
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
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.jpa.GrantsResponseJpa;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class GrantRepositoryService {
	private final GrantsResponseJpa grantsResponseJpa;

	public GrantRepositoryService(final GrantsResponseJpa grantsResponseJpa) {
		super();
		this.grantsResponseJpa = grantsResponseJpa;
	}

	public Iterable<GrantResponse> findAll() {
		return grantsResponseJpa.findAll();
	}

	public void delete(final GrantResponse grantResponse) {
		grantsResponseJpa.delete(grantResponse);
	}

	public GrantResponse save(final GrantResponse grants) {
		return grantsResponseJpa.save(grants);
	}

	public Optional<GrantResponse> findById(final UUID grantId) {
		return grantsResponseJpa.findById(grantId);
	}

}
