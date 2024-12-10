/**
 *     Copyright (C) 2019-2024 Ubiqube.
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
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.dao.mano.BlueZoneGroupInformation;
import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.ExtManagedVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.GrantInformationExt;
import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.GrantVimAssetsEntity;
import com.ubiqube.etsi.mano.dao.mano.Instance;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VimComputeResourceFlavourEntity;
import com.ubiqube.etsi.mano.dao.mano.VimSoftwareImageEntity;
import com.ubiqube.etsi.mano.dao.mano.VimTask;
import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.ZoneGroupInformation;
import com.ubiqube.etsi.mano.dao.mano.ZoneInfoEntity;
import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionType;
import com.ubiqube.etsi.mano.dao.mano.v2.Blueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.ComputeTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.ConnectionInformationJpa;
import com.ubiqube.etsi.mano.orchestrator.entities.SystemConnections;
import com.ubiqube.etsi.mano.service.mapping.BlueZoneGroupInformationMapping;
import com.ubiqube.etsi.mano.service.mapping.GrantInformationExtMapping;
import com.ubiqube.etsi.mano.service.mapping.GrantMapper;
import com.ubiqube.etsi.mano.service.vim.VimManager;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
public abstract class AbstractGrantService implements VimResourceService {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractGrantService.class);

	private final ResourceAllocate nfvo;

	private final VimManager vimManager;

	private final ConnectionInformationJpa connectionJpa;
	private final BlueZoneGroupInformationMapping blueZoneGroupInformationMapping;
	private final GrantMapper vnfGrantMapper;
	private final GrantInformationExtMapping grantInformationExtMapping;
	private final SystemService systemService;

	protected AbstractGrantService(final ResourceAllocate nfvo, final VimManager vimManager, final ConnectionInformationJpa connectionJpa, final BlueZoneGroupInformationMapping blueZoneGroupInformationMapping, final GrantMapper vnfGrantMapper, final GrantInformationExtMapping grantInformationExtMapping,
			final SystemService systemService) {
		this.nfvo = Objects.requireNonNull(nfvo);
		this.vimManager = Objects.requireNonNull(vimManager);
		this.connectionJpa = Objects.requireNonNull(connectionJpa);
		this.blueZoneGroupInformationMapping = blueZoneGroupInformationMapping;
		this.vnfGrantMapper = vnfGrantMapper;
		this.grantInformationExtMapping = grantInformationExtMapping;
		this.systemService = systemService;
	}

	@Override
	public void allocate(final Blueprint<? extends VimTask, ? extends Instance> plan) {
		final GrantResponse grantRequest = vnfGrantMapper.mapToGrantResponse(plan);
		final Predicate<? super VimTask> isManoClass = x -> (x.getType() == ResourceTypeEnum.COMPUTE) ||
				(x.getType() == ResourceTypeEnum.LINKPORT) ||
				(x.getType() == ResourceTypeEnum.STORAGE) ||
				(x.getType() == ResourceTypeEnum.OS_CONTAINER) ||
				(x.getType() == ResourceTypeEnum.VIRTUALCP) ||
				(x.getType() == ResourceTypeEnum.PAASSERVICE) ||
				(x.getType() == ResourceTypeEnum.VL);
		plan.getTasks().stream()
				.filter(isManoClass)
				.map(VimTask.class::cast)
				.forEach(x -> {
					if (x.getChangeType() == ChangeType.ADDED) {
						final GrantInformationExt obj = grantInformationExtMapping.map(x);
						grantRequest.addAddResources(obj);
					} else {
						final GrantInformationExt obj = grantInformationExtMapping.map(x);
						obj.setResourceId(Objects.requireNonNull(x.getVimResourceId()));
						obj.setVimConnectionId(Objects.requireNonNull(x.getVimConnectionId()));
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
		plan.setZoneGroups(mapAsSet(returnedGrant.getZoneGroups()));

		Set<ZoneInfoEntity> zoneInfoEntities = returnedGrant.getZones();
		zoneInfoEntities.stream().forEach(o -> o.setId(null));
		plan.setZones(zoneInfoEntities);
		
		Set<ExtManagedVirtualLinkDataEntity> extManagedVirtualLinkDataEntities = returnedGrant.getExtManagedVirtualLinks();
		extManagedVirtualLinkDataEntities.stream().forEach(o -> o.setId(null));
		plan.addExtManagedVirtualLinks(extManagedVirtualLinkDataEntities);
		
		Optional.ofNullable(returnedGrant.getExtVirtualLinks()).ifPresent(plan::addExtVirtualLinks);
		plan.setGrantsRequestId(returnedGrant.getId().toString());
		mapVimAsset(plan.getTasks(), returnedGrant.getVimAssets());
		plan.setVimConnections(fixVimConnections(plan.getVimConnections()));
		fixUnknownTask(plan.getTasks(), plan.getVimConnections());
		fixContainerBefore431(plan);
		check(plan);
	}

	private Set<BlueZoneGroupInformation> mapAsSet(final Set<ZoneGroupInformation> zoneGroups) {
		return zoneGroups.stream().map(blueZoneGroupInformationMapping::map).collect(Collectors.toSet());
	}

	private void fixContainerBefore431(final Blueprint<? extends VimTask, ? extends Instance> plan) {
		if (plan.getTasks().stream().anyMatch(x -> x.getType() == ResourceTypeEnum.HELM)) {
			fixGenericConnection(plan, ConnectionType.HELM, plan::setMciopConnectionInfo);
		}
		if (plan.getTasks().stream().anyMatch(x -> x.getType() == ResourceTypeEnum.OS_CONTAINER)) {
			fixGenericConnection(plan, ConnectionType.OCI, plan::setCirConnectionInfo);
		}
	}

	private void fixGenericConnection(final Blueprint<? extends VimTask, ? extends Instance> plan, final ConnectionType type, final Consumer<Map<String, ConnectionInformation>> cons) {
		final Map<String, ConnectionInformation> cirConn = plan.getCirConnectionInfo();
		if ((null == cirConn) || cirConn.isEmpty()) {
			return;
		}
		final List<ConnectionInformation> cirs = connectionJpa.findByConnType(type);
		if (cirs.isEmpty()) {
			LOG.warn("No {} declared in VNFM", type);
		}
		final Map<String, ConnectionInformation> map = cirs.stream().collect(Collectors.toMap(ConnectionInformation::getName, Function.identity()));
		cons.accept(map);
	}

	protected abstract void check(Blueprint<? extends VimTask, ? extends Instance> plan);

	private Set<VimConnectionInformation> fixVimConnections(final Set<VimConnectionInformation> vimConnections) {
		return vimConnections.stream()
				.map(vimManager::registerIfNeeded).collect(Collectors.toSet());
	}

	private void fixUnknownTask(final Set<? extends VimTask> tasks, final Set<VimConnectionInformation> vimConnections) {
		tasks.stream()
				.filter(x -> x.getVimConnectionId() == null)
				.forEach(x -> {
					final List<SystemConnections> modules = systemService.findByModuleName(x.getType());
					final String res = modules.stream()
							.map(y -> vimConnections.stream()
									.filter(z -> vimEqual(z, y)).findAny())
							.filter(z -> z.isPresent())
							.map(z -> z.get().getVimId())
							.findAny()
							.orElseThrow(() -> new GenericException("Unable to find connection : " + x.getType()));
					LOG.info("Set {}={}", res, x.getType());
					x.setVimConnectionId(res);
				});
	}

	private static boolean vimEqual(final VimConnectionInformation z, final SystemConnections y) {
		return z.getVimType().equals(y.getVimType());
	}

	private static void mapVimAsset(final Set<? extends VimTask> tasks, final GrantVimAssetsEntity vimAssets) {
		tasks.stream()
				.filter(ComputeTask.class::isInstance)
				.filter(x -> x.getChangeType() != ChangeType.REMOVED)
				.map(ComputeTask.class::cast)
				.forEach(x -> {
					final VnfCompute comp = x.getVnfCompute();
					x.setFlavorId(findFlavor(vimAssets, comp.getToscaName()));
					if (comp.getSoftwareImage() != null) {
						x.setImageId(findImage(vimAssets, comp.getSoftwareImage().getName()));
					}
				});
	}

	private static String findImage(final GrantVimAssetsEntity vimAssets, final String imageName) {
		Objects.requireNonNull(vimAssets.getSoftwareImages(), "Could not map " + imageName + ", Grant did not return software images.");
		return vimAssets.getSoftwareImages().stream()
				.filter(x -> x.getVnfdSoftwareImageId().equals(imageName))
				.map(VimSoftwareImageEntity::getVimSoftwareImageId)
				.findFirst()
				.orElseThrow(() -> new NotFoundException("Could not find Image for vdu: " + imageName));
	}

	private static String findFlavor(final GrantVimAssetsEntity vimAssets, final String vduId) {
		Objects.requireNonNull(vimAssets.getComputeResourceFlavours(), "Request flavour for vdu " + vduId + ", but result is empty.");
		return vimAssets.getComputeResourceFlavours().stream()
				.filter(x -> x.getVnfdVirtualComputeDescId().equals(vduId))
				.map(VimComputeResourceFlavourEntity::getVimFlavourId)
				.findFirst()
				.orElseThrow(() -> new NotFoundException("Could not find flavor for vdu: " + vduId));
	}

	private static VimTask findTask(final Blueprint<? extends VimTask, ? extends Instance> plan, final UUID grantUuid) {
		return plan.getTasks().stream()
				.filter(x -> x.getId().compareTo(grantUuid) == 0)
				.findFirst()
				.orElseThrow(() -> new NotFoundException("Could not find task: " + grantUuid));
	}
}
