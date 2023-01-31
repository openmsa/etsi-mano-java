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
package com.ubiqube.etsi.mano.vnfm.v351;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.ubiqube.etsi.mano.dao.mano.ExtManagedVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.ExtVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.GrantInformationExt;
import com.ubiqube.etsi.mano.dao.mano.SoftwareImage;
import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfExtCpDataEntity;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfStorage;
import com.ubiqube.etsi.mano.dao.mano.alarm.Alarms;
import com.ubiqube.etsi.mano.dao.mano.dto.VnfInfoModificationsDto;
import com.ubiqube.etsi.mano.dao.mano.dto.VnfInstantiatedCompute;
import com.ubiqube.etsi.mano.dao.mano.dto.VnfInstantiatedExtLinkPort;
import com.ubiqube.etsi.mano.dao.mano.dto.VnfInstantiatedStorage;
import com.ubiqube.etsi.mano.dao.mano.dto.VnfInstantiatedVirtualLink;
import com.ubiqube.etsi.mano.dao.mano.dto.VnfLcmOpOccs;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.em.v351.model.SubscriptionAuthentication;
import com.ubiqube.etsi.mano.em.v351.model.SubscriptionAuthenticationParamsOauth2ClientCredentials;
import com.ubiqube.etsi.mano.em.v351.model.vnffm.Alarm;
import com.ubiqube.etsi.mano.em.v351.model.vnffm.FmSubscription;
import com.ubiqube.etsi.mano.em.v351.model.vnffm.FmSubscriptionRequest;
import com.ubiqube.etsi.mano.em.v351.model.vnfind.VnfIndicatorSubscriptionRequest;
import com.ubiqube.etsi.mano.em.v351.model.vnflcm.AffectedExtLinkPort;
import com.ubiqube.etsi.mano.em.v351.model.vnflcm.AffectedVirtualLink;
import com.ubiqube.etsi.mano.em.v351.model.vnflcm.AffectedVirtualStorage;
import com.ubiqube.etsi.mano.em.v351.model.vnflcm.AffectedVnfc;
import com.ubiqube.etsi.mano.em.v351.model.vnflcm.ExtManagedVirtualLinkInfo;
import com.ubiqube.etsi.mano.em.v351.model.vnflcm.ExtVirtualLinkInfo;
import com.ubiqube.etsi.mano.em.v351.model.vnflcm.LccnSubscription;
import com.ubiqube.etsi.mano.em.v351.model.vnflcm.LccnSubscriptionRequest;
import com.ubiqube.etsi.mano.em.v351.model.vnflcm.VnfExtCpData;
import com.ubiqube.etsi.mano.em.v351.model.vnflcm.VnfInfoModifications;
import com.ubiqube.etsi.mano.em.v351.model.vnflcm.VnfInstanceInstantiatedVnfInfo;
import com.ubiqube.etsi.mano.em.v351.model.vnflcm.VnfLcmOpOcc;
import com.ubiqube.etsi.mano.em.v351.model.vnflcm.VnfcResourceInfo;
import com.ubiqube.etsi.mano.nfvo.v351.model.vnf.PkgmSubscriptionRequest;
import com.ubiqube.etsi.mano.nfvo.v351.model.vnf.VnfPackageSoftwareImageInfo;
import com.ubiqube.etsi.mano.nfvo.v351.model.vnf.VnfPackageSoftwareImageInfo.ContainerFormatEnum;
import com.ubiqube.etsi.mano.nfvo.v351.model.vnf.VnfPackageSoftwareImageInfo.DiskFormatEnum;
import com.ubiqube.etsi.mano.nfvo.v351.model.vnf.VnfPkgInfo;
import com.ubiqube.etsi.mano.nfvo.v351.model.vnfsnapshotpkgm.Checksum;
import com.ubiqube.etsi.mano.service.event.model.AuthParamOauth2;
import com.ubiqube.etsi.mano.service.event.model.AuthentificationInformations;
import com.ubiqube.etsi.mano.service.event.model.Subscription;
import com.ubiqube.etsi.mano.vnfm.v351.model.grant.ResourceDefinition;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import com.ubiqube.orika.OrikaMapperFactoryConfigurer;

@Component
public class OrikaMapperVnfm351 implements OrikaMapperFactoryConfigurer {

