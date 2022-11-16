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
package com.ubiqube.etsi.mano.dao.mano.k8s;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.vnfi.StatusType;

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
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class K8sServers implements Serializable {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@Id
	private UUID id;

	@ManyToOne
	private VnfInstance vnfInstance;

	private String toscaName;

	/**
	 * ca.crt
	 */
	@Column(length = 5000)
	private String caPem;

	/**
	 * User CRT.
	 */
	@Column(length = 5000)
	private String userCrt;

	private String apiAddress;

	/**
	 * Uniq identifier in VIM. (Mainly the cluster ID).
	 */
	private String vimResourceId;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> masterAddresses;

	@Enumerated(EnumType.STRING)
	private StatusType status;
}
