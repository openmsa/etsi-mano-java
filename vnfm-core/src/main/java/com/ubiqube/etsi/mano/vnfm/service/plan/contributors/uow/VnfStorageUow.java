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

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.dao.mano.v2.StorageTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Storage;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.service.vim.VimVolume;

public class VnfStorageUow extends AbstractVnfmUow<StorageTask> {
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(VnfStorageUow.class);

	private final Vim vim;
	private final VimConnectionInformation vimConnectionInformation;
	private final StorageTask task;

	public VnfStorageUow(final VirtualTaskV3<StorageTask> task, final Vim vim, final VimConnectionInformation vimConnectionInformation) {
		super(task, Storage.class);
		this.vim = vim;
		this.vimConnectionInformation = vimConnectionInformation;
		this.task = task.getTemplateParameters();
	}

	@Override
	public @Nullable String execute(final Context3d context) {
		return vim.storage(vimConnectionInformation).createStorage(task.getVnfStorage(), task.getAlias());
	}

	@Override
	public @Nullable String rollback(final Context3d context) {
		final StorageTask params = getVirtualTask().getTemplateParameters();
		waitForStatus(params.getVimResourceId());
		vim.storage(vimConnectionInformation).deleteStorage(params.getVimResourceId());
		return null;
	}

	private void waitForStatus(final String vimResourceId) {
		VimVolume st = vim.storage(vimConnectionInformation).getStorage(vimResourceId);
		if (null == st) {
			LOG.info("Storage {} not found", vimResourceId);
			return;
		}
		LOG.info("Waiting for storage {} to be deleted: {}", vimResourceId, st.getStatus());
	}

}
