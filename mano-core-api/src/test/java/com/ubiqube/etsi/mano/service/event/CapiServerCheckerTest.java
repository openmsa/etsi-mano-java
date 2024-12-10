package com.ubiqube.etsi.mano.service.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.ubiqube.etsi.mano.service.CapiServerService;
import com.ubiqube.etsi.mano.vim.k8sexecutor.K8sExecutor;

import io.fabric8.kubernetes.api.model.Pod;

@ExtendWith(MockitoExtension.class)
class CapiServerCheckerTest {
	@Mock
	private CapiServerService capiServer;
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
		when(executor.get(any(), any())).thenReturn(new Pod());

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
		when(executor.get(any(), any())).thenThrow(RuntimeException.class);

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

		assertThrows(NoSuchElementException.class, () -> {
			checker.verify(id, Map.of());
		});

		verify(executor, never()).get(any(), any());
		verify(capiServer, never()).save(any());
	}
}
