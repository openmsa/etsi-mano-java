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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2.uow;

import com.ubiqube.etsi.mano.dao.mano.AffinityRule;
import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.AffinityRuleTask;
import com.ubiqube.etsi.mano.orchestrator.Context;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.AffinityRuleNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTask;
import com.ubiqube.etsi.mano.service.vim.Vim;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class VnfAffinityUowV2 extends AbstractUowV2<AffinityRuleTask> {

	private final Vim vim;
	private final VimConnectionInformation vimConnectionInformation;

	private final VirtualTask<AffinityRuleTask> task;

	public VnfAffinityUowV2(final VirtualTask<AffinityRuleTask> task, final Vim vim, final VimConnectionInformation vimConnectionInformation) {
		super(task, AffinityRuleNode.class);
		this.vim = vim;
		this.vimConnectionInformation = vimConnectionInformation;
		this.task = task;
	}

	@Override
	public String execute(final Context context) {
		final AffinityRule affinityRule = task.getParameters().getAffinityRule();
		return vim.createServerGroup(vimConnectionInformation, affinityRule);
	}

	@Override
	public String rollback(final Context context) {
		vim.deleteServerGroup(vimConnectionInformation, task.getParameters().getVimResourceId());
		return null;
	}

}