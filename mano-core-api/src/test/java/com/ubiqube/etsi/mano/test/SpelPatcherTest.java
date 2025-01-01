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
package com.ubiqube.etsi.mano.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubiqube.etsi.mano.dao.mano.ai.KeystoneAuthV3;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.mapper.JsonWalker;
import com.ubiqube.etsi.mano.mapper.SpelWriter;
import com.ubiqube.etsi.mano.service.SpelPatcher;

class SpelPatcherTest {

	@Test
	void testName() throws Exception {
		final ObjectMapper mapper = new ObjectMapper();
		final JsonWalker jsonWalker = new JsonWalker(mapper);
		final SpelWriter spelWriter = new SpelWriter();
		final SpelPatcher sp = new SpelPatcher(mapper, jsonWalker, spelWriter);
		final TestVim vim = new TestVim();
		sp.patch("""
				{
					"accessInfo": {
						"username": "admin55"
					},
					"geoloc": {
						"lng": 45.75801,
						"lat": 4.8001016
					},
					"basicList": [ "a", "b", "c" ],
					"advList": [
						{
							"lng": 45.75801,
							"lat": 4.8001016
						}
					],
					"date": "2024-11-03T07:56:58.255739Z"
				}
				""", vim);
		assertNotNull(vim.getAccessInfo());
		assertEquals("admin55", vim.getAccessInfo().get("username"));
		assertEquals(45.75801, vim.getGeoloc().getLng());
		assertNotNull(vim.getBasicList());
		assertEquals(3, vim.getBasicList().size());
		assertEquals(1, vim.getAdvList().size());
		assertEquals(4.8001016, vim.getAdvList().get(0).getLat());
		assertNotNull(vim.getDate());
		assertEquals("2024-11-03T07:56:58.255739Z", vim.getDate().toString());
	}

	@Test
	void testSpel() {
		final SpelParserConfiguration config = new SpelParserConfiguration(true, true); // auto create objects if null
		final ExpressionParser parser = new SpelExpressionParser(config);
		final VimConnectionInformation entity = new VimConnectionInformation();
		final KeystoneAuthV3 ai = new KeystoneAuthV3();
		entity.setAccessInfo(ai);
		final StandardEvaluationContext modelContext = new StandardEvaluationContext(entity);
		parser.parseExpression("accessInfo.username").setValue(modelContext, "303ec59e-be2d-499a-afc3-e777f69fbff0");
		assertNotNull(entity.getAccessInfo());
		assertEquals("303ec59e-be2d-499a-afc3-e777f69fbff0", ai.getUsername());
	}

}
