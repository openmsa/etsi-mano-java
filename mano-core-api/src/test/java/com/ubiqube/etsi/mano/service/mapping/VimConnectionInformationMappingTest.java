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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.ubiqube.etsi.mano.dao.mano.ai.KeystoneAuthV3;
import com.ubiqube.etsi.mano.dao.mano.ai.KubernetesV1Auth;
import com.ubiqube.etsi.mano.dao.mano.dto.VimConnectionRegistrationDto;
import com.ubiqube.etsi.mano.dao.mano.ii.K8sInterfaceInfo;
import com.ubiqube.etsi.mano.dao.mano.ii.OpenstackV3InterfaceInfo;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.k8s.K8sServers;

import uk.co.jemos.podam.api.PodamFactoryImpl;

class VimConnectionInformationMappingTest {

	private VimConnectionInformationMappingImpl mapper;
	private OpenstackV3InterfaceInfo keystoneAuthV3;
	private KubernetesV1Auth kubernetesV1Auth;

	VimConnectionInformationMapping createService() {
		return Mappers.getMapper(VimConnectionInformationMapping.class);
	}

	@BeforeEach
	public void setUp() {
		mapper = new VimConnectionInformationMappingImpl();
		keystoneAuthV3 = new OpenstackV3InterfaceInfo();
		kubernetesV1Auth = new KubernetesV1Auth();
	}

	@Test
	void testVimConnectionInfoDtoNull() {
		final VimConnectionInformationMapping srv = createService();
		final VimConnectionInformation res = srv.map((VimConnectionRegistrationDto) null);
		assertNull(res);
	}

	@Test
	void testVimConnectionInfoDto() {
		final VimConnectionInformationMapping srv = createService();
		final VimConnectionRegistrationDto o = new VimConnectionRegistrationDto();
		o.setVimType("BAD");
		assertThrows(IllegalArgumentException.class, () -> srv.map(o));
	}

	@Test
	void testVimConnectionInfoDtoK8s() {
		final VimConnectionInformationMapping srv = createService();
		final VimConnectionRegistrationDto o = new VimConnectionRegistrationDto();
		o.setVimType("UBINFV.CISM.V_1");
		final VimConnectionInformation res = srv.map(o);
		assertNotNull(res);
	}

	@Test
	void testVimConnectionInfoDtoOs1() {
		final VimConnectionInformationMapping srv = createService();
		final VimConnectionRegistrationDto o = new VimConnectionRegistrationDto();
		o.setVimType("ETSINFV.OPENSTACK_KEYSTONE.V_3");
		final VimConnectionInformation res = srv.map(o);
		assertNotNull(res);
	}

	@Test
	void testVimConnectionInfo001() {
		final VimConnectionInformationMapping srv = createService();
		final VimConnectionInformation res = srv.map((VimConnectionInformation) null);
		assertNull(res);
	}

	@Test
	void testVimConnectionInfo002() {
		final VimConnectionInformationMapping srv = createService();
		final VimConnectionInformation o = new VimConnectionInformation();
		o.setVimType("ETSINFV.OPENSTACK_KEYSTONE.V_3");
		final VimConnectionInformation res = srv.map(o);
		assertNotNull(res);
	}

	@Test
	void testVimConnectionInfo003() {
		final VimConnectionInformationMapping srv = createService();
		final VimConnectionInformation o = new VimConnectionInformation();
		o.setVimType("UBINFV.CISM.V_1");
		final VimConnectionInformation res = srv.map(o);
		assertNotNull(res);
	}

	@Test
	void testMapUuid() {
		final VimConnectionInformationMapping srv = createService();
		final UUID res = srv.mapUuid(null);
		assertNull(res);
	}

	@Test
	void testMapUuidNotNull() {
		final VimConnectionInformationMapping srv = createService();
		final UUID res = srv.mapUuid("19ac766f-1c72-4e75-b58f-04f77c7b0e79");
		assertNotNull(res);
	}

	@Test
	void testAuto() {
		PodamFactoryImpl podam = new PodamFactoryImpl();
		podam.getStrategy().setDefaultNumberOfCollectionElements(1);
		final VimConnectionRegistrationDto o = podam.manufacturePojo(VimConnectionRegistrationDto.class);
		final VimConnectionInformationMapping srv = createService();
		o.setVimType("ETSINFV.OPENSTACK_KEYSTONE.V_3");
		final VimConnectionInformation res = srv.map(o);
		assertNotNull(res);
	}

