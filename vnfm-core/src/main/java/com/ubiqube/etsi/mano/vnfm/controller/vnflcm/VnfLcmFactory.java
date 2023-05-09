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
package com.ubiqube.etsi.mano.vnfm.controller.vnflcm;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.ubiqube.etsi.mano.dao.mano.InstantiationState;
import com.ubiqube.etsi.mano.dao.mano.OperationalStateType;
import com.ubiqube.etsi.mano.dao.mano.ScaleInfo;
import com.ubiqube.etsi.mano.dao.mano.VnfComputeAspectDelta;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanOperationType;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;

public class VnfLcmFactory {

	private VnfLcmFactory() {
		// Nothing.
	}

	// XXX Is it to be in LCM ?
	public static VnfInstance createVnfInstance(final String vnfInstanceName, final String vnfInstanceDescription, final VnfPackage vnfPkgInfo) {
		final VnfInstance vnfInstance = new VnfInstance();
		vnfInstance.setVnfPkg(vnfPkgInfo);
		vnfInstance.setVnfInstanceName(vnfInstanceName);
		vnfInstance.setVnfInstanceDescription(vnfInstanceDescription);
		vnfInstance.setVnfProvider(vnfPkgInfo.getVnfProvider());
		vnfInstance.setVnfProductName(vnfPkgInfo.getVnfProductName());
		vnfInstance.setVnfSoftwareVersion(vnfPkgInfo.getVnfSoftwareVersion());
		vnfInstance.setVnfdVersion(vnfPkgInfo.getVnfdVersion());
		final BlueprintParameters instantiatedVnfInfo = new BlueprintParameters();
		instantiatedVnfInfo.setFlavourId(vnfPkgInfo.getFlavorId());
		instantiatedVnfInfo.setState(OperationalStateType.STOPPED);
		vnfInstance.setInstantiationState(InstantiationState.NOT_INSTANTIATED);
		vnfInstance.setInstantiatedVnfInfo(instantiatedVnfInfo);
		final Set<ScaleInfo> scaleInfo = vnfPkgInfo.getVnfCompute().stream()
				.map(x -> x.getScalingAspectDeltas().stream()
						.map(VnfComputeAspectDelta::getAspectName)
						.distinct()
						.toList())
				.flatMap(List::stream)
				.distinct()
				.map(x -> new ScaleInfo(x, 0))
				.collect(Collectors.toSet());
		instantiatedVnfInfo.setScaleStatus(scaleInfo);
		return vnfInstance;
	}

	public static VnfBlueprint createVnfBlueprint(final PlanOperationType operation, final UUID vnfInstanceId) {
		final VnfBlueprint vnfLcmOpOcc = new VnfBlueprint();
		vnfLcmOpOcc.setOperation(operation);
		final VnfInstance vnfInstance = new VnfInstance();
		vnfInstance.setId(vnfInstanceId);
		vnfLcmOpOcc.setVnfInstance(vnfInstance);
		vnfLcmOpOcc.setStateEnteredTime(OffsetDateTime.now());
		vnfLcmOpOcc.setStartTime(OffsetDateTime.now());
		vnfLcmOpOcc.setOperationStatus(OperationStatusType.STARTING);
		final BlueprintParameters parameters = new BlueprintParameters();
		parameters.setState(OperationalStateType.STOPPED);
		vnfLcmOpOcc.setParameters(parameters);
		return vnfLcmOpOcc;
	}

}
