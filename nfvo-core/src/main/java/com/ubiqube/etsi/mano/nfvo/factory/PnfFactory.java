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
package com.ubiqube.etsi.mano.nfvo.factory;

import java.util.Map;

import jakarta.validation.constraints.NotNull;

import com.ubiqube.etsi.mano.dao.mano.OnboardingStateType;
import com.ubiqube.etsi.mano.dao.mano.PackageUsageState;
import com.ubiqube.etsi.mano.dao.mano.PnfDescriptor;

public class PnfFactory {
	private PnfFactory() {
		// Nothing.
	}

	@NotNull
	public static PnfDescriptor createPnfDescriptorsPnfdInfo(final Map<String, Object> userDefinedData) {
		final PnfDescriptor pnfd = new PnfDescriptor();
		pnfd.setPnfdOnboardingState(OnboardingStateType.CREATED);
		pnfd.setPnfdUsageState(PackageUsageState.NOT_IN_USE);
		return pnfd;
	}
}
