package com.ubiqube.etsi.mano.service.repository.juju;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.jpa.JujuCloudJpa;
import com.ubiqube.etsi.mano.service.juju.entities.JujuCloud;

@Service
public class JujuCloudRepositoryService {
	private final JujuCloudJpa jujuCloudJpa;

	JujuCloudRepositoryService(final JujuCloudJpa jujuCloudJpa) {
		this.jujuCloudJpa = jujuCloudJpa;
	}

	public JujuCloud save(final JujuCloud jCloud) {
		return jujuCloudJpa.save(jCloud);
	}

	public List<JujuCloud> findByMetadataName(final String controllername, final String status) {
		return jujuCloudJpa.findByMetadataName(controllername, status);
	}

	public Optional<JujuCloud> findById(final UUID objectId) {
		return jujuCloudJpa.findById(objectId);
	}

	public void delete(final JujuCloud jCloud) {
		jujuCloudJpa.delete(jCloud);
	}
}
