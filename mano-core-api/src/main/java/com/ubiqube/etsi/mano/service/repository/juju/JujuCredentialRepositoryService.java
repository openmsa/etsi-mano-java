package com.ubiqube.etsi.mano.service.repository.juju;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.jpa.JujuCredentialJpa;
import com.ubiqube.etsi.mano.service.juju.entities.JujuCredential;

@Service
public class JujuCredentialRepositoryService {
	private final JujuCredentialJpa jujuCredentialJpa;

	JujuCredentialRepositoryService(final JujuCredentialJpa jujuCredentialJpa) {
		this.jujuCredentialJpa = jujuCredentialJpa;
	}

	public void save(final JujuCredential credential) {
		jujuCredentialJpa.save(credential);
	}
}
