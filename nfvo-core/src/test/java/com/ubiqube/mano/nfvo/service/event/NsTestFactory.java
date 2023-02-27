/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
package com.ubiqube.mano.nfvo.service.event;

import java.util.UUID;

import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;

public class NsTestFactory {

	private NsTestFactory() {
		//
	}

	public static NsBlueprint createNsBlueprint() {
		final NsBlueprint n = new NsBlueprint();
		n.setId(UUID.randomUUID());
		n.setNsInstance(createInstance());
		n.setParameters(new BlueprintParameters());
		return n;
	}

	public static NsdInstance createInstance() {
		final NsdInstance i = new NsdInstance();
		i.setId(UUID.randomUUID());
		return i;
	}
}
