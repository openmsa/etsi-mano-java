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
package com.ubiqube.etsi.mano.dao.mano.nsd;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

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
public class VnffgDescriptor implements Serializable {
	/** Serial. */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private String vnffgdId;

	private String name;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> vnfProfileId;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> pnfProfileId;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> nestedNsProfileId;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> virtualLinkProfileId;

	private String description;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Classifier classifier;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<NfpDescriptor> nfpd;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<NsCpHandle> nsCpHandle;
}
