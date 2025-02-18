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
package com.ubiqube.etsi.mano.service.rest.vnffm;

import java.util.List;
import java.util.UUID;

import org.jspecify.annotations.NonNull;

import com.ubiqube.etsi.mano.alarm.entities.alarm.dto.AlarmDto;
import com.ubiqube.etsi.mano.service.rest.QueryParameters;

public class ManoVnfFm {
	@NonNull
	private final QueryParameters manoClient;

	public ManoVnfFm(final QueryParameters manoClient) {
		this.manoClient = manoClient;
	}

	public List<AlarmDto> find() {
		return List.of();
	}

	public ManoVnfFmId id(final UUID id) {
		return new ManoVnfFmId(manoClient, id);
	}

	public ManoVnfFmSubscription subscription() {
		return new ManoVnfFmSubscription(manoClient);
	}
}
