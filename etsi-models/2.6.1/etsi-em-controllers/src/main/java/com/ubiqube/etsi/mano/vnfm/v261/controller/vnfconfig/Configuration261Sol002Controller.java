/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
package com.ubiqube.etsi.mano.vnfm.v261.controller.vnfconfig;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.vnfm.fc.vnfconfig.VnfConfigurationFrontController;
import com.ubiqube.etsi.mano.vnfm.v261.model.vnfconfig.VnfConfigModifications;
import com.ubiqube.etsi.mano.vnfm.v261.model.vnfconfig.VnfConfiguration;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@RestController
public class Configuration261Sol002Controller implements Configuration261Sol002Api {
	private VnfConfigurationFrontController vnfConfigurationFrontController;

	@Override
	public ResponseEntity<VnfConfiguration> configurationGet() {
		return vnfConfigurationFrontController.find();
	}

	@Override
	public ResponseEntity<VnfConfigModifications> configurationPatch(@Valid final VnfConfigModifications body) {
		return vnfConfigurationFrontController.modify(body);
	}

}
