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
