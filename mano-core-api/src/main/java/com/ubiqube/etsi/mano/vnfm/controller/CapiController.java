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
package com.ubiqube.etsi.mano.vnfm.controller;

import java.io.IOException;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ubiqube.etsi.mano.dao.mano.cnf.capi.CapiServer;
import com.ubiqube.etsi.mano.dao.mano.vim.PlanStatusType;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.exception.PreConditionException;
import com.ubiqube.etsi.mano.service.Patcher;
import com.ubiqube.etsi.mano.service.event.ActionType;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.repository.CapiServerRepositoryService;
import com.ubiqube.etsi.mano.vim.k8s.OsClusterService;
import com.ubiqube.etsi.mano.vim.k8s.conn.K8s;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow.capi.CapiServerMapping;

import jakarta.annotation.Nullable;

@RestController
@RequestMapping("/vnfm-admin/capi")
public class CapiController {
	private final CapiServerRepositoryService capiServer;
	private final OsClusterService osClusterService;
	private final CapiServerMapping mapper;
	private final Patcher patcher;
	private final EventManager eventManager;

	public CapiController(final CapiServerRepositoryService capiServerJpa, final OsClusterService osClusterService, final CapiServerMapping mapper, final Patcher patcher, final EventManager eventManager) {
		this.capiServer = capiServerJpa;
		this.osClusterService = osClusterService;
		this.mapper = mapper;
		this.patcher = patcher;
		this.eventManager = eventManager;
	}

	@GetMapping
	ResponseEntity<Iterable<CapiServer>> listCapiConnections() {
		final Iterable<CapiServer> ite = capiServer.findAll();
		return ResponseEntity.ok(ite);
	}

	@PostMapping
	public ResponseEntity<CapiServer> post(@RequestBody final CapiServer srv) {
		final CapiServer res = capiServer.save(srv);
		eventManager.sendAction(ActionType.REGISTER_CAPI, res.getId());
		return ResponseEntity.ok(res);
	}

	@PostMapping("kube-config/{context}")
	public ResponseEntity<CapiServer> postKubeConfig(@PathVariable final String context, @RequestParam final MultipartFile file) throws IOException {
		final K8s srv = osClusterService.fromKubeConfig(context, file.getBytes());
		final CapiServer res = capiServer.save(mapper.map(srv));
		eventManager.sendAction(ActionType.REGISTER_CAPI, res.getId());
		eventManager.sendAction(ActionType.REGISTER_CAPI, res.getId());
		return ResponseEntity.ok(res);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable final UUID id) {
		capiServer.deleteById(id);
	}

	@PatchMapping(value = "/{id}")
	public ResponseEntity<CapiServer> patchVim(@PathVariable final UUID id, @RequestBody final String body,
			@RequestHeader(name = HttpHeaders.IF_MATCH, required = false) @Nullable final String ifMatch) {
		final CapiServer capi = capiServer.findById(id).orElseThrow(() -> new NotFoundException("Unable to find capi serveer: " + id));
		if ((ifMatch != null) && !(capi.getVersion() + "").equals(ifMatch)) {
			throw new PreConditionException(ifMatch + " does not match " + capi.getVersion());
		}
		patcher.patch(body, capi);
		capi.setServerStatus(PlanStatusType.STARTED);
		final CapiServer newCapi = capiServer.save(capi);
		eventManager.sendAction(ActionType.REGISTER_CAPI, newCapi.getId());
		return ResponseEntity.ok(newCapi);
	}

}
