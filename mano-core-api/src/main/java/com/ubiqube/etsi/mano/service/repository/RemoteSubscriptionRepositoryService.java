package com.ubiqube.etsi.mano.service.repository;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.subscription.RemoteSubscription;
import com.ubiqube.etsi.mano.jpa.RemoteSubscriptionJpa;

@Service
public class RemoteSubscriptionRepositoryService {
	private final RemoteSubscriptionJpa remoteSubscriptionJpa;

	public RemoteSubscriptionRepositoryService(final RemoteSubscriptionJpa remoteSubscriptionJpa) {
		this.remoteSubscriptionJpa = remoteSubscriptionJpa;
	}

	public List<RemoteSubscription> findByRemoteSubscriptionId(final String remoteSubscriptionId) {
		return remoteSubscriptionJpa.findByRemoteSubscriptionId(remoteSubscriptionId);
	}
}
