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
package com.ubiqube.etsi.mano.service.event.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import com.ubiqube.etsi.mano.service.event.ActionController;
import com.ubiqube.etsi.mano.service.event.ActionMessage;

import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class NfvoActionListener {

	private static final Logger LOG = LoggerFactory.getLogger(NfvoActionListener.class);

	private final ActionController actionController;

	public NfvoActionListener(final ActionController actionController) {
		this.actionController = actionController;
	}

	@JmsListener(destination = Constants.QUEUE_NFVO_ACTIONS, concurrency = "10-25")
	@Transactional(TxType.NEVER)
	@org.springframework.transaction.annotation.Transactional(propagation = Propagation.NEVER)
	public void onEvent(final ActionMessage ev) {
		LOG.info("JMS NFVO ActionController Receiving Action: {}", ev);
		actionController.onEvent(ev);
		LOG.info("JMS NFVO ActionController Done for event: {}", ev);
	}

}
