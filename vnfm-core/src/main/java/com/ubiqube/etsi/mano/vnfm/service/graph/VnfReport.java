/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
package com.ubiqube.etsi.mano.vnfm.service.graph;

import java.util.List;

import com.github.dexecutor.core.task.ExecutionResult;
import com.github.dexecutor.core.task.ExecutionResults;
import com.ubiqube.etsi.mano.dao.mano.v2.Task;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfTask;
import com.ubiqube.etsi.mano.orchestrator.uow.UnitOfWork;
import com.ubiqube.etsi.mano.service.event.Report;
import com.ubiqube.etsi.mano.service.event.ReportItem;

public class VnfReport implements Report {

	private final ExecutionResults<UnitOfWork<VnfTask>, String> results;

	public VnfReport(final ExecutionResults<UnitOfWork<VnfTask>, String> results) {
		this.results = results;
	}

	public List<ExecutionResult<UnitOfWork<VnfTask>, String>> getSkipped() {
		return results.getSkipped();
	}

	@Override
	public List<ReportItem> getSuccess() {
		return results.getSuccess().stream().map(this::map).toList();
	}

	@Override
	public List<ReportItem> getErrored() {
		return results.getErrored().stream().map(this::map).toList();
	}

	public List<ExecutionResult<UnitOfWork<VnfTask>, String>> getAll() {
		return results.getAll();
	}

	private ReportItem map(final ExecutionResult<UnitOfWork<VnfTask>, String> er) {
		final Task part = er.getId().getTask().getParameters();
		return new ReportItem(part);
	}

}
