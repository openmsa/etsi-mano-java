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

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.controller.lcmgrant.GrantManagement;
import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.service.rest.ManoClientFactory;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class VnfmGrantManagementImpl implements GrantManagement {
	private final ManoClientFactory manoClientFactory;

	public VnfmGrantManagementImpl(final ManoClientFactory manoClientFactory) {
		this.manoClientFactory = manoClientFactory;
	}

	@Override
	public GrantResponse get(final UUID grantId) {
		return manoClientFactory.getClient()
				.grant().id(grantId)
				.find();
	}

	@Override
	public GrantResponse post(final GrantResponse grant) {
		return manoClientFactory.getClient()
				.grant()
				.create(grant);
	}

}
