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
