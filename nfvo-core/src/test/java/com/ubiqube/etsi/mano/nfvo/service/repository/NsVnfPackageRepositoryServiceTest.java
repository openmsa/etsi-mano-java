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

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.UUID;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.nfvo.jpa.NsVnfPackageJpa;

class NsVnfPackageRepositoryServiceTest {

    @Mock
    private NsVnfPackageJpa vnfPackageJpa;

    @InjectMocks
    private NsVnfPackageRepositoryService nsVnfPackageRepositoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByNsdPackages_NsdPackage_Id() {
        UUID id = UUID.randomUUID();
        Set<VnfPackage> expectedPackages = new HashSet<>();
        when(vnfPackageJpa.findByNsdPackages_NsdPackage_Id(id)).thenReturn(expectedPackages);

        Set<VnfPackage> actualPackages = nsVnfPackageRepositoryService.findByNsdPackages_NsdPackage_Id(id);

        assertEquals(expectedPackages, actualPackages);
        verify(vnfPackageJpa, times(1)).findByNsdPackages_NsdPackage_Id(id);
    }
}
