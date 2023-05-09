/**
 *     Copyright (C) 2019-2023 Ubiqube.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.exception.GenericException;

@SuppressWarnings("static-method")
class ClassPathConverterTest {

	@Test
	void testBadClass() throws Exception {
		final ClassPathConverter cpc = new ClassPathConverter();
		final Class<? extends ClassPathConverterTest> clazz = getClass();
		assertThrows(GenericException.class, () -> cpc.convert(clazz));
	}

	@Test
	void testNsdClass() throws Exception {
		final ClassPathConverter cpc = new ClassPathConverter();
		final String res = cpc.convert(NsdPackage.class);
		assertNotNull(res);
	}

	@Test
	void testList() throws Exception {
		final ClassPathConverter cpc = new ClassPathConverter();
		final Set<Class<?>> res = cpc.getList();
		assertNotNull(res);
	}
}
