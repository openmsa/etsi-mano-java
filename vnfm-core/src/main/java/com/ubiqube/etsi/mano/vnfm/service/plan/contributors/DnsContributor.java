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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.DnsHostTask;
import com.ubiqube.etsi.mano.dao.mano.v2.DnsZoneTask;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.vim.ResourceTypeEnum;
import com.ubiqube.etsi.mano.orchestrator.SclableResources;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.DnsHost;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.DnsZone;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;

/**
 *
 * @author olivier
 *
 */
@Service
public class DnsContributor extends AbstractVnfmContributor<Object> {

	public DnsContributor(final VnfLiveInstanceJpa vnfLiveInstanceJpa) {
		super(vnfLiveInstanceJpa);
	}

	@Override
	public List<SclableResources<Object>> contribute(final VnfPackage bundle, final VnfBlueprint parameters) {
        final List<SclableResources<Object>> ret = new ArrayList<>();
		bundle.getVnfVl().forEach(x -> {
			final DnsZoneTask dnsZoneTask = createTask(DnsZoneTask::new);
			dnsZoneTask.setToscaName(x.getToscaName());
			dnsZoneTask.setDomainName(x.getToscaName() + ".mano.vm");
			dnsZoneTask.setType(ResourceTypeEnum.DNSZONE);
			ret.add(create(DnsZone.class, dnsZoneTask.getClass(), x.getToscaName(), 1, dnsZoneTask, parameters.getInstance(), parameters));
		});
		bundle.getVnfCompute().forEach(x -> {
			final DnsHostTask dht = createTask(DnsHostTask::new);
			dht.setToscaName(x.getToscaName());
			dht.setHostname(x.getToscaName());
			dht.setZoneId(x.getToscaName() + ".mano.vm");
			dht.setType(ResourceTypeEnum.DNSHOST);
			ret.add(create(DnsHost.class, dht.getClass(), dht.getToscaName(), 1, dht, parameters.getInstance(), parameters));
		});
		return ret;
	}

}
