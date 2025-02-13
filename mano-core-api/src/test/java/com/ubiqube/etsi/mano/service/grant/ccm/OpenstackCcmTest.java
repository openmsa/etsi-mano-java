package com.ubiqube.etsi.mano.service.grant.ccm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class OpenstackCcmTest {

	@Test
	void test() {
		OpenstackCcm srv = new OpenstackCcm();
		assertEquals("openstack", srv.getType());
	}

}
