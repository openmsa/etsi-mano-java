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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ubiqube.etsi.mano.dao.mano.ai.KeystoneAuthV3;
import com.ubiqube.etsi.mano.dao.mano.cnf.capi.CapiServer;
import com.ubiqube.etsi.mano.dao.mano.ii.OpenstackV3InterfaceInfo;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.ClusterMachine;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.ClusterOptionVersion;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.CnfInformations;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.service.grant.ccm.cni.CniInstaller;
import com.ubiqube.etsi.mano.service.grant.ccm.csi.CsiInstaller;
import com.ubiqube.etsi.mano.service.repository.CapiServerRepositoryService;
import com.ubiqube.etsi.mano.vim.k8s.OsClusterService;
import com.ubiqube.etsi.mano.vim.k8s.conn.K8s;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow.capi.CapiServerMapping;

class CapiCcmServerServiceTest {

	@Mock
	private CapiServerRepositoryService capiServerService;

	@Mock
	private OsClusterService osClusterService;

	@Mock
	private CapiServerMapping mapper;

	@Mock
	private List<CniInstaller> cniInstallers;

	@Mock
	private List<CsiInstaller> csiInstallers;

	@Mock
	private List<CcmInstaller> ccmInstallers;

	@InjectMocks
	private CapiCcmServerService capiCcmServerService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateCluster() {
		VimConnectionInformation vimConn = new VimConnectionInformation();
		CnfInformations cnfInfo = new CnfInformations();
		cnfInfo.setDnsServer("1.1.1.1");
		ClusterMachine master = new ClusterMachine();
		cnfInfo.setMaster(master);
		ClusterMachine worker = new ClusterMachine();
		cnfInfo.setWorker(worker);
		vimConn.setCnfInfo(cnfInfo);
		CapiServer capiServer = mock(CapiServer.class);
		K8s k8s = mock(K8s.class);

		when(capiServerService.findAll()).thenReturn(List.of(capiServer));
		when(mapper.map(capiServer)).thenReturn(k8s);
		when(osClusterService.getKubeConfig(any(), anyString(), anyString())).thenReturn(Optional.of(k8s));

		assertThrows(GenericException.class, () -> capiCcmServerService.createCluster(vimConn, "vnfInstanceId"));
	}

	@Test
	void testCreateCluster002() {
		VimConnectionInformation vimConn = new VimConnectionInformation();
		KeystoneAuthV3 ai = new KeystoneAuthV3(null, null, null, null, null, null, null, null);
		vimConn.setAccessInfo(ai);
		OpenstackV3InterfaceInfo ii = new OpenstackV3InterfaceInfo();
		vimConn.setInterfaceInfo(ii);
		CnfInformations cnfInfo = new CnfInformations();
		cnfInfo.setDnsServer("1.1.1.1");
		ClusterMachine master = new ClusterMachine();
		cnfInfo.setMaster(master);
		ClusterMachine worker = new ClusterMachine();
		cnfInfo.setWorker(worker);
		vimConn.setCnfInfo(cnfInfo);
		CapiServer capiServer = mock(CapiServer.class);
		K8s k8s = new K8s();

		when(capiServerService.findAll()).thenReturn(List.of(capiServer));
		when(mapper.map(capiServer)).thenReturn(k8s);
		when(osClusterService.getKubeConfig(any(), any(), any())).thenReturn(Optional.of(k8s));

		capiCcmServerService.createCluster(vimConn, "vnfInstanceId");

		verify(capiServerService).findAll();
		verify(mapper).map(capiServer);
		verify(osClusterService).createCluster(any(), any(), any());
	}

	@Test
	void testCreateCluster003() {
		VimConnectionInformation vimConn = new VimConnectionInformation();
		UUID id = UUID.randomUUID();
		KeystoneAuthV3 ai = new KeystoneAuthV3(id, "username", "password", "userDomain", "project", "projectDomain", "region", "projectId");
		vimConn.setAccessInfo(ai);
		OpenstackV3InterfaceInfo ii = new OpenstackV3InterfaceInfo();
		vimConn.setInterfaceInfo(ii);
		CnfInformations cnfInfo = new CnfInformations();
		cnfInfo.setDnsServer("1.1.1.1");
		cnfInfo.setCni(new ClusterOptionVersion(id, "module", "1.2.3"));
		cnfInfo.setCsi(new ClusterOptionVersion(id, "module", "1.2.3"));
		cnfInfo.setCcm(new ClusterOptionVersion(id, "module", "1.2.3"));
		ClusterMachine master = new ClusterMachine();
		cnfInfo.setMaster(master);
		ClusterMachine worker = new ClusterMachine();
		cnfInfo.setWorker(worker);
		vimConn.setCnfInfo(cnfInfo);
		CapiServer capiServer = mock(CapiServer.class);
		K8s k8s = new K8s();

		when(capiServerService.findAll()).thenReturn(List.of(capiServer));
		when(mapper.map(capiServer)).thenReturn(k8s);
		when(osClusterService.getKubeConfig(any(), any(), any())).thenReturn(Optional.of(k8s));

		capiCcmServerService.createCluster(vimConn, "vnfInstanceId");

		verify(capiServerService).findAll();
		verify(mapper).map(capiServer);
		verify(osClusterService).createCluster(any(), any(), any());
	}

	@Test
	void testTerminateCluster() {
		capiCcmServerService.terminateCluster("vnfInstanceId");
		// No exception should be thrown
		assertTrue(true);
	}
}
