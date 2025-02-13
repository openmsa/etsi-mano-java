package com.ubiqube.etsi.mano.nfvo.service.repository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVirtualLink;
import com.ubiqube.etsi.mano.nfvo.jpa.NsVirtualLinkJpa;

public class NsVirtualLinkRepositoryServiceTest {

    @Mock
    private NsVirtualLinkJpa nsVirtualLinkJpa;

    @InjectMocks
    private NsVirtualLinkRepositoryService nsVirtualLinkRepositoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByNsdPackage() {
        NsdPackage nsdPackage = new NsdPackage();
        Set<NsVirtualLink> expectedLinks = new HashSet<>();
        when(nsVirtualLinkJpa.findByNsdPackage(nsdPackage)).thenReturn(expectedLinks);

        Set<NsVirtualLink> actualLinks = nsVirtualLinkRepositoryService.findByNsdPackage(nsdPackage);

        assertEquals(expectedLinks, actualLinks);
        verify(nsVirtualLinkJpa, times(1)).findByNsdPackage(nsdPackage);
    }
}
