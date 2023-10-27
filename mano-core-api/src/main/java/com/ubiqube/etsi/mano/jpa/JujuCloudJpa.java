package com.ubiqube.etsi.mano.jpa;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ubiqube.etsi.mano.service.juju.entities.JujuCloud;

public interface JujuCloudJpa extends CrudRepository<JujuCloud, UUID> {
	@Query("from JujuCloud jc where jc.metadata.name = :controllername and jc.status='PASS'")
	List<JujuCloud> findByControllerName(@Param("controllername") String controllername);
}
