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
package com.ubiqube.etsi.mano.nfvo.service.plan.uow;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfTask;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.nfvo.service.plan.contributors.vt.NsVnfCreateVt;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.VnfmInterface;

@ExtendWith(MockitoExtension.class)
class VnfCreateUowTest {
	@Mock
	private VnfmInterface vnfm;

	@Test
	void testExecute() {
		final NsVnfTask task = new NsVnfTask();
		final VirtualTaskV3<NsVnfTask> vt = new NsVnfCreateVt(task);
		final VnfInstance inst = new VnfInstance();
		inst.setId(UUID.randomUUID());
		when(vnfm.createVnfInstance(any(), any(), any(), any())).thenReturn(inst);
		final VnfCreateUow vc = new VnfCreateUow(vt, vnfm);
		vc.execute(null);
		assertTrue(true);
	}

	@Test
	void testRollback() {
		final NsVnfTask task = new NsVnfTask();
		final VirtualTaskV3<NsVnfTask> vt = new NsVnfCreateVt(task);
		final VnfCreateUow vc = new VnfCreateUow(vt, vnfm);
		vc.rollback(null);
		assertTrue(true);
	}

	@Test
	void testRollback001() {
		final NsVnfTask task = new NsVnfTask();
		task.setVimResourceId("");
		final VirtualTaskV3<NsVnfTask> vt = new NsVnfCreateVt(task);
		final VnfCreateUow vc = new VnfCreateUow(vt, vnfm);
		vc.rollback(null);
		assertTrue(true);
	}

	@Test
	void testRollback002() {
		final NsVnfTask task = new NsVnfTask();
		task.setVimResourceId("");
		final VirtualTaskV3<NsVnfTask> vt = new NsVnfCreateVt(task);
		doThrow(GenericException.class).when(vnfm).delete(any(), any());
		final VnfCreateUow vc = new VnfCreateUow(vt, vnfm);
		vc.rollback(null);
		assertTrue(true);
	}

}
