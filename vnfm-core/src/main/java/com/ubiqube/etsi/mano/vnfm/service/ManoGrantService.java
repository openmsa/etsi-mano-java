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
package com.ubiqube.etsi.mano.vnfm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ExtManagedVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.Instance;
import com.ubiqube.etsi.mano.dao.mano.VimTask;
import com.ubiqube.etsi.mano.dao.mano.v2.Blueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfPortTask;
import com.ubiqube.etsi.mano.jpa.ConnectionInformationJpa;
import com.ubiqube.etsi.mano.service.AbstractGrantService;
import com.ubiqube.etsi.mano.service.NfvoService;
import com.ubiqube.etsi.mano.service.SystemService;
import com.ubiqube.etsi.mano.service.mapping.BlueZoneGroupInformationMapping;
import com.ubiqube.etsi.mano.service.mapping.GrantInformationExtMapping;
import com.ubiqube.etsi.mano.service.mapping.GrantMapper;
import com.ubiqube.etsi.mano.service.vim.VimManager;

import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
// Not VNFM
@Service
@Transactional(TxType.NEVER)
@ConditionalOnMissingBean(value = NfvoService.class)
public class ManoGrantService extends AbstractGrantService {

	private static final Logger LOG = LoggerFactory.getLogger(ManoGrantService.class);

	public ManoGrantService(final VnfResourceAllocate nfvo, final VimManager vimManager, final ConnectionInformationJpa connectionJpa, final BlueZoneGroupInformationMapping blueZoneGroupInformationMapping, final GrantMapper vnfGrantMapper, final GrantInformationExtMapping grantInformationExtMapping,
			final SystemService systemService) {
		super(nfvo, vimManager, connectionJpa, blueZoneGroupInformationMapping, vnfGrantMapper, grantInformationExtMapping,
				systemService);
	}

	@Override
	protected void check(final Blueprint<? extends VimTask, ? extends Instance> plan) {
		plan.getTasks().stream()
				.filter(VnfPortTask.class::isInstance)
				.map(VnfPortTask.class::cast)
				.forEach(x -> {
                    final String vl = x.getVnfLinkPort().getVirtualLink();
					final ExtManagedVirtualLinkDataEntity fVl = findVl((VnfBlueprint) plan, vl);
					if (null == fVl) {
						return;
					}
					LOG.info("Assigning VL {}", fVl.getResourceId());
					x.setExternal(fVl);
				});

	}

	private static ExtManagedVirtualLinkDataEntity findVl(final VnfBlueprint plan, final String vl) {
		return plan.getParameters().getExtManagedVirtualLinks().stream().filter(x -> x.getVnfVirtualLinkDescId().equals(vl)).findFirst().orElse(null);
	}
}
