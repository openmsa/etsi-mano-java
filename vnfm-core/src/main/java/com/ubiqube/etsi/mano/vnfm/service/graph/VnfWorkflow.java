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
package com.ubiqube.etsi.mano.vnfm.service.graph;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jgrapht.ListenableGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.SubNetworkTask;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.v2.Blueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.ComputeTask;
import com.ubiqube.etsi.mano.dao.mano.v2.DnsHostTask;
import com.ubiqube.etsi.mano.dao.mano.v2.DnsZoneTask;
import com.ubiqube.etsi.mano.dao.mano.v2.ExternalCpTask;
import com.ubiqube.etsi.mano.dao.mano.v2.MonitoringTask;
import com.ubiqube.etsi.mano.dao.mano.v2.NetworkTask;
import com.ubiqube.etsi.mano.dao.mano.v2.StorageTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfIndicatorTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfPortTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfTask;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.HelmTask;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.K8sInformationsTask;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.MciopUserTask;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.OsContainerDeployableTask;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.OsContainerTask;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.SecurityGroupTask;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.orchestrator.ContextHolder;
import com.ubiqube.etsi.mano.orchestrator.Edge2d;
import com.ubiqube.etsi.mano.orchestrator.ExecutionGraph;
import com.ubiqube.etsi.mano.orchestrator.OrchExecutionResults;
import com.ubiqube.etsi.mano.orchestrator.Planner;
import com.ubiqube.etsi.mano.orchestrator.SclableResources;
import com.ubiqube.etsi.mano.orchestrator.Vertex2d;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.AffinityRuleNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Compute;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.DnsHost;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.DnsZone;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.HelmNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.MciopUser;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Monitoring;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerDeployableNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsK8sInformationsNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.SecurityGroupNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Storage;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.SubNetwork;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfExtCp;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfIndicator;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.VnfPortNode;
import com.ubiqube.etsi.mano.orchestrator.v3.BlueprintBuilder;
import com.ubiqube.etsi.mano.orchestrator.v3.PreExecutionGraphV3;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.VnfPackageService;
import com.ubiqube.etsi.mano.service.VnfPlanService;
import com.ubiqube.etsi.mano.service.event.WorkflowV3;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.AbstractVnfmContributor;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.ComputeVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.DnsHostVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.DnsZoneVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.HelmVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.MciopUserVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.MonitoringVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.NetWorkVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.OsContainerDeployableVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.OsContainerVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.OsK8sClusterVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.SecurityGroupVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.StorageVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.SubNetworkVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.VnfExtCpVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.VnfIndicatorVt;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.VnfPortVt;

