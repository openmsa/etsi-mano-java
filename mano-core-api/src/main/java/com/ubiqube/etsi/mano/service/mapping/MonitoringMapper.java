package com.ubiqube.etsi.mano.service.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import com.ubiqube.etsi.mano.dao.mano.ai.KeystoneAuthV3;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.service.mon.dto.KeystoneV3;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MonitoringMapper {

	@Mapping(target = "connId", ignore = true)
	@Mapping(target = "connectionTimeout", source = "interfaceInfo.connectionTimeout")
	@Mapping(target = "endpoint", source = "interfaceInfo.endpoint")
	@Mapping(target = "ignoreSsl", source = "interfaceInfo.nonStrictSsl")
	@Mapping(target = "name", ignore = true)
	@Mapping(target = "readTimeout", source = "interfaceInfo.readTimeout")
	@Mapping(target = "retry", source = "interfaceInfo.retry")
	@Mapping(target = "type", source = "vimType")
	KeystoneV3 toKeystoneV3(VimConnectionInformation vimConnectionInformation);

	@Mapping(target = "connId", ignore = true)
	@Mapping(target = "connectionTimeout", ignore = true)
	@Mapping(target = "endpoint", ignore = true)
	@Mapping(target = "extra", ignore = true)
	@Mapping(target = "ignoreSsl", ignore = true)
	@Mapping(target = "name", ignore = true)
	@Mapping(target = "readTimeout", ignore = true)
	@Mapping(target = "retry", ignore = true)
	@Mapping(target = "type", ignore = true)
	void toKeystoneV3(KeystoneAuthV3 keystoneAuthV3, @MappingTarget KeystoneV3 dest);
}
