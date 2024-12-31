/**
 * Copyright (C) 2019-2024 Ubiqube.
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
 * along with this program. If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.service.search;

import static com.ubiqube.etsi.mano.Constants.getSingleField;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.grammar.GrammarNode;
import com.ubiqube.etsi.mano.grammar.GrammarNodeResult;
import com.ubiqube.etsi.mano.grammar.GrammarParser;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class SearchableService {
	private final ManoSearchResponseService searchService;

	private final GrammarParser grammarParser;

	private final ManoSearch manoSearch;

	public SearchableService(final ManoSearchResponseService searchService, final EntityManager em, final GrammarParser grammarParser, final ManoSearch manoSearch) {
		this.searchService = searchService;
		this.grammarParser = grammarParser;
		this.manoSearch = manoSearch;
	}

	@Transactional
	public <T, U> ResponseEntity<String> search(final SearchParamBuilder<T, U> params) {
		final String filter = getSingleField(params.getRequestParams(), "filter");
		final GrammarNodeResult nodes = grammarParser.parse(filter);
		final ArrayList<GrammarNode> lst = new ArrayList<>(nodes.getNodes());
		lst.addAll(params.getAdditionalNodes());
		final List<T> result = manoSearch.getCriteria(lst, params.getDbClass());
		return searchService.search(result, params);
	}

	public <U> List<U> query(final Class<U> clazz, final String filter) {
		final GrammarNodeResult nodes = grammarParser.parse(filter);
		return manoSearch.getCriteria(nodes.getNodes(), clazz);
	}
}
