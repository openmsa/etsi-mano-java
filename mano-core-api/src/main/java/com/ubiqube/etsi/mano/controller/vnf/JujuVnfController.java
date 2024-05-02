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
package com.ubiqube.etsi.mano.controller.vnf;

import java.util.HashMap;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.ubiqube.etsi.mano.service.JujuCloudService;
import com.ubiqube.etsi.mano.service.event.ActionType;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.juju.cli.JujuRemoteService;
import com.ubiqube.etsi.mano.service.juju.entities.JujuCloud;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/juju")
public class JujuVnfController {

	private static final Logger LOG = LoggerFactory.getLogger(JujuVnfController.class);

	private final JujuRemoteService remoteService;
	private final EventManager eventManager;
	private final JujuCloudService jujuCloudService;

	public JujuVnfController(final JujuRemoteService remoteService, final EventManager eventManager,
			final JujuCloudService jujuCloudService) {
		this.remoteService = remoteService;
		this.eventManager = eventManager;
		this.jujuCloudService = jujuCloudService;
	}

	@PostMapping(value = "/instantiate")
	public ResponseEntity<String> instantiate(@RequestBody @NotNull final JujuCloud jCloud) {
		LOG.info("Juju instantiating...");
		jujuCloudService.saveCloud(jCloud);
		eventManager.sendActionVnfm(ActionType.VNF_JUJU_INSTANTIATE, jCloud.getId(), new HashMap<>());
		return new ResponseEntity<>(
				"Accepted The request was accepted for processing, but the  processing has not been completed.",
				HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/checkstatus/{cloudname}/{credname}/{controllername}/{modelname}/{name}")
	public ResponseEntity<String> checkstatus(@PathVariable("cloudname") @NotNull final String cloudname,
			@PathVariable("credname") @NotNull final String credname,
			@PathVariable("controllername") @NotNull final String controllername,
			@PathVariable("modelname") @NotNull final String modelname,
			@PathVariable("name") @NotNull final String name) {

		LOG.info("Juju checking status...");
		ResponseEntity<String> responseobject = remoteService.cloudDetail(cloudname);
		if ((responseobject.getBody() != null) && !(responseobject.getBody().contains("ERROR"))) {
			responseobject = remoteService.credentialDetails(cloudname, credname);
			if ((responseobject.getBody() != null) && !(responseobject.getBody().contains("ERROR"))) {
				responseobject = remoteService.controllerDetail(controllername);
				if ((responseobject.getBody() != null) && !(responseobject.getBody().contains("ERROR"))) {
					responseobject = remoteService.modelDetail(modelname);
					if ((responseobject.getBody() != null) && !(responseobject.getBody().contains("ERROR"))) {
						responseobject = remoteService.application(name);
						if ((responseobject.getBody() != null) && !(responseobject.getBody().contains("ERROR"))) {
							return ResponseEntity.ok("Cloud : " + cloudname + "\n Credentail : " + credname
									+ "\n Controller : " + controllername + "\n Model : " + modelname
									+ "\n Application : " + name + "      are Successfully added");
						}
					}
				}
			}
		}
		return new ResponseEntity<>(responseobject.getBody(), HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "/terminate/{controllername}")
	public ResponseEntity<String> terminate(@PathVariable("controllername") @NotNull final String controllername) {
		final ResponseEntity<String> responseobject = remoteService.controllerDetail(controllername);
		if ((responseobject.getBody() != null) && !(responseobject.getBody().contains("ERROR"))) {
			LOG.info("Juju terminating...");
			eventManager.sendActionVnfm(ActionType.VNF_JUJU_TERMINATE, UUID.randomUUID(), new HashMap<>());
			// eventManager.sendAction(ActionType.VNF_JUJU_TERMINATE,
			// jClouds.get(0).getId());
			return new ResponseEntity<>(
					"Accepted The request was accepted for processing, but the  processing has not been completed.",
					HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(responseobject.getBody(), HttpStatus.NOT_FOUND);
	}

	
	@GetMapping("/cloud")
	public ResponseEntity<String> clouds() {
		return remoteService.clouds();
	}

	@GetMapping("/cloud/{cloudname}")
	public ResponseEntity<String> cloudDetail(@PathVariable("cloudname") final String cloudname) {
		return remoteService.cloudDetail(cloudname);
	}

	@GetMapping("/status")
	ResponseEntity<String> status() {
		return remoteService.status();
	}

	@GetMapping("/k8sconfig/{vnfId}")
	public ResponseEntity<String> k8sconfig(@PathVariable("vnfId") final String vnfId) {
		return remoteService.getKubeConfig(vnfId);
	}

	@GetMapping("/credential")
	ResponseEntity<String> credentials() {
		return remoteService.credentials();
	}

	@GetMapping("/credential/{cloudname}/{credname}")
	ResponseEntity<String> credentialDetails(@PathVariable("cloudname") final String cloudname, @PathVariable("credname") final String credname) {
		return remoteService.credentialDetails(cloudname,credname);
	}

	@GetMapping("/controller")
	ResponseEntity<String> controllers(){
		return remoteService.controllers();
	}

	@GetMapping("/controller/{controllername}")
	ResponseEntity<String> controllerDetail(@PathVariable("controllername") final String controllername) {
		return remoteService.controllerDetail(controllername);
	}

	@GetMapping("/model")
	ResponseEntity<String> model() {
		return remoteService.model();
	}

	@GetMapping("/model/{name}")
	ResponseEntity<String> modelDetail(@PathVariable("name") final String name) {
		return remoteService.modelDetail(name);
	}

	@GetMapping("/application/{name}")
	ResponseEntity<String> application(@PathVariable("name") final String name) {
		return remoteService.application(name);
	}

	@GetMapping("/helmlist")
	ResponseEntity<String> helmList() {
		return remoteService.helmList();
	}

	@GetMapping("/isk8sready")
	ResponseEntity<Boolean> isK8sReady() {
		return remoteService.isK8sReady();
	}

}