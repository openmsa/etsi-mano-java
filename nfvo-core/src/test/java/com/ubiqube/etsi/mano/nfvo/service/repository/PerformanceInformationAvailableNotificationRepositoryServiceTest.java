package com.ubiqube.etsi.mano.nfvo.service.repository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ubiqube.etsi.mano.dao.mano.pm.PerformanceInformationAvailableNotification;
import com.ubiqube.etsi.mano.nfvo.jpa.PerformanceInformationAvailableNotificationJpa;

class PerformanceInformationAvailableNotificationRepositoryServiceTest {

    @Mock
    private PerformanceInformationAvailableNotificationJpa availableJpa;

    @InjectMocks
    private PerformanceInformationAvailableNotificationRepositoryService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        PerformanceInformationAvailableNotification event = new PerformanceInformationAvailableNotification();
        when(availableJpa.save(event)).thenReturn(event);

        service.save(event);

        verify(availableJpa).save(event);
    }
}
