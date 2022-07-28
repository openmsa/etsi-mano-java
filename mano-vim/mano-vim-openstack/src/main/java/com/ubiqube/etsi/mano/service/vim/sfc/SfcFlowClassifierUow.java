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
package com.ubiqube.etsi.mano.service.vim.sfc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ubiqube.etsi.mano.dao.mano.nsd.Classifier;
import com.ubiqube.etsi.mano.orchestrator.Context;
import com.ubiqube.etsi.mano.orchestrator.NamedDependency;
import com.ubiqube.etsi.mano.orchestrator.entities.SystemConnections;
import com.ubiqube.etsi.mano.orchestrator.nodes.mec.VnfContextExtractorNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnffgLoadbalancerNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfPortNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTask;
import com.ubiqube.etsi.mano.service.graph.AbstractUnitOfWork;
import com.ubiqube.etsi.mano.service.vim.OsSfc;
import com.ubiqube.etsi.mano.service.vim.sfc.enity.SfcFlowClassifierTask;
import com.ubiqube.etsi.mano.service.vim.sfc.node.FlowClassifierNode;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class SfcFlowClassifierUow extends AbstractUnitOfWork<SfcFlowClassifierTask> {

	private final SystemConnections vimConnectionInformation;
	private final OsSfc sfc;
	private final SfcFlowClassifierTask task;

	public SfcFlowClassifierUow(final VirtualTask<SfcFlowClassifierTask> task, final SystemConnections vimConnectionInformation) {
		super(task, FlowClassifierNode.class);
		this.vimConnectionInformation = vimConnectionInformation;
		sfc = new OsSfc();
		this.task = task.getParameters();
	}

	@Override
	public String execute(final Context context) {
		final Classifier classifier = task.getClassifier();
		final String src = Optional.ofNullable(classifier.getLogicalSourcePort()).map(x -> context.get(VnfPortNode.class, x)).orElse(null);
		final String dst = Optional.ofNullable(classifier.getLogicalDestinationPort()).map(x -> context.get(VnfPortNode.class, x)).orElse(null);
		return sfc.createFlowClassifier(vimConnectionInformation, classifier, src, dst);
	}

	@Override
	public String rollback(final Context context) {
		sfc.deleteFlowClassifier(vimConnectionInformation, task.getVimResourceId());
		return null;
	}

	@Override
	public List<NamedDependency> getNameDependencies() {
		final List<NamedDependency> ret = new ArrayList<>();
		ret.add(new NamedDependency(VnfContextExtractorNode.class, "extract-" + task.getSrcPort()));
		ret.add(new NamedDependency(VnfContextExtractorNode.class, "extract-" + task.getDstPort()));
		task.getElement().stream().map(x -> new NamedDependency(VnffgLoadbalancerNode.class, x)).forEach(ret::add);
		return ret;
	}

	@Override
	public List<NamedDependency> getNamedProduced() {
		return List.of(new NamedDependency(getNode(), task.getToscaName()));
	}
}
