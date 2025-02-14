package com.ubiqube.etsi.mano.nfvo.service.repository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.UUID;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.nfvo.jpa.NsVnfPackageJpa;

class NsVnfPackageRepositoryServiceTest {

    @Mock
    private NsVnfPackageJpa vnfPackageJpa;

    @InjectMocks
    private NsVnfPackageRepositoryService nsVnfPackageRepositoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByNsdPackages_NsdPackage_Id() {
        UUID id = UUID.randomUUID();
        Set<VnfPackage> expectedPackages = new HashSet<>();
        when(vnfPackageJpa.findByNsdPackages_NsdPackage_Id(id)).thenReturn(expectedPackages);

        Set<VnfPackage> actualPackages = nsVnfPackageRepositoryService.findByNsdPackages_NsdPackage_Id(id);

        assertEquals(expectedPackages, actualPackages);
        verify(vnfPackageJpa, times(1)).findByNsdPackages_NsdPackage_Id(id);
    }
}
