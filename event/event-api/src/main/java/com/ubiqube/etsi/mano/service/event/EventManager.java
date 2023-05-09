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
package com.ubiqube.etsi.mano.service.event;

import java.util.Map;
import java.util.UUID;

import com.ubiqube.etsi.mano.service.event.model.NotificationEvent;

/**
 * Manage Asynchronous Event in the application. Current implementation is using
 * Quartz using it's thread pool, but we ill probably need an entreprise bus.
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public interface EventManager {
	/**
	 * Send MANO notifications.
	 *
	 * @param notificationEvent The notification event type.
	 * @param objectId          The Id.
	 */
	void sendNotification(NotificationEvent notificationEvent, UUID objectId, Map<String, String> additionalParameters);

	/**
	 * Create an asynchronous task VNFM oriented.
	 *
	 * @param actionType The type of the action.
	 * @param objectId   The object Id.
	 * @param parameters Additional parameters if any.
	 */
	void sendActionVnfm(ActionType actionType, UUID objectId, Map<String, Object> parameters);

	/**
	 * Create an asynchronous task NFVO oriented.
	 *
	 * @param actionType The type of the action.
	 * @param objectId   The object Id.
	 * @param parameters Additional parameters if any.
	 */
	void sendActionNfvo(ActionType actionType, UUID objectId, Map<String, Object> parameters);

	/**
	 * Create an asynchronous task, inside the application.
	 *
	 * @param actionType The type of the action.
	 * @param objectId   The object Id.
	 */
	void sendAction(ActionType actionType, UUID objectId);

	/**
	 * Send an asynchronous grant message.
	 *
	 * @param objectId   The grantResponse Id.
	 * @param parameters Additional parameters if any.
	 */
	void sendGrant(final UUID objectId, final Map<String, Object> parameters);

	/**
	 * Send the real notification.
	 *
	 * @param se
	 */
	void notificationSender(SubscriptionEvent se);
}
