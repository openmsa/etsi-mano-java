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
package com.ubiqube.etsi.mano.nfvo.v431.model.nslcm;

import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.etsi.mano.nfvo.v431.model.nsfm.ResourceHandle;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * VnfcSnapshotInfoStorageSnapshotResources
 */
@Validated

public class VnfcSnapshotInfoStorageSnapshotResources {
	@JsonProperty("storageResourceId")
	private String storageResourceId = null;

	@JsonProperty("storageSnapshotResources")
	private ResourceHandle storageSnapshotResources = null;

	public VnfcSnapshotInfoStorageSnapshotResources storageResourceId(final String storageResourceId) {
		this.storageResourceId = storageResourceId;
		return this;
	}

	/**
	 * Get storageResourceId
	 *
	 * @return storageResourceId
	 **/
	@Schema(required = true, description = "")
	@NotNull

	public String getStorageResourceId() {
		return storageResourceId;
	}

	public void setStorageResourceId(final String storageResourceId) {
		this.storageResourceId = storageResourceId;
	}

	public VnfcSnapshotInfoStorageSnapshotResources storageSnapshotResources(final ResourceHandle storageSnapshotResources) {
		this.storageSnapshotResources = storageSnapshotResources;
		return this;
	}

	/**
	 * Get storageSnapshotResources
	 *
	 * @return storageSnapshotResources
	 **/
	@Schema(description = "")

	@Valid
	public ResourceHandle getStorageSnapshotResources() {
		return storageSnapshotResources;
	}

	public void setStorageSnapshotResources(final ResourceHandle storageSnapshotResources) {
		this.storageSnapshotResources = storageSnapshotResources;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final VnfcSnapshotInfoStorageSnapshotResources vnfcSnapshotInfoStorageSnapshotResources = (VnfcSnapshotInfoStorageSnapshotResources) o;
		return Objects.equals(this.storageResourceId, vnfcSnapshotInfoStorageSnapshotResources.storageResourceId) &&
				Objects.equals(this.storageSnapshotResources, vnfcSnapshotInfoStorageSnapshotResources.storageSnapshotResources);
	}

	@Override
	public int hashCode() {
		return Objects.hash(storageResourceId, storageSnapshotResources);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class VnfcSnapshotInfoStorageSnapshotResources {\n");

		sb.append("    storageResourceId: ").append(toIndentedString(storageResourceId)).append("\n");
		sb.append("    storageSnapshotResources: ").append(toIndentedString(storageSnapshotResources)).append("\n");
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
