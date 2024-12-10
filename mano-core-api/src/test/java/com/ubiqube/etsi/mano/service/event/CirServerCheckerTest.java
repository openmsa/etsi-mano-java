package com.ubiqube.etsi.mano.service.event;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionType;
import com.ubiqube.etsi.mano.docker.HelmService;
import com.ubiqube.etsi.mano.docker.RegistryInformations;
import com.ubiqube.etsi.mano.service.auth.model.AuthParamBasic;
import com.ubiqube.etsi.mano.service.auth.model.AuthentificationInformations;

class CirServerCheckerTest {
	// Successfully verify connection when valid ConnectionInformation with matching
	// HelmService is provided
	@Test
	void test_verify_connection_with_valid_connection_info() {
		// Arrange
		final Map<ConnectionType, HelmService> helmServices = new HashMap<>();
		final HelmService mockHelmService = mock(HelmService.class);
		final ConnectionInformation connInfo = ConnectionInformation.builder()
				.connType(ConnectionType.HELM)
				.name("test-connection")
				.url(URI.create("http://test-registry"))
				.authentification(AuthentificationInformations.builder()
						.authParamBasic(AuthParamBasic.builder().build())
						.build())
				.build();
		helmServices.put(ConnectionType.HELM, mockHelmService);
		when(mockHelmService.getConnectionType()).thenReturn("HELM");
		final CirServerChecker checker = new CirServerChecker(List.of(mockHelmService));

		// Act
		checker.verify(connInfo);

		// Assert
		verify(mockHelmService, times(1)).verifyConnection(any(RegistryInformations.class));
	}

	// Handle null ConnectionInformation parameter
	@Test
	void test_verify_connection_with_null_connection_info() {
		// Arrange
		final CirServerChecker checker = new CirServerChecker(List.of());

		// Act & Assert
		assertThrows(NullPointerException.class, () -> {
			checker.verify(null);
		});
	}

}
