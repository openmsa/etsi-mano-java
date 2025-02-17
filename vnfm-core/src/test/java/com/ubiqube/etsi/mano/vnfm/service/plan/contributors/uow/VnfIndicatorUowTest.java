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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.VnfIndicator;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfIndicatorTask;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.mon.ExternalAlarm;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.vt.VnfIndicatorVt;

@ExtendWith(MockitoExtension.class)
class VnfIndicatorUowTest {
	private VnfIndicatorUow vnfIndicatorUow;
	@Mock
	private ExternalAlarm externaleAlarm;

	@BeforeEach
	public void setup() {
		VnfIndicatorTask t = new VnfIndicatorTask();
		t.setVnfIndicator(new VnfIndicator());
		VirtualTaskV3<VnfIndicatorTask> task = new VnfIndicatorVt(t);
		vnfIndicatorUow = new VnfIndicatorUow(task, null, externaleAlarm);
	}

	@Test
	void test() {
		vnfIndicatorUow.execute(null);
		vnfIndicatorUow.rollback(null);
		assertTrue(true);
	}

}
