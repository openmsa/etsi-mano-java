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
package com.ubiqube.etsi.mano.vnfm.service.graph.tasks;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.service.vim.ComputeInfo;
import com.ubiqube.etsi.mano.service.vim.ComputeStatus;
import com.ubiqube.etsi.mano.service.vim.Vim;

@ExtendWith(MockitoExtension.class)
class Q4WaitStatusTest {
	@Mock
	private Vim vim;

	@Test
	void testNull() {
		VimConnectionInformation vci = new VimConnectionInformation();
		Q4WaitStatus q4WaitStatus = new Q4WaitStatus("resourceId", vim, vci);
		q4WaitStatus.execute(null);
		assertTrue(true);
	}

	@Test
	void testFilledTask() {
		VimConnectionInformation vci = new VimConnectionInformation();
		Q4WaitStatus q4WaitStatus = new Q4WaitStatus("resourceId", vim, vci);
		ComputeInfo comp = new ComputeInfo();
		comp.setTaskState("taskState");
		ComputeInfo compEnd = new ComputeInfo();
		compEnd.setTaskState(null);
		compEnd.setStatus(ComputeStatus.COMPLETED);
		when(vim.getCompute(vci, "resourceId")).thenReturn(comp, compEnd);
		q4WaitStatus.execute(null);
		assertTrue(true);
	}

	@Test
	void testInterrupted() {
		VimConnectionInformation vci = new VimConnectionInformation();
		Q4WaitStatus q4WaitStatus = new Q4WaitStatus("resourceId", vim, vci);
		ComputeInfo comp = new ComputeInfo();
		comp.setTaskState("taskState");
		ComputeInfo compEnd = new ComputeInfo();
		compEnd.setTaskState(null);
		compEnd.setStatus(ComputeStatus.COMPLETED);
		when(vim.getCompute(vci, "resourceId")).thenReturn(comp, compEnd);
		Thread.currentThread().interrupt();
		assertThrows(GenericException.class, () -> q4WaitStatus.execute(null));
	}

	@ParameterizedTest
	@EnumSource(ComputeStatus.class)
	void test(final ComputeStatus status) {
		VimConnectionInformation vci = new VimConnectionInformation();
		Q4WaitStatus q4WaitStatus = new Q4WaitStatus("resourceId", vim, vci);
		ComputeInfo compEnd = new ComputeInfo();
		compEnd.setTaskState(null);
		compEnd.setStatus(status);
		ComputeInfo comp = new ComputeInfo();
		comp.setStatus(ComputeStatus.STOPPED);
		when(vim.getCompute(vci, "resourceId")).thenReturn(compEnd, comp);
		q4WaitStatus.execute(null);
		assertTrue(true);
	}

	@Test
	void testRollback() {
		VimConnectionInformation vci = new VimConnectionInformation();
		Q4WaitStatus q4WaitStatus = new Q4WaitStatus("resourceId", vim, vci);
		q4WaitStatus.rollback(null);
		assertTrue(true);
	}
}
