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

import java.util.ArrayList;
import java.util.List;

import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.v2.ExternalCpTask;
import com.ubiqube.etsi.mano.orchestrator.Context;
import com.ubiqube.etsi.mano.orchestrator.NamedDependency;
import com.ubiqube.etsi.mano.orchestrator.NamedDependency2d;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfExtCp;
import com.ubiqube.etsi.mano.orchestrator.uow.Relation;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTask;
import com.ubiqube.etsi.mano.service.vim.Vim;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class VnfExtCpUowV2 extends AbstractUowV2<ExternalCpTask> {
	private final Vim vim;
	private final VimConnectionInformation vimConnectionInformation;
	private final ExternalCpTask task;

	public VnfExtCpUowV2(final VirtualTask<ExternalCpTask> task, final Vim vim, final VimConnectionInformation vimConnectionInformation) {
		super(task, VnfExtCp.class);
		this.vim = vim;
		this.vimConnectionInformation = vimConnectionInformation;
		this.task = task.getParameters();
	}

	@Override
	public String execute(final Context context) {
		final com.ubiqube.etsi.mano.dao.mano.VnfExtCp extCp = task.getVnfExtCp();
		final String networkId = context.get(Network.class, extCp.getInternalVirtualLink());
		final String extNetwork = context.get(Network.class, extCp.getExternalVirtualLink());
		return vim.network(vimConnectionInformation).createRouter(task.getAlias(), networkId, extNetwork);
	}

	@Override
	public String rollback(final Context context) {
		final ExternalCpTask param = getTask().getParameters();
		vim.network(vimConnectionInformation).deleteRouter(param.getVimResourceId());
		return null;
	}

	@Override
	public List<NamedDependency> getNameDependencies() {
		final com.ubiqube.etsi.mano.dao.mano.VnfExtCp extCp = task.getVnfExtCp();
		final List<NamedDependency> ret = new ArrayList<>();
		ret.add(new NamedDependency(Network.class, extCp.getInternalVirtualLink()));
		ret.add(new NamedDependency(Network.class, extCp.getExternalVirtualLink()));
		return ret;
	}

	@Override
	public List<NamedDependency> getNamedProduced() {
		return List.of(new NamedDependency(getNode(), task.getAlias()));
	}

	@Override
	public List<NamedDependency2d> get2dDependencies() {
		final com.ubiqube.etsi.mano.dao.mano.VnfExtCp extCp = task.getVnfExtCp();
		final List<NamedDependency2d> ret = new ArrayList<>();
		ret.add(new NamedDependency2d(Network.class, extCp.getInternalVirtualLink(), Relation.ONE_TO_ONE));
		ret.add(new NamedDependency2d(Network.class, extCp.getExternalVirtualLink(), Relation.ONE_TO_ONE));
		return ret;

	}

}
