package com.ubiqube.etsi.mano.service.vim;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class VimTypeEnumTest {

	@Test
	void test() {
		VimTypeEnum vimTypeEnum = VimTypeEnum.ETSINFV_OPENSTACK_KEYSTONE_V_2;
		assertEquals("ETSINFV_OPENSTACK_KEYSTONE_V_2", vimTypeEnum.name());
	}

}
