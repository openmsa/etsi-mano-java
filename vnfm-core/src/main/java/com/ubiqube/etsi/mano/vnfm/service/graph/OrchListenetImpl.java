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
package com.ubiqube.etsi.mano.vnfm.service.graph;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfTask;
import com.ubiqube.etsi.mano.orchestrator.OrchExecutionListener;
import com.ubiqube.etsi.mano.orchestrator.uow.UnitOfWorkV3;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;

import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class OrchListenetImpl implements OrchExecutionListener<VnfTask> {

	private static final Logger LOG = LoggerFactory.getLogger(OrchListenetImpl.class);
	private final VnfBlueprint blueprint;
	private final VnfLiveInstanceJpa vnfLiveInstanceJpa;

	public OrchListenetImpl(final VnfBlueprint blueprint, final VnfLiveInstanceJpa vnfLiveInstanceJpa) {
		this.blueprint = blueprint;
		this.vnfLiveInstanceJpa = vnfLiveInstanceJpa;
	}

	@Override
	public void onStart(final VirtualTaskV3<VnfTask> task) {
		LOG.info("Starting {}", task);
		final VnfTask resource = task.getTemplateParameters();
		resource.setStatus(PlanStatusType.STARTED);
		resource.setEndDate(LocalDateTime.now());
	}

	@Override
	public void onTerminate(final UnitOfWorkV3<VnfTask> uaow, final @Nullable String res) {
		LOG.info("Terminate {} => {}", uaow.getTask(), res);
		final VnfTask resource = uaow.getTask().getTemplateParameters();
		resource.setVimResourceId(res);
		resource.setStatus(PlanStatusType.SUCCESS);
		resource.setEndDate(LocalDateTime.now());
		if ((resource.getChangeType() == ChangeType.ADDED) && (res != null) && (resource.getId() != null)) {
			final VnfLiveInstance vli = new VnfLiveInstance(blueprint.getInstance(), null, resource, blueprint, resource.getVimResourceId(), resource.getVimConnectionId());
			vnfLiveInstanceJpa.save(vli);
		}
	}

	@Override
	public void onError(final UnitOfWorkV3<VnfTask> uaow, final RuntimeException e) {
		final VnfTask resource = uaow.getTask().getTemplateParameters();
		resource.setStatus(PlanStatusType.FAILED);
		resource.setEndDate(LocalDateTime.now());
	}

}
