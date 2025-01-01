package com.ubiqube.etsi.mano.service;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ubiqube.etsi.mano.dao.subscription.SubscriptionType;
import com.ubiqube.etsi.mano.utils.Version;

import jakarta.annotation.Nullable;

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
