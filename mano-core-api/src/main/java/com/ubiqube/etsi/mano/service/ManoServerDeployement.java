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

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.exception.GenericException;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class ManoServerDeployement {

	private final ObjectProvider<VnfmService> vnfmService;
	private final ObjectProvider<NfvoService> nfvoService;

	public ManoServerDeployement(final ObjectProvider<VnfmService> vnfmService, final ObjectProvider<NfvoService> nfvoService) {
		super();
		this.vnfmService = vnfmService;
		this.nfvoService = nfvoService;
	}

	public DeployementFlavor getFlavor() {
		if (vnfmService.getIfUnique() != null && nfvoService.getIfUnique() != null) {
			return DeployementFlavor.NFVO_VNFM;
		}
		if (vnfmService.getIfUnique() != null) {
			return DeployementFlavor.VNFM;
		}
		if (nfvoService.getIfUnique() != null) {
			return DeployementFlavor.NFVO;
		}
		throw new GenericException("Unable to find deployement flavor.");
	}

	public enum DeployementFlavor {
		NFVO,
		VNFM,
		NFVO_VNFM
    }
}
