/**
 *     Copyright (C) 2019-2023 Ubiqube.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.vnfm.service.system;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.v2.DnsHostTask;
import com.ubiqube.etsi.mano.orchestrator.OrchestrationServiceV3;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.DnsHostVt;

@ExtendWith(MockitoExtension.class)
class DnsHostSystemTest {
	@Mock
	private VimManager vimManager;
	@Mock
	private OrchestrationServiceV3<DnsHostTask> orch;

	@Test
	void test() {
		final DnsHostSystem srv = new DnsHostSystem(vimManager);
		final DnsHostTask nt = new DnsHostTask();
		final VirtualTaskV3<DnsHostTask> vt = new DnsHostVt(nt);
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		srv.getImplementation(orch, vt, vimConn);
		srv.getType();
		srv.getVimType();
		assertTrue(true);
	}

}
