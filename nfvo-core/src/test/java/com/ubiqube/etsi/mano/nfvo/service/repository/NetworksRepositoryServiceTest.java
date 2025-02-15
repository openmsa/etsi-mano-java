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
package com.ubiqube.etsi.mano.nfvo.service.repository;

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

import com.ubiqube.etsi.mano.dao.mano.ipam.Networks;
import com.ubiqube.etsi.mano.nfvo.jpa.ipam.NetworksJpa;

class NetworksRepositoryServiceTest {

	@Mock
	private NetworksJpa networksJpa;

	@InjectMocks
	private NetworksRepositoryService networksRepositoryService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindFirstFreeNetwork() {
		UUID id = UUID.randomUUID();
		Networks network = new Networks();
		when(networksJpa.findFirstFreeNetwork(id)).thenReturn(Optional.of(network));

		Optional<Networks> result = networksRepositoryService.findFirstFreeNetwork(id);

		assertTrue(result.isPresent());
		assertEquals(network, result.get());
	}

	@Test
	void testSave() {
		Networks network = new Networks();
		networksRepositoryService.save(network);

		verify(networksJpa, times(1)).save(network);
	}
}
