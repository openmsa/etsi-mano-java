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
