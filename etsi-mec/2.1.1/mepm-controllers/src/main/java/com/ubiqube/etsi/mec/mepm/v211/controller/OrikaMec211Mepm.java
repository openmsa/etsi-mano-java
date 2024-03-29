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
package com.ubiqube.etsi.mec.mepm.v211.controller;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mec.lcm.AppInstance;
import com.ubiqube.etsi.mec.meo.v211.model.grant.GrantRequest;
import com.ubiqube.etsi.mec.meo.v211.model.lcm.AppInstanceInfo;

import ma.glasnost.orika.MapperFactory;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class OrikaMec211Mepm implements OrikaMapperFactoryConfigurer {

	@Override
	public void configure(final MapperFactory orikaMapperFactory) {
		orikaMapperFactory.classMap(AppInstanceInfo.class, AppInstance.class)
				.field("appInstanceName", "vnfInstanceName")
				.field("appInstanceDescription", "vnfInstanceDescription")
				.field("appDId", "appdId")
				.field("appName", "appProductName")
				.field("appSoftVersion", "appSoftwareVersion")
				.field("appDVersion", "appdVersion")
				.field("appPkgId", "appPkg.id")
				.byDefault()
				.register();
		orikaMapperFactory.classMap(GrantRequest.class, GrantResponse.class)
				.field("appInstanceId", "vnfInstance.id")
				.field("appLcmOpOccId", "vnfLcmOpOccs.id")
				.field("appDId", "vnfdId")
				.field("links.appLcmOpOcc.href", "lcmLink")
				.field("links.appInstance.href", "instanceLink")
				.byDefault()
				.register();
	}

}
