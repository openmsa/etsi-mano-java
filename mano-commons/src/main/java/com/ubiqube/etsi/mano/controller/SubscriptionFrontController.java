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
package com.ubiqube.etsi.mano.controller;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.service.event.model.Subscription;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
public interface SubscriptionFrontController {
	<U> ResponseEntity<List<U>> search(MultiValueMap<String, String> requestParams, Function<Subscription, U> mapper, Consumer<U> makeLinks, ApiVersionType type);

	<U> ResponseEntity<U> create(Subscription subscriptionRequest, Function<Subscription, U> mapper, Class<?> versionController, Consumer<U> makeLinks, Function<U, String> getSelfLink, ApiVersionType type);

	ResponseEntity<Void> deleteById(String subscriptionId, ApiVersionType type);

	<U> ResponseEntity<U> findById(String subscriptionId, Function<Subscription, U> mapper, Consumer<U> makeLinks, ApiVersionType type);

}
