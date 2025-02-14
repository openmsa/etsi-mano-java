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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.AccessInfo;
import com.ubiqube.etsi.mano.dao.mano.InterfaceInfo;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.orchestrator.entities.SystemConnections;
import com.ubiqube.etsi.mano.orchestrator.nodes.contrail.PtLinkNode;
import com.ubiqube.etsi.mano.service.mapping.SystemConnectionsMapping;
import com.ubiqube.etsi.mano.service.repository.SystemConnectionsRepositoryService;
import com.ubiqube.etsi.mano.service.repository.SystemsRepositoryService;
import com.ubiqube.etsi.mano.vnfm.service.graph.ResourceHolder;

@ExtendWith(MockitoExtension.class)
class SystemServiceTest {
	private final SystemConnectionsMapping systemConnectionsMapping = Mappers.getMapper(SystemConnectionsMapping.class);
	@Mock
	private SystemsRepositoryService systemJpa;
	@Mock
	private Patcher patcher;
	@Mock
	private SystemConnectionsRepositoryService connectionJpa;
	@Mock
	private ResourceTypeConverter resourceTypeConverter;

	@Test
	void testName() throws Exception {
		final UUID vimId = UUID.randomUUID();
		final SystemService ss = new SystemService(systemJpa, patcher, connectionJpa, systemConnectionsMapping, resourceTypeConverter);
		final SystemConnections sc = new SystemConnections();
		sc.setVimId(vimId.toString());
		sc.setVimType("OPENSTACK_V3");
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		vimConn.setVimId(vimId.toString());
		vimConn.setVimType("OPENSTACK_V3");
		final InterfaceInfo ii = new InterfaceInfo();
		ii.setSdnEndpoint("http://");
		vimConn.setInterfaceInfo(ii);
		ss.registerVim(vimConn);
		assertTrue(true);
	}

	@Test
	void testRegisterVimFailed() {
		final SystemService ss = new SystemService(systemJpa, patcher, connectionJpa, systemConnectionsMapping, resourceTypeConverter);
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		assertThrows(GenericException.class, () -> ss.registerVim(vimConn));
	}

	@Test
	void testCism() {
		final UUID vimId = UUID.randomUUID();
		final SystemService ss = new SystemService(systemJpa, patcher, connectionJpa, systemConnectionsMapping, resourceTypeConverter);
		final SystemConnections sc = new SystemConnections();
		sc.setVimId(vimId.toString());
		sc.setVimType("UBINFV.CISM.V_1");
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		vimConn.setVimId(vimId.toString());
		vimConn.setVimType("UBINFV.CISM.V_1");
		final InterfaceInfo ii = new InterfaceInfo();
		ii.setSdnEndpoint("http://");
		vimConn.setInterfaceInfo(ii);
		ss.registerVim(vimConn);
		assertTrue(true);
	}

	@Test
	void testFindAll() {
		final SystemService ss = new SystemService(systemJpa, patcher, connectionJpa, systemConnectionsMapping, resourceTypeConverter);
		ss.findAll();
		assertTrue(true);
	}

	@Test
	void testDeleteByVimOrigin() {
		final SystemService ss = new SystemService(systemJpa, patcher, connectionJpa, systemConnectionsMapping, resourceTypeConverter);
		ss.deleteByVimOrigin(UUID.randomUUID());
		assertTrue(true);
	}

	@Test
	void testFindByModuleName() {
		final SystemService ss = new SystemService(systemJpa, patcher, connectionJpa, systemConnectionsMapping, resourceTypeConverter);
		ss.findByModuleName(UUID.randomUUID(), "test");
		assertTrue(true);
	}

	@Test
	void testPatchModule() {
		final SystemService ss = new SystemService(systemJpa, patcher, connectionJpa, systemConnectionsMapping, resourceTypeConverter);
		UUID id = UUID.randomUUID();
		SystemConnections<InterfaceInfo, AccessInfo> conn = new SystemConnections<>();
		when(connectionJpa.findById(id)).thenReturn(Optional.of(conn));
		ss.patchModule(id, "test");
		assertTrue(true);
	}

	@Test
	void testFindByModuleNameResource() {
		final SystemService ss = new SystemService(systemJpa, patcher, connectionJpa, systemConnectionsMapping, resourceTypeConverter);
		ResourceHolder resourceHolder = mock(ResourceHolder.class);
		when(resourceTypeConverter.toResourceHolder(any())).thenReturn(Optional.of(resourceHolder));
		when(resourceHolder.node()).thenReturn(PtLinkNode.class);
		ss.findByModuleName(ResourceTypeEnum.COMPUTE);
		assertTrue(true);
	}

	@Test
	void testFindByModuleNameResourceFail() {
		final SystemService ss = new SystemService(systemJpa, patcher, connectionJpa, systemConnectionsMapping, resourceTypeConverter);
		assertThrows(GenericException.class, () -> ss.findByModuleName(ResourceTypeEnum.COMPUTE));
	}
}
