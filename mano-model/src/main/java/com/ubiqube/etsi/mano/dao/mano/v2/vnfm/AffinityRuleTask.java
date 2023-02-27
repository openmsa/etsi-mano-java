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
package com.ubiqube.etsi.mano.dao.mano.v2.vnfm;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;

import com.ubiqube.etsi.mano.dao.mano.AffinityRule;
import com.ubiqube.etsi.mano.dao.mano.AuditListener;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfTask;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Entity
@EntityListeners(AuditListener.class)
@Getter
@Setter
public class AffinityRuleTask extends VnfTask {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private AffinityRule affinityRule;

	@Override
	public VnfTask copy() {
		final AffinityRuleTask t = new AffinityRuleTask();
		super.copy(t);
		t.setAffinityRule(affinityRule);
		return t;
	}

}
