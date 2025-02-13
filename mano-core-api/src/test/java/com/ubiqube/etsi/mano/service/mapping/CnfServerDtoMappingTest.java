package com.ubiqube.etsi.mano.service.mapping;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.ubiqube.etsi.mano.dao.mano.cnf.CnfServer;
import com.ubiqube.etsi.mano.dao.mano.dto.CnfServerDto;

class CnfServerDtoMappingTest {
	private final CnfServerDtoMapping mapper = Mappers.getMapper(CnfServerDtoMapping.class);

	@Test
	void test() {
		assertNull(mapper.map(null));
		CnfServerDto o = new CnfServerDto();
		CnfServer r = mapper.map(o);
		assertNotNull(r);
	}

}
