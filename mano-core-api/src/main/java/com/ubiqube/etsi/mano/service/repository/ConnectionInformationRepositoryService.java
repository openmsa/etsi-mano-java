package com.ubiqube.etsi.mano.service.repository;

import java.util.List;

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
}
