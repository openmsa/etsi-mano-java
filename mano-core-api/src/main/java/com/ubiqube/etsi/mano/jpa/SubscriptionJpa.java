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
package com.ubiqube.etsi.mano.jpa;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ubiqube.etsi.mano.dao.subscription.SubscriptionType;
import com.ubiqube.etsi.mano.service.auth.model.ApiTypesEnum;
import com.ubiqube.etsi.mano.service.event.model.NotificationEvent;
import com.ubiqube.etsi.mano.service.event.model.Subscription;

import jakarta.annotation.Nullable;

/**
 * JPA repository for Subscriptions.
 */
public interface SubscriptionJpa extends CrudRepository<Subscription, UUID> {

	@Query("select s from Subscription s")
	List<Subscription> findEventAndVnfPkg(NotificationEvent notificationTypesEnum, String vnfPkgId);

	List<Subscription> findByApiAndCallbackUriAndSubscriptionType(@Nullable ApiTypesEnum api, URI callbackUri, SubscriptionType subscriptionType);
}
