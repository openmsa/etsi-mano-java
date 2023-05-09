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
package com.ubiqube.etsi.mano.mapper;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class JsonWalker {

	private static final Logger LOG = LoggerFactory.getLogger(JsonWalker.class);

	private final ObjectMapper mapper;

	public JsonWalker(final ObjectMapper mapper) {
		this.mapper = mapper;
	}

	public void walk(final String patchDocument, final BeanListener beanListener) {
		try {
			LOG.debug("JsonWalking ");
			final JsonNode patch = mapper.readTree(patchDocument);
			walk(patch, beanListener);
		} catch (final IOException e) {
			throw new BeanWalkerException(e);
		}
	}

	@SuppressWarnings("static-method")
	public void walk(final JsonNode patch, final BeanListener beanListener) {
		try {
			walkInner(patch, beanListener);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new BeanWalkerException(e);
		}
	}

	private static void walkInner(final JsonNode jsonNode, final BeanListener beanListener) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (jsonNode.isObject()) {
			final Iterator<Map.Entry<String, JsonNode>> iter = jsonNode.fields();
			while (iter.hasNext()) {
				final Map.Entry<String, JsonNode> entry = iter.next();
				beanListener.complexStart(entry.getKey());
				walkInner(entry.getValue(), beanListener);
				beanListener.complexEnd();
			}
		} else if (jsonNode.isArray()) {
			final ArrayNode arrayNode = (ArrayNode) jsonNode;
			for (int i = 0; i < arrayNode.size(); i++) {
				final JsonNode val = arrayNode.get(i);
				beanListener.listElementStart(i);
				if (val.isValueNode()) {
					beanListener.addProperty(val.asText());
				} else {
					walkInner(val, beanListener);
				}
				beanListener.listElementEnd();
			}
		} else if (jsonNode.isValueNode()) {
			beanListener.addProperty(jsonNode.asText());
		}
	}

}
