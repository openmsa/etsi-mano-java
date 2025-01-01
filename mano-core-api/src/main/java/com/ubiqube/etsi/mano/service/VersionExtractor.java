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
package com.ubiqube.etsi.mano.service;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ubiqube.etsi.mano.dao.subscription.SubscriptionType;
import com.ubiqube.etsi.mano.utils.Version;

import org.jspecify.annotations.Nullable;

public class VersionExtractor {

	private VersionExtractor() {
		//
	}

	public static @Nullable String extractVersion(final Class<?> version, final SubscriptionType type, final ServerService serverService) {
		final String versionString = extractVersion(version);
		return serverService.convertManoVersionToFe(type, versionString).map(Version::toString).orElse(null);
	}

	public static @Nullable String extractVersion(final Class<?> version) {
		final RequestMapping annotation = AnnotationUtils.findAnnotation(version, RequestMapping.class);
		if (annotation == null) {
			return null;
		}
		final String[] headers = annotation.headers();
		for (final String header : headers) {
			if (header.startsWith("Version=")) {
				return header.substring("Version=".length());
			}
		}
		return null;
	}
}
