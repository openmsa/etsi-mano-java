package com.ubiqube.etsi.mano.vnfm.service.graph;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ResourceHolderTest {

	@Test
	void test() {
		ResourceHolder rh = new ResourceHolder<>(null, null, null);
		assertNotNull(rh);
	}

}
