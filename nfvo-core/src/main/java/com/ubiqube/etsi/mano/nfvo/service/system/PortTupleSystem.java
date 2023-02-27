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
package com.ubiqube.etsi.mano.nfvo.service.system;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.nfvo.service.plan.uow.PortTupleUow;
import com.ubiqube.etsi.mano.orchestrator.OrchestrationServiceV3;
import com.ubiqube.etsi.mano.orchestrator.SystemBuilder;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.contrail.PortTupleNode;
import com.ubiqube.etsi.mano.orchestrator.uow.UnitOfWorkV3;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.system.AbstractVimSystemV3;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.tf.entities.PortTupleTask;

@Service
public class PortTupleSystem extends AbstractVimSystemV3<PortTupleTask> {

	protected PortTupleSystem(final VimManager vimManager) {
		super(vimManager);
	}

	@Override
	public String getVimType() {
		return "CONTRAIL";
	}

	@Override
	public Class<? extends Node> getType() {
		return PortTupleNode.class;
	}

	@Override
	protected SystemBuilder<UnitOfWorkV3<PortTupleTask>> getImplementation(final OrchestrationServiceV3<PortTupleTask> orchestrationService, final VirtualTaskV3<PortTupleTask> virtualTask, final VimConnectionInformation vimConnectionInformation) {
		return orchestrationService.systemBuilderOf(new PortTupleUow(virtualTask, getSystemConnections()));
	}

}
