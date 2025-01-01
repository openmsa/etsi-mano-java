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
package com.ubiqube.etsi.mano.vnfm.service.graph;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLinkPort;
import com.ubiqube.etsi.mano.dao.mano.dto.VnfLcmOpOccs;
import com.ubiqube.etsi.mano.dao.mano.v2.ComputeTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;

@Service
public class DefaultVduNamingStrategy implements VduNamingStrategy {

	@Override
	public String nameVdu(final VnfLcmOpOccs vnfLcmOpOccs, final String name, final int count) {
		final String vnfInstanceId = vnfLcmOpOccs.getVnfInstance().getId().toString();
		return vnfInstanceId.substring(0, 8) + '-' + name + '-' + String.format("%04d", count);
	}

	@Override
	public String nameVdu(final VnfBlueprint plan, final String toscaName, final int count) {
		final String vnfInstanceId = plan.getVnfInstance().getId().toString();
		return vnfInstanceId.substring(0, 8) + '-' + toscaName + '-' + String.format("%04d", count);
	}

	@Override
	public String nameSingleResource(final VnfBlueprint blueprint, final String toscaName) {
		final String vnfInstanceId = blueprint.getVnfInstance().getId().toString();
		return vnfInstanceId.substring(0, 8) + '-' + toscaName;
	}

	@Override
	public String nameConnectionPort(final VnfLinkPort linkPort, final ComputeTask compute) {
		return linkPort.getToscaName() + "-" + compute.getAlias();
	}

	@Override
	public String osContainerName(final VnfInstance vnfInstance, final String name) {
		return vnfInstance.getId().toString().substring(0, 8) + "-" + name;
	}

	@Override
	public String getOsContainerAlias(final VnfInstance vnfInstance, final String name) {
		return osContainerName(vnfInstance, name);
	}

}
