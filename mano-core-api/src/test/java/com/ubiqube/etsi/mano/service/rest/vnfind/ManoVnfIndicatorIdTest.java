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
package com.ubiqube.etsi.mano.service.rest.vnfind;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.service.rest.ManoQueryBuilder;
import com.ubiqube.etsi.mano.service.rest.QueryParameters;

@ExtendWith(MockitoExtension.class)
class ManoVnfIndicatorIdTest {
	@Mock
	private QueryParameters client;
	@Mock
	private ManoQueryBuilder<Object, Object> manoQueryBuilder;
	private final UUID id = UUID.randomUUID();

	ManoVnfIndicatorId createService() {
		return new ManoVnfIndicatorId(client, id);
	}

	@Test
	void test() {
		final ManoVnfIndicatorId srv = createService();
		when(client.createQuery()).thenReturn(manoQueryBuilder);
		when(manoQueryBuilder.setWireOutClass(any())).thenReturn(manoQueryBuilder);
		when(manoQueryBuilder.setOutClass(any())).thenReturn(manoQueryBuilder);
		srv.find();
		assertTrue(true);
	}

}
