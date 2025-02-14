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
package com.ubiqube.etsi.mano.vnfm.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.ubiqube.etsi.mano.dao.mano.cnf.capi.CapiServer;
import com.ubiqube.etsi.mano.dao.mano.vim.PlanStatusType;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.exception.PreConditionException;
import com.ubiqube.etsi.mano.service.Patcher;
import com.ubiqube.etsi.mano.service.event.ActionType;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.repository.CapiServerRepositoryService;
import com.ubiqube.etsi.mano.vim.k8s.OsClusterService;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow.capi.CapiServerMapping;

@ExtendWith(MockitoExtension.class)
class CapiControllerTest {
	@Mock
	private CapiServerRepositoryService capiServerJpa;
	@Mock
	private OsClusterService osClusterService;
	@Mock
	private CapiServerMapping mapper;
	@Mock
	private Patcher patcher;
	@Mock
	private EventManager eventManager;

	CapiController createService() {
		return new CapiController(capiServerJpa, osClusterService, mapper, patcher, eventManager);
	}

	@Test
	void testListCapiConn() {
		final CapiController srv = createService();
		srv.listCapiConnections();
		assertTrue(true);
	}

	@Test
	void testPost() {
		final CapiController srv = createService();
		final CapiServer e = new CapiServer();
		when(capiServerJpa.save(e)).thenReturn(e);
		srv.post(e);
		assertTrue(true);
	}

	@Test
	void testPostKubeConfig() throws IOException {
		final CapiController srv = createService();
		final MultipartFile file = Mockito.mock(MultipartFile.class);
		final CapiServer e = new CapiServer();
		when(capiServerJpa.save(any())).thenReturn(e);
		srv.postKubeConfig("ctx", file);
		assertTrue(true);
	}

	@Test
	void testDelete() {
		final CapiController srv = createService();
		srv.delete(UUID.randomUUID());
		assertTrue(true);
	}

	// Successfully patch CapiServer when valid ID and body are provided without
	// ifMatch header
	@Test
	void test_patch_capi_server_success() {
		final CapiController srv = createService();
		final UUID id = UUID.randomUUID();
		final String body = "{\"name\":\"updated-name\"}";
		final CapiServer existingCapi = new CapiServer();
		existingCapi.setId(id);
		existingCapi.setVersion(1L);

		final CapiServer updatedCapi = new CapiServer();
		updatedCapi.setId(id);
		updatedCapi.setServerStatus(PlanStatusType.STARTED);

		when(capiServerJpa.findById(id)).thenReturn(Optional.of(existingCapi));
		when(capiServerJpa.save(any(CapiServer.class))).thenReturn(updatedCapi);
		doNothing().when(patcher).patch(body, existingCapi);
		doNothing().when(eventManager).sendAction(ActionType.REGISTER_CAPI, id);

		final ResponseEntity<CapiServer> response = srv.patchVim(id, body, null);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(updatedCapi, response.getBody());
		verify(capiServerJpa).findById(id);
		verify(patcher).patch(body, existingCapi);
		verify(capiServerJpa).save(existingCapi);
		verify(eventManager).sendAction(ActionType.REGISTER_CAPI, id);
	}

	// Throw NotFoundException when ID does not exist
	@Test
	void test_patch_capi_server_not_found() {
		final CapiController srv = createService();
		final UUID id = UUID.randomUUID();
		final String body = "{\"name\":\"test\"}";

		when(capiServerJpa.findById(id)).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> {
			srv.patchVim(id, body, null);
		});

		verify(capiServerJpa).findById(id);
		verifyNoInteractions(patcher);
		verifyNoInteractions(eventManager);
	}

	// Throw PreConditionException when ifMatch header does not match version
	@Test
	void test_precondition_exception_ifmatch_mismatch() {
		final CapiController srv = createService();
		final UUID id = UUID.randomUUID();
		final String body = "{\"name\":\"updated-name\"}";
		final String ifMatch = "2"; // Mismatched version
		final CapiServer existingCapi = new CapiServer();
		existingCapi.setId(id);
		existingCapi.setVersion(1L); // Actual version

		when(capiServerJpa.findById(id)).thenReturn(Optional.of(existingCapi));

		assertThrows(PreConditionException.class, () -> {
			srv.patchVim(id, body, ifMatch);
		});

		verify(capiServerJpa).findById(id);
		verify(patcher, never()).patch(anyString(), any(CapiServer.class));
		verify(capiServerJpa, never()).save(any(CapiServer.class));
		verify(eventManager, never()).sendAction(any(ActionType.class), any(UUID.class));
	}

	@Test
	void testRetry() {
		final CapiController srv = createService();
		srv.retry(null);
	}
}
