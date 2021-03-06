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
package com.ubiqube.etsi.mano.orchestrator.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.orchestrator.OrchestrationService;
import com.ubiqube.etsi.mano.orchestrator.SystemBuilder;
import com.ubiqube.etsi.mano.orchestrator.entities.SystemConnections;
import com.ubiqube.etsi.mano.orchestrator.exceptions.OrchestrationException;
import com.ubiqube.etsi.mano.orchestrator.uow.UnitOfWork;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTask;
import com.ubiqube.etsi.mano.service.sys.System;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class ImplementationService<U> {
	private final Map<String, System<U>> systems;
	private final SystemManager vimManager;
	private final OrchestrationService<U> orchestrationService;

	public ImplementationService(final List<System<U>> systems, final SystemManager vimManager, final OrchestrationService<U> orchestrationService) {
		super();
		this.systems = systems.stream().collect(Collectors.toMap(System::getProviderId, Function.identity()));
		this.vimManager = vimManager;
		this.orchestrationService = orchestrationService;
	}

	public SystemBuilder<UnitOfWork<U>> getTargetSystem(final VirtualTask<U> virtualTask) {
		final String connectionId = virtualTask.getVimConnectionId();
		if (null == connectionId) {
			throw new OrchestrationException("Unable to find VimId: " + virtualTask.getVimConnectionId() + ", for task: " + virtualTask.getName());
		}
		final SystemConnections vim = vimManager.findVimByVimIdAndProviderId(connectionId, virtualTask.getVimProviderId());
		final System<U> sys = systems.get(virtualTask.getFactoryProviderId());
		if (null == sys) {
			throw new OrchestrationException("Unable to find system matching: " + vim.getVimType() + "/" + connectionId + " / " + virtualTask.getFactoryProviderId());
		}
		return sys.getImplementation(orchestrationService, virtualTask, vim);
	}

}
