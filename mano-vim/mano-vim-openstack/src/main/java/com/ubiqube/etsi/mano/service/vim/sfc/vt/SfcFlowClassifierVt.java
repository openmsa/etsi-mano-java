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
package com.ubiqube.etsi.mano.service.vim.sfc.vt;

import java.util.ArrayList;
import java.util.List;

import com.ubiqube.etsi.mano.orchestrator.NamedDependency;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfPortNode;
import com.ubiqube.etsi.mano.service.graph.vt.NsVtBase;
import com.ubiqube.etsi.mano.service.vim.sfc.enity.SfcFlowClassifierTask;
import com.ubiqube.etsi.mano.service.vim.sfc.node.FlowClassifierNode;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class SfcFlowClassifierVt extends NsVtBase<SfcFlowClassifierTask> {

	private final SfcFlowClassifierTask task;

	public SfcFlowClassifierVt(final SfcFlowClassifierTask nt) {
		super(nt);
		this.task = nt;
	}

	@Override
	public List<NamedDependency> getNameDependencies() {
		final List<NamedDependency> l = new ArrayList<>();
		if (task.getClassifier().getLogicalDestinationPort() != null) {
			l.add(new NamedDependency(VnfPortNode.class, task.getClassifier().getLogicalDestinationPort()));
		}
		if (task.getClassifier().getLogicalSourcePort() != null) {
			l.add(new NamedDependency(VnfPortNode.class, task.getClassifier().getLogicalSourcePort()));
		}
		return l;
	}

	@Override
	public List<NamedDependency> getNamedProduced() {
		return List.of(new NamedDependency(FlowClassifierNode.class, task.getToscaName()));
	}

	@Override
	public String getFactoryProviderId() {
		return "SFC-FLOW-CLASSIFIER";
	}

	@Override
	public String getVimProviderId() {
		return "OPENSTACK_V3";
	}

}
