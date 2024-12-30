/**
 *     Copyright (C) 2019-2024 Ubiqube.
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
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ubiqube.etsi.mano.utils.Version;

import jakarta.annotation.Nullable;

@Lazy
@Service
public class EndpointService {
	private static final Logger LOG = LoggerFactory.getLogger(EndpointService.class);
	private static final Pattern URL_PATTERN = Pattern.compile("/.*/(?<part>\\w+)/v\\d");
	private final ApplicationContext applicationContext;
	public static final Set<String> FRAGMENTS = Set.of(
		"/vrqan/", "/vnfpkgm/", "/grant/", "/vnfpm/", "/vnflcm/", "/vnfind/", 
		"/vnffm/", "/vrgan/", "/nsd/", "/nsfm/", "/nslcm/", "/nspm/", 
		"/vnfconfig/", "/vnfsnapshotpkgm/", "/nsiun/"
	);
	private static final MultiValueMap<String, Endpoint> dedupe = new LinkedMultiValueMap<>();

	public EndpointService(final ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		// extractVersions
	}

	public void extractVersions() {
		final String[] beanNames = applicationContext.getBeanNamesForAnnotation(Controller.class);
		final List<String> beanList = Arrays.asList(beanNames);
		final Map<String, Endpoint> endpoints = new HashMap<>();

		beanList.forEach(beanName -> {
			LOG.trace("Reading: {}", beanName);
			if (isSkippable(beanName)) {
				return;
			}

			final Object bean = applicationContext.getBean(beanName);
			final RequestMapping requestMapping = AnnotationUtils.findAnnotation(bean.getClass(), RequestMapping.class);

			if (requestMapping != null && haveUsableRequest(requestMapping)) {
				final String part = extractPart(requestMapping.value()[0]);
				final List<Version> versions = getVersion(requestMapping.headers());

				if (part == null) {
					LOG.warn("Ignoring controller: {}", beanName);
				} else {
					final List<HttpMethod> methods = extractMethods(bean.getClass());
					versions.forEach(version -> endpoints.put(part + version, new Endpoint(part, version, beanName, methods)));
				}
			}
		});

		endpoints.forEach((key, value) -> dedupe.add(value.part, value));
	}

	private List<HttpMethod> extractMethods(final Class<?> clazz) {
		final List<HttpMethod> methods = new ArrayList<>();
		final Method[] classMethods = clazz.getMethods();

		for (final Method method : classMethods) {
			final Set<RequestMapping> requestMappings = AnnotatedElementUtils.findAllMergedAnnotations(method, RequestMapping.class);
			if (!requestMappings.isEmpty()) {
				final RequestMapping requestMapping = requestMappings.iterator().next();
				methods.add(new HttpMethod(requestMapping.method()[0].name(), safeGetArrayValue(requestMapping.value())));
			}
		}

		return methods;
	}

	@Nullable
	private static String safeGetArrayValue(final @Nullable String[] values) {
		if (values == null || values.length == 0) {
			return null;
		}
		return values[0];
	}

	@SuppressWarnings("static-method")
	public MultiValueMap<String, Endpoint> getEndpoints() {
		if (dedupe.isEmpty()) {
			extractVersions();
		}
		return dedupe;
	}

	private static @Nullable String extractPart(final String url) {
		final Matcher matcher = URL_PATTERN.matcher(url);
		if (!matcher.matches()) {
			return null;
		}
		return matcher.group("part");
	}

	private static boolean haveUsableRequest(final @Nullable RequestMapping requestMapping) {
		return requestMapping != null && requestMapping.headers() != null && requestMapping.value().length > 0;
	}

	private static boolean isSkippable(final String beanName) {
		return "nfvoApiVersion".equals(beanName) || "vnfmApiVersion".equals(beanName) || "swaggerWelcome".equals(beanName);
	}

	private static List<Version> getVersion(final String[] headers) {
		final List<Version> versions = new ArrayList<>();
		if (headers.length == 0) {
			return versions;
		}

		for (final String header : headers) {
			if (header.startsWith("Version=")) {
				final String version = header.substring("Version=".length());
				versions.add(new Version(version));
			}
		}

		return versions;
	}

	public record Endpoint(String part, Version version, Object bean, List<HttpMethod> methods) {
		//
	}

	record HttpMethod(String action, @Nullable String path) {
		//
	}
}
