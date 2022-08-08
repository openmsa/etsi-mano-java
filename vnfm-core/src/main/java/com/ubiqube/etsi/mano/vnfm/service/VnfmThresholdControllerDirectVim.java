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
package com.ubiqube.etsi.mano.vnfm.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.dao.mano.pm.Threshold;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.ThresholdJpa;
import com.ubiqube.etsi.mano.service.SearchableService;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.service.vim.mon.VimMonitoring;
import com.ubiqube.etsi.mano.vnfm.controller.vnfpm.VnfmThresholdController;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfBlueprintJpa;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class VnfmThresholdControllerDirectVim implements VnfmThresholdController {

	private final ThresholdJpa thresholdJpa;

	private final VnfBlueprintJpa vnfBlueprintJpa;

	private final VimManager vimManager;

	private final SearchableService searchableService;

	public VnfmThresholdControllerDirectVim(final ThresholdJpa thresholdJpa, final VnfBlueprintJpa vnfBlueprintJpa, final VimManager vimManager, final SearchableService searchableService) {
		this.thresholdJpa = thresholdJpa;
		this.vnfBlueprintJpa = vnfBlueprintJpa;
		this.vimManager = vimManager;
		this.searchableService = searchableService;
	}

	@Override
	public Threshold save(final Threshold res) {
		final Optional<VnfBlueprint> obp = vnfBlueprintJpa.findById(res.getObjectInstanceId());
		final VnfBlueprint bp = obp.orElseThrow(() -> new GenericException("Could not find VNF instance :" + res.getObjectInstanceId()));
		final UUID systemId = bp.getVimConnections().iterator().next().getId();
		final Vim vim = vimManager.getVimById(systemId);
		final VimMonitoring mon = vim.getMonitoring(bp.getVimConnections().iterator().next());
		res.getSubObjectInstanceIds().stream().forEach(x -> {
			final String resource = mon.registerAlarm(x.getId(), res.getCriteria().getPerformanceMetric(),
					res.getCriteria().getSimpleThresholdDetails().getThresholdValue(), res.getCriteria().getSimpleThresholdDetails().getHysteresis(), "callback url");
			x.setResource(resource);
			x.setSystemId(systemId);
		});
		return thresholdJpa.save(res);
	}

	@Override
	public void delete(final UUID id) {
		final Threshold obj = findById(id);
		obj.getSubObjectInstanceIds().forEach(x -> {
			final Vim vim = vimManager.getVimById(x.getSystemId());
			final VimMonitoring mon = vim.getMonitoring(vimManager.findVimById(x.getSystemId()));
			mon.removeAlarm(x.getResource());
		});
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
