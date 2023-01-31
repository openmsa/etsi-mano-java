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
package com.ubiqube.etsi.mano.nfvo.controller.nslcm;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.dto.nsi.NsInstanceDto;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.UpdateRequest;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;

/**
 * Merge with {@link NsInstanceControllerService}
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public interface NsInstanceController {

	List<NsdInstance> nsInstancesGet(String filter);

	void nsInstancesNsInstanceIdDelete(@NotNull UUID id);

	NsInstanceDto nsInstancesNsInstanceIdGet(@NotNull UUID id);

	NsdInstance nsInstancesNsInstanceIdHealPost(@NotNull UUID id);

	NsdInstance nsInstancesNsInstanceIdScalePost(@NotNull UUID id);

	NsBlueprint nsInstancesNsInstanceIdUpdatePost(@NotNull UUID id, UpdateRequest req);

}