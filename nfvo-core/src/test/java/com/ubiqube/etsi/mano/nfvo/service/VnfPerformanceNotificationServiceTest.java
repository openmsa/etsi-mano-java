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
package com.ubiqube.etsi.mano.nfvo.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ubiqube.etsi.mano.dao.mano.pm.PerformanceInformationAvailableNotification;
import com.ubiqube.etsi.mano.dao.mano.pm.ThresholdCrossedNotification;
import com.ubiqube.etsi.mano.dao.subscription.RemoteSubscription;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.nfvo.service.repository.PerformanceInformationAvailableNotificationRepositoryService;
import com.ubiqube.etsi.mano.nfvo.service.repository.ThresholdCrossedNotificationRepositoryService;
import com.ubiqube.etsi.mano.service.repository.RemoteSubscriptionRepositoryService;

class VnfPerformanceNotificationServiceTest {

	@Mock
	private RemoteSubscriptionRepositoryService remoteSubscriptionJpa;

	@Mock
	private ThresholdCrossedNotificationRepositoryService vnfPmJpa;

	@Mock
	private PerformanceInformationAvailableNotificationRepositoryService availableJpa;

	@InjectMocks
	private VnfPerformanceNotificationService service;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testOnCrossedNotification() {
		ThresholdCrossedNotification event = new ThresholdCrossedNotification();
		event.setSubscriptionId("sub123");

		RemoteSubscription subscription = new RemoteSubscription();
		subscription.setRemoteServerId(UUID.randomUUID());

		when(remoteSubscriptionJpa.findByRemoteSubscriptionId("sub123")).thenReturn(List.of(subscription));
		when(vnfPmJpa.save(event)).thenReturn(event);

		service.onCrossedNotification(event, "1.0");

		verify(vnfPmJpa).save(event);
	}

	@Test
	void testOnCrossedNotification_NotFound() {
		ThresholdCrossedNotification event = new ThresholdCrossedNotification();
		event.setSubscriptionId("sub123");

		when(remoteSubscriptionJpa.findByRemoteSubscriptionId("sub123")).thenReturn(Collections.emptyList());

		assertThrows(NotFoundException.class, () -> service.onCrossedNotification(event, "1.0"));
	}

	@Test
	void testOnAvailableNotification() {
		PerformanceInformationAvailableNotification event = new PerformanceInformationAvailableNotification();
		event.setSubscriptionId("sub123");

		RemoteSubscription subscription = new RemoteSubscription();
		subscription.setRemoteServerId(UUID.randomUUID());

		when(remoteSubscriptionJpa.findByRemoteSubscriptionId("sub123")).thenReturn(List.of(subscription));
		when(availableJpa.save(event)).thenReturn(event);

		service.onAvailableNotification(event, "1.0");

		verify(availableJpa).save(event);
	}

	@Test
	void testOnAvailableNotification_NotFound() {
		PerformanceInformationAvailableNotification event = new PerformanceInformationAvailableNotification();
		event.setSubscriptionId("sub123");

		when(remoteSubscriptionJpa.findByRemoteSubscriptionId("sub123")).thenReturn(Collections.emptyList());

		assertThrows(NotFoundException.class, () -> service.onAvailableNotification(event, "1.0"));
	}
}
