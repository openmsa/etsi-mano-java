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
package com.ubiqube.etsi.mano.service.mapping;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import com.ubiqube.etsi.mano.dao.mano.AccessInfo;
import com.ubiqube.etsi.mano.dao.mano.InterfaceInfo;
import com.ubiqube.etsi.mano.dao.mano.ai.KeystoneAuthV3;
import com.ubiqube.etsi.mano.dao.mano.ai.KubernetesV1Auth;
import com.ubiqube.etsi.mano.dao.mano.dto.VimConnectionRegistrationDto;
import com.ubiqube.etsi.mano.dao.mano.ii.K8sInterfaceInfo;
import com.ubiqube.etsi.mano.dao.mano.ii.OpenstackV3InterfaceInfo;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.k8s.K8sServers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VimConnectionInformationMapping {
	@Mapping(target = "audit", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "tenantId", ignore = true)
	@Mapping(target = "version", ignore = true)
	default VimConnectionInformation map(final VimConnectionRegistrationDto o) {
		if (o == null) {
			return null;
		}
		final VimConnectionInformation ret = new VimConnectionInformation();
		ret.setCnfInfo(o.getCnfInfo());
		ret.setExtra(o.getExtra());
		ret.setJujuInfo(o.getJujuInfo());
		ret.setVimCapabilities(o.getVimCapabilities());
		ret.setVimId(o.getVimId());
		ret.setVimType(o.getVimType());
		//
		final AccessInfo ai = switch (ret.getVimType()) {
		case "OPENSTACK_V3", "ETSINFV.OPENSTACK_KEYSTONE.V_3" -> mapToKeystoneAuthV3(o.getAccessInfo(), new KeystoneAuthV3());
		case "UBINFV.CISM.V_1" -> mapToKubernetesV1Auth(o.getAccessInfo(), new KubernetesV1Auth());
		default -> throw new IllegalArgumentException("Unexpected value: " + ret.getVimType());
		};
		ret.setAccessInfo(ai);
		//
		final InterfaceInfo ii = switch (ret.getVimType()) {
		case "OPENSTACK_V3", "ETSINFV.OPENSTACK_KEYSTONE.V_3" -> mapToOpenstackV3InterfaceInfo(o.getInterfaceInfo(), new OpenstackV3InterfaceInfo());
		case "UBINFV.CISM.V_1" -> mapToK8sInterfaceInfo(o.getInterfaceInfo(), new K8sInterfaceInfo());
		default -> throw new IllegalArgumentException("Unexpected value: " + ret.getVimType());
		};
		ret.setInterfaceInfo(ii);
		return ret;
	}

	K8sInterfaceInfo mapToK8sInterfaceInfo(Map<String, String> interfaceInfo, @MappingTarget K8sInterfaceInfo k8sInterfaceInfo);

	@Mapping(target = "trustedCertificates", ignore = true)
	OpenstackV3InterfaceInfo mapToOpenstackV3InterfaceInfo(Map<String, String> interfaceInfo, @MappingTarget OpenstackV3InterfaceInfo keystoneAuthV3);

	KubernetesV1Auth mapToKubernetesV1Auth(Map<String, String> accessInfo, @MappingTarget KubernetesV1Auth kubernetesV1Auth);

	KeystoneAuthV3 mapToKeystoneAuthV3(Map<String, String> accessInfo, @MappingTarget KeystoneAuthV3 keystoneAuthV3);

	default List<VimConnectionInformation> mapAsList(final List<VimConnectionInformation> vimConnectionInfo) {
		// Doesn't works with `toList()`
		return vimConnectionInfo.stream().map(this::map).collect(Collectors.toList());
	}

	default VimConnectionInformation map(final VimConnectionInformation o) {
		if (o == null) {
			return null;
		}
		final VimConnectionInformation ret = new VimConnectionInformation();
		ret.setCnfInfo(o.getCnfInfo());
		ret.setExtra(o.getExtra());
		ret.setJujuInfo(o.getJujuInfo());
		ret.setVimCapabilities(o.getVimCapabilities());
		ret.setVimId(o.getVimId());
		ret.setVimType(o.getVimType());
		//
		final AccessInfo ai = switch (ret.getVimType()) {
		case "OPENSTACK_V3", "ETSINFV.OPENSTACK_KEYSTONE.V_3" -> mapToKeystoneAuthV3((KeystoneAuthV3) o.getAccessInfo());
		case "UBINFV.CISM.V_1" -> mapToK8sInterfaceInfo((KubernetesV1Auth) o.getAccessInfo());
		default -> throw new IllegalArgumentException("Unexpected value: " + ret.getVimType());
		};
		ret.setAccessInfo(ai);
		//
		final InterfaceInfo ii = switch (ret.getVimType()) {
		case "OPENSTACK_V3", "ETSINFV.OPENSTACK_KEYSTONE.V_3" -> mapToOpenstackV3InterfaceInfo((OpenstackV3InterfaceInfo) o.getInterfaceInfo());
		case "UBINFV.CISM.V_1" -> mapToK8sInterfaceInfo((K8sInterfaceInfo) o.getInterfaceInfo());
		default -> throw new IllegalArgumentException("Unexpected value: " + ret.getVimType());
		};
		ret.setInterfaceInfo(ii);
		return (VimConnectionInformation) ret;
	}

	K8sInterfaceInfo mapToK8sInterfaceInfo(K8sInterfaceInfo o);

	OpenstackV3InterfaceInfo mapToOpenstackV3InterfaceInfo(OpenstackV3InterfaceInfo o);

	KubernetesV1Auth mapToK8sInterfaceInfo(KubernetesV1Auth o);

	KeystoneAuthV3 mapToKeystoneAuthV3(final KeystoneAuthV3 o);

	default UUID mapUuid(final String str) {
		if (null == str) {
			return null;
		}
		return UUID.fromString(str);
	}

	@Mapping(target = "accessInfo", source = ".")
	@Mapping(target = "extra", ignore = true)
	@Mapping(target = "interfaceInfo.endpoint", source = "apiAddress")
//	@Mapping(target = "interfaceInfo.certificateAuthorityData", source = "caPem")
	@Mapping(target = "vimType", constant = "UBINFV.CISM.V_1")
	@Mapping(target = "vimId", constant = "130bdaa5-672f-437a-96ae-690c6ac3751f")
	// UBINFV.CISM.V_1
	VimConnectionInformation mapFromTls(K8sServers tls);

	@Mapping(target = "clientCertificateData", source = "userCrt")
	@Mapping(target = "clientKeyData", source = "userKey")
	KubernetesV1Auth map(K8sServers o);
}
