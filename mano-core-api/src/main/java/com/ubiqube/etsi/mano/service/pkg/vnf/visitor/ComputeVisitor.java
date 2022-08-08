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
package com.ubiqube.etsi.mano.service.pkg.vnf.visitor;

import java.util.Map;
import java.util.Set;

import javax.annotation.Priority;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.service.pkg.vnf.OnboardVisitor;
import com.ubiqube.etsi.mano.service.pkg.vnf.VnfPackageReader;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Priority(0)
@Service
public class ComputeVisitor implements OnboardVisitor {

	@Override
	public void visit(final VnfPackage vnfPackage, final VnfPackageReader vnfPackageReader, final Map<String, String> userData) {
		final Set<VnfCompute> cNodes = vnfPackageReader.getVnfComputeNodes(vnfPackage.getUserDefinedData());
		vnfPackage.setVnfCompute(cNodes);
	}

}
