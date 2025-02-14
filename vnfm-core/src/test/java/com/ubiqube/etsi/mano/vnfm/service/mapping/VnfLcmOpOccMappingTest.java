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
