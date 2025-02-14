package com.ubiqube.etsi.mano.vnfm.service.graph.tasks;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.service.vim.Vim;

@ExtendWith(MockitoExtension.class)
class Q4RebootTest {
	@Mock
	private Vim vim;

	@Test
	void test() {
		Q4Reboot srv = new Q4Reboot("test", vim, null);
		srv.execute(null);
		srv.rollback(null);
		assertTrue(true);
	}

}
