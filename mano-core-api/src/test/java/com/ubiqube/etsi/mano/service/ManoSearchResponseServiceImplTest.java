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
package com.ubiqube.etsi.mano.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.grammar.JsonBeanUtil;
import com.ubiqube.etsi.mano.service.search.ManoSearchResponseServiceImpl;
import com.ubiqube.etsi.mano.service.search.SearchParamBuilder;

@ExtendWith(MockitoExtension.class)
class ManoSearchResponseServiceImplTest {
	@Mock
	private JsonBeanUtil jsonBeanUtil;

	@Test
	void testName() {
		final ManoSearchResponseServiceImpl msrs = createService();
		final SearchParamBuilder<? extends ManoSearchResponseServiceImplTest, ? extends ManoSearchResponseServiceImplTest> p = SearchParamBuilder.of(getClass(), getClass())
				.makeLink(x -> {
					//
				})
				.mapper(x -> null)
				.build();
		msrs.search(List.of(), p);
		assertTrue(true);
	}

	private ManoSearchResponseServiceImpl createService() {
		return new ManoSearchResponseServiceImpl(jsonBeanUtil);
	}
}
