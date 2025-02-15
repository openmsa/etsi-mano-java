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

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ubiqube.etsi.mano.dao.mano.config.Configurations;
import com.ubiqube.etsi.mano.jpa.config.ConfigurationsJpa;

class ConfigurationsRepositoryServiceTest {

	@Mock
	private ConfigurationsJpa configurationsJpa;

	@InjectMocks
	private ConfigurationsRepositoryService configurationsRepositoryService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {
		String confClusterId = "testId";
		Configurations config = new Configurations();
		when(configurationsJpa.findById(confClusterId)).thenReturn(Optional.of(config));

		Optional<Configurations> result = configurationsRepositoryService.findById(confClusterId);
		assertTrue(result.isPresent());
		assertEquals(config, result.get());
	}

	@Test
	void testSave() {
		Configurations config = new Configurations();
		when(configurationsJpa.save(config)).thenReturn(config);

		Configurations result = configurationsRepositoryService.save(config);
		assertEquals(config, result);
	}
}
