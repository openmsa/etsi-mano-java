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
package com.ubiqube.etsi.mano.service;

import java.util.List;
import java.util.UUID;

import com.ubiqube.etsi.mano.service.event.model.EventMessage;
import com.ubiqube.etsi.mano.service.event.model.Subscription;
import com.ubiqube.etsi.mano.service.event.model.SubscriptionType;
import com.ubiqube.etsi.mano.service.rest.model.ApiTypesEnum;

import jakarta.annotation.Nullable;

public interface SubscriptionService {

	List<Subscription> query(String filter, SubscriptionType type);

	Subscription save(Object subscriptionPostQuery, final Class<?> version, SubscriptionType type);

	void delete(UUID subscriptionId, SubscriptionType type);

	Subscription findById(UUID subscriptionId, SubscriptionType type);

	List<Subscription> selectNotifications(EventMessage event);

	List<Subscription> findByApiAndCallbackUriSubscriptionType(@Nullable ApiTypesEnum api, String callbackUri, SubscriptionType subscriptionType);

}