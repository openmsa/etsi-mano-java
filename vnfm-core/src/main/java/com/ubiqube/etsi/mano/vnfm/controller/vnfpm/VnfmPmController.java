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
package com.ubiqube.etsi.mano.vnfm.controller.vnfpm;

import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.dao.mano.pm.PerformanceReport;
import com.ubiqube.etsi.mano.dao.mano.pm.PmJob;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
public interface VnfmPmController {

	void delete(UUID fromString);

	PmJob findById(UUID fromString);

	PerformanceReport findReport(UUID fromString, UUID fromString2);

	PmJob save(PmJob res);

	<U> ResponseEntity<String> search(final MultiValueMap<String, String> requestParams, final Function<PmJob, U> mapper, final String excludeDefaults, final Set<String> mandatoryFields, final Consumer<U> makeLink, Class<U> frontClass);

}