import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class VnfWorkflow implements WorkflowV3<VnfPackage, VnfBlueprint, VnfTask> {

	private static final Logger LOG = LoggerFactory.getLogger(VnfWorkflow.class);
	private static final Pattern pVl = Pattern.compile("virtual_link_(?<idx>\\d+)");

	private final List<AbstractVnfmContributor<VnfTask>> contributors;
	private final VnfPlanService planService;
	private final BlueprintBuilder blueprintBuilder;
	private final List<Class<? extends Node>> masterVertex;
	private final Map<ResourceTypeEnum, Function<VnfTask, VirtualTaskV3<? extends VnfTask>>> vts;

	private final Planner<VnfTask> planv2;
	private final VnfLiveInstanceJpa liveInstanceJpa;
	private final VnfPackageService vnfPackageService;

	public VnfWorkflow(final Planner<VnfTask> planv2, final VnfLiveInstanceJpa vnfInstanceJpa,
			final List<AbstractVnfmContributor<?>> contributors, final VnfPlanService planService, final BlueprintBuilder blueprintBuilder,
			final VnfPackageService vnfPackageService) {
		this.planv2 = planv2;
		this.liveInstanceJpa = vnfInstanceJpa;
		this.contributors = (List<AbstractVnfmContributor<VnfTask>>) ((Object) contributors);
		this.planService = planService;
		this.blueprintBuilder = blueprintBuilder;
		this.vnfPackageService = vnfPackageService;
		vts = new EnumMap<>(ResourceTypeEnum.class);
		vts.put(ResourceTypeEnum.VL, x -> new NetWorkVt((NetworkTask) x));
		vts.put(ResourceTypeEnum.SUBNETWORK, x -> new SubNetworkVt((SubNetworkTask) x));
		vts.put(ResourceTypeEnum.COMPUTE, x -> new ComputeVt((ComputeTask) x));
		vts.put(ResourceTypeEnum.LINKPORT, x -> new VnfPortVt((VnfPortTask) x));
		vts.put(ResourceTypeEnum.VNF_EXTCP, x -> new VnfExtCpVt((ExternalCpTask) x));
		vts.put(ResourceTypeEnum.SECURITY_GROUP, x -> new SecurityGroupVt((SecurityGroupTask) x));
		vts.put(ResourceTypeEnum.STORAGE, x -> new StorageVt((StorageTask) x));
		vts.put(ResourceTypeEnum.DNSZONE, x -> new DnsZoneVt((DnsZoneTask) x));
		vts.put(ResourceTypeEnum.DNSHOST, x -> new DnsHostVt((DnsHostTask) x));
		vts.put(ResourceTypeEnum.OS_CONTAINER, x -> new OsContainerVt((OsContainerTask) x));
		vts.put(ResourceTypeEnum.OS_CONTAINER_INFO, x -> new OsK8sClusterVt((K8sInformationsTask) x));
		vts.put(ResourceTypeEnum.OS_CONTAINER_DEPLOYABLE, x -> new OsContainerDeployableVt((OsContainerDeployableTask) x));
		vts.put(ResourceTypeEnum.MCIOP_USER, x -> new MciopUserVt((MciopUserTask) x));
		vts.put(ResourceTypeEnum.HELM, x -> new HelmVt((HelmTask) x));
		vts.put(ResourceTypeEnum.MONITORING, x -> new MonitoringVt((MonitoringTask) x));
		vts.put(ResourceTypeEnum.VNF_INDICATOR, x -> new VnfIndicatorVt((VnfIndicatorTask) x));
		masterVertex = List.of(Network.class, Compute.class, OsContainerNode.class, OsContainerDeployableNode.class, HelmNode.class, VnfIndicator.class);
	}

	@Override
	public PreExecutionGraphV3<VnfTask> setWorkflowBlueprint(final VnfPackage bundle, final VnfBlueprint blueprint) {
		final List<SclableResources<VnfTask>> sr = contributors.stream().flatMap(x -> x.contribute(bundle, blueprint).stream()).toList();
		final ListenableGraph<Vertex2d, Edge2d> g = planService.getPlanFor(bundle.getId());
		return blueprintBuilder.buildPlan(sr, g, x -> {
			LOG.trace("Running for {}={}", x.getType(), x.getToscaName());
			final VnfTask nc = x.copy();
			nc.setToscaId(UUID.randomUUID().toString());
			blueprint.addTask(nc);
			return (VirtualTaskV3<VnfTask>) Optional.ofNullable(vts.get(x.getType()))
					.orElseThrow(() -> new GenericException("Unable to find " + x.getType()))
					.apply(nc);
		}, buildContext(blueprint), masterVertex);
	}

	private List<ContextHolder> buildContext(final VnfBlueprint vnfBlueprint) {
		final VnfInstance instance = vnfBlueprint.getInstance();
		final List<VnfLiveInstance> live = liveInstanceJpa.findByVnfInstanceId(instance.getId());
		final VnfPackage vnfPkg = vnfPackageService.findById(instance.getVnfPkg().getId());
		final List<ContextHolder> l = live.stream().map(this::convert).toList();
		final ArrayList<ContextHolder> ret = new ArrayList<>(l);
		final List<ContextHolder> lExt = vnfBlueprint.getParameters().getExtManagedVirtualLinks().stream().map(x -> {
			final ListKeyPair vl = findVl(vnfPkg.getVirtualLinks(), x.getVnfVirtualLinkDescId());
			return new ContextHolder(null, Network.class, vl.getValue(), 0, x.getResourceId(), x.getVimConnectionId());
		})
				.toList();
		ret.addAll(lExt);
		return ret;
	}

	private static ListKeyPair findVl(final Set<ListKeyPair> virtualLinks, final String vnfVirtualLinkDescId) {
		final int idx = vlToPortdx(vnfVirtualLinkDescId);
		return virtualLinks.stream().filter(x -> x.getIdx() == idx).findFirst().orElseThrow();
	}

	private static int vlToPortdx(final String vnfVirtualLinkDescId) {
		if ("virtual_link".equals(vnfVirtualLinkDescId)) {
			return 0;
		}
		final Matcher m = pVl.matcher(vnfVirtualLinkDescId);
		if (!m.matches()) {
			throw new GenericException("Unable to match 'virtual_link_' in " + vnfVirtualLinkDescId);
		}
		return Integer.parseInt(m.group("idx"));
	}

	private ContextHolder convert(final VnfLiveInstance inst) {
		final Class<? extends Node> type = switch (inst.getTask().getType()) {
		case VL -> Network.class;
		case SUBNETWORK -> SubNetwork.class;
		case COMPUTE -> Compute.class;
		case STORAGE -> Storage.class;
		case SECURITY_GROUP -> SecurityGroupNode.class;
		case LINKPORT -> VnfPortNode.class;
		case VNF_EXTCP -> VnfExtCp.class;
		case MONITORING -> Monitoring.class;
		case AFFINITY_RULE -> AffinityRuleNode.class;
		case OS_CONTAINER_INFO -> OsK8sInformationsNode.class;
		case MCIOP_USER -> MciopUser.class;
		case DNSHOST -> DnsHost.class;
		case DNSZONE -> DnsZone.class;
		case OS_CONTAINER -> OsContainerNode.class;
		case OS_CONTAINER_DEPLOYABLE -> OsContainerDeployableNode.class;
		case HELM -> HelmNode.class;
		case VNF_INDICATOR -> VnfIndicator.class;
		default -> throw new GenericException(inst.getTask().getType() + " is not handled.");
		};
		final VnfTask task = inst.getTask();
		return new ContextHolder(inst.getId(), type, task.getToscaName(), task.getRank(), inst.getResourceId(), inst.getVimConnectionId());
	}

	@Override
	public OrchExecutionResults<VnfTask> execute(final PreExecutionGraphV3<VnfTask> plan, final VnfBlueprint parameters) {
		plan.toDotFile("orch-added.dot");
		final ExecutionGraph imp = planv2.implement(plan);
		populateContext(imp, parameters);
		return planv2.execute(imp, new OrchListenetImpl(parameters, liveInstanceJpa));
	}

	private static void populateContext(final ExecutionGraph imp, final VnfBlueprint parameters) {
		parameters.getParameters().getExtManagedVirtualLinks().forEach(x -> imp.add(Network.class, x.getVnfVirtualLinkDescId(), x.getResourceId()));
	}

	@Override
	public void refresh(final PreExecutionGraphV3<VnfTask> prePlan, final Blueprint<VnfTask, ?> localPlan) {
		prePlan.getPreTasks()
				.forEach(x -> {
					final VnfTask task = find(x.getTemplateParameters().getToscaId(), localPlan);
					if (null == task) {
						throw new GenericException("Could not find: " + x.getAlias());
					}
					x.setTemplateParameters(task);
				});
	}

	private static @Nullable VnfTask find(final String id, final Blueprint<VnfTask, ?> localPlan) {
		return localPlan.getTasks().stream()
				.filter(x -> x.getToscaId().equals(id))
				.findFirst()
				.orElse(null);
	}

}
