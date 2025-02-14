package com.ubiqube.etsi.mano.nfvo.service.repository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;

class NsLiveInstanceRepositoryServiceTest {

    @Mock
    private NsLiveInstanceJpa nsLiveInstanceJpa;

    @InjectMocks
    private NsLiveInstanceRepositoryService nsLiveInstanceRepositoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByNsInstanceAndNsTaskToscaNameAndNsTaskClassGroupByNsTaskAlias() {
        NsdInstance nsInstance = new NsdInstance();
        String toscaName = "toscaName";
        Class<?> class1 = Object.class;
        List<NsLiveInstance> expectedList = List.of(new NsLiveInstance());

        when(nsLiveInstanceJpa.findByNsInstanceAndNsTaskToscaNameAndNsTaskClassGroupByNsTaskAlias(nsInstance, toscaName, class1))
            .thenReturn(expectedList);

        List<NsLiveInstance> result = nsLiveInstanceRepositoryService.findByNsInstanceAndNsTaskToscaNameAndNsTaskClassGroupByNsTaskAlias(nsInstance, toscaName, class1);

        assertEquals(expectedList, result);
        verify(nsLiveInstanceJpa).findByNsInstanceAndNsTaskToscaNameAndNsTaskClassGroupByNsTaskAlias(nsInstance, toscaName, class1);
    }

    @Test
    void testFindByNsInstanceId() {
        UUID nsUuid = UUID.randomUUID();
        List<NsLiveInstance> expectedList = List.of(new NsLiveInstance());

        when(nsLiveInstanceJpa.findByNsInstanceId(nsUuid)).thenReturn(expectedList);

        List<NsLiveInstance> result = nsLiveInstanceRepositoryService.findByNsInstanceId(nsUuid);

        assertEquals(expectedList, result);
        verify(nsLiveInstanceJpa).findByNsInstanceId(nsUuid);
    }
}
