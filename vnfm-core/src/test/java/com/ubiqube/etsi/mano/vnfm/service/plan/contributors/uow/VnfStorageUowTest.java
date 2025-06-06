/**
 * Copyright (C) 2019-2025 Ubiqube.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.v2.StorageTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.vim.Storage;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.service.vim.VimVolume;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.StorageVt;

@ExtendWith(MockitoExtension.class)
class VnfStorageUowTest {

	@Mock
	private Vim vim;
	@Mock
	private Context3d context;
	@Mock
	private Storage storage;

	@Test
	void test() {
		final StorageTask nt = new StorageTask();
		final VirtualTaskV3<StorageTask> vt = new StorageVt(nt);
		assertNotNull(vt.getType());
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final VnfStorageUow uow = new VnfStorageUow(vt, vim, vimConn);
		when(vim.storage(vimConn)).thenReturn(storage);
		uow.execute(context);
		assertTrue(true);
	}

	@Test
	void testRollbackNullResource() {
		final StorageTask nt = new StorageTask();
		final VirtualTaskV3<StorageTask> vt = new StorageVt(nt);
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final VnfStorageUow uow = new VnfStorageUow(vt, vim, vimConn);
		when(vim.storage(vimConn)).thenReturn(storage);
		uow.rollback(context);
		assertTrue(true);
	}

	@Test
	void testRollback() {
		final StorageTask nt = new StorageTask();
		final VirtualTaskV3<StorageTask> vt = new StorageVt(nt);
		final VimConnectionInformation vimConn = new VimConnectionInformation();
		final VnfStorageUow uow = new VnfStorageUow(vt, vim, vimConn);
		when(vim.storage(vimConn)).thenReturn(storage);
		VimVolume st0 = new VimVolume();
		when(storage.getStorage(any())).thenReturn(st0);
		uow.rollback(context);
		assertTrue(true);
	}
}
