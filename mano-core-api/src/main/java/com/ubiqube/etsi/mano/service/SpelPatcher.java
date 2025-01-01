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

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.boot.autoconfigure.web.format.WebConversionService;
import org.springframework.core.convert.ConversionService;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.TypeConverter;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.expression.spel.support.StandardTypeConverter;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.mapper.AttrHolder;
import com.ubiqube.etsi.mano.mapper.CollectHashMapListener;
import com.ubiqube.etsi.mano.mapper.JsonWalker;
import com.ubiqube.etsi.mano.mapper.SpelWriter;
import com.ubiqube.etsi.mano.service.event.model.FilterAttributes;

@Service
public class SpelPatcher implements Patcher {

	private static final Logger LOG = LoggerFactory.getLogger(SpelPatcher.class);

	private final ObjectMapper mapper;
	private final JsonWalker jsonWalker;
	private final SpelWriter spelWriter;

	public SpelPatcher(final ObjectMapper mapper, final JsonWalker jsonWalker, final SpelWriter spelWriter) {
		this.mapper = mapper;
		this.jsonWalker = jsonWalker;
		this.spelWriter = spelWriter;
		LOG.info("SpelPatcher activated.");
	}

	@Override
	public void patch(final String patchDocument, final Object entity) {
		try {
			final CollectHashMapListener beanListener = new CollectHashMapListener(entity.getClass());
			final JsonNode patch = mapper.readTree(patchDocument);
			jsonWalker.walk(patch, beanListener);
			final List<AttrHolder> attrsHolders = beanListener.getAttrs();
			final List<FilterAttributes> attrs = spelWriter.getFilterAttrs(attrsHolders);
			patchAttrs(attrs, entity);
		} catch (final IOException e) {
			throw new GenericException(e);
		}
	}

	private static void patchAttrs(final List<FilterAttributes> attrs, final Object entity) {
		final SpelParserConfiguration config = new SpelParserConfiguration(true, true); // auto create objects if null
		final ExpressionParser parser = new SpelExpressionParser(config);
		final StandardEvaluationContext modelContext = new StandardEvaluationContext(entity);
		final ConversionService sc = new WebConversionService(new DateTimeFormatters());
		final TypeConverter tc = new StandardTypeConverter(sc);
		modelContext.setTypeConverter(tc);
		LOG.debug("Patching attr: {}", attrs);
		attrs.forEach(x -> parser.parseExpression(x.getAttribute()).setValue(modelContext, x.getValue()));
	}

}
