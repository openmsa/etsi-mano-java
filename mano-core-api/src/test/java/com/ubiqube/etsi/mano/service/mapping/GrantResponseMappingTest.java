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
