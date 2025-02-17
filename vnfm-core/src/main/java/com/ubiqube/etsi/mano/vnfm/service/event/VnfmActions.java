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
package com.ubiqube.etsi.mano.vnfm.service.event;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ExtVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.Instance;
import com.ubiqube.etsi.mano.dao.mano.OperationalStateType;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.Blueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.Task;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.orchestrator.Planner;
import com.ubiqube.etsi.mano.orchestrator.v4.Q4Workflow;
import com.ubiqube.etsi.mano.service.NsScaleStrategyV3;
import com.ubiqube.etsi.mano.service.VimResourceService;
import com.ubiqube.etsi.mano.service.event.AbstractGenericActionV3;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.service.vim.VimException;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;
import com.ubiqube.etsi.mano.vnfm.service.VnfInstanceService;
import com.ubiqube.etsi.mano.vnfm.service.VnfInstanceServiceVnfm;
import com.ubiqube.etsi.mano.vnfm.service.graph.VnfWorkflow;
import com.ubiqube.etsi.mano.vnfm.service.graph.tasks.Q4Reboot;
import com.ubiqube.etsi.mano.vnfm.service.graph.tasks.Q4Start;
import com.ubiqube.etsi.mano.vnfm.service.graph.tasks.Q4Stop;
import com.ubiqube.etsi.mano.vnfm.service.graph.tasks.Q4WaitStatus;
import com.ubiqube.etsi.mano.vnfm.service.repository.VnfBlueprintRepositoryService;

import io.micrometer.context.ContextExecutorService;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class VnfmActions extends AbstractGenericActionV3 {

	private static final Logger LOG = LoggerFactory.getLogger(VnfmActions.class);

	private final VimManager vimManager;

	private final VnfInstanceService vnfInstancesService;

	private final VnfBlueprintRepositoryService blueprintService;

	private final VnfLiveInstanceJpa vnfLiveInstanceJpa;

	private final VnfInstanceServiceVnfm vnfInstanceServiceVnfm;

	private final Q4Workflow workflow;

	public VnfmActions(final VimManager vimManager, final VnfOrchestrationAdapter orchestrationAdapter, final VnfInstanceService vnfInstancesService,
			final VnfBlueprintRepositoryService blueprintService, final VimResourceService vimResourceService, final VnfLiveInstanceJpa vnfLiveInstanceJpa,
			final VnfInstanceServiceVnfm vnfInstanceServiceVnfm, final VnfWorkflow workflow, final Planner<Task> planv2, final Q4Workflow workflow2) {
		super(workflow, vimResourceService, orchestrationAdapter, new NsScaleStrategyV3(), planv2);
		this.vimManager = vimManager;
		this.vnfInstancesService = vnfInstancesService;
		this.blueprintService = blueprintService;
		this.vnfLiveInstanceJpa = vnfLiveInstanceJpa;
		this.vnfInstanceServiceVnfm = vnfInstanceServiceVnfm;
		this.workflow = workflow2;
	}

	public void vnfOperate(final UUID blueprintId) {
		actionShield(blueprintId, actionParameter -> {
			final Vim vim = vimManager.getVimById(actionParameter.vimConnection.getId());
			if (actionParameter.blueprint.getOperateChanges().getTerminationType() == OperationalStateType.STARTED) {
				actionParameter.workflow.addEdge(new Q4WaitStatus(actionParameter.task.getVimResourceId(), vim, actionParameter.vimConnection),
						new Q4Start(actionParameter.task.getVimResourceId(), vim, actionParameter.vimConnection));
			} else {
				actionParameter.workflow.addEdge(new Q4WaitStatus(actionParameter.task.getVimResourceId(), vim, actionParameter.vimConnection),
						new Q4Stop(actionParameter.task.getVimResourceId(), vim, actionParameter.vimConnection));
			}
		});
	}

	public void vnfHeal(final UUID blueprintId) {
		actionShield(blueprintId, actionParameter -> {
			LOG.info("Rebooting: {}", actionParameter.task.getVimResourceId());
			final Vim vim = vimManager.getVimById(actionParameter.vimConnection.getId());
			actionParameter.workflow.addEdge(new Q4WaitStatus(actionParameter.task.getVimResourceId(), vim, actionParameter.vimConnection),
					new Q4Reboot(actionParameter.task.getVimResourceId(), vim, actionParameter.vimConnection));
		});
	}

	private void actionShield(final UUID blueprintId, final Consumer<ActionParameter> func) {
		final VnfBlueprint blueprint = blueprintService.findById(blueprintId);
		final VnfInstance vnfInstance = vnfInstanceServiceVnfm.findById(blueprint.getVnfInstance().getId());
		try {
			final VimConnectionInformation vimConnection = vnfInstance.getVimConnectionInfo().iterator().next();
			blueprint.getTasks().forEach(x -> func.accept(new ActionParameter(x, blueprint, vnfInstance, vimConnection, workflow)));
			run(workflow);
			completeOperation(blueprint, vnfInstance, OperationStatusType.COMPLETED);
		} catch (final RuntimeException e) {
			LOG.error("", e);
			completeOperation(blueprint, vnfInstance, OperationStatusType.FAILED);
		}
	}

	private void run(final Q4Workflow workflow) {
		final ThreadPoolExecutor tpe = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
		final ExecutorService executor = ContextExecutorService.wrap(tpe);
		workflow.run(executor);
		tpe.shutdown();
		try {
			tpe.awaitTermination(5, TimeUnit.MINUTES);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new VimException(e);
		}
	}

	private void completeOperation(final VnfBlueprint blueprint, final VnfInstance vnfInstance, final OperationStatusType oState) {
		blueprint.setOperationStatus(oState);
		blueprint.setStateEnteredTime(OffsetDateTime.now());
		blueprintService.save(blueprint);
		vnfInstance.setLockedBy(null);
		vnfInstancesService.save(vnfInstance);
	}

	public void vnfChangeVnfConn(final UUID blueprintId) {
		final VnfBlueprint blueprint = blueprintService.findById(blueprintId);
		final VnfInstance vnfInstance = vnfInstanceServiceVnfm.findById(blueprint.getVnfInstance().getId());
		final Set<ExtVirtualLinkDataEntity> evl = blueprint.getChangeExtVnfConnRequest().getExtVirtualLinks();
		final List<VnfLiveInstance> vli = evl.stream()
				.flatMap(x -> x.getExtCps().stream())
				.flatMap(y -> vnfLiveInstanceJpa.findByTaskVnfInstanceAndToscaName(vnfInstance, y.getCpdId()).stream())
				.toList();
		LOG.debug("{}", vli.getFirst().getTask());
	}

	@Override
	protected void mergeVirtualLinks(final Instance vnfInstance, final Blueprint<?, ?> localPlan) {
		final VnfBlueprint vp = (VnfBlueprint) localPlan;
		vnfInstance.setExtManagedVirtualLinks(vp.getParameters().getExtManagedVirtualLinks());
		vnfInstance.setExtVirtualLinks(vp.getParameters().getExtVirtualLinkInfo());
	}

	private record ActionParameter(VnfTask task, VnfBlueprint blueprint, VnfInstance vnfInstance, VimConnectionInformation vimConnection, Q4Workflow workflow) {
		// Nothing.
	}
}
