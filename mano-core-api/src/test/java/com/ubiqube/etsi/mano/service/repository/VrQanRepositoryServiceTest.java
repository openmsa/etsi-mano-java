package com.ubiqube.etsi.mano.service.repository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ubiqube.etsi.mano.dao.mano.vrqan.VrQan;
import com.ubiqube.etsi.mano.jpa.VrQanJpa;

public class VrQanRepositoryServiceTest {

    @Mock
    private VrQanJpa vrQanJpa;

    @InjectMocks
    private VrQanRepositoryService vrQanRepositoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByVimId() {
        UUID id = UUID.randomUUID();
        VrQan vrQan = new VrQan();
        when(vrQanJpa.findById(id)).thenReturn(Optional.of(vrQan));

        Optional<VrQan> result = vrQanRepositoryService.findByVimId(id);

        assertTrue(result.isPresent());
        assertEquals(vrQan, result.get());
    }

    @Test
    public void testSave() {
        VrQan vrQan = new VrQan();
        when(vrQanJpa.save(vrQan)).thenReturn(vrQan);

        VrQan result = vrQanRepositoryService.save(vrQan);

        assertNotNull(result);
        assertEquals(vrQan, result);
    }
}
