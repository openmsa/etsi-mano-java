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
package com.ubiqube.etsi.mano.vnfm.service.system;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfIndicatorTask;
import com.ubiqube.etsi.mano.orchestrator.OrchestrationServiceV3;
import com.ubiqube.etsi.mano.orchestrator.SystemBuilder;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfIndicator;
import com.ubiqube.etsi.mano.orchestrator.uow.UnitOfWorkV3;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.system.AbstractVimSystemV3;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.vnfm.service.VnfMonitoringService;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v3.uow.VnfIndicatorUowV3;

@Service
public class VnfIndicatorSystem extends AbstractVimSystemV3<VnfIndicatorTask> {
	private final VnfMonitoringService vnfMonitoringService;

	public VnfIndicatorSystem(final VnfMonitoringService vnfMonitoringService, final VimManager vimManager) {
		super(vimManager);
		this.vnfMonitoringService = vnfMonitoringService;
	}

	@Override
	protected SystemBuilder<UnitOfWorkV3<VnfIndicatorTask>> getImplementation(final OrchestrationServiceV3<VnfIndicatorTask> orchestrationService, final VirtualTaskV3<VnfIndicatorTask> virtualTask, final VimConnectionInformation vimConnectionInformation) {
		return orchestrationService.systemBuilderOf(new VnfIndicatorUowV3(virtualTask, getType()));
	}

	@Override
	public String getVimType() {
		return "OPENSTACK_V3";
	}

	@Override
	public Class<? extends Node> getType() {
		return VnfIndicator.class;
	}

}
