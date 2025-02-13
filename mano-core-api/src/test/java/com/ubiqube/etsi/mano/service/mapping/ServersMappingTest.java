package com.ubiqube.etsi.mano.service.mapping;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.ubiqube.etsi.mano.dao.mano.config.ServerDto;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;

class ServersMappingTest {
	private final ServersMapping mapper = Mappers.getMapper(ServersMapping.class);

	@Test
	void test() {
		assertNull(mapper.map((ServerDto) null));
		ServerDto o = new ServerDto();
		o.setUrl("http://localhost");
		Servers r = mapper.map(o);
		assertNotNull(r);
	}

}
