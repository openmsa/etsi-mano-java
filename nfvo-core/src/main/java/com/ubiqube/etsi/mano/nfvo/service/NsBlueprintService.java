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
package com.ubiqube.etsi.mano.nfvo.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

import jakarta.annotation.Nonnull;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.Blueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVirtualLink;

public interface NsBlueprintService {

	int getNumberOfLiveVl(NsdInstance nsInstance, NsVirtualLink x);

	NsBlueprint findById(UUID blueprintId);

	@Nonnull
	NsBlueprint save(NsBlueprint nsBlueprint);

	<U> ResponseEntity<String> search(final MultiValueMap<String, String> requestParams, final Class<U> clazz, final String excludeDefaults, final Set<String> mandatoryFields, final Consumer<U> makeLink);

	Blueprint<NsTask, NsdInstance> updateState(NsBlueprint localPlan, OperationStatusType processing);

	List<NsLiveInstance> findByNsdInstanceAndClass(NsdInstance ret, String simpleName);

	long countByNsInstance(NsdInstance ret);

}