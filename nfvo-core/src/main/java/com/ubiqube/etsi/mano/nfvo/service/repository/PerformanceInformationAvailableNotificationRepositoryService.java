package com.ubiqube.etsi.mano.nfvo.service.repository;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.pm.PerformanceInformationAvailableNotification;
import com.ubiqube.etsi.mano.nfvo.jpa.PerformanceInformationAvailableNotificationJpa;

@Service
public class PerformanceInformationAvailableNotificationRepositoryService {
	private final PerformanceInformationAvailableNotificationJpa availableJpa;

	PerformanceInformationAvailableNotificationRepositoryService(final PerformanceInformationAvailableNotificationJpa availableJpa) {
		this.availableJpa = availableJpa;
	}

	public PerformanceInformationAvailableNotification save(final PerformanceInformationAvailableNotification event) {
		return availableJpa.save(event);
	}

}
