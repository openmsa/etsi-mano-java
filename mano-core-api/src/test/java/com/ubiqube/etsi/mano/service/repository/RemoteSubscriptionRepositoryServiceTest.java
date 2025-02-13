package com.ubiqube.etsi.mano.service.repository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ubiqube.etsi.mano.dao.subscription.RemoteSubscription;
import com.ubiqube.etsi.mano.jpa.RemoteSubscriptionJpa;

public class RemoteSubscriptionRepositoryServiceTest {

    @Mock
    private RemoteSubscriptionJpa remoteSubscriptionJpa;

    @InjectMocks
    private RemoteSubscriptionRepositoryService remoteSubscriptionRepositoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByRemoteSubscriptionId() {
        String remoteSubscriptionId = "testId";
        RemoteSubscription subscription = new RemoteSubscription();
        List<RemoteSubscription> expectedSubscriptions = Arrays.asList(subscription);

        when(remoteSubscriptionJpa.findByRemoteSubscriptionId(remoteSubscriptionId)).thenReturn(expectedSubscriptions);

        List<RemoteSubscription> actualSubscriptions = remoteSubscriptionRepositoryService.findByRemoteSubscriptionId(remoteSubscriptionId);

        assertEquals(expectedSubscriptions, actualSubscriptions);
        verify(remoteSubscriptionJpa, times(1)).findByRemoteSubscriptionId(remoteSubscriptionId);
    }
}
