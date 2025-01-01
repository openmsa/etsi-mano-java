package com.ubiqube.etsi.mano.nfvo.service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.nfvo.jpa.NsdInstanceJpa;

@Service
public class NsdInstanceRepositoryService {
	private final NsdInstanceJpa nsdInstanceJpa;

	NsdInstanceRepositoryService(final NsdInstanceJpa nsdInstanceJpa) {
		this.nsdInstanceJpa = nsdInstanceJpa;
	}

	public NsdInstance save(final NsdInstance nsInstance) {
		return nsdInstanceJpa.save(nsInstance);
	}

	public void deleteById(final UUID nsInstanceUuid) {
		nsdInstanceJpa.deleteById(nsInstanceUuid);
	}

	public Optional<NsdInstance> findById(final UUID nsUuid) {
		return nsdInstanceJpa.findById(nsUuid);
	}

	public int countByNsdInfo(final NsdPackage nsPackage) {
		return nsdInstanceJpa.countByNsdInfo(nsPackage);
	}

	public Iterable<NsdInstance> findAll() {
		return nsdInstanceJpa.findAll();
	}
}
