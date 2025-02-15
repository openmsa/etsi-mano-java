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
