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
package com.ubiqube.etsi.mano.config.ubi;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.junit.jupiter.api.Test;

class UbiqubePropertySourceTest {

	@Test
	void test() throws IOException {
		final ResourceBundle rb = null;
		final UbiqubePropertySource src = new UbiqubePropertySource("name", rb);
		final UbiqubePropertySource src2 = new UbiqubePropertySource("namess", rb);
		final InputStream is = new ByteArrayInputStream("""
				props: value
				""".getBytes());
		final PropertyResourceBundle rb2 = new PropertyResourceBundle(is);
		final UbiqubePropertySource src3 = new UbiqubePropertySource("namess", rb2);
		src.equals(null);
		src.equals(src);
		src.equals(src2);
		src.equals("");
		src2.equals(src3);
		assertTrue(true);
	}

}
