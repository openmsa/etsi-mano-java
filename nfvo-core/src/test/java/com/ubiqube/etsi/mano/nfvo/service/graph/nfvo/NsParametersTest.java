package com.ubiqube.etsi.mano.nfvo.service.graph.nfvo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.service.vim.Vim;

class NsParametersTest {

	private Vim vim;
	private VimConnectionInformation vimConnectionInformation;
	private Map<String, String> context;
	private String vimResourceId;
	private NsParameters nsParameters;

	@BeforeEach
	public void setUp() {
		vim = mock(Vim.class);
		vimConnectionInformation = new VimConnectionInformation();
		context = new HashMap<>();
		vimResourceId = "testResourceId";
		nsParameters = new NsParameters(vim, vimConnectionInformation, context, vimResourceId);
	}

	@Test
	void testNsParametersInitialization() {
		assertNotNull(nsParameters.getVim());
		assertNotNull(nsParameters.getVimConnectionInformation());
		assertEquals(context, nsParameters.getContext());
		assertEquals(vimResourceId, nsParameters.getVimResourceId());
	}

	@Test
	void testSetVim() {
		Vim newVim = mock(Vim.class);
		nsParameters.setVim(newVim);
		assertEquals(newVim, nsParameters.getVim());
	}

	@Test
	void testSetVimConnectionInformation() {
		VimConnectionInformation newVimConnectionInformation = new VimConnectionInformation();
		nsParameters.setVimConnectionInformation(newVimConnectionInformation);
		assertEquals(newVimConnectionInformation, nsParameters.getVimConnectionInformation());
	}
}
