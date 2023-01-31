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
package com.ubiqube.etsi.mano.dao.mano;

import java.util.UUID;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import com.ubiqube.etsi.mano.dao.mano.alarm.ResourceHandle;
import com.ubiqube.etsi.mano.dao.mano.dto.VnfInstantiatedBase;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ExtLinkPortDataEntity extends VnfInstantiatedBase {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id = null;

	@Embedded
	private ResourceHandle resourceHandle;

	// 2.7.1
	private String cpInstanceId;

	/**
	 * @since 3.5.1
	 */
	private String secondaryCpInstanceId;

	/**
	 * @since 3.5.1
	 */
	private String trunkResourceId;

}
