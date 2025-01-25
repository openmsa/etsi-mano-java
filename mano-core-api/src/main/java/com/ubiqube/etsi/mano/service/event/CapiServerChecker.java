/**
 * Copyright (C) 2019-2025 Ubiqube.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.service.event;

import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.cnf.capi.CapiServer;
import com.ubiqube.etsi.mano.dao.mano.vim.PlanStatusType;
import com.ubiqube.etsi.mano.dao.rfc7807.FailureDetails;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.service.repository.CapiServerRepositoryService;
import com.ubiqube.etsi.mano.vim.k8sexecutor.K8sExecutor;

import io.fabric8.kubernetes.api.model.KubernetesResourceList;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;

@Service
public class CapiServerChecker {
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(CapiServerChecker.class);

	private final CapiServerRepositoryService capiServer;
	private final K8sExecutor executor;

	public CapiServerChecker(final CapiServerRepositoryService capiServer, final K8sExecutor executor) {
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
					.withOauthTokenProvider(res::getToken)
					.build();
			haveCapiClusterDefinition(cfg);
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

	private void haveCapiClusterDefinition(final Config cfg) {
		KubernetesResourceList<io.fabric8.kubernetes.api.model.apiextensions.v1.CustomResourceDefinition> query = executor.list(cfg, c -> c.apiextensions().v1().customResourceDefinitions().list());
		if (null == query) {
			throw new GenericException("Failed to get CRD");
		}
		query.getItems().stream()
				.filter(crd -> "clusters.cluster.x-k8s.io".equals(crd.getMetadata().getName()))
				.findFirst()
				.orElseThrow(() -> new GenericException("CRD not found"));
	}

}
