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
package com.ubiqube.etsi.mano.nfvo.service.plan.contributors.v3;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.nsd.NfpDescriptor;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgDescriptor;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgInstance;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.vnffg.VnffgLoadbalancerTask;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.orchestrator.SclableResources;

@ExtendWith(MockitoExtension.class)
class VnffgLoadbalancerContributorV3Test {
	@Mock
	private NsLiveInstanceJpa nsLiveInstance;

	@Test
	void testBasic() throws Exception {
		final VnffgLoadbalancerContributorV3 srv = new VnffgLoadbalancerContributorV3(nsLiveInstance);
		final NsBlueprint blueprint = new NsBlueprint();
		final NsdPackage bundle = new NsdPackage();
		bundle.setVnffgs(Set.of());
		final List<SclableResources<VnffgLoadbalancerTask>> res = srv.contribute(bundle, blueprint);
		assertNotNull(res);
		assertTrue(res.isEmpty());
	}

	@Test
	void testOk() throws Exception {
		final VnffgLoadbalancerContributorV3 srv = new VnffgLoadbalancerContributorV3(nsLiveInstance);
		final NsBlueprint blueprint = new NsBlueprint();
		final NsdPackage bundle = new NsdPackage();
		final VnffgDescriptor vnffg01 = new VnffgDescriptor();
		final NfpDescriptor nfp01 = new NfpDescriptor();
		final VnffgInstance inst01 = new VnffgInstance();
		inst01.setToscaName("inst01");
		nfp01.setInstances(List.of(inst01));
		vnffg01.setNfpd(List.of(nfp01));
		bundle.setVnffgs(Set.of(vnffg01));
		final List<SclableResources<VnffgLoadbalancerTask>> res = srv.contribute(bundle, blueprint);
		assertNotNull(res);
		assertEquals(1, res.size());
		TestUtils.assertNameNotNull(res);
	}
}
