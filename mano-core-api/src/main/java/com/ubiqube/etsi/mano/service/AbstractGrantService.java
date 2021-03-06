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
package com.ubiqube.etsi.mano.service;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.ubiqube.etsi.mano.dao.mano.BlueZoneGroupInformation;
import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.GrantInformationExt;
import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.GrantVimAssetsEntity;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VimComputeResourceFlavourEntity;
import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.VimSoftwareImageEntity;
import com.ubiqube.etsi.mano.dao.mano.VimTask;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.common.GeoPoint;
import com.ubiqube.etsi.mano.dao.mano.v2.Blueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.ComputeTask;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.service.vim.VimManager;

import ma.glasnost.orika.MapperFacade;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public abstract class AbstractGrantService implements VimResourceService {

	private final MapperFacade mapper;

	private final ResourceAllocate nfvo;

	private final VimManager vimManager;

	protected AbstractGrantService(final MapperFacade mapper, final ResourceAllocate nfvo, final VimManager vimManager) {
		this.mapper = mapper;
		this.nfvo = nfvo;
		this.vimManager = vimManager;
	}

	// @Override
	@SuppressWarnings("unchecked")
	@Override
	public final void allocate(final Blueprint plan) {
		final GrantResponse grantRequest = mapper.map(plan, GrantResponse.class);
		final Predicate<? super VimTask> isManoClass = x -> (x.getType() == ResourceTypeEnum.COMPUTE) ||
				(x.getType() == ResourceTypeEnum.LINKPORT) ||
				(x.getType() == ResourceTypeEnum.STORAGE) ||
				(x.getType() == ResourceTypeEnum.VL);
		plan.getTasks().stream()
				.filter(isManoClass)
				.map(VimTask.class::cast)
				.forEach(xx -> {
					final VimTask x = (VimTask) xx;
					if (x.getChangeType() == ChangeType.ADDED) {
						final GrantInformationExt obj = mapper.map(x, GrantInformationExt.class);
						obj.setResourceTemplateId(x.getToscaName());
						grantRequest.addAddResources(obj);
					} else {
						final GrantInformationExt obj = mapper.map(x, GrantInformationExt.class);
						obj.setResourceId(x.getVimResourceId());
						grantRequest.addRemoveResources(obj);
					}
				});
		final GrantResponse returnedGrant = nfvo.sendSyncGrantRequest(grantRequest);
		// Merge resources.
		Optional.ofNullable(returnedGrant.getAddResources()).orElseGet(LinkedHashSet::new)
				.forEach(x -> {
					// Get VNFM Grant Resource information ID.
					final UUID grantUuid = UUID.fromString(x.getResourceDefinitionId());
					final VimTask task = findTask(plan, grantUuid);
					task.setVimReservationId(x.getReservationId());
					task.setResourceGroupId(x.getResourceGroupId());
					task.setZoneId(x.getZoneId());
					task.setResourceProviderId(x.getResourceProviderId());
					task.setVimConnectionId(x.getVimConnectionId());
				});
		returnedGrant.getVimConnections().forEach(plan::addVimConnection);
		plan.setZoneGroups(mapper.mapAsSet(returnedGrant.getZoneGroups(), BlueZoneGroupInformation.class));
		plan.setZones(returnedGrant.getZones());
		plan.addExtManagedVirtualLinks(returnedGrant.getExtManagedVirtualLinks());
		Optional.ofNullable(returnedGrant.getExtVirtualLinks()).ifPresent(plan::addExtVirtualLinks);
		plan.setGrantsRequestId(returnedGrant.getId().toString());
		mapVimAsset(plan.getTasks(), returnedGrant.getVimAssets());
		fixUnknownTask(plan.getTasks(), plan.getVimConnections());
		plan.setVimConnections(fixVimConnections(plan.getVimConnections()));
		check(plan);
	}

	protected abstract void check(Blueprint plan);

	private Set<VimConnectionInformation> fixVimConnections(final Set<VimConnectionInformation> vimConnections) {
		return vimConnections.stream().map(x -> {
			x.setGeoloc(new GeoPoint(10, 10));
			return vimManager.registerIfNeeded(x);
		}).collect(Collectors.toSet());
	}

	private static void fixUnknownTask(final Set<? extends VimTask> tasks, final Set<VimConnectionInformation> vimConnections) {
		final VimConnectionInformation vimConn = vimConnections.iterator().next();
		tasks.stream()
				.filter(x -> x.getVimConnectionId() == null)
				.forEach(x -> x.setVimConnectionId(vimConn.getVimId()));
	}

	private static void mapVimAsset(final Set<VimTask> tasks, final GrantVimAssetsEntity vimAssets) {
		tasks.stream()
				.filter(ComputeTask.class::isInstance)
				.filter(x -> x.getChangeType() != ChangeType.REMOVED)
				.map(ComputeTask.class::cast)
				.forEach(x -> {
					x.setFlavorId(findFlavor(vimAssets, x.getVnfCompute().getToscaName()));
					x.setImageId(findImage(vimAssets, x.getVnfCompute().getSoftwareImage().getName()));
				});
	}

	private static String findImage(final GrantVimAssetsEntity vimAssets, final String imageName) {
		return vimAssets.getSoftwareImages().stream()
				.filter(x -> x.getVnfdSoftwareImageId().equals(imageName))
				.map(VimSoftwareImageEntity::getVimSoftwareImageId)
				.findFirst()
				.orElseThrow(() -> new NotFoundException("Could not find Image for vdu: " + imageName));
	}

	private static String findFlavor(final GrantVimAssetsEntity vimAssets, final String vduId) {
		return vimAssets.getComputeResourceFlavours().stream()
				.filter(x -> x.getVnfdVirtualComputeDescId().equals(vduId))
				.map(VimComputeResourceFlavourEntity::getVimFlavourId)
				.findFirst()
				.orElseThrow(() -> new NotFoundException("Could not find flavor for vdu: " + vduId));
	}

	private static VimTask findTask(final Blueprint<VimTask, VnfInstance> plan, final UUID grantUuid) {
		return plan.getTasks().stream()
				.filter(x -> x.getId().compareTo(grantUuid) == 0)
				.findFirst()
				.orElseThrow(() -> new NotFoundException("Could not find task: " + grantUuid));
	}
}
