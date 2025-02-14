package com.ubiqube.etsi.mano.exception;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class VnfmExceptionTest {

	@Test
	void test() {
		VnfmException exception = new VnfmException("test");
		assertNotNull(exception);
	}

}
