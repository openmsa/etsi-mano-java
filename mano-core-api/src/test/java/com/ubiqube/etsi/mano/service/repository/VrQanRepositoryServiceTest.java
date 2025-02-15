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
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ubiqube.etsi.mano.dao.mano.vrqan.VrQan;
import com.ubiqube.etsi.mano.jpa.VrQanJpa;

class VrQanRepositoryServiceTest {

	@Mock
	private VrQanJpa vrQanJpa;

	@InjectMocks
	private VrQanRepositoryService vrQanRepositoryService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindByVimId() {
		UUID id = UUID.randomUUID();
		VrQan vrQan = new VrQan();
		when(vrQanJpa.findById(id)).thenReturn(Optional.of(vrQan));

		Optional<VrQan> result = vrQanRepositoryService.findByVimId(id);

		assertTrue(result.isPresent());
		assertEquals(vrQan, result.get());
	}

	@Test
	void testSave() {
		VrQan vrQan = new VrQan();
		when(vrQanJpa.save(vrQan)).thenReturn(vrQan);

		VrQan result = vrQanRepositoryService.save(vrQan);

		assertNotNull(result);
		assertEquals(vrQan, result);
	}
}
