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
package com.ubiqube.etsi.mano.nfvo.service.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.NsdPackageVnfPackage;
import com.ubiqube.etsi.mano.nfvo.jpa.NsdPackageVnfPackageJpa;

class NsdPackageVnfPackageRepositoryServiceTest {

	@Mock
	private NsdPackageVnfPackageJpa nsdPackageVnfPackageJpa;

	@InjectMocks
	private NsdPackageVnfPackageRepositoryService nsdPackageVnfPackageRepositoryService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindByNsdPackage() {
		NsdPackage nsdPackage = new NsdPackage();
		Set<NsdPackageVnfPackage> expectedSet = new HashSet<>();
		when(nsdPackageVnfPackageJpa.findByNsdPackage(nsdPackage)).thenReturn(expectedSet);

		Set<NsdPackageVnfPackage> resultSet = nsdPackageVnfPackageRepositoryService.findByNsdPackage(nsdPackage);

		assertEquals(expectedSet, resultSet);
		verify(nsdPackageVnfPackageJpa).findByNsdPackage(nsdPackage);
	}

	@Test
	void testFindByVnfdId() {
		String vnfdId = "vnfdId";
		Set<NsdPackageVnfPackage> expectedSet = new HashSet<>();
		when(nsdPackageVnfPackageJpa.findByVnfdId(vnfdId)).thenReturn(expectedSet);

		Set<NsdPackageVnfPackage> resultSet = nsdPackageVnfPackageRepositoryService.findByVnfdId(vnfdId);

		assertEquals(expectedSet, resultSet);
		verify(nsdPackageVnfPackageJpa).findByVnfdId(vnfdId);
	}
}
