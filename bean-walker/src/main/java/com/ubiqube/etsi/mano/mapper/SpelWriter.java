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
package com.ubiqube.etsi.mano.mapper;

import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import com.ubiqube.etsi.mano.service.event.model.FilterAttributes;

import org.jspecify.annotations.Nullable;

public class SpelWriter {
	private final BasicConverter basicConverter;

	public SpelWriter() {
		this.basicConverter = new BasicConverter();
	}

	public List<FilterAttributes> getFilterAttrs(final List<AttrHolder> attrs) {
		return attrs.stream()
				.map(this::handle)
				.toList();
	}

	private FilterAttributes handle(final AttrHolder attrHolder) {
		final StringBuilder sb = new StringBuilder();
		final Deque<AttrNode> stack = attrHolder.getStack();
		AttrNode prev = null;
		final Iterator<AttrNode> ite = stack.descendingIterator();
		while (ite.hasNext()) {
			final AttrNode elem = ite.next();
			final String res = handleElement(elem, prev);
			sb.append(res);
			prev = elem;
		}
		final FilterAttributes filterAttributes = new FilterAttributes();
		filterAttributes.setAttribute(sb.toString());
		if (null != attrHolder.getValue()) {
			filterAttributes.setValue(basicConverter.convert(attrHolder.getValue(), String.class));
		}

		return filterAttributes;
	}

	private static String handleElement(final AttrNode elem, final @Nullable AttrNode prev) {
		if (elem instanceof final NamedAttrNode node) {
			if (prev == null) {
				return node.getName();
			}
			// Is there another way to adress map.
			if ((prev instanceof final NamedAttrNode previous) && "userDefinedData".equals(previous.getName())) {
				return '[' + node.getName() + ']';
			}
			return '.' + node.getName();
		}
		if (elem instanceof IndiceAttrNode) {
			return elem.toString();
		}
		if (elem instanceof final ListAttrNode node) {
			if (prev == null) {
				return node.getName();
			}
			return '.' + node.getName();
		}
		if (elem instanceof final AttrMapEntryNode node) {
			return '[' + node.getName() + ']';
		}
		throw new BeanWalkerException("Unknown Node instance: " + elem.getClass());
	}
}
