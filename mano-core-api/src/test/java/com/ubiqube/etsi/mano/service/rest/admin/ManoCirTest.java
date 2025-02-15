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
