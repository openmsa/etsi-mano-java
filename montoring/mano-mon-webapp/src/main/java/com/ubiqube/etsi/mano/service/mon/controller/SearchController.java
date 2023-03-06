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
package com.ubiqube.etsi.mano.service.mon.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.mon.api.SearchApi;
import com.ubiqube.etsi.mano.mon.dao.TelemetryMetricsResult;

@RestController
@RequestMapping("/search")
@Validated
public class SearchController {

	private final SearchApi searchApi;

	public SearchController(final SearchApi searchApi) {
		this.searchApi = searchApi;
	}

	@GetMapping("/{instance}/{subObject}")
	public ResponseEntity<TelemetryMetricsResult> search(final @PathVariable("instance") String instance, final @PathVariable("subObject") String object) {
		final TelemetryMetricsResult metric = searchApi.search(instance, object);
		return ResponseEntity.ok(metric);
	}
}
