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
package com.ubiqube.etsi.mano.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.UUID;

import org.jgrapht.ListenableGraph;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.orchestrator.Edge2d;
import com.ubiqube.etsi.mano.orchestrator.Vertex2d;
import com.ubiqube.etsi.mano.repository.ByteArrayResource;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.service.VnfPlanService;
import com.ubiqube.etsi.mano.service.graph.GraphGenerator;
import com.ubiqube.etsi.mano.service.pkg.PackageDescriptor;
import com.ubiqube.etsi.mano.service.pkg.vnf.VnfPackageManager;
import com.ubiqube.etsi.mano.service.pkg.vnf.VnfPackageOnboardingImpl;
import com.ubiqube.etsi.mano.service.pkg.vnf.VnfPackageReader;
import com.ubiqube.etsi.mano.utils.TemporaryFileSentry;
import com.ubiqube.etsi.mano.utils.Version;
import com.ubiqube.parser.tosca.ParseException;

/**
 *
 * @author ncuser
 *
 */
@RestController
@RequestMapping("/v3")
public class V3Controller {
	private final VnfPackageManager packageManager;
	private final VnfPackageOnboardingImpl vnfPackageOnboardingImpl;
	private final VnfPlanService vnfPlanService;
	private static final String JAR_PATH = "/tosca-class-%s-2.0.0-SNAPSHOT.jar";

	public V3Controller(final VnfPackageManager packageManager, final VnfPackageOnboardingImpl vnfPackageOnboardingImpl, final VnfPlanService vnfPlanService) {
		this.packageManager = packageManager;
		this.vnfPackageOnboardingImpl = vnfPackageOnboardingImpl;
		this.vnfPlanService = vnfPlanService;
	}

	@PostMapping(value = "/validate/vnf", consumes = { "multipart/form-data" })
	public ResponseEntity<BufferedImage> validateVnf(@RequestParam("file") final MultipartFile file) {

		try (TemporaryFileSentry tfs = new TemporaryFileSentry()) {
			final Path p = tfs.get();
			final ManoResource data = new ByteArrayResource(file.getBytes(), p.toFile().getName());
			final PackageDescriptor<VnfPackageReader> packageProvider = packageManager.getProviderFor(data);
			final VnfPackage vnfPackage = new VnfPackage();
			vnfPackage.setId(UUID.randomUUID());
			vnfPackageOnboardingImpl.mapVnfPackage(vnfPackage, data, packageProvider);
		} catch (final IOException e) {
			throw new GenericException(e);
		}
		return ResponseEntity.accepted().build();
	}

	@GetMapping("/plan/vnf/2d/{id}")
	public ResponseEntity<BufferedImage> getVnf2dPlan(@PathVariable("id") final UUID id) {
		final ListenableGraph<Vertex2d, Edge2d> g = vnfPlanService.getPlanFor(id);
		return ResponseEntity
				.ok().contentType(MediaType.IMAGE_PNG)
				.body(GraphGenerator.drawGraph2(g));
	}

	@GetMapping("/json-schema/{type:vnfd|nsd|pnfd}/{version}")
	public ResponseEntity<String> getSchemaVersion(@PathVariable final String type, @PathVariable final Version version) {
		String ret = "{}";
		final String jarPath = String.format(JAR_PATH, toJarVersions(version));
		final URL cls = this.getClass().getResource(jarPath);
		if (null == cls) {
			throw new ParseException("Unable to find " + jarPath);
		}
		final URLClassLoader urlLoader = URLClassLoader.newInstance(new URL[] { cls }, this.getClass().getClassLoader());
		Thread.currentThread().setContextClassLoader(urlLoader);
		final String filename = "%s/schema-%s.json".formatted(version.toString(), type);
		try (InputStream is = urlLoader.getResourceAsStream(filename);
				ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			is.transferTo(baos);
			baos.flush();
			ret = baos.toString();
		} catch (final IOException e) {
			throw new GenericException(e);
		}
		return ResponseEntity.ok(ret);
	}

	private static String toJarVersions(final Version v) {
		return v.toString().replace(".", "");
	}
}
