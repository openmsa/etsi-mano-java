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
