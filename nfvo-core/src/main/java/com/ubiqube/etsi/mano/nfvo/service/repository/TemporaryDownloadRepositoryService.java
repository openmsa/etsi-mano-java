package com.ubiqube.etsi.mano.nfvo.service.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.TemporaryDownload;
import com.ubiqube.etsi.mano.nfvo.jpa.TemporaryDownloadJpa;

@Service
public class TemporaryDownloadRepositoryService {
	private final TemporaryDownloadJpa temporaryJpa;

	TemporaryDownloadRepositoryService(final TemporaryDownloadJpa temporaryJpa) {
		this.temporaryJpa = temporaryJpa;
	}

	public TemporaryDownload save(final TemporaryDownload td) {
		return temporaryJpa.save(td);
	}

	public Optional<TemporaryDownload> findByIdAndExpirationDateAfter(final String id, final Date date) {
		return temporaryJpa.findByIdAndExpirationDateAfter(id, date);
	}
}
