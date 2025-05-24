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

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ubiqube.mano.pdns.invoker.ApiClient;
import com.ubiqube.mano.pdns.invoker.auth.ApiKeyAuth;
import com.ubiqube.mano.pdns.model.Server;

import reactor.core.publisher.Flux;

class DefaultApiTest {

	private static final String PDNS_KEY = "";

	private ApiClient createClient() {
		ApiClient defaultClient = new ApiClient();
		defaultClient.setBasePath("http://10.31.1.180:8081/api/v1");
		ApiKeyAuth authKey = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
		authKey.setApiKey(PDNS_KEY);
		return defaultClient;
	}

	@Test
	void dummyTest() {
		// This is a dummy test to ensure the test class is recognized by the test
		// framework.
		// Actual tests are in the methods below.
		assertTrue(true, "This is a dummy test to ensure the test class is recognized.");
	}

	void test() {
		ServersApi apiInstance = new ServersApi(createClient());
		Flux<Server> result = apiInstance.listServers();
		result.toIterable().forEach(System.out::println);
	}

	void test2() {
		ZonesApi apiInstance = new ZonesApi(createClient());
		apiInstance.createZone(null, null, null);
	}
}
