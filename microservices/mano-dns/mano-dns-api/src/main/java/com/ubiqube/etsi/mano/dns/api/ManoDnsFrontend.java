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

import com.ubiqube.etsi.mano.dns.api.model.RrSet;
import com.ubiqube.etsi.mano.dns.api.model.Zone;

public interface ManoDnsFrontend {

	/**
	 * List all RR sets.
	 *
	 * @return a list of RR sets
	 */
	List<RrSet> recordList(String zoneId);

	boolean recordDelete(String zoneId, String rrSetName, String type);

	boolean recordCreate(String zoneId, RrSet rrSet);

	boolean recordModify(String zoneId, String rrSetName, RrSet rrSet);

	List<Zone> zoneList();

	boolean zoneDelete(String zoneId);

	Zone zoneCreate(Zone zone);

	Zone zoneModify(String zoneId, Zone zone);

	boolean zoneExists(String zoneId);

}
