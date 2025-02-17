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
package com.ubiqube.etsi.mano.dao.mano.pm;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.service.rest.model.AuthentificationInformations;
import com.ubiqube.etsi.mano.utils.ToStringIgnore;
import com.ubiqube.etsi.mano.utils.ToStringUtil;
import com.ubiqube.etsi.mano.utils.UriConverter;

import org.jspecify.annotations.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@NoArgsConstructor
@AllArgsConstructor
@Indexed
public class PmJob implements Serializable {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@Id
	@DocumentId
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	/**
	 * Type of the measured object. The applicable measured object type for a
	 * measurement is defined in clause 7.2 of ETSI GS NFV-IFA 027 [5].
	 */
	@Enumerated(EnumType.STRING)
	private PmType objectType;

	/**
	 * Identifiers of the measured object instances for which performance
	 * information is collected.
	 */
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> objectInstanceIds;

	/**
	 * Identifiers of the sub-object instances of the measured object instance for
	 * which performance information is requested to be collected. May be present if
	 * a sub-object is defined in clause 6.2 of ETSI GS NFV-IFA 027 [5] for the
	 * related measured object type If this attribute is present, the cardinality of
	 * the "objectInstanceIds" attribute shall be 1. If this attribute is absent and
	 * a sub-object is defined in clause 6.2 of ETSI GS NFV-IFA 027 [5] for the
	 * related measured object type, measurements will be taken for all sub-object
	 * instances of the measured object instance.
	 */
	@ToStringIgnore
	@ElementCollection // (fetch = FetchType.EAGER)
	private List<String> subObjectInstanceIds;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<ResolvedObjectId> resolvedSubObjectInstanceIds;
	/**
	 * Criteria of the collection of performance information.
	 */
	@Embedded
	private PmJobCriteria criteria;

	/**
	 * The URI of the endpoint to send the notification to.
	 */
	@Convert(converter = UriConverter.class)
	@FullTextField
	private URI callbackUri;

	/**
	 * Information about available reports collected by this PM job.
	 */
	@ToStringIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<PmReport> reports;

	@ToStringIgnore
	@Embedded
	@Nullable
	private AuthentificationInformations authentication;

	@ToStringIgnore
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	private VimConnectionInformation vimConnectionInformation;

	private UUID subscriptionRemoteId;

	@OneToMany(cascade = CascadeType.ALL)
	private List<RemoteMetric> remoteMonitoring = new ArrayList<>();

	@Override
	public String toString() {
		return ToStringUtil.toString(this);
	}

	public void addRemoteMonitoring(final RemoteMetric rm) {
		if (null == remoteMonitoring) {
			remoteMonitoring = new ArrayList<>();
		}
		remoteMonitoring.add(rm);
	}
}
