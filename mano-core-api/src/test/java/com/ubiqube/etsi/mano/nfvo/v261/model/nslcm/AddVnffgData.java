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

import java.util.Objects;

import org.jspecify.annotations.NonNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * This type specifies the parameters used for the creation of a new VNFFG
 * instance. It shall comply with the provisions defined in Table 6.5.3.36-1.
 */
@Schema(description = "This type specifies the parameters used for the creation of a new VNFFG instance. It shall comply with the provisions defined in Table 6.5.3.36-1. ")
@Validated


public class AddVnffgData {
	@JsonProperty("targetNsInstanceId")
	private String targetNsInstanceId = null;

	@JsonProperty("vnffgName")
	private String vnffgName = null;

	@JsonProperty("description")
	private String description = null;

	public AddVnffgData targetNsInstanceId(final String targetNsInstanceId) {
		this.targetNsInstanceId = targetNsInstanceId;
		return this;
	}

	/**
	 * Identifier of the VNFFGD used to create this VNFFG instance.
	 * 
	 * @return targetNsInstanceId
	 **/
	@Schema(description = "Identifier of the VNFFGD used to create this VNFFG instance. ")

	public String getTargetNsInstanceId() {
		return targetNsInstanceId;
	}

	public void setTargetNsInstanceId(final String targetNsInstanceId) {
		this.targetNsInstanceId = targetNsInstanceId;
	}

	public AddVnffgData vnffgName(final String vnffgName) {
		this.vnffgName = vnffgName;
		return this;
	}

	/**
	 * Human readable name for the VNFFG.
	 * 
	 * @return vnffgName
	 **/
	@Schema(required = true, description = "Human readable name for the VNFFG. ")
	@NonNull

	public String getVnffgName() {
		return vnffgName;
	}

	public void setVnffgName(final String vnffgName) {
		this.vnffgName = vnffgName;
	}

	public AddVnffgData description(final String description) {
		this.description = description;
		return this;
	}

	/**
	 * Human readable description for the VNFFG.
	 * 
	 * @return description
	 **/
	@Schema(required = true, description = "Human readable description for the VNFFG. ")
	@NonNull

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final AddVnffgData addVnffgData = (AddVnffgData) o;
		return Objects.equals(this.targetNsInstanceId, addVnffgData.targetNsInstanceId) &&
				Objects.equals(this.vnffgName, addVnffgData.vnffgName) &&
				Objects.equals(this.description, addVnffgData.description);
	}

	@Override
	public int hashCode() {
		return Objects.hash(targetNsInstanceId, vnffgName, description);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class AddVnffgData {\n");

		sb.append("    targetNsInstanceId: ").append(toIndentedString(targetNsInstanceId)).append("\n");
		sb.append("    vnffgName: ").append(toIndentedString(vnffgName)).append("\n");
		sb.append("    description: ").append(toIndentedString(description)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(final java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
