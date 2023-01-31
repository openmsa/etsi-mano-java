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

import java.util.UUID;

import jakarta.annotation.Nonnull;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.controller.vnf.VnfSubscriptionManagement;
import com.ubiqube.etsi.mano.dao.mano.VnfPackageChangeNotification;
import com.ubiqube.etsi.mano.dao.mano.VnfPackageOnboardingNotification;
import com.ubiqube.etsi.mano.service.ServerService;
import com.ubiqube.etsi.mano.service.SubscriptionService;
import com.ubiqube.etsi.mano.service.event.Notifications;
import com.ubiqube.etsi.mano.service.event.model.Subscription;
import com.ubiqube.etsi.mano.service.event.model.SubscriptionType;
import com.ubiqube.etsi.mano.service.rest.ServerAdapter;

@Service
public class VnfSubscriptionManagementImpl implements VnfSubscriptionManagement {
	private final Notifications notifications;

	private final SubscriptionService subscriptionService;

	private final ServerService serverService;

	public VnfSubscriptionManagementImpl(final Notifications notifications, final SubscriptionService subscriptionRepository, final ServerService serverService) {
		this.notifications = notifications;
		this.subscriptionService = subscriptionRepository;
		this.serverService = serverService;
	}

	@Override
	public void vnfPackageChangeNotificationPost(@Nonnull final VnfPackageChangeNotification notificationsMessage) {
		final UUID subscriptionId = UUID.fromString(notificationsMessage.getSubscriptionId());

		final Subscription subscriptionsRepository = subscriptionService.findById(subscriptionId, SubscriptionType.VNF);
		final String callbackUri = subscriptionsRepository.getCallbackUri();
		final ServerAdapter server = serverService.buildServerAdapter(subscriptionsRepository);
		notifications.doNotification(notificationsMessage, callbackUri, server);
	}

	@Override
	public void vnfPackageOnboardingNotificationPost(@Nonnull final VnfPackageOnboardingNotification notificationsMessage) {
		final UUID subscriptionId = UUID.fromString(notificationsMessage.getSubscriptionId());
		final Subscription subscription = subscriptionService.findById(subscriptionId, SubscriptionType.VNF);
		final String cbUrl = subscription.getCallbackUri();
		final ServerAdapter server = serverService.buildServerAdapter(subscription);
		notifications.doNotification(notificationsMessage, cbUrl, server);
	}

}
