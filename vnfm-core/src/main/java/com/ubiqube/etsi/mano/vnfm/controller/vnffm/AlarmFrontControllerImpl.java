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
package com.ubiqube.etsi.mano.vnfm.controller.vnffm;

import static com.ubiqube.etsi.mano.Constants.ALARM_SEARCH_DEFAULT_EXCLUDE_FIELDS;
import static com.ubiqube.etsi.mano.Constants.ALARM_SEARCH_MANDATORY_FIELDS;
import static com.ubiqube.etsi.mano.Constants.getSafeUUID;

import java.util.function.Consumer;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.controller.AlarmVnfmController;
import com.ubiqube.etsi.mano.dao.mano.alarm.AckState;
import com.ubiqube.etsi.mano.dao.mano.alarm.Alarms;
import com.ubiqube.etsi.mano.dao.mano.alarm.PerceivedSeverityType;
import com.ubiqube.etsi.mano.vnfm.fc.vnffm.AlarmFrontController;

import jakarta.annotation.Nullable;
import ma.glasnost.orika.MapperFacade;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class AlarmFrontControllerImpl implements AlarmFrontController {

	private final MapperFacade mapper;

	private final AlarmVnfmController alarmVnfmController;

	public AlarmFrontControllerImpl(final MapperFacade mapper, final AlarmVnfmController alarmVnfmController) {
		this.mapper = mapper;
		this.alarmVnfmController = alarmVnfmController;
	}

	@Override
	public ResponseEntity<Void> escalate(final String alarmId, final PerceivedSeverityType perceivedSeverityRequest) {
		alarmVnfmController.escalate(getSafeUUID(alarmId), perceivedSeverityRequest);
		return ResponseEntity.noContent().build();
	}

	@Override
	public <U> ResponseEntity<U> findById(final String alarmId, final Class<U> clazz, final Consumer<U> makeLink) {
		final Alarms alarm = alarmVnfmController.findById(getSafeUUID(alarmId));
		final U ret = mapper.map(alarm, clazz);
		makeLink.accept(ret);
		return ResponseEntity.ok(ret);
	}

	@Override
	public <U> ResponseEntity<U> patch(final String alarmId, final AckState ackState, final @Nullable String ifMatch, final Class<U> clazz) {
		final Alarms alarm = alarmVnfmController.modify(getSafeUUID(alarmId), ackState, ifMatch);
		return ResponseEntity.ok(mapper.map(alarm, clazz));
	}

	@Override
	public <U> ResponseEntity<String> search(final MultiValueMap<String, String> requestParams, final Class<U> clazz, final Consumer<U> makeLink) {
		return alarmVnfmController.search(requestParams, clazz, ALARM_SEARCH_DEFAULT_EXCLUDE_FIELDS, ALARM_SEARCH_MANDATORY_FIELDS, makeLink);
	}

}
