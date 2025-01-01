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
package com.ubiqube.etsi.mano.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.core.ParameterizedTypeReference;

import com.ubiqube.etsi.mano.controller.subscription.ApiAndType;
import com.ubiqube.etsi.mano.dao.mano.CancelModeTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.ScaleTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VnfIndicator;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.ChangeVnfFlavourData;
import com.ubiqube.etsi.mano.dao.mano.pm.PmJob;
import com.ubiqube.etsi.mano.dao.mano.pm.Threshold;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.vnfi.ChangeExtVnfConnRequest;
import com.ubiqube.etsi.mano.model.VnfHealRequest;
import com.ubiqube.etsi.mano.model.VnfInstantiate;
import com.ubiqube.etsi.mano.model.VnfOperateRequest;
import com.ubiqube.etsi.mano.model.VnfScaleToLevelRequest;
import com.ubiqube.etsi.mano.service.event.model.EventMessage;
import com.ubiqube.etsi.mano.service.event.model.Subscription;
import com.ubiqube.etsi.mano.utils.Version;

/**
 *
 * @author olivier
 *
 */
public class HttpGatewayGood extends AbstractHttpGateway {

	@Override
	public Class<?> getVnfPackageClass() {
		//
		return null;
	}

	@Override
	public Object createVnfPackageRequest(final Map<String, String> userDefinedData) {
		//
		return null;
	}

	@Override
	public ParameterizedTypeReference<List<Class<?>>> getVnfPackageClassList() {
		//
		return null;
	}

	@Override
	public Class<?> getVnfPackageSubscriptionClass() {
		//
		return null;
	}

	@Override
	public Object getPkgmSubscriptionRequest(final Subscription req) {
		//
		return null;
	}

	@Override
	public Class<?> getVnfIndicatorValueChangeSubscriptionClass() {
		//
		return null;
	}

	@Override
	public Object getVnfIndicatorValueChangeSubscriptionRequest(final Subscription req) {
		//
		return null;
	}

	@Override
	public Object mapGrantRequest(final GrantResponse o) {
		//
		return null;
	}

	@Override
	public Class<?> getGrantResponse() {
		//
		return null;
	}

	@Override
	public Object createGrantRequest(final GrantResponse grant) {
		//
		return null;
	}

	@Override
	public void makeGrantLinks(final Object manoGrant) {
		//

	}

	@Override
	public String getSubscriptionUriFor(final ApiAndType at, final String id) {
		//
		return null;
	}

	@Override
	public Class<?> getVnfInstanceClass() {
		//
		return null;
	}

	@Override
	public Class<?> getVnfThresholdClass() {
		//
		return null;
	}

	@Override
	public ParameterizedTypeReference<List<Class<?>>> getVnfInstanceListParam() {
		//
		return null;
	}

	@Override
	public ParameterizedTypeReference<List<Class<?>>> getListVnfLcmOpOccs() {
		//
		return null;
	}

	@Override
	public ParameterizedTypeReference<List<Class<?>>> getNsdPackageClassList() {
		//
		return null;
	}

	@Override
	public Object createVnfInstanceRequest(final String vnfdId, final String vnfInstanceName, final String vnfInstanceDescription) {
		//
		return null;
	}

	@Override
	public Object getVnfInstanceInstantiateRequest(final VnfInstantiate req) {
		//
		return null;
	}

	@Override
	public Class<?> getVnfLcmOpOccsClass() {
		//
		return null;
	}

	@Override
	public Object createVnfInstanceTerminate(final CancelModeTypeEnum terminationType, final Integer gracefulTerminationTimeout) {
		//
		return null;
	}

	@Override
	public Object getVnfInstanceScaleToLevelRequest(final VnfScaleToLevelRequest req) {
		//
		return null;
	}

	@Override
	public Object createVnfInstanceScaleRequest(final ScaleTypeEnum scaleTypeEnum, final String aspectId, final Integer numberOfSteps) {
		//
		return null;
	}

	@Override
	public Object createVnfInstanceHealRequest(final VnfHealRequest req) {
		//
		return null;
	}

	@Override
	public Object getVnfInstanceOperateRequest(final VnfOperateRequest req) {
		//
		return null;
	}

	@Override
	public Object getVnfInstanceChangeFalvourRequest(final ChangeVnfFlavourData req) {
		//
		return null;
	}

	@Override
	public Object getVnfInstanceChangeExtConnRequest(final ChangeExtVnfConnRequest req) {
		//
		return null;
	}

	@Override
	public Object createEvent(final UUID uuid, final EventMessage event) {
		//
		return null;
	}

	@Override
	public Version getVersion() {
		//
		return Version.of("2.3.4");
	}

	@Override
	public Class<?> getNsdPackageClass() {
		//
		return null;
	}

	@Override
	public Object createNsdPackageRequest(final Map<String, Object> userDefinedData) {
		//
		return null;
	}

	@Override
	public Class<?> getVnfPmJobClass() {
		//
		return null;
	}

	@Override
	public Object createVnfPmJobRequest(final PmJob pmJob) {
		//
		return null;
	}

	@Override
	public Object createVnfThresholdRequest(final Threshold req) {
		//
		return null;
	}

	@Override
	public Class<?> getVnfIndicatorClass() {
		//
		return null;
	}

	@Override
	public ParameterizedTypeReference<List<Class<?>>> getVnfIndicatorClassList() {
		//
		return null;
	}

	@Override
	public Class<?> getVnfIndicatorSubscriptionClass() {
		//
		return null;
	}

	@Override
	public Object createVnfInstanceSubscriptionRequest(final Subscription subscription) {
		//
		return null;
	}

	@Override
	public Object createVnfIndicatorSubscriptionRequest(final Subscription subscription) {
		//
		return null;
	}

	@Override
	public Object createVnfFmSubscriptionRequest(final Subscription subscription) {
		//
		return null;
	}

	@Override
	public Class<?> getVnfFmSubscriptionClass() {
		//
		return null;
	}

	@Override
	public Object mapVrQanSubscriptionRequest(final Subscription o) {
		//
		return null;
	}

	@Override
	public Class<?> getVrQanSubscriptionClass() {
		//
		return null;
	}

	@Override
	public Subscription mapVnfFmSubscription(final Object o) {
		//
		return null;
	}

	@Override
	public Subscription mapVrQanSubscriptionSubscription(final Object o) {
		//
		return null;
	}

	@Override
	public Subscription mapToPkgmSubscription(final Object o) {
		//
		return null;
	}

	@Override
	public Subscription mapToVnfIndicatorSubscription(final Object o) {
		//
		return null;
	}

	@Override
	public GrantResponse mapToGrantResponse(final Object o) {
		//
		return null;
	}

	@Override
	public NsdPackage mapToNsdPackage(final Object o) {
		//
		return null;
	}

	@Override
	public VnfIndicator mapToVnfIndicator(final Object o) {
		//
		return null;
	}

	@Override
	public VnfInstance mapToVnfInstance(final Object o) {
		//
		return null;
	}

	@Override
	public VnfBlueprint mapToVnfBlueprint(final Object o) {
		//
		return null;
	}

	@Override
	public VnfPackage mapToVnfPackage(final Object o) {
		//
		return null;
	}

	@Override
	public Threshold mapToThreshold(final Object o) {
		//
		return null;
	}

	@Override
	public PmJob mapToPmJob(final Object o) {
		//
		return null;
	}

}
