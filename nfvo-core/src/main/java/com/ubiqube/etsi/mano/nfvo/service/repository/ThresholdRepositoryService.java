package com.ubiqube.etsi.mano.nfvo.service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.pm.Threshold;
import com.ubiqube.etsi.mano.jpa.ThresholdJpa;

@Service
public class ThresholdRepositoryService {
	private final ThresholdJpa thresholdJpa;

	ThresholdRepositoryService(final ThresholdJpa thresholdJpa) {
		this.thresholdJpa = thresholdJpa;
	}

	public Threshold save(final Threshold threshold) {
		return thresholdJpa.save(threshold);
	}

	public void deleteById(final UUID id) {
		thresholdJpa.deleteById(id);
	}

	public Optional<Threshold> findById(final UUID id) {
		return thresholdJpa.findById(id);
	}

}
