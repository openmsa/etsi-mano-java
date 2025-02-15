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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ubiqube.etsi.mano.dao.subscription.RemoteSubscription;
import com.ubiqube.etsi.mano.jpa.RemoteSubscriptionJpa;

class RemoteSubscriptionRepositoryServiceTest {

	@Mock
	private RemoteSubscriptionJpa remoteSubscriptionJpa;

	@InjectMocks
	private RemoteSubscriptionRepositoryService remoteSubscriptionRepositoryService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindByRemoteSubscriptionId() {
		String remoteSubscriptionId = "testId";
		RemoteSubscription subscription = new RemoteSubscription();
		List<RemoteSubscription> expectedSubscriptions = Arrays.asList(subscription);

		when(remoteSubscriptionJpa.findByRemoteSubscriptionId(remoteSubscriptionId)).thenReturn(expectedSubscriptions);

		List<RemoteSubscription> actualSubscriptions = remoteSubscriptionRepositoryService.findByRemoteSubscriptionId(remoteSubscriptionId);

		assertEquals(expectedSubscriptions, actualSubscriptions);
		verify(remoteSubscriptionJpa, times(1)).findByRemoteSubscriptionId(remoteSubscriptionId);
	}
}
