package com.ubiqube.etsi.mano.nfvo.service.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ubiqube.etsi.mano.dao.mano.vnflcm.VnfLcmNotification;
import com.ubiqube.etsi.mano.nfvo.jpa.VnfLcmNotificationJpa;

class VnfLcmNotificationRepositoryServiceTest {

	@Mock
	private VnfLcmNotificationJpa vnfLcmJpa;

	@InjectMocks
	private VnfLcmNotificationRepositoryService vnfLcmNotificationRepositoryService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testSave() {
		VnfLcmNotification event = new VnfLcmNotification();
		when(vnfLcmJpa.save(event)).thenReturn(event);

		VnfLcmNotification result = vnfLcmNotificationRepositoryService.save(event);

		verify(vnfLcmJpa).save(event);
		assertEquals(event, result);
	}
}
