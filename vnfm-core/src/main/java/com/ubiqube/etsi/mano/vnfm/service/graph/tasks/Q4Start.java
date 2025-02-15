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

import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.orchestrator.v4.Q4Context;
import com.ubiqube.etsi.mano.orchestrator.v4.Q4Task;
import com.ubiqube.etsi.mano.service.vim.Vim;

public class Q4Start implements Q4Task {
	private final String resourceId;
	private final Vim vim;
	private final VimConnectionInformation vimConnectionInformation;

	public Q4Start(final String resourceId, final Vim vim, final VimConnectionInformation vimConnectionInformation) {
		this.resourceId = resourceId;
		this.vim = vim;
		this.vimConnectionInformation = vimConnectionInformation;
	}

	@Override
	public String execute(final Q4Context context3d) {
		vim.startServer(vimConnectionInformation, resourceId);
		return null;
	}

	@Override
	public String rollback(final Q4Context context3d) {
		return null;
	}

}
