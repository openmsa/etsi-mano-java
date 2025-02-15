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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ubiqube.etsi.mano.dao.subscription.SubscriptionType;
import com.ubiqube.etsi.mano.jpa.SubscriptionJpa;
import com.ubiqube.etsi.mano.service.auth.model.ApiTypesEnum;
import com.ubiqube.etsi.mano.service.event.model.NotificationEvent;
import com.ubiqube.etsi.mano.service.event.model.Subscription;

class SubscriptionRepositoryServiceTest {

	@Mock
	private SubscriptionJpa subscriptionJpa;

	@InjectMocks
	private SubscriptionRepositoryService subscriptionRepositoryService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testSave() {
		Subscription subscription = new Subscription();
		when(subscriptionJpa.save(subscription)).thenReturn(subscription);

		Subscription result = subscriptionRepositoryService.save(subscription);

		assertEquals(subscription, result);
		verify(subscriptionJpa, times(1)).save(subscription);
	}

	@Test
	void testDeleteById() {
		UUID subscriptionId = UUID.randomUUID();

		subscriptionRepositoryService.deleteById(subscriptionId);

		verify(subscriptionJpa, times(1)).deleteById(subscriptionId);
	}

	@Test
	void testFindById() {
		UUID subscriptionId = UUID.randomUUID();
		Subscription subscription = new Subscription();
		when(subscriptionJpa.findById(subscriptionId)).thenReturn(Optional.of(subscription));

		Optional<Subscription> result = subscriptionRepositoryService.findById(subscriptionId);

		assertTrue(result.isPresent());
		assertEquals(subscription, result.get());
		verify(subscriptionJpa, times(1)).findById(subscriptionId);
	}

	@Test
	void testFindEventAndVnfPkg() {
		NotificationEvent notificationEvent = NotificationEvent.APP_INSTANCE_CREATE;
		String string = "test";
		List<Subscription> subscriptions = List.of(new Subscription());
		when(subscriptionJpa.findEventAndVnfPkg(notificationEvent, string)).thenReturn(subscriptions);

		List<Subscription> result = subscriptionRepositoryService.findEventAndVnfPkg(notificationEvent, string);

		assertEquals(subscriptions, result);
		verify(subscriptionJpa, times(1)).findEventAndVnfPkg(notificationEvent, string);
	}

	@Test
	void testFindByApiAndCallbackUriAndSubscriptionType() {
		ApiTypesEnum api = ApiTypesEnum.SOL003;
		URI callbackUri = URI.create("http://example.com");
		SubscriptionType subscriptionType = SubscriptionType.GRANT;
		List<Subscription> subscriptions = List.of(new Subscription());
		when(subscriptionJpa.findByApiAndCallbackUriAndSubscriptionType(api, callbackUri, subscriptionType)).thenReturn(subscriptions);

		List<Subscription> result = subscriptionRepositoryService.findByApiAndCallbackUriAndSubscriptionType(api, callbackUri, subscriptionType);

		assertEquals(subscriptions, result);
		verify(subscriptionJpa, times(1)).findByApiAndCallbackUriAndSubscriptionType(api, callbackUri, subscriptionType);
	}
}
