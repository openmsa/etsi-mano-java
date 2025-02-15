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
package com.ubiqube.etsi.mano.service.search.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.grammar.GrammarException;
import com.ubiqube.etsi.mano.grammar.GrammarValue;
import com.ubiqube.etsi.mano.service.search.SearchException;

class ConvertHelperTest {

	@Test
	void testConvertComparableString() {
		GrammarValue gv = new GrammarValue("testString");
		Comparable result = ConvertHelper.convertComparable(gv, String.class);
		assertEquals("testString", result);
	}

	@Test
	void testConvertComparableLong() {
		GrammarValue gv = new GrammarValue("123456789");
		Comparable result = ConvertHelper.convertComparable(gv, Long.class);
		assertEquals(123456789L, result);
	}

	@Test
	void testConvertComparableInteger() {
		GrammarValue gv = new GrammarValue("12345");
		Comparable result = ConvertHelper.convertComparable(gv, Integer.class);
		assertEquals(12345, result);
	}

	@Test
	void testConvertComparableUUID() {
		UUID uuid = UUID.randomUUID();
		GrammarValue gv = new GrammarValue(uuid.toString());
		Comparable result = ConvertHelper.convertComparable(gv, UUID.class);
		assertEquals(uuid, result);
	}

	@Test
	void testConvertComparableEnum() {
		GrammarValue gv = new GrammarValue(TestEnum.VALUE1.name());
		Comparable result = ConvertHelper.convertComparable(gv, TestEnum.class);
		assertEquals(TestEnum.VALUE1, result);
	}

	@Test
	void testConvertComparableInvalidEnum() {
		GrammarValue gv = new GrammarValue("INVALID");
		assertThrows(GrammarException.class, () -> ConvertHelper.convertComparable(gv, TestEnum.class));
	}

	@Test
	void testConvertComparableUnknownClass() {
		GrammarValue gv = new GrammarValue("test");
		assertThrows(SearchException.class, () -> ConvertHelper.convertComparable(gv, BigDecimal.class));
	}

	private enum TestEnum {
		VALUE1, VALUE2
	}
}
