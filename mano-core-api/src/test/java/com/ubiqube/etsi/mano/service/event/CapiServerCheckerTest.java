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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.cnf.capi.CapiServer;
import com.ubiqube.etsi.mano.dao.mano.vim.PlanStatusType;
import com.ubiqube.etsi.mano.service.repository.CapiServerRepositoryService;
import com.ubiqube.etsi.mano.vim.k8sexecutor.K8sExecutor;

import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.api.model.KubernetesResourceList;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.apiextensions.v1.CustomResourceDefinition;
import io.fabric8.kubernetes.api.model.apiextensions.v1.CustomResourceDefinitionList;

@ExtendWith(MockitoExtension.class)
class CapiServerCheckerTest {
	@Mock
	private CapiServerRepositoryService capiServer;
	@Mock
	private K8sExecutor executor;

	private CapiServerChecker createService() {
		return new CapiServerChecker(capiServer, executor);
	}

	@Test
	void test_verify_capi_server_success() {
		final CapiServerChecker checker = createService();
		final UUID id = UUID.randomUUID();
		final CapiServer server = new CapiServer();
		server.setUrl("https://test.com");
		server.setCertificateAuthorityData("ca-data");
		server.setClientCertificateData("cert-data");
		server.setClientKeyData("key-data");

		when(capiServer.findById(id)).thenReturn(Optional.of(server));
		when(capiServer.save(any())).thenReturn(server);
		ObjectMeta metadata = new ObjectMeta();
		metadata.setName("clusters.cluster.x-k8s.io");
		CustomResourceDefinition crd = new CustomResourceDefinition("VERSION", "KIND", metadata, null, null);
		KubernetesResourceList<? extends HasMetadata> data = new CustomResourceDefinitionList("version", List.of(crd), "kind", null);
		when(executor.list(any(), any())).thenReturn((KubernetesResourceList<HasMetadata>) data);
		final CapiServer result = checker.verify(id, Map.of());

		assertEquals(PlanStatusType.SUCCESS, result.getServerStatus());
		assertNull(result.getError());
		verify(capiServer).save(server);
	}

	@Test
	void test_verify_capi_server_error() {
		final CapiServerChecker checker = createService();
		final UUID id = UUID.randomUUID();
		final CapiServer server = new CapiServer();
		server.setUrl("https://test.com");
		server.setCertificateAuthorityData("ca-data");
		server.setClientCertificateData("cert-data");
		server.setClientKeyData("key-data");

		when(capiServer.findById(id)).thenReturn(Optional.of(server));
		when(capiServer.save(any())).thenReturn(server);

		final CapiServer result = checker.verify(id, Map.of());

		assertEquals(PlanStatusType.FAILED, result.getServerStatus());
		assertNotNull(result.getError());
		verify(capiServer).save(server);
	}

	@Test
	void test_verify_capi_server_not_found() {
		final CapiServerChecker checker = createService();
		final UUID id = UUID.randomUUID();
		when(capiServer.findById(id)).thenReturn(Optional.empty());
		final Map<String, Object> map = Map.of();
		assertThrows(NoSuchElementException.class, () -> {
			checker.verify(id, map);
		});

		verify(executor, never()).get(any(), any());
		verify(capiServer, never()).save(any());
	}
}
