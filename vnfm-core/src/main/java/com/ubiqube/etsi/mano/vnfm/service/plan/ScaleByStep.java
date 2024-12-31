/**
 *     Copyright (C) 2019-2024 Ubiqube.
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
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.vnfm.service.plan;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ScaleInfo;
import com.ubiqube.etsi.mano.dao.mano.ScaleTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfComputeAspectDelta;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.vnfm.service.plan.ScalingStrategy.NumberOfCompute;
import com.ubiqube.etsi.mano.vnfm.service.repository.VnfBlueprintRepositoryService;

@Service
public class ScaleByStep {

	private static final Logger LOG = LoggerFactory.getLogger(ScaleByStep.class);

	private final VnfBlueprintRepositoryService planService;

	public ScaleByStep(final VnfBlueprintRepositoryService planService) {
		this.planService = planService;
	}

	public NumberOfCompute handleStep(final BlueprintParameters parameters, final VnfPackage vnfPackage, final VnfCompute compute, final VnfInstance instance) {
		LOG.debug("Handling step with parameters: {}, vnfPackage: {}, compute: {}, instance: {}", parameters, vnfPackage, compute, instance);
		final int currentInst = planService.getNumberOfLiveInstance(instance, compute);
		LOG.debug("Current number of live instances: {}", currentInst);
		final int baseStep = getBaseStep(instance, parameters.getAspectId());
		LOG.debug("Base step for aspectId {}: {}", parameters.getAspectId(), baseStep);
		final List<VnfComputeAspectDelta> stepMapping = findStepMapping(parameters.getAspectId(), compute);
		if (stepMapping.isEmpty()) {
			LOG.warn("No step mapping found for aspectId: {}", parameters.getAspectId());
			final Set<String> uniqAspect = vnfPackage.getScaleStatus().stream().map(ScaleInfo::getAspectId).collect(Collectors.toSet());
			if (uniqAspect.isEmpty()) {
				LOG.debug("No unique aspect found, returning default NumberOfCompute");
				return new NumberOfCompute(currentInst, 1, new ScaleInfo(parameters.getAspectId(), 0));
			}
			if (uniqAspect.size() > 1) {
				LOG.warn("Multiple aspectIds found, taking the first one: {}", uniqAspect);
			}
			final String currentAspect = uniqAspect.iterator().next();
			final Optional<ScaleInfo> instanceLevel = instance.getInstantiatedVnfInfo().getScaleStatus().stream().filter(x -> x.getAspectId().equals(currentAspect)).findFirst();
			final List<VnfComputeAspectDelta> instStep = findStepMapping(currentAspect, compute);
			if (instStep.isEmpty()) {
				LOG.warn("Could not find step mapping for aspectId: {}", currentAspect);
				return new NumberOfCompute(currentInst, 1, new ScaleInfo(parameters.getAspectId(), 1));
			}
			if (instanceLevel.isEmpty()) {
				final int s = getStep(instStep, 0);
				LOG.debug("Instance level is empty, returning step: {}", s);
				return new NumberOfCompute(currentInst, s, new ScaleInfo(parameters.getAspectId(), s));
			}
			final int s = getStep(instStep, instanceLevel.get().getScaleLevel());
			LOG.debug("Returning step: {}", s);
			return new NumberOfCompute(currentInst, s, new ScaleInfo(parameters.getAspectId(), s));
		}
		final int newLevel = computeLevel(parameters, baseStep);
		LOG.debug("Computed new level: {}", newLevel);
		final int s = getStep(stepMapping, newLevel);
		LOG.debug("Returning step: {}", s);
		return new NumberOfCompute(currentInst, s, new ScaleInfo(parameters.getAspectId(), s));
	}

	@SuppressWarnings("boxing")
	private static int getBaseStep(final VnfInstance instance, final String aspectId) {
		final Set<ScaleInfo> nsScale = instance.getInstantiatedVnfInfo().getScaleStatus();
		if (null == nsScale) {
			return 0;
		}
		return nsScale.stream()
				.filter(x -> x.getAspectId().equals(aspectId))
				.map(ScaleInfo::getScaleLevel)
				.findFirst()
				.orElse(0);
	}

	private static List<VnfComputeAspectDelta> findStepMapping(final String aspectId, final VnfCompute vnfCompute) {
		return vnfCompute.getScalingAspectDeltas().stream()
				.filter(x -> x.getAspectName().equals(aspectId))
				.toList();
	}

	private static int getStep(final List<VnfComputeAspectDelta> vnfComputeAspectDelta, final int i) {
		int instNum = 1;
		final List<VnfComputeAspectDelta> sorted = vnfComputeAspectDelta.stream().sorted(Comparator.comparing(VnfComputeAspectDelta::getLevel)).toList();
		for (final VnfComputeAspectDelta stepMapping : sorted) {
			final int curr = stepMapping.getLevel();
			if (curr == i) {
				return stepMapping.getNumInst();
			}
			if (curr <= i) {
				instNum = stepMapping.getNumInst();
			}
			if (curr > i) {
				return instNum;
			}
		}
		return instNum;
	}

	@SuppressWarnings("boxing")
	private static int computeLevel(final BlueprintParameters param, final Integer base) {
		if (param.getScaleType() == ScaleTypeEnum.IN) {
			final int ret = base - param.getNumberOfSteps();
			if (ret < 0) {
				return 0;
			}
			return ret;
		}
		return base + param.getNumberOfSteps();
	}
}
