package com.ubiqube.etsi.mano.service.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionType;
import com.ubiqube.etsi.mano.jpa.ConnectionInformationJpa;

@Service
public class ConnectionInformationRepositoryService {
	private final ConnectionInformationJpa connectionJpa;

	public ConnectionInformationRepositoryService(final ConnectionInformationJpa connectionJpa) {
		this.connectionJpa = connectionJpa;
	}

	public List<ConnectionInformation> findByConnType(final ConnectionType type) {
		return connectionJpa.findByConnType(type);
	}

	public void deleteById(final UUID id) {
		connectionJpa.deleteById(id);
	}

	public Optional<ConnectionInformation> findById(final UUID id) {
		return connectionJpa.findById(id);
	}

	public ConnectionInformation save(final ConnectionInformation conn) {
		return connectionJpa.save(conn);
	}

	public Iterable<ConnectionInformation> findAll() {
		return connectionJpa.findAll();
	}
}
