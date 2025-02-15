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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionType;
import com.ubiqube.etsi.mano.jpa.ConnectionInformationJpa;

class ConnectionInformationRepositoryServiceTest {

	@Mock
	private ConnectionInformationJpa connectionJpa;

	@InjectMocks
	private ConnectionInformationRepositoryService connectionService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindByConnType() {
		ConnectionType type = ConnectionType.GENERIC;
		ConnectionInformation connInfo = new ConnectionInformation();
		when(connectionJpa.findByConnType(type)).thenReturn(Arrays.asList(connInfo));

		List<ConnectionInformation> result = connectionService.findByConnType(type);
		assertEquals(1, result.size());
		verify(connectionJpa).findByConnType(type);
	}

	@Test
	void testDeleteById() {
		UUID id = UUID.randomUUID();
		doNothing().when(connectionJpa).deleteById(id);

		connectionService.deleteById(id);
		verify(connectionJpa).deleteById(id);
	}

	@Test
	void testFindById() {
		UUID id = UUID.randomUUID();
		ConnectionInformation connInfo = new ConnectionInformation();
		when(connectionJpa.findById(id)).thenReturn(Optional.of(connInfo));

		Optional<ConnectionInformation> result = connectionService.findById(id);
		assertTrue(result.isPresent());
		verify(connectionJpa).findById(id);
	}

	@Test
	void testSave() {
		ConnectionInformation connInfo = new ConnectionInformation();
		when(connectionJpa.save(connInfo)).thenReturn(connInfo);

		ConnectionInformation result = connectionService.save(connInfo);
		assertEquals(connInfo, result);
		verify(connectionJpa).save(connInfo);
	}

	@Test
	void testFindAll() {
		ConnectionInformation connInfo = new ConnectionInformation();
		when(connectionJpa.findAll()).thenReturn(Arrays.asList(connInfo));

		Iterable<ConnectionInformation> result = connectionService.findAll();
		assertNotNull(result);
		verify(connectionJpa).findAll();
	}
}
