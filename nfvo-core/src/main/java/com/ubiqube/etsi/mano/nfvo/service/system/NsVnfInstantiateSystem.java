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
package com.ubiqube.etsi.mano.nfvo.service.system;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfInstantiateTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.nfvo.service.plan.uow.VnfInstantiateUow;
import com.ubiqube.etsi.mano.orchestrator.OrchestrationServiceV3;
import com.ubiqube.etsi.mano.orchestrator.SystemBuilder;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnfInstantiateNode;
import com.ubiqube.etsi.mano.orchestrator.uow.UnitOfWorkV3;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.VnfmInterface;
import com.ubiqube.etsi.mano.service.system.AbstractVimSystemV3;
import com.ubiqube.etsi.mano.service.vim.VimManager;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class NsVnfInstantiateSystem extends AbstractVimSystemV3<NsVnfInstantiateTask> {
	private final VnfmInterface vnfm;

	public NsVnfInstantiateSystem(final VnfmInterface vnfm, final VimManager vimManager) {
		super(vimManager);
		this.vnfm = vnfm;
	}

	@Override
	protected SystemBuilder<UnitOfWorkV3<NsVnfInstantiateTask>> getImplementation(final OrchestrationServiceV3<NsVnfInstantiateTask> orchestrationService, final VirtualTaskV3<NsVnfInstantiateTask> virtualTask, final VimConnectionInformation vimConnectionInformation) {
		return orchestrationService.systemBuilderOf(new VnfInstantiateUow(virtualTask, vnfm));
	}

	@Override
	public String getVimType() {
		return "OPENSTACK_V3";
	}

	@Override
	public Class<? extends Node> getType() {
		return VnfInstantiateNode.class;
	}
}
