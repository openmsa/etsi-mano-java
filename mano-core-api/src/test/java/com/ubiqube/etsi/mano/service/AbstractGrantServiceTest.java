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
package com.ubiqube.etsi.mano.service;

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

import com.ubiqube.etsi.mano.dao.mano.AccessInfo;
import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.GrantInformationExt;
import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.InterfaceInfo;
import com.ubiqube.etsi.mano.dao.mano.vim.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.orchestrator.entities.SystemConnections;
import com.ubiqube.etsi.mano.service.mapping.BlueZoneGroupInformationMapping;
import com.ubiqube.etsi.mano.service.mapping.GrantInformationExtMapping;
import com.ubiqube.etsi.mano.service.mapping.GrantMapper;
import com.ubiqube.etsi.mano.service.repository.ConnectionInformationRepositoryService;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.service.vim.VimTypeConverter;

@ExtendWith(MockitoExtension.class)
class AbstractGrantServiceTest {
	@Mock
	private ResourceAllocate nfvo;
	@Mock
	private VimManager vimManager;
	@Mock
	private ConnectionInformationRepositoryService connectionJpa;
	@Mock
	private GrantMapper vnfGrantMapper;
	@Mock
	private VimTypeConverter vimTypeConverter;
	@Mock
	SystemService systemService;
	private final GrantInformationExtMapping grantInformationExtMapping = Mappers.getMapper(GrantInformationExtMapping.class);
	private final BlueZoneGroupInformationMapping blueZoneGroupInformationMapping = Mappers.getMapper(BlueZoneGroupInformationMapping.class);

	public AbstractGrantServiceTest() {
		//
	}

	@Test
	void testBasic() {
		final TestAbstractGrantService srv = createService();
		final TestBluePrint bp = new TestBluePrint();
		bp.setTasks(Set.of());
		final GrantResponse response = new GrantResponse();
		final UUID id = UUID.randomUUID();
		response.setId(id);
		response.setZoneGroups(Set.of());
		response.setZones(Set.of());
		response.setExtManagedVirtualLinks(Set.of());
		final VimConnectionInformation vim01 = new VimConnectionInformation();
		final Set<VimConnectionInformation> s = Set.of(vim01);
		bp.setVimConnections(s);
		when(nfvo.sendSyncGrantRequest(any())).thenReturn(response);
		srv.allocate(bp);
		assertTrue(true);
	}

	private TestAbstractGrantService createService() {
		return new TestAbstractGrantService(nfvo, vimManager, connectionJpa, blueZoneGroupInformationMapping, vnfGrantMapper, grantInformationExtMapping, systemService);
	}

	/**
	 * Pre grant request.
	 *
	 * @throws Exception
	 */
	@Test
	void testOneTask() {
		final TestAbstractGrantService srv = createService();
		final TestBluePrint bp = new TestBluePrint();
		//
		final TestTask task = new TestTask(ResourceTypeEnum.NETWORK);
		task.setChangeType(ChangeType.ADDED);
		//
		final TestTask task2 = new TestTask(ResourceTypeEnum.COMPUTE);
		task2.setChangeType(ChangeType.REMOVED);
		task2.setVimResourceId("");
		task2.setVimConnectionId("");
		bp.setTasks(Set.of(task, task2));
		//
		final GrantResponse response = new GrantResponse();
		final UUID id = UUID.randomUUID();
		response.setId(id);
		response.setZoneGroups(Set.of());
		response.setZones(Set.of());
		response.setExtManagedVirtualLinks(Set.of());
		final VimConnectionInformation vim01 = new VimConnectionInformation();
		vim01.setVimId("CDE");
		vim01.setVimType("TYPE");
		final Set<VimConnectionInformation> s = Set.of(vim01);
		bp.setVimConnections(s);
		//
		final SystemConnections sc = new SystemConnections<InterfaceInfo, AccessInfo>();
		sc.setVimType("TYPE");
		final List<SystemConnections> lst = List.of(sc);
		when(systemService.findByModuleName(any())).thenReturn(lst);
		when(vnfGrantMapper.mapToGrantResponse(bp)).thenReturn(response);
		when(nfvo.sendSyncGrantRequest(any())).thenReturn(response);
		when(vimManager.registerIfNeeded(vim01)).thenReturn(vim01);
		srv.allocate(bp);
		assertTrue(true);
	}

	@Test
	void testPostRequest() {
		final TestAbstractGrantService srv = createService();
		final UUID tid = UUID.randomUUID();
		final TestBluePrint bp = new TestBluePrint();
		bp.setTasks(Set.of());
		final GrantResponse response = new GrantResponse();
		final GrantInformationExt gie01 = new GrantInformationExt();
		gie01.setResourceDefinitionId(tid.toString());
		final LinkedHashSet<GrantInformationExt> set = new LinkedHashSet<>();
		set.add(gie01);
		response.setAddResources(set);
		final UUID id = UUID.randomUUID();
		response.setId(id);
		response.setZoneGroups(Set.of());
		response.setZones(Set.of());
		response.setExtManagedVirtualLinks(Set.of());
		final TestTask task = new TestTask(ResourceTypeEnum.COMPUTE);
		task.setChangeType(ChangeType.ADDED);
		task.setId(tid);
		bp.setTasks(Set.of(task));
		final VimConnectionInformation vim01 = new VimConnectionInformation();
		vim01.setVimType("TYPE");
		vim01.setVimId("ABC");
		final Set<VimConnectionInformation> s = Set.of(vim01);
		bp.setVimConnections(s);
		when(vnfGrantMapper.mapToGrantResponse(bp)).thenReturn(response);
		final SystemConnections sc = new SystemConnections<InterfaceInfo, AccessInfo>();
		sc.setVimType("TYPE");
		final List<SystemConnections> lst = List.of(sc);
		when(systemService.findByModuleName(any())).thenReturn(lst);
		final GrantResponse response2 = new GrantResponse();
		response2.setId(id);
		response2.setZoneGroups(Set.of());
		response2.setAddResources(Set.of(gie01));
		response2.setVimConnections(Set.of(vim01));
		response2.setZones(Set.of());
		response2.setExtManagedVirtualLinks(Set.of());
		when(nfvo.sendSyncGrantRequest(any())).thenReturn(response2);
		when(vimManager.registerIfNeeded(vim01)).thenReturn(vim01);
		srv.allocate(bp);
		assertTrue(true);
	}

}
