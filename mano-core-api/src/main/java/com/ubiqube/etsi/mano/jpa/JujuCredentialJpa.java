package com.ubiqube.etsi.mano.jpa;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.ubiqube.etsi.mano.service.juju.entities.JujuCredential;

public interface JujuCredentialJpa extends CrudRepository<JujuCredential, UUID> {

}
