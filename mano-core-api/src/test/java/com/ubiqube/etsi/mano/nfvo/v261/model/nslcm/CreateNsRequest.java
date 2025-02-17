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
 * CreateNsRequest
 */
@Validated

public class CreateNsRequest {
	@JsonProperty("nsdId")
	private String nsdId = null;

	@JsonProperty("nsName")
	private String nsName = null;

	@JsonProperty("nsDescription")
	private String nsDescription = null;

	public CreateNsRequest nsdId(final String nsdId) {
		this.nsdId = nsdId;
		return this;
	}

	/**
	 * Identifier of the NSD that defines the NS instance to be created.
	 *
	 * @return nsdId
	 **/
	@Schema(required = true, description = "Identifier of the NSD that defines the NS instance to be created. ")
	public String getNsdId() {
		return nsdId;
	}

	public void setNsdId(final String nsdId) {
		this.nsdId = nsdId;
	}

	public CreateNsRequest nsName(final String nsName) {
		this.nsName = nsName;
		return this;
	}

	/**
	 * Human-readable name of the NS instance to be created.
	 *
	 * @return nsName
	 **/
	@Schema(required = true, description = "Human-readable name of the NS instance to be created. ")
	@NonNull

	public String getNsName() {
		return nsName;
	}

	public void setNsName(final String nsName) {
		this.nsName = nsName;
	}

	public CreateNsRequest nsDescription(final String nsDescription) {
		this.nsDescription = nsDescription;
		return this;
	}

	/**
	 * Human-readable description of the NS instance to be created.
	 *
	 * @return nsDescription
	 **/
	@Schema(required = true, description = "Human-readable description of the NS instance to be created. ")
	@NonNull

	public String getNsDescription() {
		return nsDescription;
	}

	public void setNsDescription(final String nsDescription) {
		this.nsDescription = nsDescription;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final CreateNsRequest createNsRequest = (CreateNsRequest) o;
		return Objects.equals(this.nsdId, createNsRequest.nsdId) &&
				Objects.equals(this.nsName, createNsRequest.nsName) &&
				Objects.equals(this.nsDescription, createNsRequest.nsDescription);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nsdId, nsName, nsDescription);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class CreateNsRequest {\n");

		sb.append("    nsdId: ").append(toIndentedString(nsdId)).append("\n");
		sb.append("    nsName: ").append(toIndentedString(nsName)).append("\n");
		sb.append("    nsDescription: ").append(toIndentedString(nsDescription)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces (except the first line).
	 */
	private String toIndentedString(final java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
