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
package com.ubiqube.etsi.mano.dao.mano.v2;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

import com.ubiqube.etsi.mano.dao.mano.VnfExtCp;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Getter
@Setter
@Entity
public class ExternalCpTask extends VnfTask {

	/** Serial. */
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	private VnfExtCp vnfExtCp;

	private Boolean port;

	/**
	 * The vim resource ID if concerning a pre ExtCp.
	 */
	private String preExtCpResourceId;

	@Override
	public VnfTask copy() {
		final ExternalCpTask t = new ExternalCpTask();
		super.copy(t);
		t.setVnfExtCp(vnfExtCp);
		t.setPort(port);
		t.setPreExtCpResourceId(preExtCpResourceId);
		return t;
	}

}
