package com.ubiqube.etsi.mano.service.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.jpa.config.ServersJpa;

class ServersRepositoryServiceTest {

	@Mock
	private ServersJpa serversJpa;

	@InjectMocks
	private ServersRepositoryService serversRepositoryService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindAll() {
		List<Servers> serversList = Arrays.asList(new Servers(), new Servers());
		when(serversJpa.findAll()).thenReturn(serversList);

		List<Servers> result = serversRepositoryService.findAll();
		assertEquals(2, result.size());
		verify(serversJpa, times(1)).findAll();
	}

	@Test
	void testFindById() {
		UUID id = UUID.randomUUID();
		Servers server = new Servers();
		when(serversJpa.findById(id)).thenReturn(Optional.of(server));

		Optional<Servers> result = serversRepositoryService.findById(id);
		assertTrue(result.isPresent());
		assertEquals(server, result.get());
		verify(serversJpa, times(1)).findById(id);
	}

	@Test
	void testFindByUrl() {
		URI url = URI.create("http://example.com");
		Servers server = new Servers();
		when(serversJpa.findByUrl(url)).thenReturn(Optional.of(server));

		Optional<Servers> result = serversRepositoryService.findByUrl(url);
		assertTrue(result.isPresent());
		assertEquals(server, result.get());
		verify(serversJpa, times(1)).findByUrl(url);
	}

	@Test
	void testSave() {
		Servers server = new Servers();
		when(serversJpa.save(server)).thenReturn(server);

		Servers result = serversRepositoryService.save(server);
		assertEquals(server, result);
		verify(serversJpa, times(1)).save(server);
	}

	@Test
	void testDeleteById() {
		UUID id = UUID.randomUUID();
		doNothing().when(serversJpa).deleteById(id);

		serversRepositoryService.deleteById(id);
		verify(serversJpa, times(1)).deleteById(id);
	}

	@Test
	void testFindByServerStatusIn() {
		serversRepositoryService.findByServerStatusIn(List.of());
		assertTrue(true);
	}

	@Test
	void testFindByServerTypeAndServerStatusIn() {
		serversRepositoryService.findByServerTypeAndServerStatusIn(null, null);
		assertTrue(true);
	}
}
