package com.ubiqube.etsi.mano.service.grant.ccm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class NoopCcmServerServiceTest {

	@Test
	void test() {
		NoopCcmServerService srv = new NoopCcmServerService();
		srv.createCluster(null, null);
		srv.terminateCluster(null);
		assertTrue(true);
	}

}
