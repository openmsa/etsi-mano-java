package com.ubiqube.etsi.mano.jpa;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import com.ubiqube.etsi.mano.service.juju.entities.JujuMetadata;

public interface JujuMetadataJpa extends CrudRepository<JujuMetadata, UUID> {

}
