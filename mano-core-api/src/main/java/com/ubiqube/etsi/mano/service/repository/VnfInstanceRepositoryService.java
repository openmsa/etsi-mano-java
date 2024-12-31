package com.ubiqube.etsi.mano.service.repository;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.jpa.VnfInstanceJpa;

@Service
public class VnfInstanceRepositoryService {
	private final VnfInstanceJpa vnfInstanceJpa;

	public VnfInstanceRepositoryService(final VnfInstanceJpa vnfInstanceJpa) {
		this.vnfInstanceJpa = vnfInstanceJpa;
	}

	public Object findById(final UUID id) {
		return vnfInstanceJpa.findById(id);
	}
}
