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

import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.ubiqube.etsi.mano.controller.AbstractEtsiImplementation;

class ApiVersionInformationApiVersionsMappingTest {
	private final ApiVersionInformationApiVersionsMapping mapper = Mappers.getMapper(ApiVersionInformationApiVersionsMapping.class);

	@Test
	void testNull() {
		assertNull(mapper.map(null));
	}

	@Test
	void test() {
		assertNotNull(mapper.map(new MappingClassTest()));
	}

	class MappingClassTest extends AbstractEtsiImplementation {

		@Override
		public String getVersion() {
			return "1.1";
		}

		@Override
		public boolean isDeprecated() {
			return false;
		}

		@Override
		public OffsetDateTime getRetirementDate() {
			return null;
		}

	}

}
