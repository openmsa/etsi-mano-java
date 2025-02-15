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
package com.ubiqube.etsi.mano.service.mapping;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.ubiqube.etsi.mano.dao.mano.AccessInfo;
import com.ubiqube.etsi.mano.dao.mano.InterfaceInfo;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.orchestrator.entities.SystemConnections;

class SystemConnectionsMappingTest {
	private final SystemConnectionsMapping mapper = Mappers.getMapper(SystemConnectionsMapping.class);

	@Test
	void test() {
		assertNull(mapper.map(null));
		VimConnectionInformation o = new VimConnectionInformation();
		o.setVimType("ETSINFV.OPENSTACK_KEYSTONE.V_3");
		SystemConnections r = mapper.map(o);
		assertNotNull(r);
	}

	@Test
	void testMapToOpenstackV3Ai() {
		AccessInfo ai = new AccessInfo();
		AccessInfo r = mapper.mapToOpenstackV3Ai(ai);
		assertNotNull(r);
		assertNull(mapper.mapToOpenstackV3Ai(null));
	}

	@Test
	void testMapToOpenstackV3InterfaceInfo() {
		InterfaceInfo ii = new InterfaceInfo();
		InterfaceInfo r = mapper.mapToOpenstackV3InterfaceInfo(ii);
		assertNotNull(r);
		assertNull(mapper.mapToOpenstackV3InterfaceInfo(null));
	}

	@Test
	void testMapToK8sAi() {
		AccessInfo ai = new AccessInfo();
		AccessInfo r = mapper.mapToK8sAi(ai);
		assertNotNull(r);
		assertNull(mapper.mapToK8sAi(null));
	}

	@Test
	void mapMapToK8sInterfaceInfo() {
		InterfaceInfo ii = new InterfaceInfo();
		InterfaceInfo r = mapper.mapToK8sInterfaceInfo(ii);
		assertNotNull(r);
		assertNull(mapper.mapToK8sInterfaceInfo(null));
	}
}
