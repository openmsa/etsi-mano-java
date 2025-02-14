package com.ubiqube.etsi.mano.nfvo.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class NfvoBeanTest {

	@Test
	void test() {
		NfvoBean nfvoBean = new NfvoBean();
		assertNotNull(nfvoBean.dockerService());
	}

}
