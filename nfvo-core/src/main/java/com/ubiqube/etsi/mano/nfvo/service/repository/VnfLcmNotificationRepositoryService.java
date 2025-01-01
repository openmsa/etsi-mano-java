package com.ubiqube.etsi.mano.nfvo.service.repository;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.vnflcm.VnfLcmNotification;
import com.ubiqube.etsi.mano.nfvo.jpa.VnfLcmNotificationJpa;

@Service
public class VnfLcmNotificationRepositoryService {
	private final VnfLcmNotificationJpa vnfLcmJpa;

	VnfLcmNotificationRepositoryService(final VnfLcmNotificationJpa vnfLcmJpa) {
		this.vnfLcmJpa = vnfLcmJpa;
	}

	public VnfLcmNotification save(final VnfLcmNotification event) {
		return vnfLcmJpa.save(event);
	}
}
