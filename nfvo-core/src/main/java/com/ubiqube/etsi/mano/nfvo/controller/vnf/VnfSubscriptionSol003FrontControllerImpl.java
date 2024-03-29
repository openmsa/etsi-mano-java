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
package com.ubiqube.etsi.mano.nfvo.controller.vnf;

import static com.ubiqube.etsi.mano.Constants.getSafeUUID;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.controller.vnf.VnfSubscriptionManagement;
import com.ubiqube.etsi.mano.controller.vnf.VnfSubscriptionSol003FrontController;
import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.service.SubscriptionService;
import com.ubiqube.etsi.mano.service.event.model.Subscription;

import ma.glasnost.orika.MapperFacade;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class VnfSubscriptionSol003FrontControllerImpl implements VnfSubscriptionSol003FrontController {

	private final VnfSubscriptionManagement vnfSubscriptionManagement;

	private final MapperFacade mapper;

	private final SubscriptionService subscriptionService;

	public VnfSubscriptionSol003FrontControllerImpl(final VnfSubscriptionManagement vnfSubscriptionManagement, final MapperFacade mapper, final SubscriptionService subscriptionService) {
		this.vnfSubscriptionManagement = vnfSubscriptionManagement;
		this.mapper = mapper;
		this.subscriptionService = subscriptionService;
	}

	@Override
	public <U> ResponseEntity<List<U>> search(final String filters, final Class<U> clazz, final Consumer<U> makeLinks) {
		final List<Subscription> list = subscriptionService.query(filters, ApiVersionType.SOL005_VNFPKGM);
		final List<U> pkgms = mapper.mapAsList(list, clazz);
		pkgms.stream().forEach(makeLinks::accept);
		return ResponseEntity.ok(pkgms);
	}

	@Override
	public <U> ResponseEntity<U> create(final Object subscriptionsPostQuery, final Class<U> clazz, final Consumer<U> makeLinks) {
		final Subscription subscription = subscriptionService.save(subscriptionsPostQuery, clazz, ApiVersionType.SOL005_VNFPKGM);
		final U pkgmSubscription = mapper.map(subscription, clazz);
		makeLinks.accept(pkgmSubscription);
		return new ResponseEntity<>(pkgmSubscription, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Void> delete(final String subscriptionId) {
		subscriptionService.delete(getSafeUUID(subscriptionId), ApiVersionType.SOL005_VNFPKGM);
		return ResponseEntity.noContent().build();
	}

	@Override
	public <U> ResponseEntity<U> findById(final String subscriptionId, final Class<U> clazz, final Consumer<U> makeLinks) {
		final Subscription subscription = subscriptionService.findById(getSafeUUID(subscriptionId), ApiVersionType.SOL005_VNFPKGM);
		final U pkgmSubscription = mapper.map(subscription, clazz);
		makeLinks.accept(pkgmSubscription);
		return new ResponseEntity<>(pkgmSubscription, HttpStatus.OK);
	}

	@Override
	public void vnfPackageChangeNotificationPost(final Object notificationsMessage) {
		final com.ubiqube.etsi.mano.dao.mano.VnfPackageChangeNotification msg = mapper.map(notificationsMessage, com.ubiqube.etsi.mano.dao.mano.VnfPackageChangeNotification.class);
		vnfSubscriptionManagement.vnfPackageChangeNotificationPost(msg);
	}

	@Override
	public void vnfPackageOnboardingNotificationPost(final Object notificationsMessage) {
		final com.ubiqube.etsi.mano.dao.mano.VnfPackageOnboardingNotification msg = mapper.map(notificationsMessage, com.ubiqube.etsi.mano.dao.mano.VnfPackageOnboardingNotification.class);
		vnfSubscriptionManagement.vnfPackageOnboardingNotificationPost(msg);
	}

}
