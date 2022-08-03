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
package com.ubiqube.etsi.mano.nfvo.service.graph.nfvo;

import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.controller.vnflcm.VnfInstanceLcm;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsdTask;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.model.VnfInstantiate;
import com.ubiqube.etsi.mano.orchestrator.Context;
import com.ubiqube.etsi.mano.orchestrator.NamedDependency;
import com.ubiqube.etsi.mano.orchestrator.NamedDependency2d;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.NsdInstantiateNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnfInstantiateNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTask;
import com.ubiqube.etsi.mano.service.graph.AbstractUnitOfWork;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class NsUow extends AbstractUnitOfWork<NsdTask> {

	private static final Logger LOG = LoggerFactory.getLogger(NsUow.class);

	private final NsdTask nsdTask;

	private final VnfInstanceLcm nsLcmOpOccsService;

	private final BiFunction<Servers, UUID, VnfBlueprint> func;

	public NsUow(final VirtualTask<NsdTask> task, final VnfInstanceLcm nsLcmOpOccsService) {
		super(task, NsdInstantiateNode.class);
		this.nsdTask = task.getParameters();
		this.nsLcmOpOccsService = nsLcmOpOccsService;
		func = nsLcmOpOccsService::vnfLcmOpOccsGet;
	}

	@Override
	public String execute(final Context context) {
		final VnfInstantiate instantiateRequest = createInstantiateRequest();
		instantiateRequest.setFlavourId(nsdTask.getFlavourId());
		instantiateRequest.setInstantiationLevelId(nsdTask.getInstantiationLevelId());
		instantiateRequest.setLocalizationLanguage(nsdTask.getLocalizationLanguage());
		instantiateRequest.setVimConnectionInfo(nsdTask.getVimConnectionInformations().stream().toList());
		final VnfBlueprint lcm = nsLcmOpOccsService.instantiate(nsdTask.getServer(), nsdTask.getNsInstanceId(), instantiateRequest);
		final VnfBlueprint result = UowUtils.waitLcmCompletion(lcm, func, nsdTask.getServer());
		if (OperationStatusType.COMPLETED != result.getOperationStatus()) {
			throw new GenericException("NSD LCM Failed: " + result.getError().getDetail());
		}
		return lcm.getId().toString();
	}

	private VnfInstantiate createInstantiateRequest() {
		final VnfInstantiate inst = new VnfInstantiate();
		// inst.setExtManagedVirtualLinks(nsdTask.getExtCps());
		// inst.setExtVirtualLinks(nsdTask.getExtCps());
		inst.setFlavourId(nsdTask.getFlavourId());
		inst.setInstantiationLevelId(nsdTask.getInstantiationLevelId());
		inst.setLocalizationLanguage(nsdTask.getLocalizationLanguage());
		inst.setVimConnectionInfo(nsdTask.getVimConnectionInformations().stream().toList());
		return inst;
	}

	@Override
	public String rollback(final Context context) {
		final VnfBlueprint lcm = nsLcmOpOccsService.terminate(nsdTask.getServer(), nsdTask.getNsInstanceId(), null, 0);
		final VnfBlueprint result = UowUtils.waitLcmCompletion(lcm, func, nsdTask.getServer());
		if (OperationStatusType.COMPLETED != result.getOperationStatus()) {
			throw new GenericException("NSD LCM Failed: " + result.getError().getDetail());
		}
		return lcm.getId().toString();
	}

	@Override
	public List<NamedDependency> getNameDependencies() {
		return List.of();
	}

	@Override
	public List<NamedDependency> getNamedProduced() {
		return List.of(new NamedDependency(VnfInstantiateNode.class, nsdTask.getAlias()));
	}

	@Override
	public List<NamedDependency2d> get2dDependencies() {
		return List.of();
	}

}
