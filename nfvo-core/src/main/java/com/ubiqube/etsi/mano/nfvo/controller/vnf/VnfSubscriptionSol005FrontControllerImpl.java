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
package com.ubiqube.etsi.mano.nfvo.controller.vnf;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ubiqube.etsi.mano.controller.vnf.VnfSubscriptionManagement;
import com.ubiqube.etsi.mano.controller.vnf.VnfSubscriptionSol005FrontController;
import com.ubiqube.etsi.mano.dao.mano.ApiTypesEnum;
import com.ubiqube.etsi.mano.dao.mano.Subscription;
import com.ubiqube.etsi.mano.dao.mano.common.ApiVersionType;
import com.ubiqube.etsi.mano.dao.mano.subs.SubscriptionType;
import com.ubiqube.etsi.mano.service.ServerService;

import ma.glasnost.orika.MapperFacade;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class VnfSubscriptionSol005FrontControllerImpl implements VnfSubscriptionSol005FrontController {

	private final VnfSubscriptionManagement vnfSubscriptionManagement;

	private final MapperFacade mapper;

	private final ServerService serverService;

	public VnfSubscriptionSol005FrontControllerImpl(final VnfSubscriptionManagement vnfSubscriptionManagement, final MapperFacade mapper, final ServerService serverService) {
		this.vnfSubscriptionManagement = vnfSubscriptionManagement;
		this.mapper = mapper;
		this.serverService = serverService;
	}

	@Override
	public <U> ResponseEntity<List<U>> search(final String filters, final Class<U> clazz, final Consumer<U> makeLinks) {
		final List<Subscription> list = vnfSubscriptionManagement.subscriptionsGet(filters, SubscriptionType.VNF);
		final List<U> pkgms = mapper.mapAsList(list, clazz);
		pkgms.stream().forEach(makeLinks::accept);
		return ResponseEntity.ok(pkgms);
	}

	@Override
	public <U> ResponseEntity<U> create(final Object subscriptionsPostQuery, final Class<?> version, final Class<U> clazz, final Consumer<U> makeLinks) {
		Subscription subscription = mapper.map(subscriptionsPostQuery, Subscription.class);
		final String v = extractVersion(version);
		final String nv = serverService.convertFeVersionToMano(ApiVersionType.SOL005_VNFPKGM, v);
		subscription.setVersion(nv);
		subscription = vnfSubscriptionManagement.subscriptionsPost(subscription, ApiTypesEnum.SOL005);
		final U pkgmSubscription = mapper.map(subscription, clazz);
		makeLinks.accept(pkgmSubscription);
		return new ResponseEntity<>(pkgmSubscription, HttpStatus.CREATED);
	}

	private static String extractVersion(final Class<?> version) {
		final RequestMapping ann = AnnotationUtils.findAnnotation(version, RequestMapping.class);
		if (null == ann) {
			return null;
		}
		final String[] headers = ann.headers();
		for (final String header : headers) {
			if (header.startsWith("Version=")) {
				return header.substring("Version=".length());
			}
		}
		return null;
	}

	@Override
	public ResponseEntity<Void> delete(final String subscriptionId) {
		vnfSubscriptionManagement.subscriptionsSubscriptionIdDelete(subscriptionId, SubscriptionType.NSDVNF);
		return ResponseEntity.noContent().build();
	}

	@Override
	public <U> ResponseEntity<U> findById(final String subscriptionId, final Class<U> clazz, final Consumer<U> makeLinks) {
		final Subscription subscription = vnfSubscriptionManagement.subscriptionsSubscriptionIdGet(UUID.fromString(subscriptionId), SubscriptionType.NSDVNF);
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
