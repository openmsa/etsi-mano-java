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
package com.ubiqube.etsi.mano.nfvo.service.event;

import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.nfvo.service.pkg.ns.NsPackageOnboardingImpl;
import com.ubiqube.etsi.mano.service.event.ActionType;
import com.ubiqube.etsi.mano.service.pkg.vnf.VnfPackageOnboardingImpl;

@Service
public class NfvoActionController {

	private static final Logger LOG = LoggerFactory.getLogger(NfvoActionController.class);

	private final NfvoActions nfvoActions;

	private final NsPackageOnboardingImpl nsPackagingManager;

	private final VnfPackageOnboardingImpl vnfPackageOnboarding;

	private final NsUpadteManager nsUpdateManager;

	public NfvoActionController(final NfvoActions nfvoActions, final NsPackageOnboardingImpl nsPackagingManager, final VnfPackageOnboardingImpl vnfPackageOnboarding,
			final NsUpadteManager nsUpdateManager) {
		this.nfvoActions = nfvoActions;
		this.nsPackagingManager = nsPackagingManager;
		this.vnfPackageOnboarding = vnfPackageOnboarding;
		this.nsUpdateManager = nsUpdateManager;
	}

	public void dispatch(final ActionType eventType, final UUID objectId, final Map<String, Object> parameters) {
		switch (eventType) {
		case VNF_PKG_ONBOARD_FROM_URI -> vnfPackageOnboarding.vnfPackagesVnfPkgIdPackageContentUploadFromUriPost(objectId.toString());
		case VNF_PKG_ONBOARD_FROM_BYTES -> vnfPackageOnboarding.vnfPackagesVnfPkgIdPackageContentPut(objectId.toString());
		case NSD_PKG_ONBOARD_FROM_BYTES -> nsPackagingManager.nsOnboarding(objectId);
		case NS_INSTANTIATE -> nfvoActions.instantiate(objectId);
		case NS_TERMINATE -> nfvoActions.terminate(objectId);
		case NS_HEAL -> nfvoActions.heal(objectId);
		case NS_SCALE -> nfvoActions.scale(objectId);
		case NS_UPDATE -> nsUpdateManager.update(objectId);
		case NS_UPDATE_ACTION -> nfvoActions.update(objectId);
		default -> LOG.warn("Unknown event: {}", eventType);
		}
	}

}
