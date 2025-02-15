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
package com.ubiqube.etsi.mano.nfvo.service.mapping;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.ubiqube.etsi.mano.dao.mano.NestedNsInstanceData;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfInstanceData;
import com.ubiqube.etsi.mano.dao.mano.dto.ParamsForNestedNsd;
import com.ubiqube.etsi.mano.dao.mano.dto.nsi.NsInstantiate;
import com.ubiqube.etsi.mano.dao.mano.nfvo.ParamsForVnf;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

class NsBlueprintMappingTest {
	private PodamFactory factory;
	private final NsBlueprintMapping mapper = Mappers.getMapper(NsBlueprintMapping.class);

	@BeforeEach
	void setUp() {
		factory = new PodamFactoryImpl();
		factory.getStrategy().setDefaultNumberOfCollectionElements(1);
	}

	@Test
	void test() {
		NsBlueprint tgt = new NsBlueprint();
		mapper.map((NsInstantiate) null, tgt);
		NsInstantiate obj = factory.manufacturePojo(NsInstantiate.class);
		mapper.map(obj, tgt);
		assertNotNull(tgt);
	}

	@Test
	void testNsdInstance() {
		NsdInstance tgt = new NsdInstance();
		mapper.map(null, tgt);
		NsInstantiate obj = factory.manufacturePojo(NsInstantiate.class);
		mapper.map(obj, tgt);
		assertNotNull(tgt);
	}

	@Test
	void testNsdInstanceNotNull() {
		NsdInstance tgt = new NsdInstance();
		tgt.setAdditionalParamForNestedNs(new HashSet<>(Set.of(factory.manufacturePojo(ParamsForNestedNsd.class))));
		tgt.setAdditionalParamsForNs(new HashMap<>());
		tgt.setAdditionalParamsForVnf(new HashSet<>(Set.of(factory.manufacturePojo(ParamsForVnf.class))));
		tgt.setNestedNsInstanceData(new HashSet<>(Set.of(factory.manufacturePojo(NestedNsInstanceData.class))));
		tgt.setVnfInstanceData(new HashSet<>(Set.of(factory.manufacturePojo(VnfInstanceData.class))));
		mapper.map(null, tgt);
		NsInstantiate obj = factory.manufacturePojo(NsInstantiate.class);
		mapper.map(obj, tgt);
		assertNotNull(tgt);
	}
}
