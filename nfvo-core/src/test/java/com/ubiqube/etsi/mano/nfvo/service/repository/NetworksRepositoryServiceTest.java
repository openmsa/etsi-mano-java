package com.ubiqube.etsi.mano.nfvo.service.repository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ubiqube.etsi.mano.dao.mano.ipam.Networks;
import com.ubiqube.etsi.mano.nfvo.jpa.ipam.NetworksJpa;

public class NetworksRepositoryServiceTest {

    @Mock
    private NetworksJpa networksJpa;

    @InjectMocks
    private NetworksRepositoryService networksRepositoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindFirstFreeNetwork() {
        UUID id = UUID.randomUUID();
        Networks network = new Networks();
        when(networksJpa.findFirstFreeNetwork(id)).thenReturn(Optional.of(network));

        Optional<Networks> result = networksRepositoryService.findFirstFreeNetwork(id);

        assertTrue(result.isPresent());
        assertEquals(network, result.get());
    }

    @Test
    void testSave() {
        Networks network = new Networks();
        networksRepositoryService.save(network);

        verify(networksJpa, times(1)).save(network);
    }
}
