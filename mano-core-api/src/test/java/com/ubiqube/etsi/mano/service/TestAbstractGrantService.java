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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.service;

import com.ubiqube.etsi.mano.dao.mano.Instance;
import com.ubiqube.etsi.mano.dao.mano.VimTask;
import com.ubiqube.etsi.mano.dao.mano.v2.Blueprint;
import com.ubiqube.etsi.mano.jpa.ConnectionInformationJpa;
import com.ubiqube.etsi.mano.service.vim.VimManager;

import ma.glasnost.orika.MapperFacade;

public class TestAbstractGrantService extends AbstractGrantService {

	protected TestAbstractGrantService(final MapperFacade mapper, final ResourceAllocate nfvo, final VimManager vimManager, final ConnectionInformationJpa connectionJpa) {
		super(mapper, nfvo, vimManager, connectionJpa);
	}

	@Override
	protected void check(final Blueprint<? extends VimTask, ? extends Instance> plan) {
		//
	}

}
