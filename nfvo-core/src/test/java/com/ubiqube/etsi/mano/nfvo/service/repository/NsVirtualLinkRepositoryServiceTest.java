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
import static org.mockito.Mockito.times;
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
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVirtualLink;
import com.ubiqube.etsi.mano.nfvo.jpa.NsVirtualLinkJpa;

class NsVirtualLinkRepositoryServiceTest {

	@Mock
	private NsVirtualLinkJpa nsVirtualLinkJpa;

	@InjectMocks
	private NsVirtualLinkRepositoryService nsVirtualLinkRepositoryService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindByNsdPackage() {
		NsdPackage nsdPackage = new NsdPackage();
		Set<NsVirtualLink> expectedLinks = new HashSet<>();
		when(nsVirtualLinkJpa.findByNsdPackage(nsdPackage)).thenReturn(expectedLinks);

		Set<NsVirtualLink> actualLinks = nsVirtualLinkRepositoryService.findByNsdPackage(nsdPackage);

		assertEquals(expectedLinks, actualLinks);
		verify(nsVirtualLinkJpa, times(1)).findByNsdPackage(nsdPackage);
	}
}
