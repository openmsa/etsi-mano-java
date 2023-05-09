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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow;

import com.ubiqube.etsi.mano.dao.mano.SubNetworkTask;
import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.SubNetwork;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.vim.Vim;

import jakarta.annotation.Nullable;

public class VnfSubnetworkUow extends AbstractVnfmUow<SubNetworkTask> {
	private final Vim vim;
	private final VimConnectionInformation vimConnectionInformation;
	private final SubNetworkTask task;

	public VnfSubnetworkUow(final VirtualTaskV3<SubNetworkTask> task, final Vim vim, final VimConnectionInformation vimConnectionInformation) {
		super(task, SubNetwork.class);
		this.vim = vim;
		this.vimConnectionInformation = vimConnectionInformation;
		this.task = task.getTemplateParameters();
	}

	@Override
	public @Nullable String execute(final Context3d context) {
		final String networkId = context.get(Network.class, task.getParentName());
		return vim.network(vimConnectionInformation).createSubnet(task.getL3Data(), task.getIpPool(), networkId);
	}

	@Override
	public @Nullable String rollback(final Context3d context) {
		// params.getVim().deleteSubnet(params.getVimConnectionInformation(),
		// params.getVimResourceId());
		return null;
	}

}
