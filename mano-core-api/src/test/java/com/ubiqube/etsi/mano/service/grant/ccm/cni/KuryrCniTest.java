package com.ubiqube.etsi.mano.service.grant.ccm.cni;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class KuryrCniTest {

	@Test
	void test() {
		KuryrCni srv = new KuryrCni();
		assertEquals("kuryr", srv.getType());
	}

}
