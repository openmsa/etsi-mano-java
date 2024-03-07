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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.ubiqube.etsi.mano.Constants;
import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainer;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.OsContainerTask;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.CnfInformations;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.JujuInformations;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.orchestrator.Context3d;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.JujuCloudService;
import com.ubiqube.etsi.mano.service.juju.entities.JujuCloud;
import com.ubiqube.etsi.mano.service.juju.entities.JujuCredential;
import com.ubiqube.etsi.mano.service.juju.entities.JujuMetadata;
import com.ubiqube.etsi.mano.service.juju.entities.JujuModel;
import com.ubiqube.etsi.mano.service.juju.entities.JujuRegion;
import com.ubiqube.etsi.mano.service.vim.CnfK8sParams;
import com.ubiqube.etsi.mano.service.vim.Vim;

import jakarta.annotation.Nullable;

public class OsContainerUow extends AbstractVnfmUow<OsContainerTask> {
	private final Vim vim;
	private final VimConnectionInformation vimConnectionInformation;
	private final OsContainerTask task;
	private final JujuCloudService jujuCloudService;
	private final VnfPackageRepository vnfRepo;

	public OsContainerUow(final VirtualTaskV3<OsContainerTask> task, final Vim vim, final VimConnectionInformation vimConnectionInformation,
			final JujuCloudService jujuCloudService, final VnfPackageRepository vnfRepo) {
		super(task, OsContainerNode.class);
		this.vim = vim;
		this.vimConnectionInformation = vimConnectionInformation;
		this.task = task.getTemplateParameters();
		this.jujuCloudService = jujuCloudService;
		this.vnfRepo = vnfRepo;
	}

	@Override
	public @Nullable String execute(final Context3d context) {
		final CnfInformations cnfi = vimConnectionInformation.getCnfInfo();
		final JujuInformations jujui = vimConnectionInformation.getJujuInfo();
		final OsContainer osc = task.getOsContainer();
		osc.getCpuResourceLimit();
		osc.getEphemeralStorageResourceLimit();
		osc.getExtendedResourceRequests();
		osc.getHugePagesResources();
		osc.getMemoryResourceLimit();
		osc.getRequestedCpuResources();
		osc.getRequestedEphemeralStorageResources();
		osc.getRequestedMemoryResources();
		if (cnfi != null) {
			final CnfK8sParams params = CnfK8sParams.builder()
					.clusterTemplate(cnfi.getClusterTemplate())
					.dnsServer(cnfi.getDnsServer())
					.externalNetworkId("")
					.keypair(cnfi.getKeyPair())
					.masterFlavor(cnfi.getMasterFlavorId())
					.name(task.getAlias())
					.networkDriver("flannel")
					.serverType("vm")
					.volumeSize(10)
					.build();
			vim.cnf(vimConnectionInformation).createContainer(params);
		}
		if (jujui != null) {
			String type = vimConnectionInformation.getVimType();
			String nameofCloud = vimConnectionInformation.getVimId();
			Map<String, String> accessInfo = vimConnectionInformation.getAccessInfo();
			String password = accessInfo.get("password");
			String credName = "admin-" + accessInfo.get("projectId");
			String username = accessInfo.get("username");
			Map<String, String> interfaceInfo = vimConnectionInformation.getInterfaceInfo();
			String endpoint = interfaceInfo.get("endpoint");
			String controllerName = vimConnectionInformation.getVimId() + "-" + vimConnectionInformation.getJujuInfo().getRegion().toLowerCase();
			String imageId = vimConnectionInformation.getJujuInfo().getImageId();
			String constraints = vimConnectionInformation.getJujuInfo().getConstraints();
			String region = vimConnectionInformation.getJujuInfo().getRegion();
			String networkId = vimConnectionInformation.getJujuInfo().getNetworkId();
			String charmName = vimConnectionInformation.getJujuInfo().getCharmName();
			String appName = vimConnectionInformation.getJujuInfo().getAppName();
			String osSeries = vimConnectionInformation.getJujuInfo().getOsSeries();
			String charmAppName = osc.getDescription();
			if (charmAppName.indexOf('/')>= 1) {
				String[] charmAppValues = charmAppName.split("/");
				charmName = charmAppValues[0];
				appName = charmAppValues[1];
			}
			JujuRegion region2 = new JujuRegion(region, endpoint);
			List<JujuRegion> regions = new ArrayList<>();
			regions.add(region2);
			JujuCredential jujuCredential = new JujuCredential(credName, "userpass", username, password, "admin");
			JujuModel model = new JujuModel("k8s-ubi-model-kt", charmName, appName);
			List<JujuModel> models = new ArrayList<>();
			models.add(model);
			List<String> constraints2 = new ArrayList<>();
			constraints2.add(constraints);
			JujuMetadata metadata = new JujuMetadata(controllerName, imageId, "~/simplestreams", osSeries, endpoint, constraints2, networkId, region, models);
			JujuCloud jCloud = new JujuCloud(nameofCloud, type, "userpass", regions, jujuCredential, metadata);
			jujuCloudService.saveCloud(jCloud);
			boolean isSuccess = jujuCloudService.jujuInstantiate(jCloud.getId());
			try {
				String helmName = task.getOsContainer().getArtifacts().entrySet().iterator().next().getValue().getName();
				final File tmpFile = copyFile(task.getOsContainer().getArtifacts().entrySet().iterator().next().getValue().getImagePath(), task.getBlueprint().getInstance().getVnfPkg().getId());
			    if(tmpFile != null && tmpFile.length() > 0) {
				jujuCloudService.installHelm(helmName, tmpFile);
			    }
			    else {
			    	throw new GenericException("File is Null or Empty ");
			    }
			} catch (URISyntaxException se) {
				return "FAIL";
			}
			return isSuccess ? "SUCCESS" : "FAIL";
		}
		return null;
	}

	@Override
	public @Nullable String rollback(final Context3d context) {
		vim.cnf(vimConnectionInformation).deleteContainer(task.getVimResourceId());
		String helmName = task.getOsContainer().getArtifacts().entrySet().iterator().next().getValue().getName();
		jujuCloudService.uninstallHelm(helmName);
		List<JujuCloud> jClouds = jujuCloudService.findByMetadataName(vimConnectionInformation.getVimId() + "-" + vimConnectionInformation.getJujuInfo().getRegion().toLowerCase(), "PASS");
		if (!jClouds.isEmpty()) {
			boolean isSuccess = jujuCloudService.jujuTerminate(jClouds.get(0).getId());
			return isSuccess ? "SUCCESS" : "FAIL";
		}
		return null;
	}
	
	private File copyFile(final String url, final UUID id) {
		final ManoResource f = vnfRepo.getBinary(id, new File(Constants.REPOSITORY_FOLDER_ARTIFACTS, url).toString());
		final Path tmp = createTempFile();
		try (final FileOutputStream fos = new FileOutputStream(tmp.toFile());
				InputStream is = f.getInputStream()) {
			is.transferTo(fos);
		} catch (final IOException e) {
			throw new GenericException(e);
		}
		return tmp.toFile();
	}

	private static Path createTempFile() {
		try {
			return Files.createTempFile("", "tgz");
		} catch (final IOException e) {
			throw new GenericException(e);
		}
	}

}