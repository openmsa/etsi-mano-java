package com.ubiqube.etsi.mano.nfvo.service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ipam.Networks;
import com.ubiqube.etsi.mano.nfvo.jpa.ipam.NetworksJpa;

@Service
public class NetworksRepositoryService {
	private final NetworksJpa networksJpa;

	NetworksRepositoryService(final NetworksJpa networksJpa) {
		this.networksJpa = networksJpa;
	}

	public Optional<Networks> findFirstFreeNetwork(final UUID id) {
		return networksJpa.findFirstFreeNetwork(id);
	}

	public void save(final Networks network) {
		networksJpa.save(network);
	}
}
