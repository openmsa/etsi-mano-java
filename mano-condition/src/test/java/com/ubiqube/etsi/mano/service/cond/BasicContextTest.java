package com.ubiqube.etsi.mano.service.cond;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.LinkedHashMap;

import org.junit.jupiter.api.Test;

class BasicContextTest {

	@Test
	void test() {
		BasicContext basicContext = new BasicContext(new LinkedHashMap<String, Object>());
		assertNotNull(basicContext);
		assertEquals(null, basicContext.lookup("test"));
	}

}
