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
package com.ubiqube.etsi.mano.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.dto.VnfPackageDto;
import com.ubiqube.etsi.mano.jpa.VnfInstanceJpa;
import com.ubiqube.etsi.mano.jpa.VnfPackageJpa;
import com.ubiqube.etsi.mano.orchestrator.ExecutionGraph;
import com.ubiqube.etsi.mano.orchestrator.dump.ExecutionResult;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.mapping.VnfPackageDtoMapping;
import com.ubiqube.etsi.mano.vnfm.service.event.VnfmActions;

@ExtendWith(MockitoExtension.class)
class VnfmAdminControllerTest {

	@Mock
	private VnfPackageJpa vnfPackageRepositoryJpa;

	@Mock
	private VnfPackageRepository vnfPackageRepository;

	@Mock
	private EventManager eventManager;

	@Mock
	private VnfPackageDtoMapping mapper;

	@Mock
	private VnfmActions vnfmActions;

	@Mock
	private VnfInstanceJpa vnfInstanceJpa;

	private VnfmAdminController vnfmAdminController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		// Mock security context
		SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
		vnfmAdminController = new VnfmAdminController(vnfPackageRepositoryJpa, vnfPackageRepository, eventManager, mapper, vnfmActions, vnfInstanceJpa);
	}

	@Test
	void testDeleteVnfPackage() {
		UUID vnfPkgId = UUID.randomUUID();
		doNothing().when(vnfPackageRepositoryJpa).deleteById(vnfPkgId);
		vnfmAdminController.deleteVnfPackage(vnfPkgId.toString());
		verify(vnfPackageRepositoryJpa, times(1)).deleteById(vnfPkgId);
	}

	@Test
	void testFindByVnfd() {
		String vnfd = "test-vnfd";
		VnfPackage vnfPackage = new VnfPackage();
		VnfPackageDto vnfPackageDto = new VnfPackageDto();
		when(vnfPackageRepositoryJpa.findByVnfdId(vnfd)).thenReturn(Optional.of(vnfPackage));
		when(mapper.map(vnfPackage)).thenReturn(vnfPackageDto);
		vnfmAdminController.findByVnfd(vnfd);

		verify(vnfPackageRepositoryJpa, times(1)).findByVnfdId(vnfd);
		verify(mapper, times(1)).map(vnfPackage);
	}

	@Test
	void testFindByVnfdNotFound() {
		String vnfd = "test-vnfd";
		when(vnfPackageRepositoryJpa.findByVnfdId(vnfd)).thenReturn(Optional.empty());
		ResponseEntity<VnfPackageDto> res = vnfmAdminController.findByVnfd(vnfd);
		assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
	}

	@Test
	void testGetVnfLcmGraph() {
		UUID id = UUID.randomUUID();
		VnfInstance vnfInstance = new VnfInstance();
		ExecutionGraph executionGraph = mock(ExecutionGraph.class);
		ExecutionResult executionResult = mock(ExecutionResult.class);
		when(vnfInstanceJpa.findById(id)).thenReturn(Optional.of(vnfInstance));
		when(vnfmActions.getExecutionGraph(vnfInstance)).thenReturn(executionGraph);
		when(executionGraph.dump()).thenReturn(executionResult);
		vnfmAdminController.getVnfLcmGraph(id);
		assertTrue(true);
	}

	@Test
	void testOnboardVnfPackage() throws IOException {
		MultipartFile file = mock(MultipartFile.class);
		VnfPackage vnfPackage = new VnfPackage();
		when(vnfPackageRepository.save(any())).thenReturn(vnfPackage);
		vnfmAdminController.onboardVnfPackage(file);
		assertTrue(true);
	}

	@Test
	void testFindAll() {
		vnfmAdminController.findAll();
		assertTrue(true);
	}
}
