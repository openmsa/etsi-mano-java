package com.ubiqube.etsi.mano.nfvo.service.event;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.exception.GenericException;

class NfvoActionComtrollerTest {

	@Test
	void test() {
		NfvoActionComtroller nfvoActionComtroller = new NfvoActionComtroller();
		assertThrows(GenericException.class, () -> nfvoActionComtroller.onEvent(null));
	}

}
