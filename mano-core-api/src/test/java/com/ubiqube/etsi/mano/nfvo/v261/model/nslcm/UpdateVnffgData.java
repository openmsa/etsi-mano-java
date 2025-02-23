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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * This type specifies the parameters used for the update of an existing VNFFG
 * instance. It shall comply with the provisions defined in Table 6.5.3.37-1.
 */
@Schema(description = "This type specifies the parameters used for the update of an existing VNFFG instance. It shall comply with the provisions defined in Table 6.5.3.37-1. ")
@Validated


public class UpdateVnffgData {
	@JsonProperty("vnffgInfoId")
	private String vnffgInfoId = null;

	@JsonProperty("nfp")
	@Valid
	private List<NfpData> nfp = null;

	@JsonProperty("nfpInfoId")
	@Valid
	private List<String> nfpInfoId = null;

	public UpdateVnffgData vnffgInfoId(final String vnffgInfoId) {
		this.vnffgInfoId = vnffgInfoId;
		return this;
	}

	/**
	 * Identifier of an existing VNFFG to be updated for the NS Instance.
	 * 
	 * @return vnffgInfoId
	 **/
	@Schema(required = true, description = "Identifier of an existing VNFFG to be updated for the NS Instance. ")
	@NonNull

	public String getVnffgInfoId() {
		return vnffgInfoId;
	}

	public void setVnffgInfoId(final String vnffgInfoId) {
		this.vnffgInfoId = vnffgInfoId;
	}

	public UpdateVnffgData nfp(final List<NfpData> nfp) {
		this.nfp = nfp;
		return this;
	}

	public UpdateVnffgData addNfpItem(final NfpData nfpItem) {
		if (this.nfp == null) {
			this.nfp = new ArrayList<>();
		}
		this.nfp.add(nfpItem);
		return this;
	}

	/**
	 * Indicate the desired new NFP(s) for a given VNFFG after the operations of
	 * addition/removal of NS components (e.g. VNFs, VLs, etc.) have been completed,
	 * or indicate the updated or newly created NFP classification and selection
	 * rule which applied to an existing NFP.
	 * 
	 * @return nfp
	 **/
	@Schema(description = "Indicate the desired new NFP(s) for a given VNFFG after the operations of addition/removal of NS components (e.g. VNFs, VLs, etc.) have been completed, or indicate the updated or newly created NFP classification and selection rule which applied to an existing NFP. ")

	@Valid

	public List<NfpData> getNfp() {
		return nfp;
	}

	public void setNfp(final List<NfpData> nfp) {
		this.nfp = nfp;
	}

	public UpdateVnffgData nfpInfoId(final List<String> nfpInfoId) {
		this.nfpInfoId = nfpInfoId;
		return this;
	}

	public UpdateVnffgData addNfpInfoIdItem(final String nfpInfoIdItem) {
		if (this.nfpInfoId == null) {
			this.nfpInfoId = new ArrayList<>();
		}
		this.nfpInfoId.add(nfpInfoIdItem);
		return this;
	}

	/**
	 * Identifier(s) of the NFP to be deleted from a given VNFFG.
	 * 
	 * @return nfpInfoId
	 **/
	@Schema(description = "Identifier(s) of the NFP to be deleted from a given VNFFG. ")

	public List<String> getNfpInfoId() {
		return nfpInfoId;
	}

	public void setNfpInfoId(final List<String> nfpInfoId) {
		this.nfpInfoId = nfpInfoId;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final UpdateVnffgData updateVnffgData = (UpdateVnffgData) o;
		return Objects.equals(this.vnffgInfoId, updateVnffgData.vnffgInfoId) &&
				Objects.equals(this.nfp, updateVnffgData.nfp) &&
				Objects.equals(this.nfpInfoId, updateVnffgData.nfpInfoId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(vnffgInfoId, nfp, nfpInfoId);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class UpdateVnffgData {\n");

		sb.append("    vnffgInfoId: ").append(toIndentedString(vnffgInfoId)).append("\n");
		sb.append("    nfp: ").append(toIndentedString(nfp)).append("\n");
		sb.append("    nfpInfoId: ").append(toIndentedString(nfpInfoId)).append("\n");
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
