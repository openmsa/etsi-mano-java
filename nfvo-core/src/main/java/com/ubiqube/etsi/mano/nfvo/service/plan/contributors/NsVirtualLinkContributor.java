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
package com.ubiqube.etsi.mano.nfvo.service.plan.contributors;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Priority;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVirtualLink;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVirtualLinkTask;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.nfvo.service.graph.NsBundleAdapter;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.NsVirtualLinkVt;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Network;
import com.ubiqube.etsi.mano.service.NsBlueprintService;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Priority(0)
@Service
public class NsVirtualLinkContributor extends AbstractNsContributor<NsVirtualLinkTask, NsVirtualLinkVt> {
	private final NsBlueprintService blueprintService;
	private final NsLiveInstanceJpa nsLiveInstanceJpa;

	public NsVirtualLinkContributor(final NsBlueprintService blueprintService, final NsLiveInstanceJpa nsLiveInstanceJpa) {
		this.blueprintService = blueprintService;
		this.nsLiveInstanceJpa = nsLiveInstanceJpa;
	}

	@Override
	protected List<NsVirtualLinkVt> onTerminate(final NsdInstance instance) {
		final List<NsVirtualLinkVt> ret = new ArrayList<>();
		final List<NsLiveInstance> insts = nsLiveInstanceJpa.findByNsdInstanceAndClass(instance, NsVirtualLinkTask.class.getSimpleName());
		insts.stream().forEach(x -> {
			final NsVirtualLinkTask nt = createDeleteTask(NsVirtualLinkTask::new, x);
			nt.setVimResourceId(x.getResourceId());
			nt.setType(ResourceTypeEnum.VL);
			ret.add(new NsVirtualLinkVt(nt));
		});
		return ret;
	}

	@Override
	public Class<? extends Node> getNode() {
		return Network.class;
	}

	private static String buildAlias(final NsBlueprint plan, final String toscaName, final int count) {
		final String vnfInstanceId = plan.getInstance().getId().toString();
		return vnfInstanceId.substring(0, 8) + '-' + toscaName + '-' + String.format("%04d", count);
	}

	@Override
	protected List<NsVirtualLinkVt> onScale(final NsBundleAdapter bundle, final NsBlueprint blueprint) {
		return onInstantiate(bundle, blueprint);
	}

	@Override
	protected List<NsVirtualLinkVt> onInstantiate(final NsBundleAdapter bundle, final NsBlueprint blueprint) {
		final Set<NsVirtualLink> vlss = bundle.nsPackage().getNsVirtualLinks();
		return vlss.stream()
				.filter(x -> 0 == blueprintService.getNumberOfLiveVl(blueprint.getNsInstance(), x))
				.map(x -> {
					final NsVirtualLinkTask nsVl = createTask(NsVirtualLinkTask::new, x);
					nsVl.setChangeType(ChangeType.ADDED);
					nsVl.setNsVirtualLink(x);
					nsVl.setType(ResourceTypeEnum.VL);
					nsVl.setAlias(buildAlias(blueprint, nsVl.getToscaName(), 0));
					return new NsVirtualLinkVt(nsVl);
				}).toList();
	}

	@Override
	protected List<NsVirtualLinkVt> onOther(final NsBundleAdapter bundle, final NsBlueprint blueprint) {
		return onInstantiate(bundle, blueprint);
	}
}