	@Test
	void testAutok8s() {
		PodamFactoryImpl podam = new PodamFactoryImpl();
		podam.getStrategy().setDefaultNumberOfCollectionElements(1);
		final K8sServers o = podam.manufacturePojo(K8sServers.class);
		final VimConnectionInformationMapping srv = createService();
		VimConnectionInformation res = srv.mapFromTls(o);
		assertNotNull(res);
		res = srv.mapFromTls(null);
		assertNull(res);
	}

	@Test
	void testMapToKeystoneAuthV3() {
		PodamFactoryImpl podam = new PodamFactoryImpl();
		podam.getStrategy().setDefaultNumberOfCollectionElements(1);
		final KeystoneAuthV3 o = podam.manufacturePojo(KeystoneAuthV3.class);
		final VimConnectionInformationMapping srv = createService();
		final KeystoneAuthV3 res = srv.mapToKeystoneAuthV3(o);
		assertNotNull(res);
	}

	@Test
	void testKubernetesV1Auth() {
		PodamFactoryImpl podam = new PodamFactoryImpl();
		podam.getStrategy().setDefaultNumberOfCollectionElements(1);
		final KubernetesV1Auth o = podam.manufacturePojo(KubernetesV1Auth.class);
		final VimConnectionInformationMapping srv = createService();
		final KubernetesV1Auth res = srv.mapToK8sInterfaceInfo(o);
		assertNotNull(res);
	}

	@Test
	void testOpenstackV3InterfaceInfo() {
		PodamFactoryImpl podam = new PodamFactoryImpl();
		podam.getStrategy().setDefaultNumberOfCollectionElements(1);
		final OpenstackV3InterfaceInfo o = podam.manufacturePojo(OpenstackV3InterfaceInfo.class);
		final VimConnectionInformationMapping srv = createService();
		final OpenstackV3InterfaceInfo res = srv.mapToOpenstackV3InterfaceInfo(o);
		assertNotNull(res);
	}

	@Test
	void testK8sInterfaceInfo() {
		PodamFactoryImpl podam = new PodamFactoryImpl();
		podam.getStrategy().setDefaultNumberOfCollectionElements(1);
		final K8sInterfaceInfo o = podam.manufacturePojo(K8sInterfaceInfo.class);
		final VimConnectionInformationMapping srv = createService();
		final K8sInterfaceInfo res = srv.mapToK8sInterfaceInfo(o);
		assertNotNull(res);
	}

	@Test
	void testMapToK8sInterfaceInfo_NullInput() {
		K8sInterfaceInfo k8sInterfaceInfo = new K8sInterfaceInfo();
		K8sInterfaceInfo result = mapper.mapToK8sInterfaceInfo(null, k8sInterfaceInfo);
		assertSame(k8sInterfaceInfo, result);
	}

	@Test
	void testMapToK8sInterfaceInfo_EmptyMap() {
		K8sInterfaceInfo k8sInterfaceInfo = new K8sInterfaceInfo();
		K8sInterfaceInfo result = mapper.mapToK8sInterfaceInfo(new HashMap<>(), k8sInterfaceInfo);
		assertNull(result.getEndpoint());
		assertNull(result.getId());
		assertNull(result.getIface());
		assertNull(result.getNatHost());
		assertNull(result.getRegionName());
		assertNull(result.getSdnEndpoint());
		assertNull(result.getCertificateAuthorityData());
	}

	@Test
	void testMapToK8sInterfaceInfo_ValidMap() {
		UUID id = UUID.randomUUID();
		Map<String, String> interfaceInfo = new HashMap<>();
		interfaceInfo.put("connectionTimeout", "1000");
		interfaceInfo.put("endpoint", "http://example.com");
		interfaceInfo.put("id", id.toString());
		interfaceInfo.put("iface", "eth0");
		interfaceInfo.put("natHost", "nat.example.com");
		interfaceInfo.put("nonStrictSsl", "true");
		interfaceInfo.put("readTimeout", "2000");
		interfaceInfo.put("regionName", "us-west-1");
		interfaceInfo.put("retry", "3");
		interfaceInfo.put("sdnEndpoint", "http://sdn.example.com");
		interfaceInfo.put("certificateAuthorityData", "certData");

		K8sInterfaceInfo k8sInterfaceInfo = new K8sInterfaceInfo();
		K8sInterfaceInfo result = mapper.mapToK8sInterfaceInfo(interfaceInfo, k8sInterfaceInfo);

		assertEquals(1000, result.getConnectionTimeout());
		assertEquals("http://example.com", result.getEndpoint());
		assertEquals(id, result.getId());
		assertEquals("eth0", result.getIface());
		assertEquals("nat.example.com", result.getNatHost());
		assertTrue(result.isNonStrictSsl());
		assertEquals(2000, result.getReadTimeout());
		assertEquals("us-west-1", result.getRegionName());
		assertEquals(3, result.getRetry());
		assertEquals("http://sdn.example.com", result.getSdnEndpoint());
		assertEquals("certData", result.getCertificateAuthorityData());
	}

