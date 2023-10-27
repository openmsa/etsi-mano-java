package com.ubiqube.etsi.mano.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.jpa.JujuCloudJpa;
import com.ubiqube.etsi.mano.jpa.JujuCredentialJpa;
import com.ubiqube.etsi.mano.jpa.JujuMetadataJpa;
import com.ubiqube.etsi.mano.service.juju.entities.JujuCloud;

@Service
public class JujuCloudService {

	private final JujuCloudJpa jujuCloudJpa;
	private final JujuCredentialJpa jujuCredentialJpa;
	private final JujuMetadataJpa jujuMetadataJpa;

	public JujuCloudService(final JujuCloudJpa jujuCloudJpa, final JujuCredentialJpa jujuCredentialJpa,
			final JujuMetadataJpa jujuMetadataJpa) {
		this.jujuCloudJpa = jujuCloudJpa;
		this.jujuCredentialJpa = jujuCredentialJpa;
		this.jujuMetadataJpa = jujuMetadataJpa;
	}

	public JujuCloud saveCloud(final JujuCloud jCloud) {
		jujuCredentialJpa.save(jCloud.getCredential());
		jujuMetadataJpa.save(jCloud.getMetadata());
		jCloud.setStatus("PROCESS");
		return jujuCloudJpa.save(jCloud);
	}

	public List<JujuCloud> findByControllerName(String controllername) {
		return jujuCloudJpa.findByControllerName(controllername);
	}
}
