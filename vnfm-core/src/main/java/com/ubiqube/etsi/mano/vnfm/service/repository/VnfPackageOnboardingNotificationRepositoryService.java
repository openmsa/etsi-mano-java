package com.ubiqube.etsi.mano.vnfm.service.repository;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VnfPackageOnboardingNotification;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfPackageOnboardingNotificationJpa;

@Service
public class VnfPackageOnboardingNotificationRepositoryService {
	private final VnfPackageOnboardingNotificationJpa vnfPackageOnboardingNotificationJpa;

	VnfPackageOnboardingNotificationRepositoryService(final VnfPackageOnboardingNotificationJpa vnfPackageOnboardingNotificationJpa) {
		this.vnfPackageOnboardingNotificationJpa = vnfPackageOnboardingNotificationJpa;
	}

	public VnfPackageOnboardingNotification save(final VnfPackageOnboardingNotification event) {
		return vnfPackageOnboardingNotificationJpa.save(event);
	}

}
