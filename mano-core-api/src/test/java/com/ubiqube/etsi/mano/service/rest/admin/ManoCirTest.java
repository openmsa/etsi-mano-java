package com.ubiqube.etsi.mano.service.rest.admin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.util.UriComponentsBuilder;

import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionInformation;
import com.ubiqube.etsi.mano.service.rest.FluxRest;
import com.ubiqube.etsi.mano.service.rest.QueryParameters;
import com.ubiqube.etsi.mano.service.rest.ServerAdapter;

@ExtendWith(MockitoExtension.class)
class ManoCirTest {
	private ManoCir manoCir;
	@Mock
	private QueryParameters mockQueryParameters;
	@Mock
	private ServerAdapter mockServerAdapter;
	@Mock
	private ConnectionInformation mockConnectionInformation;
	@Mock
	private FluxRest mock;

	@BeforeEach
	public void setUp() {
		manoCir = new ManoCir(mockQueryParameters);
	}

	@Test
	void testRegister() {
		String root = "http://localhost";
		URI uri = UriComponentsBuilder.fromUriString(root).pathSegment("admin/cir").build().toUri();
		when(mockQueryParameters.getServer()).thenReturn(mockServerAdapter);
		when(mockServerAdapter.rest()).thenReturn(mock);
		when(mock.post(eq(uri), eq(mockConnectionInformation), eq(ConnectionInformation.class), isNull())).thenReturn(mockConnectionInformation);

		ConnectionInformation result = manoCir.register(mockConnectionInformation, root);

		assertNotNull(result);
		assertEquals(mockConnectionInformation, result);
		verify(mockServerAdapter.rest()).post(eq(uri), eq(mockConnectionInformation), eq(ConnectionInformation.class), isNull());
	}

	@Test
	void testRegisterWithId() {
		String root = "http://localhost";
		URI uri = UriComponentsBuilder.fromUriString(root).pathSegment("admin/cir").build().toUri();
		when(mockQueryParameters.getServer()).thenReturn(mockServerAdapter);
		when(mockQueryParameters.getObjectId()).thenReturn(UUID.randomUUID());
		when(mockServerAdapter.rest()).thenReturn(mock);
		when(mock.post(eq(uri), eq(mockConnectionInformation), eq(ConnectionInformation.class), isNull())).thenReturn(mockConnectionInformation);

		ConnectionInformation result = manoCir.register(mockConnectionInformation, root);

		assertNotNull(result);
		assertEquals(mockConnectionInformation, result);
		verify(mockServerAdapter.rest()).post(eq(uri), eq(mockConnectionInformation), eq(ConnectionInformation.class), isNull());
	}
}
