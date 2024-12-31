/**
 *     Copyright (C) 2019-2024 Ubiqube.
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
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.vnfm.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.alarm.Alarms;
import com.ubiqube.etsi.mano.vnfm.jpa.AlarmsJpa;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class AlarmRepositoryService {
	private final AlarmsJpa alarmsJpa;

	public AlarmRepositoryService(final AlarmsJpa alarmsJpa) {
		super();
		this.alarmsJpa = alarmsJpa;
	}

	public Optional<Alarms> findById(final UUID id) {
		return alarmsJpa.findById(id);
	}

	public Alarms save(final Alarms alarm) {
		return alarmsJpa.save(alarm);
	}

}
