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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.service.grant.ccm;

import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.service.CapiServerService;
import com.ubiqube.etsi.mano.vnfm.jpa.K8sServerInfoJpa;

public class NoopCcm implements CcmManager {
	private final CapiServerService capiServerService;
	private final K8sServerInfoJpa k8sServerInfoJpa;

	public NoopCcm(final CapiServerService capiServerService, final K8sServerInfoJpa k8sServerInfoJpa) {
		this.capiServerService = capiServerService;
		this.k8sServerInfoJpa = k8sServerInfoJpa;
	}

	@Override
	public VimConnectionInformation getVimConnection(final GrantResponse grants, final VnfPackage vnfPackage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getTerminateCluster(final String vnfInstanceId) {
		// TODO Auto-generated method stub

	}

}
