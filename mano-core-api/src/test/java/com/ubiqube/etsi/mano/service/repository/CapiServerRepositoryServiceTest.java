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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ubiqube.etsi.mano.dao.mano.cnf.capi.CapiServer;
import com.ubiqube.etsi.mano.jpa.CapiServerJpa;

class CapiServerRepositoryServiceTest {

	@Mock
	private CapiServerJpa capiServerJpa;

	@InjectMocks
	private CapiServerRepositoryService capiServerRepositoryService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindAll() {
		capiServerRepositoryService.findAll();
		verify(capiServerJpa, times(1)).findAll();
	}

	@Test
	void testSave() {
		CapiServer server = new CapiServer();
		when(capiServerJpa.save(server)).thenReturn(server);
		CapiServer savedServer = capiServerRepositoryService.save(server);
		assertEquals(server, savedServer);
		verify(capiServerJpa, times(1)).save(server);
	}

	@Test
	void testDeleteById() {
		UUID id = UUID.randomUUID();
		capiServerRepositoryService.deleteById(id);
		verify(capiServerJpa, times(1)).deleteById(id);
	}

	@Test
	void testFindById() {
		UUID id = UUID.randomUUID();
		CapiServer server = new CapiServer();
		when(capiServerJpa.findById(id)).thenReturn(Optional.of(server));
		Optional<CapiServer> foundServer = capiServerRepositoryService.findById(id);
		assertTrue(foundServer.isPresent());
		assertEquals(server, foundServer.get());
		verify(capiServerJpa, times(1)).findById(id);
	}
}
