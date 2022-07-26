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
package com.ubiqube.etsi.mano.nfvo.service.pkg.ns.visitor;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.nfvo.service.pkg.ns.NsOnboardingVisitor;
import com.ubiqube.etsi.mano.service.pkg.bean.NsInformations;
import com.ubiqube.etsi.mano.service.pkg.ns.NsPackageProvider;

import ma.glasnost.orika.MapperFacade;

@Service
public class NsdVisitor implements NsOnboardingVisitor {
	private final MapperFacade mapper;

	public NsdVisitor(final MapperFacade mapper) {
		super();
		this.mapper = mapper;
	}

	@Override
	public void visit(final NsdPackage nsPackage, final NsPackageProvider packageProvider, final Map<String, String> userData) {
		final NsInformations nsInformations = packageProvider.getNsInformations(userData);
		mapper.map(nsInformations, nsPackage);
	}

}
