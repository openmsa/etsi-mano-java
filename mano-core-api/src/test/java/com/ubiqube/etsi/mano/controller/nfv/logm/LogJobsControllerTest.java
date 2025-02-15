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
package com.ubiqube.etsi.mano.controller.nfv.logm;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.dao.mano.sol009.logm.CompileLogRequest;
import com.ubiqube.etsi.mano.dao.mano.sol009.logm.CreateLoggingJobRequest;

public class LogJobsControllerTest {

	private LogJobsController logJobsController;

	@BeforeEach
	public void setUp() {
		logJobsController = new LogJobsController();
	}

	@Test
	void testSearch() {
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		assertThrows(UnsupportedOperationException.class, () -> {
			logJobsController.search(requestParams, job -> job, null);
		});
	}

	@Test
	void testCompile() {
		CompileLogRequest req = new CompileLogRequest();
		assertThrows(UnsupportedOperationException.class, () -> {
			logJobsController.compile("logJobId", req, report -> report);
		});
	}

	@Test
	void testDelete() {
		assertThrows(UnsupportedOperationException.class, () -> {
			logJobsController.delete("logJobId");
		});
	}

	@Test
	void testFindById() {
		assertThrows(UnsupportedOperationException.class, () -> {
			logJobsController.findById("logJobId", job -> job);
		});
	}

	@Test
	void testFindLogReport() {
		assertThrows(UnsupportedOperationException.class, () -> {
			logJobsController.findLogReport("logJobId", "logReportId", report -> report);
		});
	}

	@Test
	void testCreate() {
		CreateLoggingJobRequest request = new CreateLoggingJobRequest();
		assertThrows(UnsupportedOperationException.class, () -> {
			logJobsController.create(request, job -> job);
		});
	}
}
