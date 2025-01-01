package com.ubiqube.etsi.mano.service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.vrqan.VrQan;
import com.ubiqube.etsi.mano.jpa.VrQanJpa;

@Service
public class VrQanRepositoryService {
	private final VrQanJpa vrQanJpa;

	public VrQanRepositoryService(final VrQanJpa vrQanJpa) {
		this.vrQanJpa = vrQanJpa;
	}

	public Optional<VrQan> findByVimId(final UUID id) {
		return vrQanJpa.findById(id);
	}

	public VrQan save(final VrQan vq) {
		return vrQanJpa.save(vq);
	}
}
