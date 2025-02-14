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
