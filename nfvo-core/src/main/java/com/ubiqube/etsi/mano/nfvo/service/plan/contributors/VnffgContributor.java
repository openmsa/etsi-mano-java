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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgDescriptor;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsSfcTask;
import com.ubiqube.etsi.mano.nfvo.service.graph.NsBundleAdapter;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.NsVnffgPostVt;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnffgPreNode;
import com.ubiqube.etsi.mano.service.graph.vt.NsVtBase;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class VnffgContributor extends AbstractNsContributor<NsSfcTask, NsVtBase<NsSfcTask>> {

	@Override
	public Class<? extends Node> getNode() {
		return VnffgPreNode.class;
	}

	@Override
	protected List<NsVtBase<NsSfcTask>> onTerminate(final NsdInstance blueprint) {
		return List.of();
	}

	@Override
	protected List<NsVtBase<NsSfcTask>> onScale(final NsBundleAdapter bundle, final NsBlueprint blueprint) {
		return onInstantiate(bundle, blueprint);
	}

	@Override
	protected List<NsVtBase<NsSfcTask>> onInstantiate(final NsBundleAdapter bundle, final NsBlueprint blueprint) {
		final Set<VnffgDescriptor> vnffgs = bundle.nsPackage().getVnffgs();
		if ((vnffgs == null) || vnffgs.isEmpty()) {
			return List.of();
		}
		return vnffgs.stream().map(x -> {
			final NsSfcTask task = new NsSfcTask();
			task.setToscaName(x.getName());
			task.setAlias(x.getName());
			task.setType(ResourceTypeEnum.VNFFG);
			task.setChangeType(ChangeType.ADDED);
			task.setStatus(PlanStatusType.SUCCESS);
			task.setVnffg(x);
			return new NsVnffgPostVt(task);
		}).collect(Collectors.toList());
	}

	@Override
	protected List<NsVtBase<NsSfcTask>> onOther(final NsBundleAdapter bundle, final NsBlueprint blueprint) {
		return onInstantiate(bundle, blueprint);
	}

}
