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
package com.ubiqube.etsi.mano.nfvo.service.pkg.ns.visitor;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.nfvo.service.mapping.NsdPackageMapping;
import com.ubiqube.etsi.mano.service.pkg.ns.NsPackageProvider;

@ExtendWith(MockitoExtension.class)
class NsdVisitorTest {
	private final NsdPackageMapping mapper = Mappers.getMapper(NsdPackageMapping.class);
	@Mock
	private NsPackageProvider packageProvider;

	@Test
	void test() {
		final NsdVisitor vis = new NsdVisitor(mapper);
		final NsdPackage nasPkg = new NsdPackage();
		vis.visit(nasPkg, packageProvider, Map.of());
		assertTrue(true);
	}

}
