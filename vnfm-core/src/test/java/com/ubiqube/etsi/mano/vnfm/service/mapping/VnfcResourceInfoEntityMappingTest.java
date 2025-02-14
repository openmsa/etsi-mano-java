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
