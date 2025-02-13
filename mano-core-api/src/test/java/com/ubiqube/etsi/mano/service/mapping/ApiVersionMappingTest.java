package com.ubiqube.etsi.mano.service.mapping;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.ubiqube.etsi.mano.dao.mano.version.ApiVersion;
import com.ubiqube.etsi.mano.model.ApiVersionInformation;
import com.ubiqube.etsi.mano.model.ApiVersionInformationApiVersions;

class ApiVersionMappingTest {
	private final ApiVersionMapping mapper = Mappers.getMapper(ApiVersionMapping.class);

	@Test
	void test() {
		assertNull(mapper.map((ApiVersionInformation) null));
		ApiVersionInformation avi = new ApiVersionInformation();
		ApiVersion r = mapper.map(avi);
		assertNotNull(r);
	}

	@Test
	void testNullList() {
		assertNull(mapper.map((ApiVersionInformation) null));
		ApiVersionInformation avi = new ApiVersionInformation();
		avi.setApiVersions(null);
		ApiVersion r = mapper.map(avi);
		assertNotNull(r);
	}

	@Test
	void testFullList() {
		assertNull(mapper.map((ApiVersionInformation) null));
		ApiVersionInformation avi = new ApiVersionInformation();
		ApiVersionInformationApiVersions e1 = new ApiVersionInformationApiVersions();
		avi.setApiVersions(List.of(e1));
		ApiVersion r = mapper.map(avi);
		assertNotNull(r);
	}

	@Test
	void testApiVersionInformationApiVersions() {
		assertNull(mapper.map((ApiVersionInformationApiVersions) null));
	}
}
