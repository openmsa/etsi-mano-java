package com.ubiqube.etsi.mano.nfvo.service.pkg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PackageVersionTest {

	@BeforeEach
	void setUp() {
		//
	}

	@ParameterizedTest
	@ValueSource(strings = { "name/version", "flavorId/name/version", "name" })
	void testElement(final String str) {
		PackageVersion pv = new PackageVersion(str);
		assertEquals("name", pv.getName());
		assertEquals(str, pv.toString());
		pv.getFlavorId();
		pv.getName();
		pv.getVersion();
	}

	@Test
	void test4Element() {
		PackageVersion pv = new PackageVersion("flavorId/name/version/bad");
		assertEquals("error", pv.toString());
		assertNull(pv.getFlavorId());
		assertNull(pv.getName());
		assertNull(pv.getVersion());
	}

	@Test
	void testSetter() {
		PackageVersion pv = new PackageVersion("flavorId/name/version");
		pv.setFlavorId("flavorId");
		assertEquals("flavorId", pv.getFlavorId());
		pv.setName("name");
		assertEquals("name", pv.getName());
		pv.setVersion("version");
		assertEquals("version", pv.getVersion());
	}
}
