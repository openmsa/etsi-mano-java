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
package com.ubiqube.etsi.mano.vnfm.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.ExtManagedVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.VnfLinkPort;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfPortTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.orchestrator.entities.SystemConnections;
import com.ubiqube.etsi.mano.service.SystemService;
import com.ubiqube.etsi.mano.service.mapping.BlueZoneGroupInformationMapping;
import com.ubiqube.etsi.mano.service.mapping.GrantInformationExtMapping;
import com.ubiqube.etsi.mano.service.mapping.GrantMapper;
import com.ubiqube.etsi.mano.service.repository.ConnectionInformationRepositoryService;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.test.controllers.TestFactory;

@ExtendWith(MockitoExtension.class)
class ManoGrantServiceTest {
	@Mock
	private VnfResourceAllocate nfvo;
	@Mock
	private VimManager vimManager;
	@Mock
	private ConnectionInformationRepositoryService connectionJpa;
	@Mock
	private GrantMapper vnfGrantMapper;
	@Mock
	private GrantInformationExtMapping grantInformationExtMapping;
	@Mock
	private SystemService systemService;

	private final BlueZoneGroupInformationMapping blueZoneGroupInformationMapping = Mappers.getMapper(BlueZoneGroupInformationMapping.class);

	@Test
	void test() {
		final ManoGrantService srv = createServer();
		final VnfBlueprint bp = TestFactory.createBlueprint();
		bp.setTasks(Set.of());
		final GrantResponse response = new GrantResponse();
		final UUID id = UUID.randomUUID();
		response.setId(id);
		response.setZoneGroups(Set.of());
		response.setZones(Set.of());
		response.setExtManagedVirtualLinks(Set.of());
		final VimConnectionInformation vim01 = new VimConnectionInformation();
		vim01.setVimType("TYPE");
		vim01.setVimId("ID");
		bp.setVimConnections(Set.of(vim01));
		final VnfPortTask ts1 = new VnfPortTask();
		final VnfLinkPort link = new VnfLinkPort();
		link.setVirtualLink("virtual_link");
		ts1.setVnfLinkPort(link);
		bp.setTasks(Set.of(ts1));
		when(nfvo.sendSyncGrantRequest(any())).thenReturn(response);
		final SystemConnections sc = new SystemConnections<>();
		sc.setVimType("TYPE");
		final List<SystemConnections> lst = List.of(sc);
		when(systemService.findByModuleName(any())).thenReturn(lst);
		when(vimManager.registerIfNeeded(any())).thenReturn(vim01);
		srv.allocate(bp);
		assertTrue(true);
	}

	private ManoGrantService createServer() {
		return new ManoGrantService(nfvo, vimManager, connectionJpa, blueZoneGroupInformationMapping, vnfGrantMapper, grantInformationExtMapping, systemService);
	}

	@Test
	void test2() {
		final ManoGrantService srv = createServer();
		final VnfBlueprint bp = TestFactory.createBlueprint();
		bp.setTasks(Set.of());
		final GrantResponse response = new GrantResponse();
		final UUID id = UUID.randomUUID();
		response.setId(id);
		response.setZoneGroups(Set.of());
		response.setZones(Set.of());
		response.setExtManagedVirtualLinks(Set.of());
		final VimConnectionInformation vim01 = new VimConnectionInformation();
		vim01.setVimType("TYPE");
		vim01.setVimId("ID");
		bp.setVimConnections(Set.of(vim01));
		final VnfPortTask ts1 = new VnfPortTask();
		final VnfLinkPort link = new VnfLinkPort();
		link.setVirtualLink("virtual_link");
		ts1.setVnfLinkPort(link);
		bp.setTasks(Set.of(ts1));
		final ExtManagedVirtualLinkDataEntity ext = new ExtManagedVirtualLinkDataEntity();
		ext.setVnfVirtualLinkDescId("virtual_link");
		bp.getParameters().setExtManagedVirtualLinks(new LinkedHashSet<>());
		bp.getParameters().getExtManagedVirtualLinks().add(ext);
		when(nfvo.sendSyncGrantRequest(any())).thenReturn(response);
		final SystemConnections sc = new SystemConnections<>();
		sc.setVimType("TYPE");
		final List<SystemConnections> lst = List.of(sc);
		when(systemService.findByModuleName(any())).thenReturn(lst);
		when(vimManager.registerIfNeeded(any())).thenReturn(vim01);
		srv.allocate(bp);
		assertTrue(true);
	}

}
