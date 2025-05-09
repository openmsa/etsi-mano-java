package com.ubiqube.etsi.mano.vnfm.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.Constants;
import com.ubiqube.etsi.mano.dao.mano.config.Configurations;
import com.ubiqube.etsi.mano.service.repository.ConfigurationsRepositoryService;

@Service
public class TagService {

	private final ConfigurationsRepositoryService configurationsRepositoryService;

	TagService(final ConfigurationsRepositoryService configurationsRepositoryService) {
		// Value is not present on first startup.
		this.configurationsRepositoryService = configurationsRepositoryService;
	}

	public Map<String, String> buildTags(final UUID id) {
		Configurations conf = configurationsRepositoryService.findById(Constants.CONF_CLUSTER_ID).orElseThrow(() -> new RuntimeException("Cluster ID not found"));
		return Map.of("mano:instance_id", conf.getWalue(), "mano:vnf_instance", id.toString());
	}
}
