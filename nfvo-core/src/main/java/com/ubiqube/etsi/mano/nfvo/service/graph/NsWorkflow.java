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
package com.ubiqube.etsi.mano.nfvo.service.graph;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.Blueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsTask;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.AbstractNsContributor;
import com.ubiqube.etsi.mano.orchestrator.Context;
import com.ubiqube.etsi.mano.orchestrator.ExecutionGraph;
import com.ubiqube.etsi.mano.orchestrator.OrchExecutionResults;
import com.ubiqube.etsi.mano.orchestrator.OrchestrationService;
import com.ubiqube.etsi.mano.orchestrator.Planner;
import com.ubiqube.etsi.mano.orchestrator.PreExecutionGraph;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.NsdCreateNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnfCreateNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTask;
import com.ubiqube.etsi.mano.service.event.Workflow;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class NsWorkflow implements Workflow<NsdPackage, NsBlueprint, NsTask> {

	private static final Logger LOG = LoggerFactory.getLogger(NsWorkflow.class);

	private final Planner<NsBlueprint, NsTask, NsTask> planv2;
	private final List<AbstractNsContributor> planContributors;
	private final OrchestrationService<?> orchestrationService;
	private final NsLiveInstanceJpa nsLiveInstanceJpa;

	public NsWorkflow(final Planner<NsBlueprint, NsTask, NsTask> planv2, final List<AbstractNsContributor> planContributors,
			final OrchestrationService<?> orchestrationService, final NsLiveInstanceJpa nsLiveInstanceJpa) {
		this.planv2 = planv2;
		this.planContributors = planContributors;
		this.orchestrationService = orchestrationService;
		this.nsLiveInstanceJpa = nsLiveInstanceJpa;
	}

	@Override
	public PreExecutionGraph<NsTask> setWorkflowBlueprint(final NsdPackage bundle, final NsBlueprint blueprint) {
		final List<Class<? extends Node>> planConstituent = new ArrayList<>();
		// Doesn't works with jdk17 but fine with eclipse.
		for (final AbstractNsContributor b : planContributors) {
			planConstituent.add(b.getNode());
		}
		final PreExecutionGraph<NsTask> plan = planv2.makePlan(new NsBundleAdapter(bundle), planConstituent, blueprint);
		plan.getPreTasks().stream().map(VirtualTask::getParameters).forEach(blueprint::addTask);
		return plan;
	}

	@Override
	@Transactional
	public OrchExecutionResults<NsTask> execute(final PreExecutionGraph<NsTask> plan, final NsBlueprint parameters) {
		final ExecutionGraph impl = planv2.implement(plan);
		final Context context = orchestrationService.createEmptyContext();
		populateContext(context, parameters);
		return planv2.execute(impl, context, new NsOrchListenetImpl(nsLiveInstanceJpa, parameters));
	}

	private void populateContext(final Context context, final NsBlueprint parameters) {
		final List<NsLiveInstance> live = nsLiveInstanceJpa.findByNsInstanceId(parameters.getInstance().getId());
		live.forEach(x -> {
			switch (x.getNsTask().getType()) {
			case VL -> context.add(Network.class, x.getNsTask().getToscaName(), x.getResourceId());
			case VNF -> context.add(VnfCreateNode.class, x.getNsTask().getToscaName(), x.getResourceId());
			case NSD -> context.add(NsdCreateNode.class, x.getNsTask().getToscaName(), x.getResourceId());
			case VNFFG -> LOG.debug("");
			default -> throw new GenericException(x.getNsTask().getType() + " is not handled.");
			}
		});
	}

	@Override
	public void refresh(final PreExecutionGraph<NsTask> prePlan, final Blueprint<NsTask, ?> localPlan) {
		prePlan.getPreTasks().forEach(x -> {
			final NsTask task = find(x.getParameters().getToscaId(), localPlan);
			x.setParameters(task);
		});
	}

	private static NsTask find(final String id, final Blueprint<NsTask, ?> localPlan) {
		return localPlan.getTasks().stream().filter(x -> x.getToscaId().equals(id)).findFirst().orElseThrow();
	}

}
