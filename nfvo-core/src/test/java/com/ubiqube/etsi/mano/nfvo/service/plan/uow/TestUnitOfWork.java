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
package com.ubiqube.etsi.mano.nfvo.service.plan.uow;

import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.uow.UnitOfWorkV3;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;

public class TestUnitOfWork implements UnitOfWorkV3<TestTask> {

	private VirtualTaskV3<TestTask> task;

	public TestUnitOfWork() {
		//
	}

	public TestUnitOfWork(final VirtualTaskV3<TestTask> task, final String res) {
		this.task = task;
	}

	@Override
	public VirtualTaskV3<TestTask> getVirtualTask() {
		return task;
	}

	public void setTask(final VirtualTaskV3<TestTask> task) {
		this.task = task;
	}

	@Override
	public String execute(final Context3d context) {
		return null;
	}

	@Override
	public String rollback(final Context3d context) {
		return null;
	}

	@Override
	public Class<? extends Node> getType() {
		return null;
	}

	@Override
	public void setResource(final String res) {
		//
	}

}
