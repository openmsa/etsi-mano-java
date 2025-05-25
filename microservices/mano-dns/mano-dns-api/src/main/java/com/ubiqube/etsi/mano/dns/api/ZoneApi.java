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
package com.ubiqube.etsi.mano.dns.api;

import java.util.List;

import com.ubiqube.etsi.mano.dns.api.model.Zone;

public class ZoneApi {
	private ManoDnsFrontend frontend;

	public List<Zone> list() {
		return frontend.zoneList();
	}

	public boolean delete(final String zoneId) {
		return frontend.zoneDelete(zoneId);
	}

	public Zone create(final Zone zone) {
		return frontend.zoneCreate(zone);
	}

	public Zone modify(final String zoneId, final Zone zone) {
		return frontend.zoneModify(zoneId, zone);
	}

	public boolean exists(final String zoneId) {
		return frontend.zoneExists(zoneId);
	}

	public RecordApi rrset() {
		return new RecordApi();
	}
}
