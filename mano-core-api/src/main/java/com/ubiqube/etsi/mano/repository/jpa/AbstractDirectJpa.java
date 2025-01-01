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
package com.ubiqube.etsi.mano.repository.jpa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubiqube.etsi.mano.dao.base.BaseEntity;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.grammar.GrammarNodeResult;
import com.ubiqube.etsi.mano.grammar.GrammarParser;
import com.ubiqube.etsi.mano.repository.ContentManager;
import com.ubiqube.etsi.mano.repository.CrudRepositoryNg;
import com.ubiqube.etsi.mano.repository.NamingStrategy;
import com.ubiqube.etsi.mano.service.search.ManoSearch;

import org.jspecify.annotations.Nullable;
import jakarta.validation.constraints.NotNull;

public abstract class AbstractDirectJpa<U extends BaseEntity> extends AbstractBinaryRepository implements CrudRepositoryNg<U> {

	private final org.springframework.data.repository.CrudRepository<U, UUID> repository;
	private final GrammarParser grammarParser;
	private final ManoSearch manoSearch;

	protected AbstractDirectJpa(final org.springframework.data.repository.CrudRepository<U, UUID> repository,
			final ContentManager contentManager, final ObjectMapper jsonMapper, final NamingStrategy namingStrategy, final GrammarParser grammarParser,
			final ManoSearch manoSearch) {
		super(contentManager, jsonMapper, namingStrategy);
		this.repository = repository;
		this.grammarParser = grammarParser;
		this.manoSearch = manoSearch;
	}

	@Override
	@NotNull
	public final U get(final UUID id) {
		final Optional<U> entity = repository.findById(id);
		return entity.orElseThrow(() -> new NotFoundException(getFrontClass().getSimpleName() + " entity " + id + " not found."));
	}

	@Override
	protected abstract Class<U> getFrontClass();

	@Override
	public final void delete(final UUID id) {
		repository.deleteById(id);
		super.delete(id);
	}

	@Override
	public void deleteRepositoryOnly(final UUID id) {
		super.delete(id);
	}

	@Override
	@NotNull
	public final U save(final U entity) {
		final U res = repository.save(entity);
		mkdir(res.getId());
		return res;
	}

	@Override
	@NotNull
	public final List<U> query(@Nullable final String filter) {
		final GrammarNodeResult nodes = grammarParser.parse(filter);
		return manoSearch.getCriteria(nodes.getNodes(), getFrontClass());
	}

}
