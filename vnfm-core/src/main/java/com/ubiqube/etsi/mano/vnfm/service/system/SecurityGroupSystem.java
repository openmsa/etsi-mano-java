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
package com.ubiqube.etsi.mano.vnfm.service.system;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.SecurityGroupTask;
import com.ubiqube.etsi.mano.dao.mano.vnfm.SecurityRuleTask;
import com.ubiqube.etsi.mano.orchestrator.OrchestrationServiceV3;
import com.ubiqube.etsi.mano.orchestrator.SystemBuilder;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.SecurityGroupNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.system.AbstractVimSystemV3;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow.SecurityGroupUow;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow.SecurityRuleUow;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.SecurityRuleVt;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class SecurityGroupSystem extends AbstractVimSystemV3<SecurityGroupTask> {
	private final Vim vim;

	public SecurityGroupSystem(final Vim vim, final VimManager vimManager) {
		super(vimManager);
		this.vim = vim;
	}

	@Override
	protected SystemBuilder getImplementation(final OrchestrationServiceV3<SecurityGroupTask> orchestrationService, final VirtualTaskV3<SecurityGroupTask> virtualTask, final VimConnectionInformation vimConnectionInformation) {
		final SystemBuilder s = orchestrationService.createEmptySystemBuilder();
		final SecurityGroupUow src = new SecurityGroupUow(virtualTask, vim, vimConnectionInformation);
		final SecurityRuleTask task = new SecurityRuleTask(virtualTask.getTemplateParameters().getToscaName(), virtualTask.getTemplateParameters().getSecurityGroup(), virtualTask.getTemplateParameters().getToscaName());
		final SecurityRuleUow dst = new SecurityRuleUow(new SecurityRuleVt(task), vim, vimConnectionInformation);
		s.add(src, dst);
		return s;
	}

	@Override
	public String getVimType() {
		return "OPENSTACK_V3";
	}

	@Override
	public Class<? extends Node> getType() {
		return SecurityGroupNode.class;
	}
}
