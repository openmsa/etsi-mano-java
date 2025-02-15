package com.ubiqube.etsi.mano.nfvo.service.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ubiqube.etsi.mano.dao.mano.alarm.AlarmNotification;
import com.ubiqube.etsi.mano.nfvo.jpa.AlarmNotificationJpa;

class AlarmNotificationRepositoryServiceTest {

	@Mock
	private AlarmNotificationJpa alarmNotificationJpa;

	@InjectMocks
	private AlarmNotificationRepositoryService alarmNotificationRepositoryService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testSave() {
		AlarmNotification alarmNotification = new AlarmNotification();
		when(alarmNotificationJpa.save(alarmNotification)).thenReturn(alarmNotification);

		AlarmNotification result = alarmNotificationRepositoryService.save(alarmNotification);

		assertNotNull(result);
		verify(alarmNotificationJpa, times(1)).save(alarmNotification);
	}
}
