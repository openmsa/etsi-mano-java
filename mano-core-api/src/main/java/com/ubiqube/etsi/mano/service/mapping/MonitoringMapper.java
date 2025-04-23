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