	@Test
	void testMapToOpenstackV3InterfaceInfo_NullInterfaceInfo() {
		OpenstackV3InterfaceInfo result = mapper.mapToOpenstackV3InterfaceInfo(null, keystoneAuthV3);
		assertSame(keystoneAuthV3, result);
	}

	@Test
	void testMapToOpenstackV3InterfaceInfo_AllKeysPresent() {
		Map<String, String> interfaceInfo = new HashMap<>();
		UUID id = UUID.randomUUID();
		interfaceInfo.put("connectionTimeout", "1000");
		interfaceInfo.put("endpoint", "http://example.com");
		interfaceInfo.put("id", id.toString());
		interfaceInfo.put("iface", "eth0");
		interfaceInfo.put("natHost", "nat.example.com");
		interfaceInfo.put("nonStrictSsl", "true");
		interfaceInfo.put("readTimeout", "2000");
		interfaceInfo.put("regionName", "region1");
		interfaceInfo.put("retry", "3");
		interfaceInfo.put("sdnEndpoint", "http://sdn.example.com");
		interfaceInfo.put("skipCertificateHostnameCheck", "true");
		interfaceInfo.put("skipCertificateVerification", "false");
		OpenstackV3InterfaceInfo result = mapper.mapToOpenstackV3InterfaceInfo(interfaceInfo, keystoneAuthV3);

		assertEquals(1000, result.getConnectionTimeout());
		assertEquals("http://example.com", result.getEndpoint());
		assertEquals(id, result.getId());
		assertEquals("eth0", result.getIface());
		assertEquals("nat.example.com", result.getNatHost());
		assertTrue(result.isNonStrictSsl());
		assertEquals(2000, result.getReadTimeout());
		assertEquals("region1", result.getRegionName());
		assertEquals(3, result.getRetry());
		assertEquals("http://sdn.example.com", result.getSdnEndpoint());
		assertTrue(result.isSkipCertificateHostnameCheck());
		assertEquals("false", result.getSkipCertificateVerification());
	}

	@Test
	void testMapToOpenstackV3InterfaceInfo_SomeKeysMissing() {
		Map<String, String> interfaceInfo = new HashMap<>();
		interfaceInfo.put("connectionTimeout", "1000");
		interfaceInfo.put("endpoint", "http://example.com");
		OpenstackV3InterfaceInfo result = mapper.mapToOpenstackV3InterfaceInfo(interfaceInfo, keystoneAuthV3);

		assertEquals(1000, result.getConnectionTimeout());
		assertEquals("http://example.com", result.getEndpoint());
		assertNull(result.getId());
		assertNull(result.getIface());
		assertNull(result.getNatHost());
		assertFalse(result.isNonStrictSsl());
		assertEquals(5000, result.getReadTimeout());
		assertNull(result.getRegionName());
		assertEquals(5, result.getRetry());
		assertNull(result.getSdnEndpoint());
		assertFalse(result.isSkipCertificateHostnameCheck());
		assertNull(result.getSkipCertificateVerification());
	}

	@Test
	void testMapToKubernetesV1Auth_NullAccessInfo() {
		KubernetesV1Auth result = mapper.mapToKubernetesV1Auth(null, kubernetesV1Auth);
		assertSame(kubernetesV1Auth, result);
	}

	@Test
	void testMapToKubernetesV1Auth_AllKeysPresent() {
		UUID id = UUID.randomUUID();
		Map<String, String> accessInfo = new HashMap<>();
		accessInfo.put("id", id.toString());
		accessInfo.put("clientCertificateData", "certData");
		accessInfo.put("clientKeyData", "keyData");

		KubernetesV1Auth result = mapper.mapToKubernetesV1Auth(accessInfo, kubernetesV1Auth);

		assertEquals(id, result.getId());
		assertEquals("certData", result.getClientCertificateData());
		assertEquals("keyData", result.getClientKeyData());
	}

	@Test
	void testMapToKubernetesV1Auth_SomeKeysMissing() {
		Map<String, String> accessInfo = new HashMap<>();
		UUID id = UUID.randomUUID();
		accessInfo.put("id", id.toString());

		KubernetesV1Auth result = mapper.mapToKubernetesV1Auth(accessInfo, kubernetesV1Auth);

		assertEquals(id, result.getId());
		assertNull(result.getClientCertificateData());
		assertNull(result.getClientKeyData());
	}
}
