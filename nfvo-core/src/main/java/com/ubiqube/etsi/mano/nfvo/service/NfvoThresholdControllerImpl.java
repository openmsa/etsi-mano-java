/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
package com.ubiqube.etsi.mano.nfvo.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.controller.nspm.NfvoThresholdController;
import com.ubiqube.etsi.mano.dao.mano.pm.Threshold;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.ThresholdJpa;
import com.ubiqube.etsi.mano.service.SearchableService;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class NfvoThresholdControllerImpl implements NfvoThresholdController {

	private final ThresholdJpa thresholdJpa;
	private final SearchableService searchableService;

	public NfvoThresholdControllerImpl(final SearchableService searchableService, final ThresholdJpa thresholdJpa) {
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
	public <U> ResponseEntity<String> search(final MultiValueMap<String, String> requestParams, final Class<U> clazz, final String excludeDefaults, final Set<String> mandatoryFields, final Consumer<U> makeLink) {
		return searchableService.search(Threshold.class, requestParams, clazz, excludeDefaults, mandatoryFields, makeLink, List.of());
	}

}
