package com.ubiqube.etsi.mano.nfvo.service.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ubiqube.etsi.mano.dao.mano.pm.ThresholdCrossedNotification;
import com.ubiqube.etsi.mano.nfvo.jpa.ThresholdCrossedNotificationJpa;

class ThresholdCrossedNotificationRepositoryServiceTest {

	@Mock
	private ThresholdCrossedNotificationJpa vnfPmJpa;

	@InjectMocks
	private ThresholdCrossedNotificationRepositoryService service;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testSave() {
		ThresholdCrossedNotification event = new ThresholdCrossedNotification();
		when(vnfPmJpa.save(event)).thenReturn(event);

		ThresholdCrossedNotification result = service.save(event);

		assertNotNull(result);
		assertEquals(event, result);
		verify(vnfPmJpa, times(1)).save(event);
	}
}
