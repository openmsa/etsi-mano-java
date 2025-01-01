package com.ubiqube.etsi.mano.nfvo.service.repository;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.pm.ThresholdCrossedNotification;
import com.ubiqube.etsi.mano.nfvo.jpa.ThresholdCrossedNotificationJpa;

@Service
public class ThresholdCrossedNotificationRepositoryService {
	private final ThresholdCrossedNotificationJpa vnfPmJpa;

	public ThresholdCrossedNotificationRepositoryService(final ThresholdCrossedNotificationJpa vnfIndJpa) {
		this.vnfPmJpa = vnfIndJpa;
	}

	public ThresholdCrossedNotification save(final ThresholdCrossedNotification event) {
		return vnfPmJpa.save(event);
	}

}
