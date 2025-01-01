package com.ubiqube.etsi.mano.nfvo.service.repository;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ind.NsIndiValueChangeNotification;
import com.ubiqube.etsi.mano.jpa.NsIndiValueChangeNotificationJpa;

@Service
public class NsIndiValueChangeNotificationRepositoryService {
	private final NsIndiValueChangeNotificationJpa nsIndValueNotificationJpa;

	NsIndiValueChangeNotificationRepositoryService(final NsIndiValueChangeNotificationJpa nsIndValueNotificationJpa) {
		this.nsIndValueNotificationJpa = nsIndValueNotificationJpa;
	}

	public Iterable<NsIndiValueChangeNotification> findAll() {
		return nsIndValueNotificationJpa.findAll();
	}

	public void deleteAll(final List<NsIndiValueChangeNotification> notifications) {
		nsIndValueNotificationJpa.deleteAll(notifications);
	}

}
