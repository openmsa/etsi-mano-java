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
package com.ubiqube.etsi.mano.vnfm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.PackageChangeType;
import com.ubiqube.etsi.mano.dao.mano.PackageOperationalState;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfPackageChangeNotification;
import com.ubiqube.etsi.mano.dao.mano.VnfPackageOnboardingNotification;
import com.ubiqube.etsi.mano.dao.subscription.RemoteSubscription;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.service.event.ActionType;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.repository.RemoteSubscriptionRepositoryService;
import com.ubiqube.etsi.mano.service.repository.VnfPackageRepositoryService;
import com.ubiqube.etsi.mano.vnfm.service.repository.VnfPackageOnboardingNotificationRepositoryService;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class VnfNotificationService {

	private static final Logger LOG = LoggerFactory.getLogger(VnfNotificationService.class);
	private final VnfPackageOnboardingNotificationRepositoryService vnfPackageOnboardingNotificationJpa;
	private final EventManager eventManager;
	private final RemoteSubscriptionRepositoryService remoteSubscriptionJpa;
	private final VnfPackageRepositoryService vnfPackageJpa;

	public VnfNotificationService(final VnfPackageOnboardingNotificationRepositoryService vnfPackageOnboardingNotificationJpa, final EventManager eventManager, final RemoteSubscriptionRepositoryService remoteSubscriptionJpa, final VnfPackageRepositoryService vnfPackageJpa) {
		this.vnfPackageOnboardingNotificationJpa = vnfPackageOnboardingNotificationJpa;
		this.eventManager = eventManager;
		this.remoteSubscriptionJpa = remoteSubscriptionJpa;
		this.vnfPackageJpa = vnfPackageJpa;
	}

	public void onNotification(final VnfPackageOnboardingNotification event, final String version) {
		final RemoteSubscription subscription = findRemoteSubscription(event.getSubscriptionId());
		event.setNfvoId(subscription.getRemoteServerId());
		event.setVersion(version);
		final VnfPackageOnboardingNotification newEvent = vnfPackageOnboardingNotificationJpa.save(event);
		LOG.info("Event received: {} => Id: {}", newEvent.getNfvoId(), newEvent.getId());
		eventManager.sendActionVnfm(ActionType.VNF_PKG_ONBOARD_DOWNLOAD, newEvent.getId(), new HashMap<>());
	}

	public void onChange(final VnfPackageChangeNotification event) {
		LOG.info("Receiver Change event {}", event);
		findRemoteSubscription(event.getSubscriptionId());
		if (event.getChangeType() == PackageChangeType.PKG_DELETE) {
			vnfPackageJpa.deleteByVnfdId(event.getVnfdId());
		} else {
			final Optional<VnfPackage> pkg = vnfPackageJpa.findByVnfdIdOpt(event.getVnfdId());
			if (pkg.isPresent()) {
				final VnfPackage p = pkg.get();
				p.setOperationalState(PackageOperationalState.fromValue(event.getOperationalState().toString()));
				vnfPackageJpa.save(p);
			} else {
				LOG.warn("Could not find vnfdId {}", event.getVnfdId());
			}
		}
	}

	private RemoteSubscription findRemoteSubscription(final String id) {
		final List<RemoteSubscription> subscription = remoteSubscriptionJpa.findByRemoteSubscriptionId(id);
		if (subscription.isEmpty()) {
			LOG.warn("Unable to find change event {} in database.", id);
			throw new NotFoundException("Unable to find notification event " + id);
		}
		return subscription.getFirst();
	}
}
