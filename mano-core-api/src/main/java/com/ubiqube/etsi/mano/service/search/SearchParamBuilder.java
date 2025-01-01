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
package com.ubiqube.etsi.mano.service.search;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.grammar.GrammarNode;

import org.jspecify.annotations.Nullable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchParamBuilder<T, U> {
	private final Class<T> dbClass;
	private final MultiValueMap<String, String> requestParams;
	private final Function<T, U> mapper;
	@Nullable
	private final String excludeDefaults;
	@Nullable
	private final Set<String> mandatoryFields;
	private final Consumer<U> makeLink;
	private final List<GrammarNode> additionalNodes;
	private final Class<U> frontClass;

	public static <T, U> SearchParamBuilderBuilder<T, U> of(final Class<T> dbClass, final Class<U> frontClass) {
		final CustomSearchParamBuilder<T, U> cust = (CustomSearchParamBuilder<T, U>) new CustomSearchParamBuilder<>().dbClass((Class<Object>) dbClass);
		return cust.frontClass(frontClass);
	}

	public static class CustomSearchParamBuilder<T, U> extends SearchParamBuilderBuilder<T, U> {

		@Override
		public SearchParamBuilder<T, U> build() {
			Objects.requireNonNull(super.dbClass, "dbClass can't be null");
			Objects.requireNonNull(super.mapper, "mapper can't be null");
			Objects.requireNonNull(super.makeLink, "makeLink can't be null");
			Objects.requireNonNull(super.frontClass, "frontClass can't be null");
			super.requestParams = Optional.ofNullable(super.requestParams).orElseGet(LinkedMultiValueMap::new);
			super.additionalNodes = Optional.ofNullable(super.additionalNodes).orElseGet(List::of);
			return super.build();
		}

	}
}
