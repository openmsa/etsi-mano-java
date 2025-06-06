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
package com.ubiqube.etsi.mano.nfvo.service.plan.uow;

import com.ubiqube.etsi.mano.dao.mano.ScaleInfo;
import com.ubiqube.etsi.mano.dao.mano.v2.AbstractTask;
import com.ubiqube.etsi.mano.dao.mano.vim.ResourceTypeEnum;

public class TestTask extends AbstractTask {

	private static final long serialVersionUID = 1L;
	private final ResourceTypeEnum Rtype;

	public TestTask(final ResourceTypeEnum type) {
		this.Rtype = type;
	}

	@Override
	public void setVimReservationId(final String reservationId) {
		//
	}

	@Override
	public void setResourceGroupId(final String resourceGroupId) {
		//
	}

	@Override
	public void setZoneId(final String zoneId) {
		//
	}

	@Override
	public ResourceTypeEnum getType() {
		return Rtype;
	}

	@Override
	public ScaleInfo getScaleInfo() {
		return null;
	}

}
