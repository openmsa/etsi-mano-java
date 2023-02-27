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
package com.ubiqube.etsi.mano.nfvo.v261.controller.nsfm;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.nfvo.v261.model.nsfm.AlarmModifications;
import com.ubiqube.etsi.mano.nfvo.v261.model.nsfm.InlineResponse200;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@RestController
public class Alarms261Sol005Controller implements Alarms261Sol005Api {

	@Override
	public ResponseEntity<Object> alarmsAlarmIdGet(final String alarmId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Object> alarmsAlarmIdPatch(@Valid final AlarmModifications body, final String alarmId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<List<InlineResponse200>> alarmsGet(@Valid final String filter, @Valid final String nextpageOpaqueMarker) {
		// TODO Auto-generated method stub
		return null;
	}

}
