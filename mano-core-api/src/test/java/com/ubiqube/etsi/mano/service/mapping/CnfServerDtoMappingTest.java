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

import com.ubiqube.etsi.mano.dao.mano.cnf.CnfServer;
import com.ubiqube.etsi.mano.dao.mano.dto.CnfServerDto;

class CnfServerDtoMappingTest {
	private final CnfServerDtoMapping mapper = Mappers.getMapper(CnfServerDtoMapping.class);

	@Test
	void test() {
		assertNull(mapper.map(null));
		CnfServerDto o = new CnfServerDto();
		CnfServer r = mapper.map(o);
		assertNotNull(r);
	}

}
