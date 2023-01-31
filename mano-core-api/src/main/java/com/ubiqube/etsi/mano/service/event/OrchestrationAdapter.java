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
package com.ubiqube.etsi.mano.service.event;

import java.util.UUID;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;

import com.ubiqube.etsi.mano.dao.mano.Instance;
import com.ubiqube.etsi.mano.dao.mano.PackageBase;
import com.ubiqube.etsi.mano.dao.mano.VimTask;
import com.ubiqube.etsi.mano.dao.mano.v2.Blueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.Task;
import com.ubiqube.etsi.mano.service.graph.WorkflowEvent;

public interface OrchestrationAdapter<B extends VimTask, V extends Instance> {

	void createLiveInstance(@NotNull Instance instance, String il, Task task, @NotNull Blueprint<? extends Task, ? extends Instance> blueprint);

	void deleteLiveInstance(@Nonnull UUID removedLiveInstanceId);

	@Nonnull
	Blueprint<B, V> getBluePrint(@Nonnull UUID blueprintId);

	@Nonnull
	Instance getInstance(@Nonnull UUID blueprintId);

	PackageBase getPackage(@Nonnull Instance instance);

	Instance getInstance(@Nonnull Blueprint<B, V> blueprint);

	@NotNull
	Blueprint<B, V> save(@Nonnull Blueprint blueprint);

	Instance save(@Nonnull Instance instance);

	Blueprint<B, V> updateState(@Nonnull Blueprint<B, V> localPlan, OperationStatusType processing);

	void fireEvent(@Nonnull WorkflowEvent instantiateProcessing, @Nonnull UUID id);

}
