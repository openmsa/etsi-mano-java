package com.ubiqube.etsi.mano.nfvo.service.event;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ubiqube.etsi.mano.service.event.SubscriptionEvent;
import com.ubiqube.etsi.mano.service.event.VnfEvent;
import com.ubiqube.etsi.mano.service.event.model.EventMessage;
import com.ubiqube.etsi.mano.service.event.model.Subscription;

class NotificationsControllerTest {

	private NotificationsController notificationsController;

	@Mock
	private VnfEvent vnfEvent;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		notificationsController = new NotificationsController(vnfEvent);
	}

	@Test
	void testOnEvent() {
		EventMessage eventMessage = new EventMessage();
		notificationsController.onEvent(eventMessage);
		verify(vnfEvent, times(1)).onEvent(eventMessage);
	}

	@Test
	void testOnNotificationSender() {
		SubscriptionEvent subscriptionEvent = mock(SubscriptionEvent.class);
		Subscription subs = new Subscription();
		when(subscriptionEvent.getSubscription()).thenReturn(subs);
		EventMessage evt = new EventMessage();
		when(subscriptionEvent.getEvent()).thenReturn(evt);

		notificationsController.onNotificationSender(subscriptionEvent);
		verify(vnfEvent, times(1)).sendNotification(subs, evt);
	}
}
