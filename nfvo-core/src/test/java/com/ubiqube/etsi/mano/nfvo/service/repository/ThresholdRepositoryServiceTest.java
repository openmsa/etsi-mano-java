package com.ubiqube.etsi.mano.nfvo.service.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ubiqube.etsi.mano.dao.mano.pm.Threshold;
import com.ubiqube.etsi.mano.jpa.ThresholdJpa;

public class ThresholdRepositoryServiceTest {

    @Mock
    private ThresholdJpa thresholdJpa;

    @InjectMocks
    private ThresholdRepositoryService thresholdRepositoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        Threshold threshold = new Threshold();
        when(thresholdJpa.save(threshold)).thenReturn(threshold);

        Threshold result = thresholdRepositoryService.save(threshold);

        assertEquals(threshold, result);
        verify(thresholdJpa).save(threshold);
    }

    @Test
    void testDeleteById() {
        UUID id = UUID.randomUUID();

        thresholdRepositoryService.deleteById(id);

        verify(thresholdJpa).deleteById(id);
    }

    @Test
    void testFindById() {
        UUID id = UUID.randomUUID();
        Threshold threshold = new Threshold();
        when(thresholdJpa.findById(id)).thenReturn(Optional.of(threshold));

        Optional<Threshold> result = thresholdRepositoryService.findById(id);

        assertEquals(Optional.of(threshold), result);
        verify(thresholdJpa).findById(id);
    }

    @Test
    void testFindByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(thresholdJpa.findById(id)).thenReturn(Optional.empty());

        Optional<Threshold> result = thresholdRepositoryService.findById(id);

        assertFalse(result.isPresent());
        verify(thresholdJpa).findById(id);
    }
}
