/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
package com.ubiqube.etsi.mano.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.model.ApiVersionInformation;
import com.ubiqube.etsi.mano.model.ApiVersionInformationApiVersions;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Duplicate of VnfmVersion.
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@RestController
@RequestMapping(value = { "/sol002", "/sol003", "/sol005" })
@Lazy
public class NfvoApiVersion {

	private static final Logger LOG = LoggerFactory.getLogger(NfvoApiVersion.class);

	private final MultiValueMap<String, String> dedupe = new LinkedMultiValueMap<>();

	private final ApplicationContext applicationContext;
	private static final Set<String> FRAGMENTS = new HashSet<>(Arrays.asList("/vrqan/", "/vnfpkgm/", "/grant/", "/vnfpm/", "/vnflcm/", "/vnfind/", "/vnffm/", "/vrgan/", "/nsd/", "/nsfm/", "/nslcm/", "/nspm/", "/vnfpkgm/", "/vnfconfig/", "/vnfsnapshotpkgm/", "/nsiun/"));

	public NfvoApiVersion(final ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		extractVersions();
	}

	private void extractVersions() {
		final String[] beans = applicationContext.getBeanNamesForAnnotation(Controller.class);
		final List<String> list = Arrays.asList(beans);
		final Map<String, Endpoint> res = new HashMap<>();
		list.stream().forEach(x -> {
			LOG.debug("Reading: {}", x);
			if (isSkippable(x)) {
				return;
			}
			final Object obj = applicationContext.getBean(x);
			final RequestMapping req = AnnotationUtils.findAnnotation(obj.getClass(), RequestMapping.class);
			if (haveUsableRequest(req)) {
				final List<String> version = getVersion(req.headers());
				final String part = findMatch(req.value()[0]);
				if (null == part) {
					LOG.warn("Ignoring controller: {}", x);
				} else {
					version.forEach(y -> res.put(part + y, new Endpoint(part, y)));
				}
			}
		});
		res.entrySet().forEach(x -> dedupe.add(x.getValue().part, x.getValue().version));
	}

	private static boolean haveUsableRequest(final RequestMapping req) {
		return (null != req) && (req.headers() != null) && (req.value().length > 0);
	}

	private static boolean isSkippable(final String versionName) {
		return "nfvoApiVersion".equals(versionName) || "vnfmApiVersion".equals(versionName) || "swaggerWelcome".equals(versionName);
	}

	private static String findMatch(final String url) {
		for (final String string : FRAGMENTS) {
			if (url.contains(string)) {
				return getFragment(url, string);
			}
		}
		return null;
	}

	private static String getFragment(final String url, final String string) {
		final String regexp = string.replace("/", "\\/");
		final Pattern p = Pattern.compile("(" + regexp + "v[0-9])");
		final Matcher m = p.matcher(url);
		if (m.find()) {
			return m.group(0);
		}
		return string;
	}

	private static List<String> getVersion(final String[] headers) {
		final List<String> res = new ArrayList<>();
		if (headers.length == 0) {
			return res;
		}
		for (final String string : headers) {
			if (string.startsWith("Version=")) {
				res.add(string.substring("version=".length()));
			}
		}
		return res;
	}

	class Endpoint {
		private final String part;
		private final String version;

		public Endpoint(final String _part, final String _version) {
			part = _part;
			version = _version;
		}

		@Override
		public String toString() {
			return "Endpoint [part=" + part + ", version=" + version + "]";
		}

	}

	@GetMapping(value = "/{module}/v{v:\\d+}/api_versions", produces = { "application/json" }, consumes = { "application/json" })
	public ResponseEntity<ApiVersionInformation> apiMajorVersionsV1Get(@PathVariable("module") final String module, final HttpServletRequest request, @PathVariable("v") final Integer v) {
		return handleQuery(module, request.getRequestURI(), v);
	}

	@GetMapping(value = "/{module}/api_versions", produces = { "application/json" }, consumes = { "application/json" })
	public ResponseEntity<ApiVersionInformation> apiMajorVersionsGet(@PathVariable("module") final String module, final HttpServletRequest request) {
		return handleQuery(module, request.getRequestURI(), null);
	}

	private ResponseEntity<ApiVersionInformation> handleQuery(final String module, final String url, final Integer v) {
		final ApiVersionInformation apiVersion = new ApiVersionInformation();
		final String frag = getFragment(url, module);
		String key = null;
		if (module.equals(frag)) {
			final StringBuilder filter = new StringBuilder("/" + module + "/");
			if (v != null) {
				filter.append("v").append(v);
			}
			final Optional<Entry<String, List<String>>> optApi = dedupe.entrySet().stream().filter(x -> x.getKey().startsWith(filter.toString())).findFirst();
			optApi.ifPresent(x -> apiVersion.setUriPrefix(x.getKey()));
			key = apiVersion.getUriPrefix();
		} else {
			apiVersion.setUriPrefix(frag);
			key = frag;
		}

		final List<String> versions = dedupe.get(key);
		if (null == versions) {
			return ResponseEntity.noContent().build();
		}
		final List<ApiVersionInformationApiVersions> list = versions.stream().map(x -> new ApiVersionInformationApiVersions().version(x).isDeprecated(Boolean.FALSE)).toList();
		apiVersion.setApiVersions(list);
		return ResponseEntity.ok(apiVersion);
	}
}
