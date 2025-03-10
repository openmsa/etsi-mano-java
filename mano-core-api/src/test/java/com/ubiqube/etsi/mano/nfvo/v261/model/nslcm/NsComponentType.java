/**
 * Copyright (C) 2019-2025 Ubiqube.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.nfvo.v261.model.nslcm;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The enumeration NsComponentType represents the NS component type. It shall
 * comply with the provisions defined in Table 6.5.4.5-1. Value | Description
 * ------|------------ VNF | Represents the impacted NS component is a VNF. PNF
 * | Represents the impacted NS component is a PNF. NS | Represents the impacted
 * NS component is a nested NS.
 */
public enum NsComponentType {

	VNF("VNF"),

	PNF("PNF"),

	NS("NS");

	private String value;

	NsComponentType(final String value) {
		this.value = value;
	}

	@Override
	@JsonValue
	public String toString() {
		return String.valueOf(value);
	}

	@JsonCreator
	public static NsComponentType fromValue(final String text) {
		for (final NsComponentType b : NsComponentType.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}
}
