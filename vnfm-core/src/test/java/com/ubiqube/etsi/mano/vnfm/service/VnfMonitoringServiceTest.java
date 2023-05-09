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
package com.ubiqube.etsi.mano.vnfm.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.MonitoringParams;
import com.ubiqube.etsi.mano.dao.mano.pm.PmJob;
import com.ubiqube.etsi.mano.jpa.PmJobsJpa;

@ExtendWith(MockitoExtension.class)
class VnfMonitoringServiceTest {
	@Mock
	private PmJobsJpa pmJpa;

	@Test
	void test() {
		final VnfMonitoringService srv = new VnfMonitoringService(pmJpa);
		final PmJob val = new PmJob();
		val.setId(UUID.randomUUID());
		when(pmJpa.save(any())).thenReturn(val);
		final MonitoringParams monitoringParams = new MonitoringParams();
		monitoringParams.setPerformanceMetric("perf");
		srv.registerMonitoring("", monitoringParams, null);
		assertTrue(true);
	}

	@Test
	void testUnregisterEmpty() {
		final VnfMonitoringService srv = new VnfMonitoringService(pmJpa);
		final String str = UUID.randomUUID().toString();
		srv.unregister(str);
		assertTrue(true);
	}

	@Test
	void testUnregister() {
		final VnfMonitoringService srv = new VnfMonitoringService(pmJpa);
		final String str = UUID.randomUUID().toString();
		final PmJob pmj = new PmJob();
		when(pmJpa.findById(any())).thenReturn(Optional.of(pmj));
		srv.unregister(str);
		assertTrue(true);
	}
}
