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
package com.ubiqube.etsi.mano.service.pkg.vnf;

import java.util.Set;

import jakarta.annotation.Nonnull;

import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.service.pkg.ToscaException;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public final class VisitorUtils {
	private VisitorUtils() {
		// Nothing.
	}

	@Nonnull
	public static VnfCompute findVnfCompute(final VnfPackage vnfPackage, final String y) {
		return vnfPackage.getVnfCompute().stream()
				.filter(x -> x.getToscaName().equals(y))
				.findFirst()
				.orElseThrow(() -> new NotFoundException("Unable to find VDU: " + y));
	}

	public static String findVl(final String toscaName, final Set<ListKeyPair> virtualLinks) {
		final ListKeyPair vl = virtualLinks.stream()
				.filter(x -> x.getValue() != null)
				.filter(x -> x.getValue().equals(toscaName))
				.findFirst()
				.orElseThrow(() -> new ToscaException("Could not find VL named " + toscaName + " in " + virtualLinks));
		return vlToString(vl);
	}

	public static String vlToString(final ListKeyPair vl) {
		if (0 == vl.getIdx()) {
			return "virtual_link";
		}
		return "virtual_link_" + vl.getIdx();
	}

}
