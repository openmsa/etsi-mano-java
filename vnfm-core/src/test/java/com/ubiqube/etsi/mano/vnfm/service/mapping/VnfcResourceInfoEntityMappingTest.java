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

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfTask;

class VnfcResourceInfoEntityMappingTest {
	private final VnfcResourceInfoEntityMapping mapper = Mappers.getMapper(VnfcResourceInfoEntityMapping.class);

	@Test
	void testVnfLiveInstance() {
		assertNull(mapper.map((VnfLiveInstance) null));
	}

	@Test
	void testMapVimResource() {
		assertNull(mapper.mapVimResource((VnfLiveInstance) null));
	}

	@Test
	void testMap() {
		assertNull(mapper.map((VnfTask) null));
	}
}
