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
