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
package com.ubiqube.etsi.mano.vnfm.service.graph.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.orchestrator.v4.Q4Context;
import com.ubiqube.etsi.mano.orchestrator.v4.Q4Task;
import com.ubiqube.etsi.mano.service.vim.ComputeInfo;
import com.ubiqube.etsi.mano.service.vim.ComputeStatus;
import com.ubiqube.etsi.mano.service.vim.Vim;

public class Q4WaitStatus implements Q4Task {
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(Q4WaitStatus.class);

	private static final int TIMEOUT = 5 * 60 * 1000;
	private final String resourceId;
	private final Vim vim;
	private final VimConnectionInformation vimConnectionInformation;

	public Q4WaitStatus(final String resourceId, final Vim vim, final VimConnectionInformation vimConnectionInformation) {
		this.resourceId = resourceId;
		this.vim = vim;
		this.vimConnectionInformation = vimConnectionInformation;
	}

	@Override
	public String execute(final Q4Context context3d) {
		long startTime = System.currentTimeMillis();
		ComputeInfo comp = vim.getCompute(vimConnectionInformation, resourceId);
		LOG.info("Waiting for compute: {}", resourceId);
		while (!isFinished(comp)) {
			if ((System.currentTimeMillis() - startTime) > TIMEOUT) {
				throw new GenericException("Timeout while waiting for compute status: " + comp.getStatus() + "/" + comp.getTaskState());
			}
			LOG.info("Waiting for compute status: {}/{}", comp.getStatus(), comp.getTaskState());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new GenericException("Error while waiting for compute status", e);
			}
			comp = vim.getCompute(vimConnectionInformation, resourceId);
		}
		return null;
	}

	private boolean isFinished(final ComputeInfo comp) {
		if (null == comp) {
			// In this case, the compute is not found.
			return true;
		}
		String task = comp.getTaskState();
		if (task != null) {
			return false;
		}
		ComputeStatus status = comp.getStatus();
		return (status == ComputeStatus.COMPLETED) || (status == ComputeStatus.FAILED) || (status == ComputeStatus.STOPPED);
	}

	@Override
	public String rollback(final Q4Context context3d) {
		return null;
	}

}
