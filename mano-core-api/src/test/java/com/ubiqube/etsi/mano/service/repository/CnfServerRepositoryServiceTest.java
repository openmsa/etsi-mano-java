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

import com.ubiqube.etsi.mano.dao.mano.cnf.CnfServer;
import com.ubiqube.etsi.mano.jpa.CnfServerJpa;

class CnfServerRepositoryServiceTest {

	@Mock
	private CnfServerJpa cnfServerJpa;

	@InjectMocks
	private CnfServerRepositoryService cnfServerRepositoryService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindAll() {
		Iterable<CnfServer> servers = List.of(new CnfServer(), new CnfServer());
		when(cnfServerJpa.findAll()).thenReturn(servers);

		Iterable<CnfServer> result = cnfServerRepositoryService.findAll();
		assertEquals(servers, result);
	}

	@Test
	void testSave() {
		CnfServer server = new CnfServer();
		when(cnfServerJpa.save(server)).thenReturn(server);

		CnfServer result = cnfServerRepositoryService.save(server);
		assertEquals(server, result);
	}

	@Test
	void testFindById() {
		UUID id = UUID.randomUUID();
		CnfServer server = new CnfServer();
		when(cnfServerJpa.findById(id)).thenReturn(Optional.of(server));

		Optional<CnfServer> result = cnfServerRepositoryService.findById(id);
		assertTrue(result.isPresent());
		assertEquals(server, result.get());
	}
}
