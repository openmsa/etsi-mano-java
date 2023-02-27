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
package com.ubiqube.etsi.mano.dao.mano;

public enum InstantiationStatusType {
	NOT_STARTED("STARTED"),
	STARTED("STARTED"),
	STARTING("STARTING"),

	PROCESSING("PROCESSING"),
	SUCCESS("SUCCESS"),

	COMPLETED("COMPLETED"),

	FAILED_TEMP("FAILED_TEMP"),

	PARTIALLY_COMPLETED("PARTIALLY_COMPLETED"),

	FAILED("FAILED"),

	ROLLING_BACK("ROLLING_BACK"),

	ROLLED_BACK("ROLLED_BACK");

	private String value;

	InstantiationStatusType(final String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	public static InstantiationStatusType fromValue(final String text) {
		for (final InstantiationStatusType b : InstantiationStatusType.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}

	public String value() {
		return value;
	}

}
