package com.ubiqube.etsi.mano.service.mapping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionType;
import com.ubiqube.etsi.mano.dao.mano.common.GeoPoint;
import com.ubiqube.etsi.mano.dao.mano.dto.ConnectionInformationDto;
import com.ubiqube.etsi.mano.dao.mano.vim.PlanStatusType;
import com.ubiqube.etsi.mano.docker.RegistryInformations;
import com.ubiqube.etsi.mano.service.auth.model.AuthentificationInformations;

import uk.co.jemos.podam.api.PodamFactoryImpl;

class CirConnectionControllerMappingTest {

	private CirConnectionControllerMapping mapper;
	private PodamFactoryImpl podam;

	@BeforeEach
	public void setUp() {
		podam = new PodamFactoryImpl();
		podam.getStrategy().setDefaultNumberOfCollectionElements(1);
		mapper = Mappers.getMapper(CirConnectionControllerMapping.class);
	}

	@Test
	void testMap_NullInput() {
		ConnectionInformation result = mapper.map((ConnectionInformationDto) null);
		assertNull(result);
	}

	@Test
	void testMap_ValidInput() throws MalformedURLException {
		ConnectionInformationDto dto = new ConnectionInformationDto();
		AuthentificationInformations authInfo = new AuthentificationInformations();
		dto.setAuthentification(authInfo);
		dto.setConnType(ConnectionType.GENERIC);
		Map<String, String> extraMap = new LinkedHashMap<>();
		extraMap.put("key", "value");
		dto.setExtra(extraMap);
		dto.setGeoloc(new GeoPoint(1, 45));
		dto.setIgnoreSsl(true);
		dto.setName("name");
		dto.setUrl(new URL("http://example.com"));

		ConnectionInformation result = mapper.map(dto);

		assertNotNull(result);
		assertEquals(authInfo, result.getAuthentification());
		assertEquals(ConnectionType.GENERIC, result.getConnType());
		assertEquals(extraMap, result.getExtra());
		assertNotNull(result.getGeoloc());
		assertTrue(result.isIgnoreSsl());
		assertEquals("name", result.getName());
		assertNotNull(result.getUrl());
		assertEquals(PlanStatusType.STARTED, result.getServerStatus());
	}

	@Test
	void testName() throws Exception {
		ConnectionInformation ci = podam.manufacturePojo(ConnectionInformation.class);
		RegistryInformations ri = mapper.map(ci);
		assertEquals(ci.getAuthentification().getAuthParamBasic().getPassword(), ri.getPassword());
		assertNotNull(ri.getServer());
		assertEquals(ci.getAuthentification().getAuthParamBasic().getUserName(), ri.getUsername());
		ri = mapper.map((ConnectionInformation) null);
		assertNull(ri);
	}
}
