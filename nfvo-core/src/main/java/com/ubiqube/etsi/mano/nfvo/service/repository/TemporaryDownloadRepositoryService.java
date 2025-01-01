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

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.TemporaryDownload;
import com.ubiqube.etsi.mano.nfvo.jpa.TemporaryDownloadJpa;

@Service
public class TemporaryDownloadRepositoryService {
	private final TemporaryDownloadJpa temporaryJpa;

	TemporaryDownloadRepositoryService(final TemporaryDownloadJpa temporaryJpa) {
		this.temporaryJpa = temporaryJpa;
	}

	public TemporaryDownload save(final TemporaryDownload td) {
		return temporaryJpa.save(td);
	}

	public Optional<TemporaryDownload> findByIdAndExpirationDateAfter(final String id, final Date date) {
		return temporaryJpa.findByIdAndExpirationDateAfter(id, date);
	}
}
