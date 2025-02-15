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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ubiqube.etsi.mano.jpa.SysConnectionJpa;
import com.ubiqube.etsi.mano.orchestrator.entities.SystemConnections;

class SystemConnectionsRepositoryServiceTest {

	@Mock
	private SysConnectionJpa systemConnectionsJpa;

	@InjectMocks
	private SystemConnectionsRepositoryService systemConnectionsRepositoryService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {
		UUID id = UUID.randomUUID();
		SystemConnections sc = new SystemConnections();
		when(systemConnectionsJpa.findById(id)).thenReturn(Optional.of(sc));

		Optional<SystemConnections> result = systemConnectionsRepositoryService.findById(id);
		assertTrue(result.isPresent());
		assertEquals(sc, result.get());
	}

	@Test
	void testSave() {
		SystemConnections sc = new SystemConnections();
		when(systemConnectionsJpa.save(sc)).thenReturn(sc);

		SystemConnections result = systemConnectionsRepositoryService.save(sc);
		assertEquals(sc, result);
	}

	@Test
	void testFindByModuleName() {
		String moduleName = "testModule";
		List<SystemConnections> scList = List.of(new SystemConnections());
		when(systemConnectionsJpa.findByModuleName(moduleName)).thenReturn(scList);

		List<SystemConnections> result = systemConnectionsRepositoryService.findByModuleName(moduleName);
		assertEquals(scList, result);
	}
}
