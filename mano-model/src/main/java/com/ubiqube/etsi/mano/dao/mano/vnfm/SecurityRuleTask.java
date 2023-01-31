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
package com.ubiqube.etsi.mano.dao.mano.vnfm;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

import com.ubiqube.etsi.mano.dao.mano.SecurityGroup;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfTask;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Entity
@Getter
@Setter
public class SecurityRuleTask extends VnfTask {

	/** Serial. */
	private static final long serialVersionUID = 1L;

	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private SecurityGroup securityGroupRule;

	private String parentToscaName;

	public SecurityRuleTask() {
		//
	}

	public SecurityRuleTask(final String toscaName, final SecurityGroup securityGroup, final String parentToscaName) {
		setToscaName(toscaName);
		this.securityGroupRule = securityGroup;
		this.parentToscaName = parentToscaName;
	}

	@Override
	public VnfTask copy() {
		final SecurityRuleTask t = new SecurityRuleTask();
		super.copy(t);
		t.setSecurityGroupRule(securityGroupRule);
		t.setParentToscaName(parentToscaName);
		return t;
	}

}
