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
package com.ubiqube.etsi.mano.service.vim;

import java.util.List;
import java.util.Objects;

import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.ClusterMachine;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.CnfInformations;
import com.ubiqube.etsi.mano.service.sys.SysImage;
import com.ubiqube.etsi.mano.vim.dto.Flavor;

public class CnfInformationVerifier {
	private final Vim vim;
	private final VimConnectionInformation vci;

	private CnfInformationVerifier(final Vim vim, final VimConnectionInformation vci) {
		this.vim = vim;
		this.vci = vci;
	}

	public static CnfInformationVerifier of(final Vim vim, final VimConnectionInformation vci) {
		return new CnfInformationVerifier(vim, vci);
	}

	public void verifyCnf() {
		final CnfInformations cnfInfo = vci.getCnfInfo();
		if (null == cnfInfo) {
			return;
		}
		checkNetwork(cnfInfo);
		final List<Flavor> flavors = vim.getFlavorList(vci);
		verifyMachine(cnfInfo.getMaster(), flavors);
		verifyMachine(cnfInfo.getWorker(), flavors);
	}

	private void checkNetwork(final CnfInformations cnfInfo) {
		final String netName = cnfInfo.getExtNetwork();
		final String netId = cnfInfo.getExtNetworkId();
		if ((null == netName) && (null == netId)) {
			throw new VimException("You must have an external network name or ID.");
		}
		final List<NetworkObject> res = findNetworkByNameOrId(netName, netId);
		if (res.size() != 1) {
			throw new VimException("External network must be unique but was " + res.size());
		}
		final NetworkObject no = res.get(0);
		if (netName != null) {
			cnfInfo.setExtNetworkId(no.id());
		} else {
			cnfInfo.setExtNetwork(no.name());
		}
	}

	private List<NetworkObject> findNetworkByNameOrId(final String netName, final String netId) {
		if (netName != null) {
			return vim.network(vci).search(NetowrkSearchField.NAME, List.of(netName));
		}
		return vim.network(vci).search(NetowrkSearchField.ID, List.of(netId));
	}

	private void verifyMachine(final ClusterMachine machine, final List<Flavor> flavors) {
		final String flavor = machine.getFlavor();
		final String flavorId = machine.getFlavorId();
		if (flavor == null && flavorId == null) {
			throw new VimException("You must have a flavor or a flavorId");
		}
		if (flavor != null) {
			machine.setFlavorId(findFlavorByName(flavors, flavor).getId());
		} else {
			machine.setFlavor(findFlavorById(flavors, flavorId).getName());
		}
		if (machine.getMinNumberInstance() < 1) {
			throw new VimException("Number of instance must be at least 1");
		}
		final SysImage res = vim.storage(vci).getImagesInformations(machine.getImage());
		Objects.requireNonNull(res, () -> "Unable to find image: " + machine.getImage());
	}

	private static Flavor findFlavorById(final List<Flavor> flavors, final String flavorId) {
		return flavors.stream()
				.filter(flavor -> flavor.getId().equals(flavorId))
				.findFirst()
				.orElseThrow(() -> new VimException("Unable to find flavor: " + flavorId));
	}

	private static Flavor findFlavorByName(final List<Flavor> flavors, final String flavorName) {
		return flavors.stream()
				.filter(flavor -> flavor.getName().equals(flavorName))
				.findFirst()
				.orElseThrow(() -> new VimException("Unable to find flavor: " + flavorName));
	}
}
