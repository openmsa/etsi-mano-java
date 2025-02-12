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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.ubiqube.etsi.mano.dao.mano.AccessInfo;
import com.ubiqube.etsi.mano.dao.mano.InterfaceInfo;
import com.ubiqube.etsi.mano.dao.mano.ai.BasicAccess;
import com.ubiqube.etsi.mano.dao.mano.ai.KeystoneAuthV3;
import com.ubiqube.etsi.mano.dao.mano.ai.KubernetesV1Auth;
import com.ubiqube.etsi.mano.dao.mano.ai.OAuth2Access;
import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.ii.K8sInterfaceInfo;
import com.ubiqube.etsi.mano.dao.mano.ii.OpenstackV3InterfaceInfo;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.service.auth.model.AuthParamBasic;
import com.ubiqube.etsi.mano.service.auth.model.AuthParamOauth2;
import com.ubiqube.etsi.mano.service.auth.model.AuthType;
import com.ubiqube.etsi.mano.service.auth.model.AuthentificationInformations;
import com.ubiqube.etsi.mano.service.auth.model.OAuth2GrantType;

import uk.co.jemos.podam.api.PodamFactoryImpl;

class ConnectionMappingTest {
	private final ConnectionMapping mapper = Mappers.getMapper(ConnectionMapping.class);

	private final PodamFactoryImpl podam;

	public ConnectionMappingTest() {
		this.podam = new PodamFactoryImpl();
	}

	@Test
	void testInterfaceInfo() {
		final InterfaceInfo ai = podam.manufacturePojo(InterfaceInfo.class);
		final Map<String, String> res = mapper.map(ai);
		assertNotNull(res);
	}

	@Test
	void testInterfaceInfoNull() {
		final Map<String, String> res = mapper.map((InterfaceInfo) null);
		assertNotNull(res);
	}

	@Test
	void testAccessInfo() {
		final KeystoneAuthV3 ai = podam.manufacturePojo(KeystoneAuthV3.class);
		final Map<String, String> res = mapper.map(ai);
		assertNotNull(res);
	}

	@Test
	void testAccessInfoNull() {
		final Map<String, String> res = mapper.map((InterfaceInfo) null);
		assertNotNull(res);
	}

	@Test
	void testMapToInterfaceInfo001() {
		final Map<String, String> map = Map.of();
		assertThrows(GenericException.class, () -> mapper.mapToInterfaceInfo("BAD", map));
	}

	@Test
	void testMapToInterfaceInfo002() {
		final Map<String, String> map = Map.of();
		final InterfaceInfo res = mapper.mapToInterfaceInfo("ETSINFV.OPENSTACK_KEYSTONE.V_3", map);
		assertNotNull(res);
	}

	@Test
	void testMapToInterfaceInfo003() {
		final InterfaceInfo res = mapper.mapToInterfaceInfo("BAD", null);
		assertNull(res);
	}

	@Test
	void testMapToInterfaceInfo004() {
		final Map<String, String> map = Map.of();
		final InterfaceInfo res = mapper.mapToInterfaceInfo("UBINFV.CISM.V_1", map);
		assertNotNull(res);
	}

	@Test
	void testMapToInterfaceInfo005() {
		final Map<String, String> map = Map.of();
		final InterfaceInfo res = mapper.mapToInterfaceInfo("PAAS", map);
		assertNotNull(res);
	}

	@Test
	void testMapToAccessInfo001() {
		final Map<String, String> map = Map.of();
		final AccessInfo res = mapper.mapToAccessInfo("PAAS", map);
		assertNotNull(res);
	}

	@Test
	void testMapToAccessInfo002() {
		final AccessInfo res = mapper.mapToAccessInfo("PAAS", null);
		assertNull(res);
	}

	@Test
	void testMapToAccessInfo003() {
		final Map<String, String> map = Map.of();
		final AccessInfo res = mapper.mapToAccessInfo("ETSINFV.OPENSTACK_KEYSTONE.V_3", map);
		assertNotNull(res);
	}

	@Test
	void testMapToAccessInfo004() {
		final Map<String, String> map = Map.of();
		final AccessInfo res = mapper.mapToAccessInfo("UBINFV.CISM.V_1", map);
		assertNotNull(res);
	}

	@Test
	void testMapToAccessInfo005() {
		final Map<String, String> map = Map.of();
		assertThrows(GenericException.class, () -> mapper.mapToAccessInfo("BAD", map));
	}

	@Test
	void testMap001() {
		final Map<String, String> res = mapper.map((AccessInfo) null);
		assertNotNull(res);
	}

	@Test
	void testMap002() {
		final AccessInfo ai = new AccessInfo();
		final Map<String, String> res = mapper.map(ai);
		assertNotNull(res);
	}

