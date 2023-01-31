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
package com.ubiqube.etsi.mano.dao.mano.nslcm.scale;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import com.ubiqube.etsi.mano.dao.mano.VnfInstanceData;
import com.ubiqube.etsi.mano.dao.mano.nfvo.ParamsForVnf;

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
public class ScaleNsData implements Serializable {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id = null;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<VnfInstanceData> vnfInstanceToBeAdded;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> vnfInstanceToBeRemoved;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private ScaleNsByStepsData scaleNsByStepsData;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private ScaleNsToLevelData scaleNsToLevelData;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private ParamsForVnf additionalParamsForNs;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<ParamsForVnf> additionalParamsForVnf;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<VnfLocationConstraint> locationConstraints;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<NestedNsLocationConstraint> nestedNslocationConstraints;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<ScaleVnfData> scaleVnfData;

}
