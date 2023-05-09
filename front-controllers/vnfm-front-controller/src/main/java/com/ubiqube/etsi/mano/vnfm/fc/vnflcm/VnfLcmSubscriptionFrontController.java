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
package com.ubiqube.etsi.mano.vnfm.fc.vnflcm;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import jakarta.annotation.Nullable;

public interface VnfLcmSubscriptionFrontController {

	<U> ResponseEntity<U> findById(String id, Class<U> clazz, Consumer<U> setLink);

	<U> ResponseEntity<List<U>> search(MultiValueMap<String, String> requestParams, @Nullable String nextpageOpaqueMarker, Class<U> clazz, Consumer<U> setLink);

	<U> ResponseEntity<U> create(Object body, Class<U> clazz, Class<?> versionController, Consumer<U> makeLinks, Function<U, String> setLink);

	ResponseEntity<Void> deleteById(String id);

}