	@Test
	void testMap003() {
		final AccessInfo ai = new KeystoneAuthV3();
		ai.setId(UUID.randomUUID());
		final Map<String, String> res = mapper.map(ai);
		assertNotNull(res);
	}

	@Test
	void testMap004() {
		final AccessInfo ai = new KubernetesV1Auth();
		ai.setId(UUID.randomUUID());
		final Map<String, String> res = mapper.map(ai);
		assertNotNull(res);
	}

	@Test
	void testMap005() {
		final Map<String, String> res = mapper.map((KeystoneAuthV3) null);
		assertNotNull(res);
	}

	@Test
	void testMapToVimType001() {
		final ConnectionInformation value = new ConnectionInformation();
		final String res = mapper.mapToVimType(value);
		assertNotNull(res);
	}

	@Test
	void testMapToVimType002() {
		final ConnectionInformation value = new ConnectionInformation();
		final AuthentificationInformations auth = new AuthentificationInformations();
		auth.setAuthType(List.of(AuthType.BASIC));
		value.setAuthentification(auth);
		final String res = mapper.mapToVimType(value);
		assertNotNull(res);
	}

	@Test
	void testMapToVimType003() {
		final String res = mapper.mapToVimType(null);
		assertNull(res);
	}

	@Test
	void testMapToMap001() {
		final Map<String, String> res = mapper.mapToMap(null);
		assertNotNull(res);
	}

	@Test
	void testMapToMap002() {
		final AuthentificationInformations ai = new AuthentificationInformations();
		ai.setAuthType(List.of(AuthType.BASIC));
		final AuthParamBasic basic = new AuthParamBasic();
		ai.setAuthParamBasic(basic);
		final Map<String, String> res = mapper.mapToMap(ai);
		assertNotNull(res);
	}

	@Test
	void testMapToMap003() {
		final AuthentificationInformations ai = new AuthentificationInformations();
		ai.setAuthType(List.of(AuthType.OAUTH2_CLIENT_CREDENTIALS));
		final AuthParamOauth2 auth = new AuthParamOauth2();
		ai.setAuthParamOauth2(auth);
		final Map<String, String> res = mapper.mapToMap(ai);
		assertNotNull(res);
	}

	@Test
	void testMapToMap004() {
		final AuthentificationInformations ai = new AuthentificationInformations();
		ai.setAuthType(List.of(AuthType.OAUTH2_CLIENT_CREDENTIALS));
		final AuthParamOauth2 auth = new AuthParamOauth2();
		auth.setGrantType(OAuth2GrantType.CLIENT_CREDENTIAL);
		ai.setAuthParamOauth2(auth);
		final Map<String, String> res = mapper.mapToMap(ai);
		assertNotNull(res);
	}

	@Test
	void testMapToInterfaceMap001() {
		final Map<String, String> res = mapper.mapToInterfaceMap(null);
		assertNotNull(res);
	}

	@Test
	void testMapToInterfaceMap002() {
		final ConnectionInformation ci = new ConnectionInformation();
		final Map<String, String> res = mapper.mapToInterfaceMap(ci);
		assertNotNull(res);
	}

	@Test
	void testMapToInterfaceMap003() {
		final ConnectionInformation ci = new ConnectionInformation();
		ci.setIgnoreSsl(true);
		ci.setUrl(URI.create("http://localhost/"));
		final Map<String, String> res = mapper.mapToInterfaceMap(ci);
		assertNotNull(res);
	}

	@Test
	void testK8sInterfaceInfo() {
		final K8sInterfaceInfo kii = new K8sInterfaceInfo();
		kii.setCertificateAuthorityData("data");
		final Map<String, String> res = mapper.map(kii);
		assertNotNull(res);
		assertEquals("data", res.get("certificate-authority-data"));
	}

	@Test
	void testMapToHelmInterfaceInfoEmpty() {
		assertNull(mapper.mapToHelmInterfaceInfo(null));
		InterfaceInfo res = mapper.mapToHelmInterfaceInfo(Map.of());
		assertNotNull(res);
	}

	@Test
	void testMapToHelmInterfaceInfo() {
		assertNull(mapper.mapToHelmInterfaceInfo(null));
		InterfaceInfo res = mapper.mapToHelmInterfaceInfo(Map.of(
				"connectionTimeout", "456",
				"endpoint", "endp",
				"id", UUID.randomUUID().toString(),
				"iface", "true",
				"natHost", "",
				"nonStrictSsl", "true",
				"readTimeout", "789",
				"regionName", "region",
				"retry", "4",
				"sdnEndpoint", "sdnEndpoint"));
		assertNotNull(res);
	}

