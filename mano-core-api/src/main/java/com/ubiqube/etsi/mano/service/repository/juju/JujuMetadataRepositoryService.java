package com.ubiqube.etsi.mano.service.repository.juju;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.jpa.JujuMetadataJpa;
import com.ubiqube.etsi.mano.service.juju.entities.JujuMetadata;

@Service
public class JujuMetadataRepositoryService {
	private final JujuMetadataJpa jujuMetadataJpa;

	JujuMetadataRepositoryService(final JujuMetadataJpa jujuMetadataJpa) {
		this.jujuMetadataJpa = jujuMetadataJpa;
	}

	public void save(final JujuMetadata metadata) {
		jujuMetadataJpa.save(metadata);
	}
}
