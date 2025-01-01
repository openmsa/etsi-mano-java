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
package com.ubiqube.etsi.mano.nfvo.controller.nslcm;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.controller.nslcm.NsLcmSubscriptionsGenericFrontController;
import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.service.SubscriptionService;
import com.ubiqube.etsi.mano.service.event.model.Subscription;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class NsLcmSubscriptionsGenericFrontControllerImpl implements NsLcmSubscriptionsGenericFrontController {
	private final SubscriptionService subscriptionService;

	public NsLcmSubscriptionsGenericFrontControllerImpl(final SubscriptionService subscriptionService) {
		this.subscriptionService = subscriptionService;
	}

	/**
	 * Query multiple subscriptions.
	 *
	 * Query Subscription Information. The GET method queries the list of active
	 * subscriptions of the functional block that invokes the method. It can be used
	 * e.g. for resynchronization after error situations.
	 *
	 */
	@Override
	public <U> ResponseEntity<List<U>> search(final String filter, final Function<Subscription, U> func, final Consumer<U> makeLink) {
		final List<Subscription> list = subscriptionService.query(filter, ApiVersionType.SOL005_NSLCM);
		final List<U> pkgms = list.stream().map(func::apply).toList();
		pkgms.stream().forEach(makeLink::accept);
		return ResponseEntity.ok(pkgms);
	}

	/**
	 * Subscribe to NS lifecycle change notifications.
	 *
	 * The POST method creates a new subscription. This method shall support the URI
	 * query parameters, request and response data structures, and response codes,
	 * as specified in the Tables 6.4.16.3.1-1 and 6.4.16.3.1-2. Creation of two
	 * subscription resources with the same callbackURI and the same filter can
	 * result in performance degradation and will provide duplicates of
	 * notifications to the OSS, and might make sense only in very rare use cases.
	 * Consequently, the NFVO may either allow creating a subscription resource if
	 * another subscription resource with the same filter and callbackUri already
	 * exists (in which case it shall return the \&quot;201 Created\&quot; response
	 * code), or may decide to not create a duplicate subscription resource (in
	 * which case it shall return a \&quot;303 See Other\&quot; response code
	 * referencing the existing subscription resource with the same filter and
	 * callbackUri).
	 *
	 */
	@Override
	public <U> ResponseEntity<U> create(final Subscription lccnSubscriptionRequest, final Function<Subscription, U> func, final Class<?> versionController, final Consumer<U> makeLink, final Function<U, String> setLink) {
		final Subscription subscription = subscriptionService.save(lccnSubscriptionRequest, versionController, ApiVersionType.SOL005_NSLCM);
		final U pkgmSubscription = func.apply(subscription);
		makeLink.accept(pkgmSubscription);
		final String location = setLink.apply(pkgmSubscription);
		return ResponseEntity.created(URI.create(location)).body(pkgmSubscription);
	}

	/**
	 * Terminate a subscription.
	 *
	 * The DELETE method terminates an individual subscription. This method shall
	 * support the URI query parameters, request and response data structures, and
	 * response codes, as specified in the Tables 6.4.17.3.5-1 and 6.4.17.3.5-2.
	 *
	 */
	@Override
	public ResponseEntity<Void> delete(final String subscriptionId) {
		subscriptionService.delete(UUID.fromString(subscriptionId), ApiVersionType.SOL005_NSLCM);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Read an individual subscription resource.
	 *
	 * The GET method retrieves information about a subscription by reading an
	 * individual subscription resource. This method shall support the URI query
	 * parameters, request and response data structures, and response codes, as
	 * specified in the Tables 6.4.17.3.2-1 and 6.4.17.3.2-2
	 *
	 */
	@Override
	public <U> ResponseEntity<U> findById(final String subscriptionId, final Function<Subscription, U> func, final Consumer<U> makeLink) {
		final Subscription subscription = subscriptionService.findById(UUID.fromString(subscriptionId), ApiVersionType.SOL005_NSLCM);
		final U pkgmSubscription = func.apply(subscription);
		makeLink.accept(pkgmSubscription);
		return new ResponseEntity<>(pkgmSubscription, HttpStatus.OK);
	}

}
