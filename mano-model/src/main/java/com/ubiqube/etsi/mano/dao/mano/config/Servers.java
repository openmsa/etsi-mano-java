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
package com.ubiqube.etsi.mano.dao.mano.config;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;

import com.ubiqube.etsi.mano.dao.mano.Audit;
import com.ubiqube.etsi.mano.dao.mano.AuditListener;
import com.ubiqube.etsi.mano.dao.mano.Auditable;
import com.ubiqube.etsi.mano.dao.mano.common.ApiVersion;
import com.ubiqube.etsi.mano.dao.mano.common.FailureDetails;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanStatusType;
import com.ubiqube.etsi.mano.service.event.model.AuthentificationInformations;
import com.ubiqube.etsi.mano.service.event.model.SubscriptionType;
import com.ubiqube.etsi.mano.utils.ToStringUtil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditListener.class)
public class Servers implements Auditable {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private String name;

	private AuthentificationInformations authentification;

	private String url;

	private boolean ignoreSsl;

	@Column(length = 5000)
	private String tlsCert;

	private String version;

	@Enumerated(EnumType.STRING)
	// XXX only on DB@NotNull
	private SubscriptionType subscriptionType;

	@Enumerated(EnumType.STRING)
	private PlanStatusType serverStatus;

	@Enumerated(EnumType.STRING)
	private ServerType serverType;

	private FailureDetails failureDetails;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> capabilities;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<RemoteSubscription> remoteSubscriptions;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<ApiVersion> versions;

	@Version
	private long tupleVersion;

	private Audit audit = new Audit();

	public void addVersion(final ApiVersion version2) {
		if (null == versions) {
			versions = new HashSet<>();
		}
		versions.add(version2);
	}

	@Override
	public String toString() {
		return ToStringUtil.toString(this);
	}
}
