/**
 *     Copyright (C) 2019-2023 Ubiqube.
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

import java.util.List;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.jpa.JujuCloudJpa;
import com.ubiqube.etsi.mano.jpa.JujuCredentialJpa;
import com.ubiqube.etsi.mano.jpa.JujuMetadataJpa;
import com.ubiqube.etsi.mano.service.juju.entities.JujuCloud;

@Service
public class JujuCloudService {

	private final JujuCloudJpa jujuCloudJpa;
	private final JujuCredentialJpa jujuCredentialJpa;
	private final JujuMetadataJpa jujuMetadataJpa;

	public JujuCloudService(final JujuCloudJpa jujuCloudJpa, final JujuCredentialJpa jujuCredentialJpa,
			final JujuMetadataJpa jujuMetadataJpa) {
		this.jujuCloudJpa = jujuCloudJpa;
		this.jujuCredentialJpa = jujuCredentialJpa;
		this.jujuMetadataJpa = jujuMetadataJpa;
	}

	public JujuCloud saveCloud(final JujuCloud jCloud) {
		jujuCredentialJpa.save(jCloud.getCredential());
		jujuMetadataJpa.save(jCloud.getMetadata());
		jCloud.setStatus("PROCESS");
		return jujuCloudJpa.save(jCloud);
	}

	public List<JujuCloud> findByControllerName(String controllername) {
		return jujuCloudJpa.findByControllerName(controllername);
	}
}
