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
package com.ubiqube.etsi.mano.service.mon;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.mon.dao.TelemetryMetricsResult;

public interface ExternalMonitoring {

	UUID createBatch(String resourceId, Set<String> set, Long pollingInterval, VimConnectionInformation vimConnectionInformation);

	void deleteResources(String resourceId);

	List<TelemetryMetricsResult> searchMetric(MultiValueMap<String, String> params);
}
