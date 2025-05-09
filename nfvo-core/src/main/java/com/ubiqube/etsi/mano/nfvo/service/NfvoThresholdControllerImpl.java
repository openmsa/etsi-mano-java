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
package com.ubiqube.etsi.mano.nfvo.service;

import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.controller.nspm.NfvoThresholdController;
import com.ubiqube.etsi.mano.dao.mano.pm.Threshold;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.nfvo.service.repository.ThresholdRepositoryService;
import com.ubiqube.etsi.mano.service.search.SearchParamBuilder;
import com.ubiqube.etsi.mano.service.search.SearchableService;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class NfvoThresholdControllerImpl implements NfvoThresholdController {

	private final ThresholdRepositoryService thresholdJpa;
	private final SearchableService searchableService;

	public NfvoThresholdControllerImpl(final SearchableService searchableService, final ThresholdRepositoryService thresholdJpa) {
		this.searchableService = searchableService;
		this.thresholdJpa = thresholdJpa;
	}

	@Override
	public Threshold save(final Threshold threshold) {
		return thresholdJpa.save(threshold);
	}

	@Override
	public void delete(final UUID id) {
		findById(id);
		thresholdJpa.deleteById(id);
	}

	@Override
	public Threshold findById(final UUID id) {
		return thresholdJpa.findById(id).orElseThrow(() -> new NotFoundException("Could not find Threshold: " + id));
	}

	@Override
	public <U> ResponseEntity<String> search(final MultiValueMap<String, String> requestParams, final Function<Threshold, U> mapper, final String excludeDefaults, final Set<String> mandatoryFields, final Consumer<U> makeLink, final Class<U> frontClass) {
		final SearchParamBuilder<Threshold, U> params = SearchParamBuilder.of(Threshold.class, frontClass)
				.requestParams(requestParams)
				.mapper(mapper)
				.excludeDefaults(excludeDefaults)
				.mandatoryFields(mandatoryFields)
				.makeLink(makeLink)
				.build();
		return searchableService.search(params);
	}

}
