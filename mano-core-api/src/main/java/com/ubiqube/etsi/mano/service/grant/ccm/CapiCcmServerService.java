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
package com.ubiqube.etsi.mano.service.grant.ccm;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.cnf.capi.CapiServer;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.CnfInformations;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.service.CapiServerService;
import com.ubiqube.etsi.mano.service.grant.ccm.cni.CniInstaller;
import com.ubiqube.etsi.mano.vim.k8s.K8s;
import com.ubiqube.etsi.mano.vim.k8s.OsClusterService;
import com.ubiqube.etsi.mano.vim.k8s.model.K8sParams;
import com.ubiqube.etsi.mano.vim.k8s.model.OsMachineParams;
import com.ubiqube.etsi.mano.vim.k8s.model.OsParams;
import com.ubiqube.etsi.mano.vnfm.service.plan.contributors.uow.capi.CapiServerMapping;

@Service
public class CapiCcmServerService implements CcmServerService {
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(CapiCcmServerService.class);

	private final CapiServerService capiServerService;
	private final OsClusterService osClusterService;
	private final CapiServerMapping mapper;
	private final List<CniInstaller> cniInstaller;

	public CapiCcmServerService(final CapiServerService capiServerService, final OsClusterService osClusterService, final CapiServerMapping mapper, final List<CniInstaller> cniInstaller) {
		this.capiServerService = capiServerService;
		this.osClusterService = osClusterService;
		this.mapper = mapper;
		this.cniInstaller = cniInstaller;
	}

	@Override
	public void terminateCluster(final String vnfInstanceId) {
		// Nothing.
	}

	@Override
	public K8s createCluster(final VimConnectionInformation vimConn, final String vnfInstanceId) {
		final Iterable<CapiServer> ite = capiServerService.findAll();
		if (!ite.iterator().hasNext()) {
			throw new GenericException("Unable to find a CAPI connection.");
		}
		final CapiServer capiSrv = ite.iterator().next();
		LOG.info("Using capi cluster: {}", capiSrv.getName());
		final K8s k8s = mapper.map(capiSrv);
		final Optional<K8s> res = osClusterService.getKubeConfig(k8s, "default", vimConn.getVimId());
		if (res.isEmpty()) {
			return deployServer(capiSrv, k8s, "default", vimConn.getVimId(), vimConn);
		}
		return res.get();
	}

	private K8s deployServer(final CapiServer capiSrv, final K8s k8s, final String ns, final String clusterName, final VimConnectionInformation vci) {
		final CnfInformations cnfInfo = capiSrv.getCnfInfo();
		final K8sParams params = K8sParams.builder()
				.clusterName(clusterName)
				.clusterNetworkCidr(List.of("192.168.0.0/16"))
				.k8sVersion(cnfInfo.getK8sVersion())
				.openStackParams(OsParams.builder()
						.cidr("10.6.0.0/24")
						.controlPlane(OsMachineParams.builder()
								.flavor(cnfInfo.getMasterFlavorId())
								.image(cnfInfo.getImages())
								.replica(1)
								.build())
						.dns(List.of(cnfInfo.getDnsServer()))
						.domainZone("nova")
						.extNetId(cnfInfo.getExtNetworkId())
						.sshKey(cnfInfo.getKeyPair())
						.worker(OsMachineParams.builder()
								.flavor(cnfInfo.getWorkerFlavorId())
								.image(cnfInfo.getImages())
								.replica(cnfInfo.getMinNumberInstance())
								.build())
						.build())
				.serviceDomain("cluster.local")
				.build();
		osClusterService.createCluster(vci, k8s, params);
		final Optional<K8s> opt = osClusterService.getKubeConfig(k8s, ns, clusterName);
		if (opt.isPresent()) {
			final K8s cluster = opt.get();
			LOG.info("Deploying default CNI.");
			final List<String> cniDocs = getCniInstallDocuments();
			cniDocs.forEach(x -> osClusterService.apply(cluster, x));
			return cluster;
		}
		throw new GenericException("Unable to find cluster: " + ns + "/" + clusterName);
	}

	private List<String> getCniInstallDocuments() {
		return cniInstaller.stream()
				.filter("calico"::equals)
				.map(x -> x.getK8sDocuments("3.28.0"))
				.findFirst()
				.orElse(List.of());
	}

}
