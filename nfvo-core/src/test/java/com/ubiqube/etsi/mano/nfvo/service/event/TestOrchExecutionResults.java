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
package com.ubiqube.etsi.mano.nfvo.service.event;

import java.util.List;

import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsTask;
import com.ubiqube.etsi.mano.orchestrator.OrchExecutionResult;
import com.ubiqube.etsi.mano.orchestrator.OrchExecutionResults;

public class TestOrchExecutionResults implements OrchExecutionResults<NsTask> {

	@Override
	public List<OrchExecutionResult<NsTask>> getSuccess() {
		return List.of();
	}

	@Override
	public List<OrchExecutionResult<NsTask>> getErrored() {
		return List.of();
	}

	@Override
	public void addAll(final OrchExecutionResults<NsTask> convertResults) {
		// TODO Auto-generated method stub

	}

}
