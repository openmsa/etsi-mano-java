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

import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.VlProtocolData;
import com.ubiqube.etsi.mano.dao.mano.v2.NetworkTask;
import com.ubiqube.etsi.mano.orchestrator.Context;
import com.ubiqube.etsi.mano.orchestrator.NamedDependency;
import com.ubiqube.etsi.mano.orchestrator.NamedDependency2d;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.DnsZone;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTask;
import com.ubiqube.etsi.mano.service.vim.Vim;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class VirtualLinkUowV2 extends AbstractUowV2<NetworkTask> {
	private final Vim vim;
	private final VimConnectionInformation vimConnectionInformation;
	private final VlProtocolData vlProtocolData;
	private final NetworkTask task;

	public VirtualLinkUowV2(final VirtualTask<NetworkTask> task, final Vim vim, final VimConnectionInformation vimConnectionInformation) {
		super(task, Network.class);
		this.vim = vim;
		this.vimConnectionInformation = vimConnectionInformation;
		this.task = task.getParameters();
		vlProtocolData = task.getParameters().getVnfVl().getVlProfileEntity().getVirtualLinkProtocolData().iterator().next();
	}

	@Override
	public String execute(final Context context) {
		final String domainName = context.get(DnsZone.class, getTask().getParameters().getToscaName());
		return vim.network(vimConnectionInformation).createNetwork(vlProtocolData, task.getAlias(), domainName, task.getQosPolicy());
	}

	@Override
	public String rollback(final Context context) {
		final NetworkTask params = getTask().getParameters();
		vim.network(vimConnectionInformation).deleteVirtualLink(params.getVimResourceId());
		return null;
	}

	@Override
	public List<NamedDependency> getNameDependencies() {
		return List.of();
	}

	@Override
	public List<NamedDependency> getNamedProduced() {
		return List.of(new NamedDependency(getNode(), task.getToscaName()));
	}

	@Override
	public List<NamedDependency2d> get2dDependencies() {
		return List.of();
	}

}
