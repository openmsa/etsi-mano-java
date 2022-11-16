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
package com.ubiqube.etsi.mano.vnfm.service;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

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
import com.ubiqube.etsi.mano.service.vim.VimManager;

import ma.glasnost.orika.MapperFacade;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
// Not VNFM
@Service
@Transactional(TxType.NEVER)
@ConditionalOnMissingBean(value = NfvoService.class)
public class ManoGrantService extends AbstractGrantService {

	private static final Logger LOG = LoggerFactory.getLogger(ManoGrantService.class);

	public ManoGrantService(final MapperFacade mapper, final VnfResourceAllocate nfvo, final VimManager vimManager, final ConnectionInformationJpa connectionJpa) {
		super(mapper, nfvo, vimManager, connectionJpa);
	}

	@Override
	protected void check(final Blueprint<? extends VimTask, ? extends Instance> plan) {
		plan.getTasks().stream()
				.filter(VnfPortTask.class::isInstance)
				.map(VnfPortTask.class::cast)
				.forEach(x -> {
					final VnfPortTask t = x;
					final String vl = t.getVnfLinkPort().getVirtualLink();
					final ExtManagedVirtualLinkDataEntity fVl = findVl((VnfBlueprint) plan, vl);
					if (null == fVl) {
						return;
					}
					LOG.info("Assigning VL {}", fVl);
					t.setExternal(fVl);
				});

	}

	private static ExtManagedVirtualLinkDataEntity findVl(final VnfBlueprint plan, final String vl) {
		return plan.getParameters().getExtManagedVirtualLinks().stream().filter(x -> x.getVnfVirtualLinkDescId().equals(vl)).findFirst().orElse(null);
	}
}
