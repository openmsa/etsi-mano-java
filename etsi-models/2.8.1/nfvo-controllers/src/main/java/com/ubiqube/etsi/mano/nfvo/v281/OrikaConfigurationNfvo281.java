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
package com.ubiqube.etsi.mano.nfvo.v281;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ExtManagedVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.ExtVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.GrantInformationExt;
import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.dto.NsInstantiatedVnf;
import com.ubiqube.etsi.mano.dao.mano.dto.NsLcmOpOccs;
import com.ubiqube.etsi.mano.dao.mano.dto.VnfInstantiatedCompute;
import com.ubiqube.etsi.mano.dao.mano.dto.VnfInstantiatedExtCp;
import com.ubiqube.etsi.mano.dao.mano.dto.VnfInstantiatedVirtualLink;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.em.v281.model.SubscriptionAuthenticationParamsOauth2ClientCredentials;
import com.ubiqube.etsi.mano.em.v281.model.vnfind.VnfIndicatorSubscriptionRequest;
import com.ubiqube.etsi.mano.em.v281.model.vnflcm.ExtManagedVirtualLinkData;
import com.ubiqube.etsi.mano.em.v281.model.vnflcm.ExtManagedVirtualLinkInfo;
import com.ubiqube.etsi.mano.em.v281.model.vnflcm.ExtVirtualLinkInfo;
import com.ubiqube.etsi.mano.em.v281.model.vnflcm.InstantiateVnfRequest;
import com.ubiqube.etsi.mano.em.v281.model.vnflcm.LccnSubscription;
import com.ubiqube.etsi.mano.em.v281.model.vnflcm.LccnSubscriptionRequest;
import com.ubiqube.etsi.mano.em.v281.model.vnflcm.ScaleVnfRequest;
import com.ubiqube.etsi.mano.em.v281.model.vnflcm.ScaleVnfToLevelRequest;
import com.ubiqube.etsi.mano.em.v281.model.vnflcm.TerminateVnfRequest;
import com.ubiqube.etsi.mano.em.v281.model.vnflcm.VnfExtCpInfo;
import com.ubiqube.etsi.mano.em.v281.model.vnflcm.VnfInstance;
import com.ubiqube.etsi.mano.em.v281.model.vnflcm.VnfInstanceInstantiatedVnfInfo;
import com.ubiqube.etsi.mano.em.v281.model.vnflcm.VnfLcmOpOcc;
import com.ubiqube.etsi.mano.em.v281.model.vnflcm.VnfVirtualLinkResourceInfo;
import com.ubiqube.etsi.mano.em.v281.model.vnflcm.VnfcResourceInfo;
import com.ubiqube.etsi.mano.mapper.OffsetDateTimeToDateConverter;
import com.ubiqube.etsi.mano.mapper.OrikaFilterMapper;
import com.ubiqube.etsi.mano.mapper.UuidConverter;
import com.ubiqube.etsi.mano.nfvo.v281.model.nsd.NsdInfo;
import com.ubiqube.etsi.mano.nfvo.v281.model.nsd.NsdmSubscription;
import com.ubiqube.etsi.mano.nfvo.v281.model.nsd.NsdmSubscriptionRequest;
import com.ubiqube.etsi.mano.nfvo.v281.model.nslcm.AffectedVnf;
import com.ubiqube.etsi.mano.nfvo.v281.model.nslcm.InstantiateNsRequest;
import com.ubiqube.etsi.mano.nfvo.v281.model.nslcm.NsInstance;
import com.ubiqube.etsi.mano.nfvo.v281.model.nslcm.NsLcmOpOcc;
import com.ubiqube.etsi.mano.nfvo.v281.model.vnf.PkgmSubscription;
import com.ubiqube.etsi.mano.nfvo.v281.model.vnf.PkgmSubscriptionRequest;
import com.ubiqube.etsi.mano.service.event.model.AuthParamOauth2;
import com.ubiqube.etsi.mano.service.event.model.Subscription;
import com.ubiqube.etsi.mano.vnfm.v281.model.grant.ConstraintResourceRef;
import com.ubiqube.etsi.mano.vnfm.v281.model.grant.Grant;
import com.ubiqube.etsi.mano.vnfm.v281.model.grant.GrantRequest;
import com.ubiqube.etsi.mano.vnfm.v281.model.grant.ResourceDefinition;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import com.ubiqube.orika.OrikaMapperFactoryConfigurer;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class OrikaConfigurationNfvo281 implements OrikaMapperFactoryConfigurer {

	@Override
	public void configure(final MapperFactory orikaMapperFactory) {
		orikaMapperFactory.classMap(com.ubiqube.etsi.mano.em.v281.model.vnflcm.VnfInstance.class, VnfInstance.class)
				.field("vnfConfigurableProperties{key}", "vnfConfigurableProperties{key}")
				.field("vnfConfigurableProperties{value}", "vnfConfigurableProperties{value}")
				.field("instantiatedVnfInfo.extVirtualLinkInfo", "instantiatedVnfInfo.extVirtualLinkInfo")
				.field("metadata{key}", "metadata{key}")
				.field("metadata{value}", "metadata{value}")
				.field("extensions{key}", "extensions{key}")
				.field("extensions{value}", "extensions{value}")
				.byDefault()
				.register();

		orikaMapperFactory.classMap(NsdInfo.class, NsdPackage.class)
				.field("vnfPkgIds{}", "vnfPkgIds{id}")
				.field("pnfdInfoIds{}", "pnfdInfoIds{id}")
				.field("nestedNsdInfoIds{}", "nestedNsdInfoIds{id}")
				.byDefault()
				.register();

		orikaMapperFactory.classMap(NsInstance.class, NsdInstance.class)
				.field("nestedNsInstanceId{}", "nestedNsInstance{id}")
				.field("nsdId", "nsdInfo.nsdId")
				.field("nsdInfoId", "nsdInfo.id")
				.field("nsState", "instantiationState")
				.field("flavourId", "instanceFlavourId")
				.byDefault()
				.register();
		/*
		 * Subscription.
		 */
		orikaMapperFactory.classMap(PkgmSubscriptionRequest.class, Subscription.class)
				.fieldMap("filter", "filters").converter("filterConverter").add()
				.field("authentication.paramsBasic", "authentication.authParamBasic")
				.field("authentication.paramsOauth2ClientCredentials", "authentication.authParamOauth2")
				.field("authentication.authType", "authentication.authType")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(VnfIndicatorSubscriptionRequest.class, Subscription.class)
				.fieldMap("filter", "filters").converter("filterConverter").add()
				.field("authentication.paramsBasic", "authentication.authParamBasic")
				.field("authentication.paramsOauth2ClientCredentials", "authentication.authParamOauth2")
				.field("authentication.authType", "authentication.authType")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(PkgmSubscription.class, Subscription.class)
				.fieldMap("filter", "filters").converter("filterConverter").add()
				.byDefault()
				.register();

		orikaMapperFactory.classMap(NsdmSubscriptionRequest.class, Subscription.class)
				.fieldMap("filter", "filters").converter("filterConverter").add()
				.field("authentication.paramsBasic", "authentication.authParamBasic")
				.field("authentication.paramsOauth2ClientCredentials", "authentication.authParamOauth2")
				.field("authentication.authType", "authentication.authType")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(NsdmSubscription.class, Subscription.class)
				.fieldMap("filter", "filters").converter("filterConverter").add()
				.byDefault()
				.register();

		orikaMapperFactory.classMap(LccnSubscriptionRequest.class, Subscription.class)
				.fieldMap("filter", "filters").converter("filterConverter").add()
				.field("authentication.paramsBasic", "authentication.authParamBasic")
				.field("authentication.paramsOauth2ClientCredentials", "authentication.authParamOauth2")
				.field("authentication.authType", "authentication.authType")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(LccnSubscription.class, Subscription.class)
				.fieldMap("filter", "filters").converter("filterConverter").add()
				.byDefault()
				.register();

		/**
		 * No default !
		 */
		orikaMapperFactory.classMap(ResourceDefinition.class, GrantInformationExt.class)
				.exclude("id")
				.field("id", "resourceDefinitionId")
				.field("type", "type")
				.field("vduId", "vduId")
				.field("resourceTemplateId", "resourceTemplateId")
				.field("resource.vimConnectionId", "vimConnectionId")
				.field("resource.resourceProviderId", "resourceProviderId")
				.field("resource.vimLevelResourceType", "vimLevelResourceType")
				.field("resource.resourceId", "resourceId")
				.register();
		orikaMapperFactory.classMap(InstantiateNsRequest.class, NsdInstance.class)
				.field("nsFlavourId", "instantiatedVnfInfo.flavourId")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(ExtManagedVirtualLinkData.class, ExtManagedVirtualLinkDataEntity.class)
				.field("vnfVirtualLinkDescId", "vnfVirtualLinkDescId")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(ExtManagedVirtualLinkInfo.class, ExtManagedVirtualLinkDataEntity.class)
				.field("networkResource.vimConnectionId", "vimConnectionId")
				.field("networkResource.resourceProviderId", "resourceProviderId")
				.field("networkResource.resourceId", "resourceId")
				.field("networkResource.vimLevelResourceType", "vimLevelResourceType")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(ExtVirtualLinkInfo.class, ExtVirtualLinkDataEntity.class)
				.field("resourceHandle.vimConnectionId", "vimConnectionId")
				.field("resourceHandle.resourceProviderId", "resourceProviderId")
				.field("resourceHandle.resourceId", "resourceId")
				.field("resourceHandle.vimLevelResourceType", "vimLevelResourceType")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(AffectedVnf.class, NsInstantiatedVnf.class)
				.field("vnfdId", "")
				.field("vnfInstanceId", "vnfInstance")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(GrantRequest.class, GrantResponse.class)
				.field("vimConstraints[0].resource", "vimConnections")
				.field("links.vnfInstance.href", "instanceLink")
				.field("links.vnfLcmOpOcc.href", "lcmLink")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(Grant.class, GrantResponse.class)
				.field("links.vnfInstance.href", "instanceLink")
				.field("links.vnfLcmOpOcc.href", "lcmLink")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(ConstraintResourceRef.class, VimConnectionInformation.class)
				.field("vimConnectionId", "vimId")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(VnfcResourceInfo.class, VnfInstantiatedCompute.class)
				.field("computeResource.resourceId", "resourceId")
				.field("computeResource.resourceProviderId", "resourceProviderId")
				// .field("computeResource.vimConnectionId", "vimConnectionInformation.vimId")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(VnfExtCpInfo.class, VnfInstantiatedExtCp.class)
				.field("cpdId", "toscaName")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(VnfVirtualLinkResourceInfo.class, VnfInstantiatedVirtualLink.class)
				.field("networkResource.resourceId", "resourceId")
				.field("networkResource.resourceProviderId", "resourceProviderId")
				// .field("networkResource.vimConnectionId", "vimConnectionInformation.vimId")
				.field("vnfVirtualLinkDescId", "toscaName")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(NsLcmOpOcc.class, NsBlueprint.class)
				.field("lcmOperationType", "operation")
				.field("operationState", "operationStatus")
				.field("statusEnteredTime", "stateEnteredTime")
				.field("nsInstanceId", "nsInstance.id")
				.field("isAutomaticInvocation", "automaticInvocation")
				.field("isCancelPending", "cancelPending")
				.field("startTime", "audit.createdOn")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(VnfLcmOpOcc.class, VnfBlueprint.class)
				.field("vnfInstanceId", "vnfInstance.id")
				// .field("resourceChanges", "tasks")
				.field("grantId", "grantsRequestId")
				.field("operationState", "operationStatus")
				.field("isAutomaticInvocation", "automaticInvocation")
				.field("isCancelPending", "cancelPending")
				.byDefault()
				.customize(new CustomMapper<>() {
					@Override
					public void mapBtoA(final VnfBlueprint a, final VnfLcmOpOcc b, final MappingContext context) {
						if (null == a.getOperation()) {
							return;
						}
						final Object x = switch (a.getOperation()) {
						case INSTANTIATE -> map(a.getParameters(), InstantiateVnfRequest.class);
						case SCALE -> map(a.getParameters(), ScaleVnfRequest.class);
						case SCALE_TO_LEVEL -> map(a.getParameters(), ScaleVnfToLevelRequest.class);
						case TERMINATE -> map(a.getParameters(), TerminateVnfRequest.class);
						default -> throw new IllegalArgumentException("Unexpected value: " + a.getOperation());
						};
						b.setOperationParams(x);
					}

					private Object map(final BlueprintParameters parameters, final Class<?> class1) {
						return mapperFacade.map(parameters, class1);
					}

					@Override
					public void mapAtoB(final VnfLcmOpOcc a, final VnfBlueprint b, final MappingContext context) {
						final Object op = a.getOperationParams();
						if (op != null) {
							mapperFacade.map(op, b.getParameters());
						}
					}

				})
				.register();
		orikaMapperFactory.classMap(NsLcmOpOccs.class, NsLcmOpOcc.class)
				.field("stateEnteredTime", "statusEnteredTime")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(SubscriptionAuthenticationParamsOauth2ClientCredentials.class, AuthParamOauth2.class)
				.field("clientPassword", "clientSecret")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(InstantiateVnfRequest.class, BlueprintParameters.class)
				.field("extVirtualLinks", "extVirtualLinkInfo")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(VnfInstanceInstantiatedVnfInfo.class, BlueprintParameters.class)
				.field("vnfState", "state")
				.field("extManagedVirtualLinkInfo", "extManagedVirtualLinks")
				.field("monitoringParameters", "vnfMonitoringParameter")
				.field("extVirtualLinkInfo", "extVirtualLinkInfo")
				.byDefault()
				.register();
		// Not needed UploadVnfPkgFromUriRequest
		final var converterFactory = orikaMapperFactory.getConverterFactory();
		converterFactory.registerConverter(new UuidConverter());
		converterFactory.registerConverter(new OffsetDateTimeToDateConverter());
		converterFactory.registerConverter("filterConverter", new OrikaFilterMapper());
	}

}
