package com.ubiqube.etsi.mano.service.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.jpa.SysConnectionJpa;
import com.ubiqube.etsi.mano.orchestrator.entities.SystemConnections;

@Service
public class SystemConnectionsRepositoryService {
	private final SysConnectionJpa systemConnectionsJpa;

	public SystemConnectionsRepositoryService(final SysConnectionJpa systemConnectionsJpa) {
		this.systemConnectionsJpa = systemConnectionsJpa;
	}

	public Optional<SystemConnections> findById(final UUID id) {
		return systemConnectionsJpa.findById(id);
	}

	public SystemConnections save(final SystemConnections sc) {
		return systemConnectionsJpa.save(sc);
	}

	public List<SystemConnections> findByModuleName(final String simpleName) {
		return systemConnectionsJpa.findByModuleName(simpleName);
	}
}
