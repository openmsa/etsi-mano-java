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
package com.ubiqube.etsi.mano.nfvo.service.plan.contributors.v3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.NsdPackageNsdPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.orchestrator.SclableResources;
import com.ubiqube.etsi.mano.service.NsScaleStrategyV3;

@ExtendWith(MockitoExtension.class)
class NsdNsdContributorV3Test {
	@Mock
	private NsLiveInstanceJpa nsLiveInstanceJpa;
	@Mock
	private NsScaleStrategyV3 nsScaleStrategy;

	@Test
	void testBasic() throws Exception {
		final NsdNsdContributorV3 nnc = new NsdNsdContributorV3(nsLiveInstanceJpa, nsScaleStrategy);
		final NsdPackage bundle = new NsdPackage();
		bundle.setNestedNsdInfoIds(Set.of());
		final NsBlueprint blueprint = new NsBlueprint();
		final List<SclableResources<Object>> res = nnc.contribute(bundle, blueprint);
		assertNotNull(res);
		assertTrue(res.isEmpty());
	}

	@Test
	void testOne() throws Exception {
		final NsdNsdContributorV3 nnc = new NsdNsdContributorV3(nsLiveInstanceJpa, nsScaleStrategy);
		final NsdPackage bundle = new NsdPackage();
		final NsdPackage parent = null;
		final NsdPackage child = null;
		final NsdPackageNsdPackage nsd01 = new NsdPackageNsdPackage(parent, child, "toscaname", Set.of());
		bundle.setNestedNsdInfoIds(Set.of(nsd01));
		final NsBlueprint blueprint = new NsBlueprint();
		final List<SclableResources<Object>> res = nnc.contribute(bundle, blueprint);
		assertNotNull(res);
		assertEquals(3, res.size());
		TestUtils.assertNameNotNull(res);
	}
}
