/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.SoftwareImage;
import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.k8s.K8sServers;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.HelmTask;
import com.ubiqube.etsi.mano.dao.mano.vnfm.McIops;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.vim.k8s.K8sClient;
import com.ubiqube.etsi.mano.vnfm.jpa.K8sServerInfoJpa;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow.HelmDeployUow;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.HelmVt;

@ExtendWith(MockitoExtension.class)
class HelmDeployUowTest {
	@Mock
	private K8sClient client;
	@Mock
	private K8sServerInfoJpa serverInfoJpa;
	@Mock
	private VnfPackageRepository vnfRepo;
	private final String manoKey = "manoKey";
	@Mock
	private Context3d ctx;
	@Mock
	private ManoResource manoRes;

	@Test
	void test() {
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final HelmTask nt = new HelmTask();
		final McIops mciop = new McIops();
		final SoftwareImage si = new SoftwareImage();
		si.setImagePath("path");
		mciop.setArtifacts(Map.of("img", si));
		nt.setMciop(mciop);
		final VirtualTaskV3<HelmTask> task = new HelmVt(nt);
		assertNotNull(task.getType());
		final HelmDeployUow uow = new HelmDeployUow(task, client, serverInfoJpa, vnfRepo, manoKey);
		final K8sServers k8sServer = new K8sServers();
		when(ctx.get(any(), any())).thenReturn("e4950a2a-cd80-11ed-80f9-c8f750509d3b");
		when(serverInfoJpa.findById(any())).thenReturn(Optional.of(k8sServer));
		when(vnfRepo.getBinary(any(), any())).thenReturn(manoRes);
		when(manoRes.getInputStream()).thenReturn(InputStream.nullInputStream());
		uow.execute(ctx);
		assertTrue(true);
	}

	@Test
	void test_TransfertFailre() throws IOException {
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final HelmTask nt = new HelmTask();
		final McIops mciop = new McIops();
		final SoftwareImage si = new SoftwareImage();
		si.setImagePath("path");
		mciop.setArtifacts(Map.of("img", si));
		nt.setMciop(mciop);
		final VirtualTaskV3<HelmTask> task = new HelmVt(nt);
		final HelmDeployUow uow = new HelmDeployUow(task, client, serverInfoJpa, vnfRepo, manoKey);
		final K8sServers k8sServer = new K8sServers();
		when(ctx.get(any(), any())).thenReturn("e4950a2a-cd80-11ed-80f9-c8f750509d3b");
		when(serverInfoJpa.findById(any())).thenReturn(Optional.of(k8sServer));
		when(vnfRepo.getBinary(any(), any())).thenReturn(manoRes);
		final InputStream is = Mockito.mock(InputStream.class);
		when(manoRes.getInputStream()).thenReturn(is);
		when(is.transferTo(any())).thenThrow(IOException.class);
		assertThrows(GenericException.class, () -> uow.execute(ctx));
	}

	@Test
	void test_ServerNotFound() {
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final HelmTask nt = new HelmTask();
		final McIops mciop = new McIops();
		final SoftwareImage si = new SoftwareImage();
		si.setImagePath("path");
		mciop.setArtifacts(Map.of("img", si));
		nt.setMciop(mciop);
		final VirtualTaskV3<HelmTask> task = new HelmVt(nt);
		final HelmDeployUow uow = new HelmDeployUow(task, client, serverInfoJpa, vnfRepo, manoKey);
		final K8sServers k8sServer = new K8sServers();
		when(ctx.get(any(), any())).thenReturn("e4950a2a-cd80-11ed-80f9-c8f750509d3b");
		assertThrows(GenericException.class, () -> uow.execute(ctx));
	}

	@Test
	void testRollback() {
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final HelmTask nt = new HelmTask();
		final VirtualTaskV3<HelmTask> task = new HelmVt(nt);
		final HelmDeployUow uow = new HelmDeployUow(task, client, serverInfoJpa, vnfRepo, manoKey);
		uow.rollback(null);
		assertTrue(true);
	}
}
