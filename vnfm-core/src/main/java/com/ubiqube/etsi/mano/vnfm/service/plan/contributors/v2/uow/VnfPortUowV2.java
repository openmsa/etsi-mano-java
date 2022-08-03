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

import java.util.List;

import com.ubiqube.etsi.mano.dao.mano.ExtManagedVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.VnfLinkPort;
import com.ubiqube.etsi.mano.dao.mano.common.NicType;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfPortTask;
import com.ubiqube.etsi.mano.orchestrator.Context;
import com.ubiqube.etsi.mano.orchestrator.NamedDependency;
import com.ubiqube.etsi.mano.orchestrator.NamedDependency2d;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.SubNetwork;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfPortNode;
import com.ubiqube.etsi.mano.orchestrator.uow.Relation;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTask;
import com.ubiqube.etsi.mano.service.vim.Vim;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class VnfPortUowV2 extends AbstractUowV2<VnfPortTask> {
	private final Vim vim;
	private final VimConnectionInformation vimConnectionInformation;
	private final VnfPortTask task;

	public VnfPortUowV2(final VirtualTask<VnfPortTask> task, final Vim vim, final VimConnectionInformation vimConnectionInformation) {
		super(task, VnfPortNode.class);
		this.vim = vim;
		this.vimConnectionInformation = vimConnectionInformation;
		this.task = task.getParameters();
	}

	@Override
	public String execute(final Context context) {
		if (task.getExternal() != null) {
			final ExtManagedVirtualLinkDataEntity ext = task.getExternal();
			return vim.network(vimConnectionInformation).createPort(getTask().getAlias(), ext.getResourceId(), null, null, NicType.fromValue(getTask().getParameters().getVnfLinkPort().getVnicType()));
		}
		final VnfLinkPort extCp = task.getVnfLinkPort();
		final String extNetwork = context.get(Network.class, extCp.getVirtualLink());
		return vim.network(vimConnectionInformation).createPort(getTask().getAlias(), extNetwork, null, null, NicType.fromValue(getTask().getParameters().getVnfLinkPort().getVnicType()));
	}

	@Override
	public String rollback(final Context context) {
		final VnfPortTask param = getTask().getParameters();
		vim.network(vimConnectionInformation).deletePort(param.getVimResourceId());
		return null;
	}

	@Override
	public List<NamedDependency> getNameDependencies() {
		final VnfLinkPort extCp = task.getVnfLinkPort();
		return List.of(new NamedDependency(SubNetwork.class, "sub-" + extCp.getVirtualLink()));
	}

	@Override
	public List<NamedDependency> getNamedProduced() {
		return List.of(new NamedDependency(getNode(), task.getToscaName()));
	}

	@Override
	public List<NamedDependency2d> get2dDependencies() {
		final VnfLinkPort extCp = task.getVnfLinkPort();
		return List.of(new NamedDependency2d(SubNetwork.class, extCp.getVirtualLink(), Relation.MANY_TO_ONE));
	}
}
