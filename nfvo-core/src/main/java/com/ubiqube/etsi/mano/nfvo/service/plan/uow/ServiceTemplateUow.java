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

import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.entities.SystemConnections;
import com.ubiqube.etsi.mano.orchestrator.nodes.contrail.ServiceTemplateNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.graph.AbstractUnitOfWork;
import com.ubiqube.etsi.mano.tf.ContrailApi;
import com.ubiqube.etsi.mano.tf.entities.ServiceTemplateTask;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public class ServiceTemplateUow extends AbstractUnitOfWork<ServiceTemplateTask> {
	@Nonnull
	private final SystemConnections vimConnectionInformation;
	@Nonnull
	private final ServiceTemplateTask task;

	public ServiceTemplateUow(final VirtualTaskV3<ServiceTemplateTask> task, final SystemConnections vimConnectionInformation) {
		super(task, ServiceTemplateNode.class);
		this.vimConnectionInformation = vimConnectionInformation;
		this.task = getTask().getTemplateParameters();
	}

	@Override
	public String execute(final Context3d context) {
		final ContrailApi api = new ContrailApi();
		final String name = UowNameHelper.buildName(task.getToscaName(), task.getInstanceId());
		return api.createServiceTemplate(vimConnectionInformation, name);
	}

	@Override
	public @Nullable String rollback(final Context3d context) {
		final ContrailApi api = new ContrailApi();
		api.deleteServiceTemplate(vimConnectionInformation, task.getVimResourceId());
		return null;
	}

}
