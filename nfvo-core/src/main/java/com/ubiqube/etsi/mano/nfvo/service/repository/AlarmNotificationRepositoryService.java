package com.ubiqube.etsi.mano.nfvo.service.repository;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.alarm.AlarmNotification;
import com.ubiqube.etsi.mano.nfvo.jpa.AlarmNotificationJpa;

@Service
public class AlarmNotificationRepositoryService {
	private final AlarmNotificationJpa vnfFmJpa;

	AlarmNotificationRepositoryService(final AlarmNotificationJpa vnfFmJpa) {
		this.vnfFmJpa = vnfFmJpa;
	}

	public AlarmNotification save(final AlarmNotification event) {
		return vnfFmJpa.save(event);
	}
}
