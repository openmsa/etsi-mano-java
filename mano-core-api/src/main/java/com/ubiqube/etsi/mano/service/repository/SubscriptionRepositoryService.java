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
package com.ubiqube.etsi.mano.service.repository;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.subscription.SubscriptionType;
import com.ubiqube.etsi.mano.jpa.SubscriptionJpa;
import com.ubiqube.etsi.mano.service.auth.model.ApiTypesEnum;
import com.ubiqube.etsi.mano.service.event.model.NotificationEvent;
import com.ubiqube.etsi.mano.service.event.model.Subscription;

@Service
public class SubscriptionRepositoryService {
	private final SubscriptionJpa subscriptionJpa;

	public SubscriptionRepositoryService(final SubscriptionJpa subscriptionJpa) {
		this.subscriptionJpa = subscriptionJpa;
	}

	public Subscription save(final Subscription subscription) {
		return subscriptionJpa.save(subscription);
	}

	public void deleteById(final UUID subscriptionId) {
		subscriptionJpa.deleteById(subscriptionId);

	}

	public Optional<Subscription> findById(final UUID subscriptionId) {
		return subscriptionJpa.findById(subscriptionId);
	}

	public List<Subscription> findEventAndVnfPkg(final NotificationEvent notificationEvent, final String string) {
		return subscriptionJpa.findEventAndVnfPkg(notificationEvent, string);
	}

	public List<Subscription> findByApiAndCallbackUriAndSubscriptionType(final ApiTypesEnum api, final URI callbackUri, final SubscriptionType subscriptionType) {
		return subscriptionJpa.findByApiAndCallbackUriAndSubscriptionType(api, callbackUri, subscriptionType);
	}
}
