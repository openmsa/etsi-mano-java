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
package com.ubiqube.etsi.mano.service.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.cfg.MultiTenancySettings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.auth.config.TenantHolder;

class TenantIdentifierResolverTest {

	private TenantIdentifierResolver tenantIdentifierResolver;

	@BeforeEach
	public void setUp() {
		tenantIdentifierResolver = new TenantIdentifierResolver();
	}

	@Test
	void testCustomize() {
		Map<String, Object> hibernateProperties = new HashMap<>();
		tenantIdentifierResolver.customize(hibernateProperties);
		assertEquals(tenantIdentifierResolver, hibernateProperties.get(MultiTenancySettings.MULTI_TENANT_IDENTIFIER_RESOLVER));
	}

	@Test
	void testResolveCurrentTenantIdentifier() {
		TenantHolder.setTenantId("testTenant");
		assertEquals("testTenant", tenantIdentifierResolver.resolveCurrentTenantIdentifier());

		TenantHolder.setTenantId(null);
		assertEquals("BOOTSTRAP", tenantIdentifierResolver.resolveCurrentTenantIdentifier());
	}

	@Test
	void testValidateExistingCurrentSessions() {
		TenantHolder.setTenantId("testTenant");
		assertTrue(tenantIdentifierResolver.validateExistingCurrentSessions());

		TenantHolder.setTenantId(null);
		assertTrue(!tenantIdentifierResolver.validateExistingCurrentSessions());
	}
}
