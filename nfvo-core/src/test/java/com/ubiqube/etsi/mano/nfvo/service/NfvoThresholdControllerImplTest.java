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
package com.ubiqube.etsi.mano.nfvo.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.pm.Threshold;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.nfvo.service.repository.ThresholdRepositoryService;
import com.ubiqube.etsi.mano.service.search.SearchableService;

@ExtendWith(MockitoExtension.class)
class NfvoThresholdControllerImplTest {
	@Mock
	private SearchableService searchService;
	@Mock
	private ThresholdRepositoryService thresholdJpa;

	@Test
	void testSave() {
		final NfvoThresholdControllerImpl srv = new NfvoThresholdControllerImpl(searchService, thresholdJpa);
		srv.save(null);
		assertTrue(true);
	}

	@Test
	void testDelete() {
		final NfvoThresholdControllerImpl srv = new NfvoThresholdControllerImpl(searchService, thresholdJpa);
		final Threshold thres = new Threshold();
		when(thresholdJpa.findById(any())).thenReturn(Optional.of(thres));
		srv.delete(null);
		assertTrue(true);
	}

	@Test
	void testFindById() {
		final NfvoThresholdControllerImpl srv = new NfvoThresholdControllerImpl(searchService, thresholdJpa);
		final Threshold thres = new Threshold();
		when(thresholdJpa.findById(any())).thenReturn(Optional.of(thres));
		srv.findById(null);
		assertTrue(true);
	}

	@Test
	void testFindById_Fail() {
		final NfvoThresholdControllerImpl srv = new NfvoThresholdControllerImpl(searchService, thresholdJpa);
		assertThrows(NotFoundException.class, () -> srv.findById(null));
	}

	@Test
	void testSearch() {
		final NfvoThresholdControllerImpl srv = new NfvoThresholdControllerImpl(searchService, thresholdJpa);
		srv.search(null, x -> null, null, null, x -> {
		}, getClass());
		assertTrue(true);
	}
}
