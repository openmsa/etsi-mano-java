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
package com.ubiqube.etsi.mano.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

class MetricGroupServiceTest {

	private MetricGroupService createService() {
		return new MetricGroupService();
	}

	@Test
	void test() {
		final MetricGroupService srv = createService();
		final List<String> res = srv.findByGroupName(null);
		assertNotNull(res);
	}

	@Test
	void testVirtualisedComputeResource() {
		final MetricGroupService srv = createService();
		final List<String> res = srv.findByGroupName("VirtualisedComputeResource");
		assertNotNull(res);
	}
}
