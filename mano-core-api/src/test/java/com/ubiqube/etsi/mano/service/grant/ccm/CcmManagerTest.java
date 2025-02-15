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
package com.ubiqube.etsi.mano.service.grant.ccm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.vim.k8s.conn.CertificateAuthInfo;
import com.ubiqube.etsi.mano.vim.k8s.conn.K8s;
import com.ubiqube.etsi.mano.vnfm.jpa.K8sServerInfoJpa;

class CcmManagerTest {

	@Mock
	private K8sServerInfoJpa k8sServerInfoJpa;

	@Mock
	private CcmServerService ccmServerService;

	@InjectMocks
	private CcmManager ccmManager;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetVimConnection() {
		VimConnectionInformation vimInfo = new VimConnectionInformation();
		GrantResponse grants = new GrantResponse();
		grants.setVnfInstanceId(UUID.randomUUID().toString());
		VnfPackage vnfPackage = new VnfPackage();

		K8s k8s = new K8s();
		k8s.setApiUrl("https://api.example.com");
		k8s.setCaData("caData");
		CertificateAuthInfo cai = new CertificateAuthInfo();
		k8s.setCertificateAuthInfo(cai);
		when(ccmServerService.createCluster(any(), anyString())).thenReturn(k8s);

		VimConnectionInformation result = ccmManager.getVimConnection(vimInfo, grants, vnfPackage);

		assertNotNull(result);
		assertEquals("https://api.example.com", result.getInterfaceInfo().getEndpoint());
	}

	@Test
	void testGetTerminateCluster() {
		String vnfInstanceId = UUID.randomUUID().toString();
		when(k8sServerInfoJpa.findByVnfInstanceId(any())).thenReturn(Optional.empty());

		ccmManager.getTerminateCluster(vnfInstanceId);

		verify(ccmServerService).terminateCluster(vnfInstanceId);
		verify(k8sServerInfoJpa).findByVnfInstanceId(any());
	}
}
