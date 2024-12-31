package com.ubiqube.etsi.mano.service.repository;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.config.Configurations;
import com.ubiqube.etsi.mano.jpa.config.ConfigurationsJpa;

@Service
public class ConfigurationsRepositoryService {
	private final ConfigurationsJpa configurationsJpa;

	ConfigurationsRepositoryService(final ConfigurationsJpa confRepo) {
		this.configurationsJpa = confRepo;
	}

	public Optional<Configurations> findById(final String confClusterId) {
		return configurationsJpa.findById(confClusterId);
	}

	public Configurations save(final Configurations conf) {
		return configurationsJpa.save(conf);
	}
}
