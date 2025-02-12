package com.ubiqube.etsi.mano.controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class UiControllerTest {

	@Test
	void test() {
		UiController ctrl = new UiController();
		String res = ctrl.get();
		assertNotNull(res);
	}

}
