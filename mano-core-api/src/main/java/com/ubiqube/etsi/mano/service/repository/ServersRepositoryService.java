package com.ubiqube.etsi.mano.service.repository;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.vim.PlanStatusType;
import com.ubiqube.etsi.mano.jpa.config.ServersJpa;
import com.ubiqube.etsi.mano.service.auth.model.ServerType;

@Service
public class ServersRepositoryService {
	private final ServersJpa serversJpa;

	public ServersRepositoryService(final ServersJpa serversJpa) {
		this.serversJpa = serversJpa;
	}

	public List<Servers> findAll() {
		return serversJpa.findAll();
	}

	public Optional<Servers> findById(final UUID id) {
		return serversJpa.findById(id);
	}

	public Optional<Servers> findByUrl(final URI url) {
		return serversJpa.findByUrl(url);
	}

	public Servers save(final Servers servers) {
		return serversJpa.save(servers);
	}

	public void deleteById(final UUID id) {
		serversJpa.deleteById(id);
	}

	public List<Servers> findByServerStatusIn(final List<PlanStatusType> of) {
		return serversJpa.findByServerStatusIn(of);
	}

	public List<Servers> findByServerTypeAndServerStatusIn(final ServerType vnfm, final List<PlanStatusType> asList) {
		return serversJpa.findByServerTypeAndServerStatusIn(vnfm, asList);
	}
}
