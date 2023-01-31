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
package com.ubiqube.etsi.mano.dao.mano.pkg;

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

import com.ubiqube.etsi.mano.dao.mano.VduProfile;

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
public class OsContainerDeployableUnit implements Serializable {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private String name;

	private String description;

	/**
	 * It pose serious problems in Orika.
	 */
	// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	// private Set<LogicalNodeData> logicalNode

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<RequestedAdditionalCapability> requestedAdditionalCapabilities;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> nfviConstraints;

	private VduProfile vduProfile;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<McioConstraint> mcioConstraintParams;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> virtualStorageReq;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> containerReq;
}
