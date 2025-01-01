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

import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.jpa.VimConnectionInformationJpa;
import com.ubiqube.etsi.mano.service.VimService;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class VimRepositoryService implements VimService {
	private final VimConnectionInformationJpa vimConnectionInformationJpa;

	public VimRepositoryService(final VimConnectionInformationJpa vimConnectionInformationJpa) {
		this.vimConnectionInformationJpa = vimConnectionInformationJpa;
	}

	@Override
	public Optional<VimConnectionInformation> findById(final UUID id) {
		return vimConnectionInformationJpa.findById(id);
	}

	@Override
	public Iterable<VimConnectionInformation> findAll() {
		return vimConnectionInformationJpa.findAll();
	}

	@Override
	public VimConnectionInformation save(final VimConnectionInformation body) {
		return vimConnectionInformationJpa.save(body);
	}

}
