package com.ubiqube.etsi.mano.service.event;

import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.cnf.capi.CapiServer;
import com.ubiqube.etsi.mano.dao.mano.vim.PlanStatusType;
import com.ubiqube.etsi.mano.dao.rfc7807.FailureDetails;
import com.ubiqube.etsi.mano.service.CapiServerService;
import com.ubiqube.etsi.mano.vim.k8sexecutor.K8sExecutor;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;

@Service
public class CapiServerChecker {
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(CapiServerChecker.class);

	private final CapiServerService capiServer;
	private final K8sExecutor executor;

	public CapiServerChecker(final CapiServerService capiServer, final K8sExecutor executor) {
		this.capiServer = capiServer;
		this.executor = executor;
	}

	public CapiServer verify(final UUID objectId, final Map<String, Object> parameters) {
		final CapiServer res = capiServer.findById(objectId).orElseThrow();
		try {
			final Config cfg = new ConfigBuilder()
					.withMasterUrl(res.getUrl())
					.withCaCertData(res.getCertificateAuthorityData())
					.withClientCertData(res.getClientCertificateData())
					.withClientKeyData(res.getClientKeyData())
					.build();
			final Pod pod = new Pod();
			executor.get(cfg, c -> c.resource(pod).get());
			res.setServerStatus(PlanStatusType.SUCCESS);
			res.setError(null);
			return capiServer.save(res);
		} catch (final RuntimeException e) {
			LOG.error("", e);
			res.setError(new FailureDetails(500, e.getMessage()));
			res.setServerStatus(PlanStatusType.FAILED);
			return capiServer.save(res);
		}
	}

}
