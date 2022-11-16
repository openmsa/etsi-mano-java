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
package com.ubiqube.etsi.mano.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.core.ParameterizedTypeReference;

import com.ubiqube.etsi.mano.dao.mano.CancelModeTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.GrantInterface;
import com.ubiqube.etsi.mano.dao.mano.ScaleTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.pm.PmJob;
import com.ubiqube.etsi.mano.dao.mano.pm.Threshold;
import com.ubiqube.etsi.mano.model.EventMessage;
import com.ubiqube.etsi.mano.utils.Version;

/**
 *
 * @author olivier
 *
 */
public class HttpGatewayGood extends AbstractHttpGateway {

	@Override
	public Class<?> getVnfPackageClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createVnfPackageRequest(final Map<String, String> userDefinedData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParameterizedTypeReference<List<Class<?>>> getVnfPackageClassList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getVnfPackageSubscriptionClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getPkgmSubscriptionRequest() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Class<?> getVnfIndicatorValueChangeSubscriptionClass() {
		return null;
	}
	
	@Override
	public Class<?> getVnfIndicatorValueChangeSubscriptionRequest() {
		return null;
	}

	@Override
	public Class<?> getGrantRequest() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getGrantResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createGrantRequest(final GrantInterface grant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void makeGrantLinks(final Object manoGrant) {
		// TODO Auto-generated method stub

	}

	@Override
	public Class<?> getVnfInstanceClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParameterizedTypeReference<List<Class<?>>> getVnfInstanceListParam() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParameterizedTypeReference<List<Class<?>>> getListVnfLcmOpOccs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParameterizedTypeReference<List<Class<?>>> getNsdPackageClassList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createVnfInstanceRequest(final String vnfdId, final String vnfInstanceName, final String vnfInstanceDescription) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getVnfInstanceInstantiateRequestClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getVnfLcmOpOccs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createVnfInstanceTerminate(final CancelModeTypeEnum terminationType, final Integer gracefulTerminationTimeout) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getVnfInstanceScaleToLevelRequest() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getVnfInstanceScaleRequest() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getVnfInstanceOperateRequest() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getVnfInstanceChangeExtConnRequest() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createEvent(final UUID uuid, final EventMessage event) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Version getVersion() {
		return new Version("2.3.4");
	}

	@Override
	public Class<?> getNsdPackageClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createNsdPackageRequest(final Map<String, Object> userDefinedData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getVnfPmJobClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createVnfPmJobRequest(final PmJob pmJob) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createVnfThresholdRequest(final Threshold req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createVnfInstanceScaleRequest(ScaleTypeEnum scaleTypeEnum, String aspectId, Integer numberOfSteps) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createVnfInstanceHealRequest(String cause) {
		// TODO Auto-generated method stub
		return null;
	}

}
