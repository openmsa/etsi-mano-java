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
package com.ubiqube.etsi.mano.nfvo.service.graph.nfvo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfTask;
import com.ubiqube.etsi.mano.orchestrator.Context;
import com.ubiqube.etsi.mano.orchestrator.NamedDependency;
import com.ubiqube.etsi.mano.orchestrator.NamedDependency2d;
import com.ubiqube.etsi.mano.orchestrator.nodes.nfvo.VnfCreateNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTask;
import com.ubiqube.etsi.mano.service.VnfmInterface;
import com.ubiqube.etsi.mano.service.graph.AbstractUnitOfWork;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class VnfCreateUow extends AbstractUnitOfWork<NsVnfTask> {

	private static final Logger LOG = LoggerFactory.getLogger(VnfCreateUow.class);

	private final VnfmInterface vnfm;

	private final NsVnfTask task;

	public VnfCreateUow(final VirtualTask<NsVnfTask> task, final VnfmInterface vnfm) {
		super(task, VnfCreateNode.class);
		this.vnfm = vnfm;
		this.task = task.getParameters();
	}

	@Override
	public String execute(final Context context) {
		final Servers server = task.getServer();
		final VnfInstance vnfmVnfInstance = vnfm.createVnfInstance(server, task.getVnfdId(), task.getDescription(), task.getToscaName());
		return vnfmVnfInstance.getId().toString();
	}

	@Override
	public String rollback(final Context context) {
		if (null != task.getVimResourceId()) {
			try {
				vnfm.delete(task.getServer(), task.getVimResourceId());
			} catch (final RuntimeException e) {
				LOG.trace("", e);
				LOG.warn("Could not delete instance {}", task.getVimResourceId());
			}
		}
		return null;
	}

	@Override
	public List<NamedDependency> getNameDependencies() {
		return List.of();
	}

	@Override
	public List<NamedDependency> getNamedProduced() {
		return List.of(new NamedDependency(getNode(), task.getAlias()));
	}

	@Override
	public List<NamedDependency2d> get2dDependencies() {
		return List.of();
	}

}
