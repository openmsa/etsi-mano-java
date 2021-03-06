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

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.NsSap;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsSapTask;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.nfvo.service.graph.NsBundleAdapter;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.NsSapVt;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.SapNode;
import com.ubiqube.etsi.mano.service.NsBlueprintService;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class SapContributor extends AbstractNsContributor<NsSapTask, NsSapVt> {
	private final NsBlueprintService blueprintService;
	private final NsLiveInstanceJpa nsLiveInstanceJpa;

	public SapContributor(final NsBlueprintService blueprintService, final NsLiveInstanceJpa nsLiveInstanceJpa) {
		this.blueprintService = blueprintService;
		this.nsLiveInstanceJpa = nsLiveInstanceJpa;
	}

	@Override
	protected List<NsSapVt> onTerminate(final NsdInstance instance) {
		final List<NsSapVt> ret = new ArrayList<>();
		final List<NsLiveInstance> insts = nsLiveInstanceJpa.findByNsdInstanceAndClass(instance, NsSapTask.class.getSimpleName());
		insts.stream().forEach(x -> {
			final NsSapTask nt = createDeleteTask(NsSapTask::new, x);
			nt.setNsSap(((NsSapTask) x.getNsTask()).getNsSap());
			ret.add(new NsSapVt(nt));
		});
		return ret;
	}

	@Override
	public Class<? extends Node> getNode() {
		return SapNode.class;
	}

	@Override
	protected List<NsSapVt> onScale(final NsBundleAdapter bundle, final NsBlueprint blueprint) {
		return onInstantiate(bundle, blueprint);
	}

	@Override
	protected List<NsSapVt> onInstantiate(final NsBundleAdapter bundle, final NsBlueprint blueprint) {
		final Set<NsSap> saps = bundle.nsPackage().getNsSaps();
		return saps.stream()
				.filter(x -> 0 == blueprintService.getNumberOfLiveSap(blueprint.getNsInstance(), x))
				.map(x -> {
					final NsSapTask sap = createTask(NsSapTask::new, x);
					sap.setNsSap(x);
					sap.setChangeType(ChangeType.ADDED);
					return new NsSapVt(sap);
				}).toList();
	}

	@Override
	protected List<NsSapVt> onOther(final NsBundleAdapter bundle, final NsBlueprint blueprint) {
		return onInstantiate(bundle, blueprint);
	}

}
