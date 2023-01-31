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
package com.ubiqube.etsi.mano.dao.mano.pm;

import java.net.URI;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import com.ubiqube.etsi.mano.service.event.model.AuthentificationInformations;
import com.ubiqube.etsi.mano.utils.ToStringUtil;
import com.ubiqube.etsi.mano.utils.UriConverter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Indexed
@Entity
public class Threshold {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	/**
	 * Type of the measured object. The applicable measured object type for a
	 * measurement is defined in clause 7.2 of ETSI GS NFV-IFA 027 [5].
	 */
	@FullTextField
	private String objectType;

	/**
	 * Identifier of the measured object (i.e. VNF instance) associated with the
	 * threshold.
	 */
	@FullTextField
	private UUID objectInstanceId;

	/**
	 * Identifiers of the sub-object instances of the measured object instance
	 * associated with the threshold. May be present if a sub-object is defined in
	 * clause 6.2 of ETSI GS NFV-IFA 027 [5] for the related measurement type. If
	 * this attribute is absent and a sub-object is defined in clause 6.2 of ETSI GS
	 * NFV-IFA 027 [5] for the measured object type, measurements will be taken for
	 * all sub-object instances of the measured object instance.
	 */
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private Set<MonResource> subObjectInstanceIds;

	/**
	 * Criteria that define this threshold.
	 */
	@Embedded
	private ThresholdCriteria criteria;

	/**
	 * The URI of the endpoint to send the notification to.
	 */
	@Convert(converter = UriConverter.class)
	private URI callbackUri;

	@Embedded
	private AuthentificationInformations subscription;

	@Override
	public String toString() {
		return ToStringUtil.toString(this);
	}

}
