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
package com.ubiqube.etsi.mano.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.service.event.model.EventMessage;

import jakarta.annotation.security.RolesAllowed;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@RestController
@RolesAllowed({ "ROLE_ADMIN" })
@RequestMapping("/admin/event")
public class EventController {

	private static final Logger LOG = LoggerFactory.getLogger(EventController.class);

	private final JmsTemplate jmsTemplate;

	public EventController(final JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	@PostMapping(value = "/notification", consumes = { "application/json" })
	public ResponseEntity<Void> notification(@RequestBody final EventMessageDto ev) {
		final EventMessage msg = map(ev);
		jmsTemplate.convertAndSend("system.notifications", msg);
		LOG.info("Notification sent.");
		return ResponseEntity.noContent().build();
	}

	private static EventMessage map(final EventMessageDto ev) {
		final EventMessage nev = new EventMessage();
		nev.setAdditionalParameters(ev.getAdditionalParameters());
		nev.setNotificationEvent(ev.getNotificationEvent());
		nev.setObjectId(ev.getObjectId());
		return nev;
	}

}
