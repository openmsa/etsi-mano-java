package com.ubiqube.etsi.mano.vnfm.service.graph.tasks;

import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.orchestrator.v4.Q4Context;
import com.ubiqube.etsi.mano.orchestrator.v4.Q4Task;
import com.ubiqube.etsi.mano.service.vim.Vim;

public class Q4Reboot implements Q4Task {
	private final String resourceId;
	private final Vim vim;
	private final VimConnectionInformation vimConnectionInformation;

	public Q4Reboot(final String resourceId, final Vim vim, final VimConnectionInformation vimConnectionInformation) {
		this.resourceId = resourceId;
		this.vim = vim;
		this.vimConnectionInformation = vimConnectionInformation;
	}

	@Override
	public String execute(final Q4Context context3d) {
		vim.rebootServer(vimConnectionInformation, resourceId);
		return null;
	}

	@Override
	public String rollback(final Q4Context context3d) {
		return null;
	}

}
