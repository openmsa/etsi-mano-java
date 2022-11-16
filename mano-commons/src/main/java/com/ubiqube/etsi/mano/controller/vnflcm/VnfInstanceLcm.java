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
package com.ubiqube.etsi.mano.controller.vnflcm;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;

import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.dao.mano.CancelModeTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.dto.VnfLcmOpOccs;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.vnfi.ChangeExtVnfConnRequest;
import com.ubiqube.etsi.mano.model.VnfHealRequest;
import com.ubiqube.etsi.mano.model.VnfInstantiate;
import com.ubiqube.etsi.mano.model.VnfOperateRequest;
import com.ubiqube.etsi.mano.model.VnfScaleRequest;
import com.ubiqube.etsi.mano.model.VnfScaleToLevelRequest;

/**
 * NFVO+VNFM & VNFM Implementation.
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public interface VnfInstanceLcm {

	List<VnfInstance> get(Servers servers, final MultiValueMap<String, String> queryParameters);

	VnfInstance post(Servers servers, final String vnfdId, final String vnfInstanceName, final String vnfInstanceDescription);

	void delete(Servers servers, @Nonnull final UUID vnfInstanceId);

	VnfBlueprint instantiate(Servers servers, @Nonnull final UUID vnfInstanceId, final VnfInstantiate instantiateVnfRequest);

	VnfBlueprint terminate(Servers servers, @Nonnull final UUID vnfInstanceId, final CancelModeTypeEnum terminationType, final Integer gracefulTerminationTimeout);

	VnfBlueprint scaleToLevel(Servers servers, @Nonnull final UUID vnfInstanceId, final VnfScaleToLevelRequest scaleVnfToLevelRequest);

	VnfBlueprint scale(Servers servers, @Nonnull final UUID vnfInstanceId, final VnfScaleRequest scaleVnfRequest);
	
	VnfBlueprint heal(Servers servers, @Nonnull final UUID vnfInstanceId, final VnfHealRequest healVnfRequest);

	VnfBlueprint operate(Servers servers, @Nonnull final UUID vnfInstanceId, final VnfOperateRequest operateVnfRequest);

	VnfBlueprint vnfLcmOpOccsGet(Servers servers, @NotNull UUID id);

	VnfBlueprint changeExtConn(Servers servers, @NotNull UUID vnfInstanceId, ChangeExtVnfConnRequest cevcr);

	VnfInstance findById(Servers servers, String vnfInstance);

	List<VnfBlueprint> findByVnfInstanceId(Servers servers, @NotNull UUID id);

}
