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
package com.ubiqube.etsi.mano.nfvo.controller.nslcm;

import static com.ubiqube.etsi.mano.Constants.ensureInstantiated;
import static com.ubiqube.etsi.mano.Constants.ensureNotInstantiated;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.InstantiationState;
import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.UsageStateEnum;
import com.ubiqube.etsi.mano.dao.mano.alarm.ResourceHandle;
import com.ubiqube.etsi.mano.dao.mano.dto.nsi.NsInstanceDto;
import com.ubiqube.etsi.mano.dao.mano.dto.nsi.NsVirtualLinkInfoDto;
import com.ubiqube.etsi.mano.dao.mano.dto.nsi.VnfInstanceDto;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.UpdateRequest;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanOperationType;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVirtualLinkTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfTask;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.nfvo.factory.LcmFactory;
import com.ubiqube.etsi.mano.nfvo.service.NsBlueprintService;
import com.ubiqube.etsi.mano.nfvo.service.NsInstanceService;
import com.ubiqube.etsi.mano.nfvo.service.mapping.NsInstanceDtoMapping;
import com.ubiqube.etsi.mano.nfvo.service.repository.NsdPackageRepositoryService;
import com.ubiqube.etsi.mano.service.VnfInstanceGatewayService;
import com.ubiqube.etsi.mano.service.event.ActionType;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.event.model.NotificationEvent;

@Service
public class NsInstanceControllerImpl implements NsInstanceController {
	private final NsBlueprintService blueprintService;
	private final NsInstanceService nsInstanceService;
	private final VnfInstanceGatewayService vnfInstancesService;
	private final NsdPackageRepositoryService nsdPackageJpa;
	private final EventManager eventManager;
	private final NsInstanceDtoMapping nsInstanceDtoMapping;

	public NsInstanceControllerImpl(final NsInstanceService nsInstanceService, final NsBlueprintService lcmOpOccsService,
			final VnfInstanceGatewayService vnfInstancesService, final NsdPackageRepositoryService nsdPackageJpa, final EventManager eventManager, final NsInstanceDtoMapping nsInstanceDtoMapping) {
		this.nsInstanceService = nsInstanceService;
		this.blueprintService = lcmOpOccsService;
		this.vnfInstancesService = vnfInstancesService;
		this.nsdPackageJpa = nsdPackageJpa;
		this.eventManager = eventManager;
		this.nsInstanceDtoMapping = nsInstanceDtoMapping;
	}

	@Override
	public List<NsdInstance> nsInstancesGet(final String filter) {
		return nsInstanceService.query(filter);
	}

	@Override
	public void nsInstancesNsInstanceIdDelete(final UUID id) {
		final NsdInstance nsInstanceDb = nsInstanceService.findById(id);
		ensureNotInstantiated(nsInstanceDb);
		nsInstanceService.delete(id);
		if (!nsInstanceService.isInstantiated(nsInstanceDb.getNsdInfo())) {
			final NsdPackage nsPkg = nsdPackageJpa.findById(nsInstanceDb.getNsdInfo().getId());
			nsPkg.setNsdUsageState(UsageStateEnum.NOT_IN_USE);
			nsdPackageJpa.save(nsPkg);
			eventManager.sendNotification(NotificationEvent.NS_INSTANCE_DELETE, id, Map.of());
		}
	}

	@Override
	public NsInstanceDto nsInstancesNsInstanceIdGet(final UUID id) {
		final NsdInstance ret = nsInstanceService.findById(id);

		final NsInstanceDto dto = nsInstanceDtoMapping.map(ret.getNsdInfo());
		nsInstanceDtoMapping.map(ret, dto);
		final List<NsLiveInstance> vnfs = blueprintService.findByNsdInstanceAndClass(ret, NsVnfTask.class);
		final List<VnfInstanceDto> vnfInstance = vnfs.stream()
				.map(x -> vnfInstancesService.findById(UUID.fromString(x.getResourceId())))
				.map(nsInstanceDtoMapping::map)
				.toList();
		dto.setVnfInstance(vnfInstance);
		dto.setNsdInfoId(ret.getNsdInfo().getId().toString());
		final List<NsLiveInstance> vls = blueprintService.findByNsdInstanceAndClass(ret, NsVirtualLinkTask.class);
		final List<NsVirtualLinkInfoDto> vlsDto = vls.stream().map(x -> {
			final NsVirtualLinkInfoDto vlDto = new NsVirtualLinkInfoDto();

			vlDto.setId(x.getId().toString());
			vlDto.setNsVirtualLinkDescId(x.getNsTask().getToscaName());
			final List<ResourceHandle> resourceHandle = new ArrayList<>();
			final ResourceHandle r = nsInstanceDtoMapping.map(x);
			resourceHandle.add(r);
			vlDto.setResourceHandle(resourceHandle);
			return vlDto;
		}).toList();
		dto.setVirtualLinkInfo(vlsDto);
		dto.setNsState(blueprintService.countByNsInstance(ret) > 0 ? InstantiationState.INSTANTIATED : InstantiationState.NOT_INSTANTIATED);
		return dto;
	}

	@Override
	public NsdInstance nsInstancesNsInstanceIdHealPost(final UUID id) {
		final NsdInstance nsInstance = nsInstanceService.findById(id);
		ensureInstantiated(nsInstance);
		final NsBlueprint lcmOpOccs = LcmFactory.createNsLcmOpOcc(nsInstance, PlanOperationType.HEAL);
		blueprintService.save(lcmOpOccs);
		throw new GenericException("TODO");
	}

	@Override
	public NsdInstance nsInstancesNsInstanceIdScalePost(final UUID id) {
		final NsdInstance nsInstanceDb = nsInstanceService.findById(id);
		ensureInstantiated(nsInstanceDb);
		final NsBlueprint lcmOpOccs = LcmFactory.createNsLcmOpOcc(nsInstanceDb, PlanOperationType.SCALE);
		blueprintService.save(lcmOpOccs);
		throw new GenericException("TODO");
	}

	@Override
	public NsBlueprint nsInstancesNsInstanceIdUpdatePost(final UUID id, final UpdateRequest req) {
		final NsdInstance nsInstanceDb = nsInstanceService.findById(id);
		ensureInstantiated(nsInstanceDb);
		final NsBlueprint lcmOpOccs = LcmFactory.createNsLcmOpOcc(nsInstanceDb, PlanOperationType.UPDATE);
		lcmOpOccs.getParameters().setUpdData(req);
		blueprintService.save(lcmOpOccs);
		eventManager.sendActionNfvo(ActionType.NS_UPDATE, lcmOpOccs.getId(), new HashMap<>());
		return lcmOpOccs;
	}
}
