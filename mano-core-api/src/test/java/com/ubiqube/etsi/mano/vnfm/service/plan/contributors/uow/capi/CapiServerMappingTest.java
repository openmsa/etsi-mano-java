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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow.capi;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.ubiqube.etsi.mano.dao.mano.cnf.capi.CapiServer;
import com.ubiqube.etsi.mano.vim.k8s.conn.CertificateAuthInfo;
import com.ubiqube.etsi.mano.vim.k8s.conn.K8s;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

class CapiServerMappingTest {
	private final CapiServerMapping mapper = Mappers.getMapper(CapiServerMapping.class);
	private final PodamFactory podamFactory = new PodamFactoryImpl();

	@Test
	void test() {
		assertNull(mapper.map((CapiServer) null));
		CapiServer o = podamFactory.manufacturePojo(CapiServer.class);
		K8s r = mapper.map(o);
		assertNotNull(r);
	}

	@Test
	void testK8s() {
		assertNull(mapper.map((K8s) null));
		K8s o = podamFactory.manufacturePojo(K8s.class);
		CertificateAuthInfo cai = new CertificateAuthInfo("cert", "key");
		o.setCertificateAuthInfo(cai);
		CapiServer r = mapper.map(o);
		assertNotNull(r);
	}

	@Test
	void testK8sNull() {
		assertNull(mapper.map((K8s) null));
		K8s o = podamFactory.manufacturePojo(K8s.class);
		o.setCertificateAuthInfo(null);
		o.setOpenIdAuthInfo(null);
		o.setTokenAuthInfo(null);
		CapiServer r = mapper.map(o);
		assertNotNull(r);
	}

	@Test
	void testK8sNull002() {
		assertNull(mapper.map((K8s) null));
		K8s o = podamFactory.manufacturePojo(K8s.class);
		o.getCertificateAuthInfo().setClientCertificate(null);
		o.setOpenIdAuthInfo(null);
		o.getTokenAuthInfo().setToken(null);
		CapiServer r = mapper.map(o);
		assertNotNull(r);
	}
}
