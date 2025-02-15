package com.ubiqube.etsi.mano.service.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ubiqube.etsi.mano.jpa.SystemsJpa;
import com.ubiqube.etsi.mano.orchestrator.entities.Systems;

class SystemsRepositoryServiceTest {

	@Mock
	private SystemsJpa systemsJpa;

	@InjectMocks
	private SystemsRepositoryService systemsRepositoryService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testSave() {
		Systems system = new Systems();
		when(systemsJpa.save(system)).thenReturn(system);

		Systems result = systemsRepositoryService.save(system);

		assertEquals(system, result);
		verify(systemsJpa, times(1)).save(system);
	}

	@Test
	void testFindAll() {
		Systems system = new Systems();
		when(systemsJpa.findAll()).thenReturn(Collections.singletonList(system));

		Iterable<Systems> result = systemsRepositoryService.findAll();

		assertNotNull(result);
		assertTrue(result.iterator().hasNext());
		assertEquals(system, result.iterator().next());
		verify(systemsJpa, times(1)).findAll();
	}

	@Test
	void testDeleteByVimOrigin() {
		UUID id = UUID.randomUUID();

		systemsRepositoryService.deleteByVimOrigin(id);

		verify(systemsJpa, times(1)).deleteByVimOrigin(id);
	}

	@Test
	void testFindByIdAndSubSystemsModuleName() {
		UUID id = UUID.randomUUID();
		String moduleName = "moduleName";
		Systems system = new Systems();
		when(systemsJpa.findByIdAndSubSystemsModuleName(id, moduleName)).thenReturn(Collections.singletonList(system));

		List<Systems> result = systemsRepositoryService.findByIdAndSubSystemsModuleName(id, moduleName);

		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals(system, result.get(0));
		verify(systemsJpa, times(1)).findByIdAndSubSystemsModuleName(id, moduleName);
	}
}