	@Test
	void testMapToK8sInterfaceInfo() {
		assertNull(mapper.mapToK8sInterfaceInfo(null));
		Map<String, String> map = new HashMap<>();
		map.put("certificate-authority-data", "");
		map.put("connectionTimeout", "741");
		map.put("endpoint", "");
		map.put("id", UUID.randomUUID().toString());
		map.put("iface", "");
		map.put("natHost", "");
		map.put("nonStrictSsl", "false");
		map.put("readTimeout", "12");
		map.put("regionName", "");
		map.put("retry", "4");
		map.put("sdnEndpoint", "");

		K8sInterfaceInfo res = mapper.mapToK8sInterfaceInfo(map);
		assertNotNull(res);
	}

	@Test
	void testMapToOpenstackV3InterfaceInfo() {
		assertNull(mapper.mapToOpenstackV3InterfaceInfo(null));
		Map<String, String> map = new HashMap<>();
		map.put("certificate-authority-data", "");
		map.put("connectionTimeout", "741");
		map.put("endpoint", "");
		map.put("id", UUID.randomUUID().toString());
		map.put("iface", "");
		map.put("natHost", "");
		map.put("nonStrictSsl", "false");
		map.put("readTimeout", "12");
		map.put("regionName", "");
		map.put("retry", "4");
		map.put("sdnEndpoint", "");
		map.put("skipCertificateHostnameCheck", "false");
		map.put("skipCertificateVerification", "false");
		map.put("trustedCertificates", "false");
		OpenstackV3InterfaceInfo res = mapper.mapToOpenstackV3InterfaceInfo(map);
		assertNotNull(res);
	}

	@Test
	void testMapToKeystoneAuthV3() {
		assertNull(mapper.mapToKeystoneAuthV3(null));
		Map<String, String> map = new HashMap<>();
		map.put("id", UUID.randomUUID().toString());
		map.put("password", "");
		map.put("username", "");
		map.put("project", "false");
		map.put("projectDomain", "12");
		map.put("projectId", "");
		map.put("region", "4");
		map.put("userDomain", "");
		KeystoneAuthV3 res = mapper.mapToKeystoneAuthV3(map);
		assertNotNull(res);
	}

	@Test
	void testMapToHelmEmpty() {
		assertNull(mapper.mapToHelm(null));
		BasicAccess res = mapper.mapToHelm(Map.of());
		assertNotNull(res);
	}

	@Test
	void testMapToHelm() {
		assertNull(mapper.mapToHelm(null));
		Map<String, String> map = new HashMap<>();
		map.put("id", UUID.randomUUID().toString());
		map.put("password", "");
		map.put("username", "");
		BasicAccess res = mapper.mapToHelm(map);
		assertNotNull(res);
	}

	@Test
	void testMapToK8sAuth() {
		assertNull(mapper.mapToK8sAuth(null));
		Map<String, String> map = new HashMap<>();
		map.put("id", UUID.randomUUID().toString());
		map.put("client-certificate-data", "");
		map.put("client-key-data", "");
		KubernetesV1Auth res = mapper.mapToK8sAuth(map);
		assertNotNull(res);
	}

	@Test
	void testMapMapToOAuth2Access() {
		assertNull(mapper.mapToOAuth2Access(null));
		AuthParamOauth2 pojo = podam.manufacturePojo(AuthParamOauth2.class);
		OAuth2Access res = mapper.mapToOAuth2Access(pojo);
		assertNotNull(res);
	}

	@Test
	void testMapMapToOAuth2AccessNoGrant() {
		assertNull(mapper.mapToOAuth2Access(null));
		AuthParamOauth2 pojo = podam.manufacturePojo(AuthParamOauth2.class);
		pojo.setGrantType(null);
		OAuth2Access res = mapper.mapToOAuth2Access(pojo);
		assertNotNull(res);
	}

	@Test
	void testMapToBasicAccess() {
		assertNull(mapper.mapToBasicAccess(null));
		AuthParamBasic pojo = podam.manufacturePojo(AuthParamBasic.class);
		Object res = mapper.mapToBasicAccess(pojo);
		assertNotNull(res);
	}

	@Test
	void testMapFromConnectionInformationToVimConnectionInformation() {
		assertNull(mapper.mapFromConnectionInformationToVimConnectionInformation(null));
		ConnectionInformation pojo = podam.manufacturePojo(ConnectionInformation.class);
		Object res = mapper.mapFromConnectionInformationToVimConnectionInformation(pojo);
		assertNotNull(res);
	}

	@Test
	void testMapFromConnectionInformationToVimConnectionInformationExtraNull() {
		assertNull(mapper.mapFromConnectionInformationToVimConnectionInformation(null));
		ConnectionInformation pojo = podam.manufacturePojo(ConnectionInformation.class);
		pojo.setExtra(null);
		Object res = mapper.mapFromConnectionInformationToVimConnectionInformation(pojo);
		assertNotNull(res);
	}
}
