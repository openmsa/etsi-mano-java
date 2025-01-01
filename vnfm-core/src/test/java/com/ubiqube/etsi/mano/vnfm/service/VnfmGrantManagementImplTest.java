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
package com.ubiqube.etsi.mano.vnfm.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.service.rest.ManoClient;
import com.ubiqube.etsi.mano.service.rest.ManoClientFactory;
import com.ubiqube.etsi.mano.service.rest.grant.ManoGrant;

@ExtendWith(MockitoExtension.class)
class VnfmGrantManagementImplTest {
	@Mock
	private ManoClientFactory manoClient;
	@Mock
	private ManoClient manoCli;
	@Mock
	private ManoGrant manoGrant;

	private VnfmGrantManagementImpl createService() {
		return new VnfmGrantManagementImpl(manoClient);
	}

	@Test
	void testGet() {
		final VnfmGrantManagementImpl srv = createService();
		when(manoClient.getClient()).thenReturn(manoCli);
		when(manoCli.grant()).thenReturn(manoGrant);
		final ManoGrant mg = Mockito.mock(ManoGrant.class);
		when(manoGrant.id(any())).thenReturn(mg);
		srv.get(null);
		assertTrue(true);
	}

	@Test
	void testPost() {
		final VnfmGrantManagementImpl srv = createService();
		when(manoClient.getClient()).thenReturn(manoCli);
		when(manoCli.grant()).thenReturn(manoGrant);
		srv.post(null);
		assertTrue(true);
	}
}
