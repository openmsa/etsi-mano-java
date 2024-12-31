package com.ubiqube.etsi.mano.service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.jpa.SystemsJpa;
import com.ubiqube.etsi.mano.orchestrator.entities.Systems;

@Service
public class SystemsRepositoryService {
	private final SystemsJpa systemJpa;

	public SystemsRepositoryService(final SystemsJpa systemJpa) {
		this.systemJpa = systemJpa;
	}

	public Systems save(final Systems sys) {
		return systemJpa.save(sys);
	}

	public Iterable<Systems> findAll() {
		return systemJpa.findAll();
	}

	public void deleteByVimOrigin(final UUID id) {
		systemJpa.deleteByVimOrigin(id);
	}

	public List<Systems> findByIdAndSubSystemsModuleName(final UUID id, final String moduleName) {
		return systemJpa.findByIdAndSubSystemsModuleName(id, moduleName);
	}
}