	@Override
	public void configure(final MapperFactory orikaMapperFactory) {
		orikaMapperFactory.classMap(com.ubiqube.etsi.mano.em.v351.model.vnflcm.VnfInstance.class, VnfInstance.class)
				.field("instantiatedVnfInfo.extVirtualLinkInfo", "instantiatedVnfInfo.extVirtualLinkInfo")
				.field("vimConnectionInfo{value}", "vimConnectionInfo")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(VnfInstanceInstantiatedVnfInfo.class, BlueprintParameters.class)
				.field("vnfState", "state")
				.field("localizationLanguage", "localizationLanguage")
				.field("monitoringParameters", "vnfMonitoringParameter")
				.field("extManagedVirtualLinkInfo", "extManagedVirtualLinks")
				.field("vnfVirtualLinkResourceInfo", "virtualLinkResourceInfo")
				.field("vnfVirtualStorageResourceInfo", "virtualStorageResourceInfo")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(VnfInfoModifications.class, VnfInfoModificationsDto.class)
				.field("vimConnectionInfo{value}", "vimConnectionInfo{}")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(VnfExtCpDataEntity.class, VnfExtCpData.class)
				// .field("cpConfig", "cpConfig{value}")
				.exclude("cpConfig")
				.byDefault()
				.register();

		orikaMapperFactory.classMap(VnfPackage.class, VnfPkgInfo.class)
				.field("vnfmInfo", "vnfmInfo")
				.byDefault()
				.customize(new CustomMapper<VnfPackage, VnfPkgInfo>() {
					@Override
					public void mapAtoB(final VnfPackage a, final VnfPkgInfo b, final MappingContext context) {
						// Remap Images
						final Set<VnfCompute> vnfc = a.getVnfCompute();
						if (null != vnfc) {
							vnfc.forEach(x -> {
								final SoftwareImage img = x.getSoftwareImage();
								if (null != img) {
									b.addSoftwareImagesItem(mapSoftwareImage(img));
								}
							});
						}
						final Set<VnfStorage> storage = a.getVnfStorage();
						if (null != storage) {
							storage.forEach(x -> {
								final SoftwareImage img = x.getSoftwareImage();
								if (null != img) {
									b.addSoftwareImagesItem(mapSoftwareImage(img));
								}
							});
						}

					}

					private VnfPackageSoftwareImageInfo mapSoftwareImage(final SoftwareImage img) {
						final VnfPackageSoftwareImageInfo ret = new VnfPackageSoftwareImageInfo();
						final Checksum chk = new Checksum();
						chk.setAlgorithm(img.getChecksum().getAlgorithm());
						chk.setHash(img.getChecksum().getHash());
						ret.setChecksum(chk);
						if (null != img.getContainerFormat()) {
							ret.setContainerFormat(ContainerFormatEnum.fromValue(img.getContainerFormat().toString()));
						}
						// ret.setCreatedAt(img.get);
						if (null != img.getDiskFormat()) {
							ret.setDiskFormat(DiskFormatEnum.valueOf(img.getDiskFormat().toString()));
						}
						if (null != img.getId()) {
							ret.setId(img.getId().toString());
						}
						ret.setImagePath(img.getImagePath());
						ret.setMinDisk(img.getMinDisk());
						ret.setMinRam(img.getMinRam());
						ret.setName(img.getName());
						ret.setProvider(img.getProvider());
						ret.setSize(img.getSize());
						// ret.setUserMetadata(img.get);
						ret.setVersion(img.getVersion());
						ret.setCreatedAt(img.getAudit().getCreatedOn());
						return ret;
					}
				})
				.register();
		orikaMapperFactory.classMap(AffectedVirtualLink.class, VnfInstantiatedVirtualLink.class)
				.exclude("audit")
				.field("vnfVirtualLinkDescId", "manoResourceId")
				.field("networkResource.resourceId", "resourceId")
				.field("networkResource.resourceProviderId", "resourceProviderId")
				.field("networkResource.vimLevelResourceType", "vimLevelResourceType")
				.field("networkResource.vimConnectionId", "vimConnectionInformation.id")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(AffectedVnfc.class, VnfInstantiatedCompute.class)
				.exclude("audit")
				.field("computeResource.resourceId", "resourceId")
				.field("computeResource.resourceProviderId", "resourceProviderId")
				.field("computeResource.vimLevelResourceType", "vimLevelResourceType")
				.field("computeResource.vimConnectionId", "vimConnectionInformation.id")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(AffectedExtLinkPort.class, VnfInstantiatedExtLinkPort.class)
				.field("resourceHandle.vimConnectionId", "vimConnectionInformation.vimId")
				.field("resourceHandle.resourceProviderId", "resourceProviderId")
				.field("resourceHandle.resourceId", "resourceId")
				.field("resourceHandle.vimLevelResourceType", "vimLevelResourceType")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(VnfInstantiatedCompute.class, VnfCompute.class)
				.exclude("id")
				.field("storageResourceIds", "storages")
				.field("vduId", "id")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(VnfLcmOpOcc.class, VnfBlueprint.class)
				.field("vnfInstanceId", "vnfInstance.id")
				// .field("resourceChanges", "tasks")
				.field("grantId", "grantsRequestId")
				.field("operationState", "operationStatus")
				.field("isAutomaticInvocation", "automaticInvocation")
				.field("isCancelPending", "cancelPending")
				.field("operationParams", "parameters")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(ExtVirtualLinkInfo.class, ExtVirtualLinkDataEntity.class)
				.field("resourceHandle.vimConnectionId", "vimConnectionId")
				.field("resourceHandle.resourceProviderId", "resourceProviderId")
				.field("resourceHandle.resourceId", "resourceId")
				.field("resourceHandle.vimLevelResourceType", "vimLevelResourceType")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(ExtManagedVirtualLinkInfo.class, ExtManagedVirtualLinkDataEntity.class)
				.field("networkResource.vimConnectionId", "vimConnectionId")
				.field("networkResource.resourceProviderId", "resourceProviderId")
				.field("networkResource.resourceId", "resourceId")
				.field("networkResource.vimLevelResourceType", "vimLevelResourceType")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(AffectedVirtualStorage.class, VnfInstantiatedStorage.class)
				.field("storageResource.vimConnectionId", "vimConnectionInformation.vimId")
				.field("storageResource.resourceProviderId", "resourceProviderId")
				.field("storageResource.resourceId", "resourceId")
				.field("storageResource.vimLevelResourceType", "vimLevelResourceType")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(VnfLcmOpOcc.class, VnfLcmOpOccs.class)
				.field("vnfInstanceId", "vnfInstance.id")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(VnfcResourceInfo.class, VnfInstantiatedCompute.class)
				.field("computeResource.resourceId", "resourceId")
				.field("computeResource.resourceProviderId", "resourceProviderId")
				.field("computeResource.vimConnectionId", "vimConnectionInformation.vimId")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(ResourceDefinition.class, GrantInformationExt.class)
				.exclude("id")
				.field("id", "resourceDefinitionId")
				.field("type", "type")
				.field("vduId", "vduId")
				.field("resource.vimConnectionId", "vimConnectionId")
				.field("resource.resourceProviderId", "resourceProviderId")
				.field("resource.vimLevelResourceType", "vimLevelResourceType")
				.field("resource.resourceId", "resourceId")
				.field("resource.resourceProviderId", "resourceProviderId")
				.byDefault()
				.register();
		/*
		 * Subscriptions.
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
		orikaMapperFactory.classMap(LccnSubscriptionRequest.class, Subscription.class)
				.fieldMap("filter", "filters").converter("filterConverter").add()
				.field("authentication.paramsBasic", "authentication.authParamBasic")
				.field("authentication.paramsOauth2ClientCredentials", "authentication.authParamOauth2")
				.field("authentication.authType", "authentication.authType")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(FmSubscriptionRequest.class, Subscription.class)
				.fieldMap("filter", "filters").converter("filterConverter").add()
				.field("authentication.paramsBasic", "authentication.authParamBasic")
				.field("authentication.paramsOauth2ClientCredentials", "authentication.authParamOauth2")
				.field("authentication.authType", "authentication.authType")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(FmSubscription.class, Subscription.class)
				.fieldMap("filter", "filters").converter("filterConverter").add()
				.byDefault()
				.register();
		orikaMapperFactory.classMap(LccnSubscription.class, Subscription.class)
				.fieldMap("filter", "filters").converter("filterConverter").add()
				.byDefault()
				.register();
		orikaMapperFactory.classMap(SubscriptionAuthentication.class, AuthentificationInformations.class)
				// .fieldMap("authType[0]", "authType").converter("filterConverter").add()
				.byDefault()
				.register();
		orikaMapperFactory.classMap(SubscriptionAuthenticationParamsOauth2ClientCredentials.class, AuthParamOauth2.class)
				.field("clientPassword", "clientSecret")
				.byDefault()
				.register();
		/*
		 * Fault management.
		 */
		orikaMapperFactory.classMap(Alarm.class, Alarms.class)
				.field("isRootCause", "rootCause")
				.byDefault()
				.register();
	}
}
