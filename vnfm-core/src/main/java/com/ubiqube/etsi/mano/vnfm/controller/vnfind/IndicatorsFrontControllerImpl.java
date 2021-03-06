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
package com.ubiqube.etsi.mano.vnfm.controller.vnfind;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.vnfm.fc.vnfind.IndicatorsFrontController;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class IndicatorsFrontControllerImpl implements IndicatorsFrontController {
	@Override
	public <U> ResponseEntity<List<U>> search(final String filter, final String nextpageOpaqueMarker, final Class<U> clazz, final Consumer<U> makeLink) {
		// TODO:
		return null;
	}

	@Override
	public <U> ResponseEntity<List<U>> findByVnfInstanceId(final String vnfInstanceId, final String filter, final String nextpageOpaqueMarker, final Class<U> clazz, final Consumer<U> makeLink) {
		// TODO:
		return null;
	}

	@Override
	public <U> ResponseEntity<U> findByVnfInstanceIdAndIndicatorId(final String vnfInstanceId, final String indicatorId, final Class<U> clazz, final Consumer<U> makeLink) {
		// TODO:
		return null;
	}

	@Override
	public ResponseEntity<Void> delete(final String subscriptionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> ResponseEntity<U> findById(final String subscriptionId) {
		// TODO Auto-generated method stub
		return null;
	}

}
