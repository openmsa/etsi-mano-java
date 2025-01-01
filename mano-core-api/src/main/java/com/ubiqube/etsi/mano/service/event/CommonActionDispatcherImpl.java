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
package com.ubiqube.etsi.mano.service.event;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.auth.config.TenantHolder;
import com.ubiqube.etsi.mano.service.vim.CirConnectionManager;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class CommonActionDispatcherImpl implements CommonActionDispatcher {
	private final CommonActionController controller;
	private final CapiServerChecker capiServerChecker;
	private final CirConnectionManager cirConnectionManager;

	public CommonActionDispatcherImpl(final CommonActionController controller, final CapiServerChecker capiServerChecker, final CirConnectionManager cirConnectionManager) {
		this.controller = controller;
		this.capiServerChecker = capiServerChecker;
		this.cirConnectionManager = cirConnectionManager;
	}

	@Override
	public void dispatch(final ActionType actionType, final UUID objectId, final String tenantId, final Map<String, Object> parameters) {
		TenantHolder.setTenantId(tenantId);
		switch (actionType) {
		case REGISTER_SERVER -> controller.registerServer(objectId, parameters);
		case REGISTER_CAPI -> capiServerChecker.verify(objectId, parameters);
		case REGISTER_CIR -> cirConnectionManager.checkConnectivity(objectId);
		default -> throw new IllegalArgumentException("Unexpected value: " + actionType);
		}

	}

}
