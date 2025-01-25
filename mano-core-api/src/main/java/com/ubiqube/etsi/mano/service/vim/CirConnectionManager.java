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
package com.ubiqube.etsi.mano.service.vim;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.PlanStatusType;
import com.ubiqube.etsi.mano.dao.rfc7807.FailureDetails;
import com.ubiqube.etsi.mano.service.event.CirServerChecker;
import com.ubiqube.etsi.mano.service.repository.ConnectionInformationRepositoryService;

@Service
public class CirConnectionManager {
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(CirConnectionManager.class);
	private final ConnectionInformationRepositoryService cirConnectionInformationJpa;
	private final CirServerChecker cirServerChecker;

	public CirConnectionManager(final ConnectionInformationRepositoryService cirConnectionInformationJpa, final CirServerChecker cirServerChecker) {
		this.cirConnectionInformationJpa = cirConnectionInformationJpa;
		this.cirServerChecker = cirServerChecker;
	}

	public void deleteVim(final UUID id) {
		cirConnectionInformationJpa.deleteById(id);
	}

	public ConnectionInformation findVimById(final UUID id) {
		return cirConnectionInformationJpa.findById(id).orElseThrow();
	}

	public ConnectionInformation save(final ConnectionInformation conn) {
		return cirConnectionInformationJpa.save(conn);
	}

	public Iterable<ConnectionInformation> findAll() {
		return cirConnectionInformationJpa.findAll();
	}

	public ConnectionInformation checkConnectivity(final UUID id) {
		final ConnectionInformation vci = findVimById(id);
		try {
			cirServerChecker.verify(vci);
			vci.setFailureDetails(null);
			vci.setServerStatus(PlanStatusType.SUCCESS);
			return save(vci);
		} catch (final RuntimeException e) {
			LOG.warn("", e);
			vci.setFailureDetails(new FailureDetails(500, e.getMessage()));
			vci.setServerStatus(PlanStatusType.FAILED);
			return save(vci);
		}
	}

}
