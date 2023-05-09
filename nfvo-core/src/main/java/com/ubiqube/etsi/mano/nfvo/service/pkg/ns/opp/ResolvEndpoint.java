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
package com.ubiqube.etsi.mano.nfvo.service.pkg.ns.opp;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.nsd.ForwarderMapping;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.nfvo.service.pkg.ns.NsOnboardingPostProcessor;

/**
 *
 * @author olivier
 *
 */
@Service
public class ResolvEndpoint implements NsOnboardingPostProcessor {

	@Override
	public void visit(final NsdPackage nsPackage) {
		nsPackage.getVnffgs().stream()
				.flatMap(x -> x.getNfpd().stream())
				.flatMap(x -> x.getInstances().stream())
				.flatMap(x -> x.getPairs().stream()).forEach(x -> {
					final ForwarderMapping fw = findVnfByVl(nsPackage, x.getIngress());
					x.setVnf(fw.getVduName());
				});
	}

	private static ForwarderMapping findVnfByVl(final NsdPackage nsPackage, final String egress) {
		return nsPackage.getVnfPkgIds().stream()
				.flatMap(x -> x.getForwardMapping().stream())
				.filter(x -> x.getForwardingName().equals(egress))
				.findFirst()
				.orElseThrow(() -> new NotFoundException("Unable to find egress: " + egress));
	}

}
