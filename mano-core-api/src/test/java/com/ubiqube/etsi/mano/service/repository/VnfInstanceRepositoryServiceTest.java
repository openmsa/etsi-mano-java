package com.ubiqube.etsi.mano.service.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ubiqube.etsi.mano.jpa.VnfInstanceJpa;

class VnfInstanceRepositoryServiceTest {

	@Mock
	private VnfInstanceJpa vnfInstanceJpa;

	@InjectMocks
	private VnfInstanceRepositoryService vnfInstanceRepositoryService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {
		UUID id = UUID.randomUUID();
		vnfInstanceRepositoryService.findById(id);
		assertTrue(true);
	}
}
