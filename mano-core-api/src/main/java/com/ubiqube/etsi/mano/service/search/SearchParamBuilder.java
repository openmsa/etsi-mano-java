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

import jakarta.annotation.Nullable;
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
