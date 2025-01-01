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
package com.ubiqube.etsi.mano.nfvo.controller.lcmgrant;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.controller.lcmgrant.GrantManagement;
import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.service.vim.VimTypeConverter;

@ExtendWith(MockitoExtension.class)
class LcmGrantsFrontControllerImplTest {
	@Mock
	private GrantManagement grantManagement;
	@Mock
	private VimTypeConverter vimTypeConverter;

	@Test
	void testGet() {
		final LcmGrantsFrontControllerImpl srv = createService();
		final GrantResponse resp = new GrantResponse();
		resp.setAvailable(true);
		final Consumer<Object> cons = x -> {
		};
		when(grantManagement.get(any())).thenReturn(resp);
		srv.grantsGrantIdGet(UUID.randomUUID().toString(), x -> "", cons);
		assertTrue(true);
	}

	private LcmGrantsFrontControllerImpl createService() {
		return new LcmGrantsFrontControllerImpl(grantManagement, vimTypeConverter);
	}

	@Test
	void testGet2() {
		final LcmGrantsFrontControllerImpl srv = createService();
		final GrantResponse resp = new GrantResponse();
		resp.setAvailable(false);
		when(grantManagement.get(any())).thenReturn(resp);
		srv.grantsGrantIdGet(UUID.randomUUID().toString(), null, null);
		assertTrue(true);
	}

	@Test
	void testPost() {
		final LcmGrantsFrontControllerImpl srv = createService();
		final Function<Object, String> func = x -> "";
		final GrantResponse resp = new GrantResponse();
		when(grantManagement.post(resp)).thenReturn(resp);
		srv.grantsPost(resp, x -> "", func);
		assertTrue(true);
	}
}
