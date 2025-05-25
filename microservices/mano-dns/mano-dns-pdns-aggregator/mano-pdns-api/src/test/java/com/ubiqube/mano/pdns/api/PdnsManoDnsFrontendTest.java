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
package com.ubiqube.mano.pdns.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.dns.api.model.RrSet;
import com.ubiqube.etsi.mano.dns.api.model.Zone;

class PdnsManoDnsFrontendTest {
	private PDnsConnInfo createConnInfo() {
		return PDnsConnInfo.builder()
				.serverId("localhost")
				.baseUrl(URI.create("http://10.31.1.180:8081/api/v1"))
				.apiKey("aaaa-bbbb-cccc-dddd-eeee-ffff")
				.build();
	}

	@Test
	void dummyTest() {
		// This is a dummy test to ensure the test class is recognized by the test
		// framework.
	}

	void test() {
		PdnsManoDnsFrontend frontend = new PdnsManoDnsFrontend(createConnInfo());
		List<Zone> res = frontend.zoneList();
		assertTrue(res.isEmpty(), "Zone list should be empty for test purposes.");
		Zone zone = Zone.builder()
				.name("exemple.com.")
				.id("exemple.com")
				.ttl(3600)
				.build();
		frontend.zoneCreate(zone);
		res = frontend.zoneList();
		assertEquals(1, res.size(), "Zone list should contain one zone after creation.");
		List<RrSet> rl = frontend.recordList("exemple.com.");
		assertEquals(2, rl.size(), "Record list should have only SOA + 2*NS record.");
		assertEquals("SOA", rl.get(0).getType(), "Record type should be SOA.");
		frontend.recordCreate("exemple.com", RrSet.builder()
				.name("www.exemple.com.")
				.ttl(3600)
				.type("A")
				.records(List.of("192.168.1.1"))
				// .comments(List.of("Heelo World!"))
				.build());
		rl = frontend.recordList("exemple.com.");
		assertEquals(3, rl.size(), "Record list should contain one record after creation.");
		Optional<RrSet> ra = rl.stream().filter(x -> "A".equals(x.getType())).findFirst();
		assertTrue(ra.isPresent(), "Record list should contain an A record.");
		assertEquals("www.exemple.com.", ra.get().getName(), "Record name should be 'www'.");
		frontend.recordDelete("exemple.com", "www.exemple.com.", "A");
		rl = frontend.recordList("exemple.com.");
		assertEquals(2, rl.size(), "Record list should have only SOA record.");
		//
		boolean exist = frontend.zoneExists("exemple.com.");
		assertTrue(exist, "Zone should exist after creation.");
		exist = frontend.zoneExists("exemple2.com.");
		assertFalse(exist, "Zone should exist after creation.");
		frontend.zoneDelete("exemple.com.");
		res = frontend.zoneList();
		assertTrue(res.isEmpty(), "Zone list should be empty for test purposes.");
	}

}
