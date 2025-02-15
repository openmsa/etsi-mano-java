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
package com.ubiqube.etsi.mano.vnfm.service.mapping;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mapstruct.factory.Mappers;

import com.ubiqube.etsi.mano.dao.mano.vim.PlanStatusType;

class VnfLcmOpOccMappingTest {

	private final VnfLcmOpOccMapping mapper = Mappers.getMapper(VnfLcmOpOccMapping.class);

	@Test
	void testMapToVnfInstantiatedStorage() {
		assertNull(mapper.mapToVnfInstantiatedStorage(null));
	}

	@ParameterizedTest
	@EnumSource(value = PlanStatusType.class, names = { "FAILED", "NOT_STARTED", "STARTED", "SUCCESS" })
	void testPlanStatusType(final PlanStatusType o) {
		assertNotNull(mapper.map(o));
	}

	@Test
	void testPlanStatusTypeRemoved() {
		assertThrows(IllegalArgumentException.class, () -> mapper.map(PlanStatusType.REMOVED));
	}

	@Test
	void testMapToVnfInstantiatedVirtualLink() {
		assertNull(mapper.mapToVnfInstantiatedVirtualLink(null));
	}

	@Test
	void testMapToVnfInstantiatedCompute() {
		assertNull(mapper.mapToVnfInstantiatedCompute(null));
	}

}
