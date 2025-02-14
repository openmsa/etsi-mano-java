package com.ubiqube.etsi.mano.controller.vnf;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class AdminVnfControllerTest {

	@Test
	void test() {
		AdminVnfController adminVnfController = new AdminVnfController();
		assertNotNull(adminVnfController);
		adminVnfController.deleteVnfById(null);
	}

}
