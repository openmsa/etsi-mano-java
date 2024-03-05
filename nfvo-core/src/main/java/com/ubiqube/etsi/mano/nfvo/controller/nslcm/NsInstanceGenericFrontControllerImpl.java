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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.nfvo.controller.nslcm;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.controller.nslcm.NsInstanceGenericFrontController;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.dto.CreateNsInstance;
import com.ubiqube.etsi.mano.dao.mano.dto.nsi.NsInstanceDto;
import com.ubiqube.etsi.mano.dao.mano.dto.nsi.NsInstantiate;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.UpdateRequest;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.NsHeal;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.NsScale;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.exception.NotFoundException;

import ma.glasnost.orika.MapperFacade;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class NsInstanceGenericFrontControllerImpl implements NsInstanceGenericFrontController {
	private static final String LOCATION = "Location";

	private static final Logger LOG = LoggerFactory.getLogger(NsInstanceGenericFrontControllerImpl.class);

	private static final Set<String> NSI_SEARCH_MANDATORY_FIELDS = new HashSet<>(Arrays.asList("id", "nsInstanceDescription", "nsdId", "nsdInfoId", "nsState", "nsInstanceName"));

	private static final String NSI_SEARCH_DEFAULT_EXCLUDE_FIELDS = "vnfInstances,pnfInfo,virtualLinkInfo,vnffgInfo,sapInfo,,nsScaleStatus,additionalAffinityOrAntiAffinityRules";

	private final MapperFacade mapper;

	private final NsInstanceControllerService nsInstanceControllerService;

	private final NsInstanceController nsLcmController;

	public NsInstanceGenericFrontControllerImpl(final MapperFacade mapper, final NsInstanceControllerService nsInstanceControllerService,
			final NsInstanceController nsLcmController) {
		this.mapper = mapper;
		this.nsInstanceControllerService = nsInstanceControllerService;
		this.nsLcmController = nsLcmController;
		LOG.debug("Starting Ns Instance SOL005 Controller.");
	}

	@Override
	public <U> ResponseEntity<String> search(final MultiValueMap<String, String> requestParams, final Class<U> clazz, final String nextpageOpaqueMarker, final Consumer<U> makeLink) {
		return nsInstanceControllerService.search(requestParams, clazz, NSI_SEARCH_DEFAULT_EXCLUDE_FIELDS, NSI_SEARCH_MANDATORY_FIELDS, makeLink);
	}

	@Override
	public ResponseEntity<Void> delete(final String nsInstanceId) {
		final UUID nsInstanceUuid = UUID.fromString(nsInstanceId);
		nsLcmController.nsInstancesNsInstanceIdDelete(nsInstanceUuid);
		return ResponseEntity.noContent().build();
	}

	@Override
	public <U> ResponseEntity<U> findById(final String nsInstanceId, final Class<U> clazz, final Consumer<U> makeLink) {
		final UUID nsInstanceUuid = UUID.fromString(nsInstanceId);
		final NsInstanceDto nsInstanceDb = nsLcmController.nsInstancesNsInstanceIdGet(nsInstanceUuid);
		final U nsInstance = mapper.map(nsInstanceDb, clazz);
		makeLink.accept(nsInstance);
		return new ResponseEntity<>(nsInstance, HttpStatus.OK);
	}

	@Override
	public <U> ResponseEntity<U> heal(final String nsInstanceId, final Object request, final Function<NsBlueprint, String> getSelfLink) {
		final UUID nsInstanceUuid = UUID.fromString(nsInstanceId);
		final NsHeal nsInst = mapper.map(request, NsHeal.class);
		final NsBlueprint nsLcm = nsInstanceControllerService.heal(nsInstanceUuid, nsInst);
		final String link = getSelfLink.apply(nsLcm);
		return ResponseEntity.accepted().header(LOCATION, link).build();
	}

	@Override
	public <U> ResponseEntity<U> instantiate(final String nsInstanceId, final Object request, final Function<NsBlueprint, String> getSelfLink) {
		final UUID nsInstanceUuid = UUID.fromString(nsInstanceId);
		final NsInstantiate nsInst = mapper.map(request, NsInstantiate.class);
		final NsBlueprint nsLcm = nsInstanceControllerService.instantiate(nsInstanceUuid, nsInst);
		final String link = getSelfLink.apply(nsLcm);
		return ResponseEntity.accepted().header(LOCATION, link).build();
	}

	@Override
	public <U> ResponseEntity<U> scale(final String nsInstanceId, final Object request, final Function<NsBlueprint, String> getSelfLink) {
		final UUID nsInstanceUuid = UUID.fromString(nsInstanceId);
		final NsScale nsInst = mapper.map(request, NsScale.class);
		final NsBlueprint nsLcm = nsInstanceControllerService.scale(nsInstanceUuid, nsInst);
		final String link = getSelfLink.apply(nsLcm);
		return ResponseEntity.accepted().header(LOCATION, link).build();
	}

	@Override
	public <U> ResponseEntity<U> terminate(final String nsInstanceId, final Object request, final Function<NsBlueprint, String> getSelfLink) {
		final UUID nsInstanceUuid = UUID.fromString(nsInstanceId);
		final NsBlueprint lcm = this.nsInstanceControllerService.terminate(nsInstanceUuid, OffsetDateTime.now());
		final String link = getSelfLink.apply(lcm);
		return ResponseEntity.accepted().header(LOCATION, link).build();
	}

	@Override
	public <U> ResponseEntity<U> update(final String nsInstanceId, final Object request, final Function<NsBlueprint, String> getSelfLink) {
		final UUID nsInstanceUuid = UUID.fromString(nsInstanceId);
		final UpdateRequest req = mapper.map(request, UpdateRequest.class);
		final NsBlueprint lcm = nsLcmController.nsInstancesNsInstanceIdUpdatePost(nsInstanceUuid, req);
		final String link = getSelfLink.apply(lcm);
		return ResponseEntity.accepted().header(LOCATION, link).build();
	}

	@Override
	public <U> ResponseEntity<U> create(final Object request, final Class<U> clazz, final Consumer<U> makeLink, final Function<U, String> getSelfLink) {
		final CreateNsInstance req = mapper.map(request, CreateNsInstance.class);
		if (req.getNsdId() == null) {
			throw new NotFoundException("NsdId field is empty.");
		}
		final NsdInstance nsInstance = nsInstanceControllerService.createNsd(req.getNsdId(), req.getNsName(), req.getNsDescription());
		final U nsInstanceWeb = mapper.map(nsInstance, clazz);
		makeLink.accept(nsInstanceWeb);
		final String location = getSelfLink.apply(nsInstanceWeb);
		return ResponseEntity.created(URI.create(location)).body(nsInstanceWeb);
	}
}
