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
/**
 * This copy of Woodstox XML processor is licensed under the
 * Apache (Software) License, version 2.0 ("the License").
 * See the License for details about distribution rights, and the
 * specific rights regarding derivate works.
 *
 * You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/
 *
 * A copy is also included in the downloadable source code package
 * containing Woodstox, in file "ASL2.0", under the same directory
 * as this file.
 */
package com.ubiqube.etsi.mano.vnfm.controller.vnflcm;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.vnfm.fc.vnflcm.VnfSnapshotsFrontController;

import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class VnfSnapshotsFrontControllerImpl implements VnfSnapshotsFrontController {

	@Override
	public <U> ResponseEntity<List<U>> search(final MultiValueMap<String, String> requestParams, final @Nullable String nextpageOpaqueMarker, final Class<U> clazz, final Consumer<U> makeLink) {
		return ResponseEntity.unprocessableEntity().build();
	}

	@Override
	public <U> ResponseEntity<U> create(final Object body, final Class<U> clazz, final Consumer<U> makeLink) {
		return ResponseEntity.unprocessableEntity().build();
	}

	@Override
	public ResponseEntity<Void> delete(final String vnfSnapshotInfoId) {
		return ResponseEntity.unprocessableEntity().build();
	}

	@Override
	public <U> ResponseEntity<U> findById(final String vnfSnapshotInfoId, final Class<U> clazz, final Consumer<U> makeLink) {
		return ResponseEntity.unprocessableEntity().build();
	}

	@Override
	public <U> ResponseEntity<U> patch(final String vnfSnapshotInfoId, final Object body, final Class<U> clazz) {
		return ResponseEntity.unprocessableEntity().build();
	}

	@Override
	public ResponseEntity<Resource> fetch(final String vnfSnapshotInfoId, final @Nullable String range) {
		return ResponseEntity.unprocessableEntity().build();
	}

	@Override
	public ResponseEntity<Resource> fetchArtifact(final String vnfSnapshotPkgId, final String artifactPath) {
		return ResponseEntity.unprocessableEntity().build();
	}

}
