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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v3;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.MciopTask;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.OsContainerDeployableTask;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.OsContainerTask;
import com.ubiqube.etsi.mano.orchestrator.SclableResources;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.HelmNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerDeployableNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerNode;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;

/**
 *
 * @author olivier
 *
 */
@Service
public class OsContainerContributorV3 extends AbstractVnfmContributorV3<Object> {
	protected OsContainerContributorV3(final VnfLiveInstanceJpa vnfInstanceJpa) {
		super(vnfInstanceJpa);
	}

	@Override
	public List<SclableResources<Object>> contribute(final VnfPackage bundle, final VnfBlueprint parameters) {
		final List<SclableResources<Object>> ret = new ArrayList<>();
		final VnfPackage vnfPackage = bundle;
		vnfPackage.getOsContainer().forEach(x -> {
			final OsContainerTask t = createTask(OsContainerTask::new);
			t.setToscaName(x.getName());
			t.setBlueprint(parameters);
			t.setType(ResourceTypeEnum.CNF);
			t.setChangeType(ChangeType.ADDED);
			t.setOsContainer(x);
			ret.add(create(OsContainerNode.class, t.getToscaName(), 1, t, parameters.getInstance(), parameters));
		});
		vnfPackage.getOsContainerDeployableUnits().forEach(x -> {
			final OsContainerDeployableTask t = createTask(OsContainerDeployableTask::new);
			t.setBlueprint(parameters);
			t.setOsContainerDeployableUnit(x);
			t.setToscaName(x.getName());
			t.setType(ResourceTypeEnum.CNF);
			ret.add(create(OsContainerDeployableNode.class, t.getToscaName(), 1, t, parameters.getInstance(), parameters));
		});
		vnfPackage.getMciops().forEach(x -> x.getAssociatedVdu().forEach(y -> {
			final MciopTask t = createTask(MciopTask::new);
			t.setBlueprint(parameters);
			t.setMciop(x);
			t.setAlias(x.getToscaName());
			t.setToscaName(x.getToscaName());
			t.setType(ResourceTypeEnum.CNF);
			t.setParentVdu(y);
			t.setVnfPackageId(vnfPackage.getId());
			ret.add(create(HelmNode.class, t.getToscaName(), 1, t, parameters.getInstance(), parameters));
		}));
		return ret;
	}
}