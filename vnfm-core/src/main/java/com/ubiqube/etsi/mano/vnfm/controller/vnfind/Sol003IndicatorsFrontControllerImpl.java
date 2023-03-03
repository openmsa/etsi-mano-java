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
package com.ubiqube.etsi.mano.vnfm.controller.vnfind;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.em.client.vnfind.VnfIndRemoteService;
import com.ubiqube.etsi.mano.em.v431.model.vnfind.VnfIndicator;
import com.ubiqube.etsi.mano.vnfm.fc.vnfind.IndicatorsFrontController;

import jakarta.annotation.Nullable;
import ma.glasnost.orika.MapperFacade;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class Sol003IndicatorsFrontControllerImpl implements IndicatorsFrontController {
	private final VnfIndRemoteService vnfIndRemoteService;
	private final MapperFacade mapper;

	public Sol003IndicatorsFrontControllerImpl(final VnfIndRemoteService vnfIndRemoteService, final MapperFacade mapper) {
		this.vnfIndRemoteService = vnfIndRemoteService;
		this.mapper = mapper;
	}

	@Override
	public <U> ResponseEntity<List<U>> search(final @Nullable String filter, final @Nullable String nextpageOpaqueMarker, final Class<U> clazz, final Consumer<U> makeLink) {
		final List<VnfIndicator> res = vnfIndRemoteService.indicatorsGet(filter, nextpageOpaqueMarker);
		final List<U> ret = mapper.mapAsList(res, clazz);
		return ResponseEntity.ok(ret);
	}

	@Override
	public <U> ResponseEntity<List<U>> findByVnfInstanceId(final String vnfInstanceId, final @Nullable String filter, final @Nullable String nextpageOpaqueMarker, final Class<U> clazz, final Consumer<U> makeLink) {
		final ResponseEntity<List<VnfIndicator>> res = vnfIndRemoteService.indicatorsVnfInstanceIdGet(vnfInstanceId, filter, nextpageOpaqueMarker);
		final List<U> ret = mapper.mapAsList(res.getBody(), clazz);
		return ResponseEntity.ok(ret);
	}

	@Override
	public <U> ResponseEntity<U> findByVnfInstanceIdAndIndicatorId(final String vnfInstanceId, final String indicatorId, final Class<U> clazz, final Consumer<U> makeLink) {
		final ResponseEntity<VnfIndicator> res = vnfIndRemoteService.indicatorsVnfInstanceIdIndicatorIdGet(vnfInstanceId, indicatorId);
		final U ret = mapper.map(res.getBody(), clazz);
		return ResponseEntity.ok(ret);
	}

	@Override
	public ResponseEntity<Void> delete(final String subscriptionId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <U> ResponseEntity<U> findById(final String subscriptionId) {
		throw new UnsupportedOperationException();
	}

}
