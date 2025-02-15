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
package com.ubiqube.etsi.mano.service.mapping;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

class GrantResponseMappingTest {

	private GrantResponseMapping grantResponseMapping;
	private PodamFactory factory;

	@BeforeEach
	public void setUp() {
		grantResponseMapping = Mappers.getMapper(GrantResponseMapping.class);
		factory = new PodamFactoryImpl();
		factory.getStrategy().setDefaultNumberOfCollectionElements(1);
	}

	@Test
	void testMapVnfBlueprint_NullInput() {
		GrantResponse result = grantResponseMapping.mapVnfBlueprint(null);
		assertNull(result);
	}

	@Test
	void testMapVnfBlueprint_ValidInput() {
		VnfBlueprint vnfBlueprint = new VnfBlueprint();

		GrantResponse result = grantResponseMapping.mapVnfBlueprint(vnfBlueprint);

		assertNotNull(result);
//
	}

}
