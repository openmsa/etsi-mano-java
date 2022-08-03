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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Priority;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfStorage;
import com.ubiqube.etsi.mano.dao.mano.v2.ComputeTask;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanOperationType;
import com.ubiqube.etsi.mano.dao.mano.v2.StorageTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.orchestrator.Bundle;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Storage;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;
import com.ubiqube.etsi.mano.vnfm.service.graph.VnfBundleAdapter;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2.vt.StorageVt;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Priority(200)
@Service
public class VnfV2StorageContributor extends AbstractContributorV2Base<StorageTask, StorageVt> {
	private final VnfLiveInstanceJpa vnfLiveInstanceJpa;

	public VnfV2StorageContributor(final VnfLiveInstanceJpa vnfLiveInstanceJpa) {
		this.vnfLiveInstanceJpa = vnfLiveInstanceJpa;
	}

	@Override
	public List<StorageVt> vnfContribute(final Bundle bundle, final VnfBlueprint plan) {
		if (plan.getOperation() == PlanOperationType.TERMINATE) {
			return doTerminatePlan(plan.getVnfInstance());
		}
		final VnfPackage vnfPackage = ((VnfBundleAdapter) bundle).vnfPackage();
		final List<StorageVt> ret = new ArrayList<>();
		plan.getTasks().stream()
				.filter(ComputeTask.class::isInstance)
				.map(ComputeTask.class::cast)
				.forEach(x -> {
					if (x.getChangeType() == ChangeType.REMOVED) {
						removeStorage(ret, x, plan.getInstance());
					} else {
						createStorage(ret, x, vnfPackage, plan.getInstance());
					}
				});
		return ret;
	}

	private void createStorage(final List<StorageVt> ret, final ComputeTask x, final VnfPackage vnfPackage, final VnfInstance vnfInstance) {
		x.getVnfCompute().getStorages().forEach(y -> {
			final int cnt = countVli(vnfInstance, x.getAlias());
			if (cnt != 0) {
				return;
			}
			final StorageTask task = createTask(StorageTask::new);
			task.setType(ResourceTypeEnum.STORAGE);
			task.setToscaName(y + "-" + x.getAlias());
			task.setAlias(y + "-" + x.getAlias() + "-" + RandomStringUtils.random(5, true, true));
			task.setParentAlias(x.getAlias());
			task.setVnfStorage(findVnfStorage(vnfPackage.getVnfStorage(), y));
			ret.add(new StorageVt(task));
		});
	}

	private void removeStorage(final List<StorageVt> ret, final ComputeTask computeTask, final VnfInstance vnfInstance) {
		final List<VnfLiveInstance> vs = vnfLiveInstanceJpa.findByVnfInstanceIdAndClass(vnfInstance, StorageTask.class.getSimpleName());
		computeTask.getVnfCompute().getStorages().forEach(x -> findStorageByName(x + "-" + computeTask.getAlias(), vs).ifPresent(y -> {
			final StorageTask task = createDeleteTask(StorageTask::new, y);
			task.setType(ResourceTypeEnum.STORAGE);
			task.setRemovedLiveInstance(y.getId());
			task.setVnfStorage(((StorageTask) y.getTask()).getVnfStorage());
			ret.add(new StorageVt(task));
		}));
	}

	// XXX move this in JPA this is a SQL query.
	private static Optional<VnfLiveInstance> findStorageByName(final String y, final List<VnfLiveInstance> vs) {
		for (final VnfLiveInstance vnfLiveInstance : vs) {
			final StorageTask t = (StorageTask) vnfLiveInstance.getTask();
			if (t.getToscaName().equals(y)) {
				return Optional.of(vnfLiveInstance);
			}
		}
		return Optional.empty();
	}

	// XXX move this in JPA this is a SQL query.
	private int countVli(final VnfInstance vnfInstance, final String computeAlias) {
		final List<VnfLiveInstance> vs = vnfLiveInstanceJpa.findByVnfInstanceIdAndClass(vnfInstance, StorageTask.class.getSimpleName());
		int i = 0;
		for (final VnfLiveInstance vnfLiveInstance : vs) {
			if (vnfLiveInstance.getTask() instanceof final StorageTask t && t.getParentAlias().equals(computeAlias)) {
				i++;
			}
		}
		return i;
	}

	private List<StorageVt> doTerminatePlan(final VnfInstance vnfInstance) {
		final List<VnfLiveInstance> instances = vnfLiveInstanceJpa.findByVnfInstanceIdAndClass(vnfInstance, StorageTask.class.getSimpleName());
		return instances.stream().map(x -> {
			final StorageTask task = createDeleteTask(StorageTask::new, x);
			task.setType(ResourceTypeEnum.STORAGE);
			task.setRemovedLiveInstance(x.getId());
			task.setVnfStorage(((StorageTask) x.getTask()).getVnfStorage());
			return new StorageVt(task);
		}).toList();
	}

	private static VnfStorage findVnfStorage(final Set<VnfStorage> vnfStorage, final String toscaName) {
		return vnfStorage.stream()
				.filter(x -> x.getToscaName().equals(toscaName))
				.findAny()
				.orElseThrow(() -> new GenericException("Could not find storage [" + toscaName + "] in: " + vnfStorage));
	}

	@Override
	public Class<? extends Node> getNode() {
		return Storage.class;
	}

}
