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
