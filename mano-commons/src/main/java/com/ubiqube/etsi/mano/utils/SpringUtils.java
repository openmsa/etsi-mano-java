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
package com.ubiqube.etsi.mano.utils;

import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerMapping;

import jakarta.servlet.ServletRequest;

public final class SpringUtils {

	private SpringUtils() {
		// Nothing
	}

	/**
	 * Should be removed. Should match {*attr}
	 *
	 * @see https://www.baeldung.com/spring-5-mvc-url-matching
	 * @param request
	 * @return
	 */
	public static String extractParams(final ServletRequest request) {
		final String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		final String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

		return new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
	}

}
