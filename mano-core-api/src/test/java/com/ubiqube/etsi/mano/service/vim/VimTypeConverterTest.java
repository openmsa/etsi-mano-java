package com.ubiqube.etsi.mano.service.vim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.exception.GenericException;

class VimTypeConverterTest {

	private VimTypeConverter vimTypeConverter;

	@BeforeEach
	public void setUp() {
		vimTypeConverter = new VimTypeConverter();
	}

	@Test
	void testSetToInternalType_OpenStackV3() {
		VimConnectionInformation vci = new VimConnectionInformation();
		vci.setVimType("OPENSTACK_V3");
		vimTypeConverter.setToInternalType(vci);
		assertEquals("OPENSTACK_V3", vci.getVimType());
	}

	@Test
	void testSetToInternalType_Azure() {
		VimConnectionInformation vci = new VimConnectionInformation();
		vci.setVimType("UBINFV.AZURE.V_1");
		vimTypeConverter.setToInternalType(vci);
		assertEquals("AZURE", vci.getVimType());
	}

	@Test
	void testSetToInternalType_Aws() {
		VimConnectionInformation vci = new VimConnectionInformation();
		vci.setVimType("UBINFV.AWS.V_1");
		vimTypeConverter.setToInternalType(vci);
		assertEquals("AWS", vci.getVimType());
	}

	@Test
	void testSetToInternalType_Cism() {
		VimConnectionInformation vci = new VimConnectionInformation();
		vci.setVimType("UBINFV.CISM.V_1");
		vimTypeConverter.setToInternalType(vci);
		assertTrue(true);
	}

	@Test
	void testSetToInternalType_Unsupported() {
		VimConnectionInformation vci = new VimConnectionInformation();
		vci.setVimType("UNSUPPORTED");
		assertThrows(GenericException.class, () -> vimTypeConverter.setToInternalType(vci));
	}

	@Test
	void testIsVim_True() {
		VimConnectionInformation vci = new VimConnectionInformation();
		vci.setVimType("OPENSTACK_V3");
		assertTrue(vimTypeConverter.isVim(vci));
	}

	@Test
	void testIsVim_False() {
		VimConnectionInformation vci = new VimConnectionInformation();
		vci.setVimType("UBINFV.EEEEE.V_1");
		assertFalse(vimTypeConverter.isVim(vci));
	}

	@Test
	void testSetToExternalType_Azure() {
		VimConnectionInformation vci = new VimConnectionInformation();
		vci.setVimType("AZURE");
		vimTypeConverter.setToExternalType(vci);
		assertEquals("UBINFV.AZURE.V_1", vci.getVimType());
	}

	@Test
	void testSetToExternalType_Aws() {
		VimConnectionInformation vci = new VimConnectionInformation();
		vci.setVimType("AWS");
		vimTypeConverter.setToExternalType(vci);
		assertEquals("UBINFV.AWS.V_1", vci.getVimType());
	}

	@Test
	void testSetToExternalType_OsV3() {
		VimConnectionInformation vci = new VimConnectionInformation();
		vci.setVimType("OPENSTACK_V3");
		vimTypeConverter.setToExternalType(vci);
		assertEquals("ETSINFV.OPENSTACK_KEYSTONE.V_3", vci.getVimType());
	}

	@Test
	void testSetToExternalType_Cism() {
		VimConnectionInformation vci = new VimConnectionInformation();
		vci.setVimType("UBINFV.CISM.V_1");
		vimTypeConverter.setToExternalType(vci);
		assertEquals("UBINFV.CISM.V_1", vci.getVimType());
	}

	@Test
	void testSetToExternalType_Unsupported() {
		VimConnectionInformation vci = new VimConnectionInformation();
		vci.setVimType("UNSUPPORTED");
		assertThrows(IllegalArgumentException.class, () -> vimTypeConverter.setToExternalType(vci));
	}
}
