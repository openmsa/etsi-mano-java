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

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.etsi.mano.nfvo.v431.model.nsfm.ResourceHandle;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This type represents a VNFC Snapshot. It shall comply with the provisions
 * defined in table 6.5.3.77-1. NOTE 1: The identifier of the compute snapshot
 * resource is assigned during creation of a VNFC Snapshot being returned from
 * the VIM as output data in the response message of the individual resource
 * operations. This attribute shall only be present for a VNFC snapshot that has
 * been newly created by the VNFM as a result of the \&quot;Create VNF snapshot
 * task\&quot;. NOTE 2: The identifier of the storage snapshot resource is
 * assigned during creation of a VNFC snapshot being returned from the VIM as
 * output data in the response message of the individual resource operations.
 * This attribute shall only be present for a VNFC snapshot with an associated
 * storage resource and that has been newly created by the VNFM as a result of
 * the \&quot;Create VNF snapshot task\&quot;.
 */
@Schema(description = "This type represents a VNFC Snapshot. It shall comply with the provisions defined in table 6.5.3.77-1. NOTE 1: The identifier of the compute snapshot resource is assigned during creation of a VNFC Snapshot being returned from the VIM as output data in the response message of the individual resource operations. This attribute shall only be present for a VNFC snapshot that has been newly created by the VNFM as a result of the \"Create VNF snapshot task\". NOTE 2: The identifier of the storage snapshot resource is assigned during creation of a VNFC snapshot being returned from the VIM as output data in the response message of the individual resource operations. This attribute shall only be present for a VNFC snapshot with an associated storage resource and that has been newly created by the VNFM as a result of the \"Create VNF snapshot task\". ")
@Validated

public class VnfcSnapshotInfo {
	@JsonProperty("id")
	private String id = null;

	@JsonProperty("vnfcInstanceId")
	private String vnfcInstanceId = null;

	@JsonProperty("creationStartedAt")
	private OffsetDateTime creationStartedAt = null;

	@JsonProperty("creationFinishedAt")
	private OffsetDateTime creationFinishedAt = null;

	@JsonProperty("vnfcResourceInfoId")
	private String vnfcResourceInfoId = null;

	@JsonProperty("computeSnapshotResource")
	private ResourceHandle computeSnapshotResource = null;

	@JsonProperty("storageSnapshotResources")
	@Valid
	private List<VnfcSnapshotInfoStorageSnapshotResources> storageSnapshotResources = null;

	@JsonProperty("userDefinedData")
	private Map<String, String> userDefinedData = null;

	public VnfcSnapshotInfo id(final String id) {
		this.id = id;
		return this;
	}

	/**
	 * Get id
	 *
	 * @return id
	 **/
	@Schema(required = true, description = "")
	@NotNull

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public VnfcSnapshotInfo vnfcInstanceId(final String vnfcInstanceId) {
		this.vnfcInstanceId = vnfcInstanceId;
		return this;
	}

	/**
	 * Get vnfcInstanceId
	 *
	 * @return vnfcInstanceId
	 **/
	@Schema(required = true, description = "")
	@NotNull

	public String getVnfcInstanceId() {
		return vnfcInstanceId;
	}

	public void setVnfcInstanceId(final String vnfcInstanceId) {
		this.vnfcInstanceId = vnfcInstanceId;
	}

	public VnfcSnapshotInfo creationStartedAt(final OffsetDateTime creationStartedAt) {
		this.creationStartedAt = creationStartedAt;
		return this;
	}

	/**
	 * Get creationStartedAt
	 *
	 * @return creationStartedAt
	 **/
	@Schema(description = "")

	@Valid
	public OffsetDateTime getCreationStartedAt() {
		return creationStartedAt;
	}

	public void setCreationStartedAt(final OffsetDateTime creationStartedAt) {
		this.creationStartedAt = creationStartedAt;
	}

	public VnfcSnapshotInfo creationFinishedAt(final OffsetDateTime creationFinishedAt) {
		this.creationFinishedAt = creationFinishedAt;
		return this;
	}

	/**
	 * Get creationFinishedAt
	 *
	 * @return creationFinishedAt
	 **/
	@Schema(description = "")

	@Valid
	public OffsetDateTime getCreationFinishedAt() {
		return creationFinishedAt;
	}

	public void setCreationFinishedAt(final OffsetDateTime creationFinishedAt) {
		this.creationFinishedAt = creationFinishedAt;
	}

	public VnfcSnapshotInfo vnfcResourceInfoId(final String vnfcResourceInfoId) {
		this.vnfcResourceInfoId = vnfcResourceInfoId;
		return this;
	}

	/**
	 * Get vnfcResourceInfoId
	 *
	 * @return vnfcResourceInfoId
	 **/
	@Schema(description = "")

	public String getVnfcResourceInfoId() {
		return vnfcResourceInfoId;
	}

	public void setVnfcResourceInfoId(final String vnfcResourceInfoId) {
		this.vnfcResourceInfoId = vnfcResourceInfoId;
	}

	public VnfcSnapshotInfo computeSnapshotResource(final ResourceHandle computeSnapshotResource) {
		this.computeSnapshotResource = computeSnapshotResource;
		return this;
	}

	/**
	 * Get computeSnapshotResource
	 *
	 * @return computeSnapshotResource
	 **/
	@Schema(description = "")

	@Valid
	public ResourceHandle getComputeSnapshotResource() {
		return computeSnapshotResource;
	}

	public void setComputeSnapshotResource(final ResourceHandle computeSnapshotResource) {
		this.computeSnapshotResource = computeSnapshotResource;
	}

	public VnfcSnapshotInfo storageSnapshotResources(final List<VnfcSnapshotInfoStorageSnapshotResources> storageSnapshotResources) {
		this.storageSnapshotResources = storageSnapshotResources;
		return this;
	}

	public VnfcSnapshotInfo addStorageSnapshotResourcesItem(final VnfcSnapshotInfoStorageSnapshotResources storageSnapshotResourcesItem) {
		if (this.storageSnapshotResources == null) {
			this.storageSnapshotResources = new ArrayList<>();
		}
		this.storageSnapshotResources.add(storageSnapshotResourcesItem);
		return this;
	}

	/**
	 * Reference to the \"VirtualStorageResourceInfo\" structure in the
	 * \"VnfInstance\" structure that represents the virtual storage resource. See
	 * note 2.
	 *
	 * @return storageSnapshotResources
	 **/
	@Schema(description = "Reference to the \"VirtualStorageResourceInfo\" structure in the \"VnfInstance\" structure that represents the virtual storage resource. See note 2. ")
	@Valid
	public List<VnfcSnapshotInfoStorageSnapshotResources> getStorageSnapshotResources() {
		return storageSnapshotResources;
	}

	public void setStorageSnapshotResources(final List<VnfcSnapshotInfoStorageSnapshotResources> storageSnapshotResources) {
		this.storageSnapshotResources = storageSnapshotResources;
	}

	public VnfcSnapshotInfo userDefinedData(final Map<String, String> userDefinedData) {
		this.userDefinedData = userDefinedData;
		return this;
	}

	/**
	 * Get userDefinedData
	 *
	 * @return userDefinedData
	 **/
	@Schema(description = "")

	@Valid
	public Map<String, String> getUserDefinedData() {
		return userDefinedData;
	}

	public void setUserDefinedData(final Map<String, String> userDefinedData) {
		this.userDefinedData = userDefinedData;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final VnfcSnapshotInfo vnfcSnapshotInfo = (VnfcSnapshotInfo) o;
		return Objects.equals(this.id, vnfcSnapshotInfo.id) &&
				Objects.equals(this.vnfcInstanceId, vnfcSnapshotInfo.vnfcInstanceId) &&
				Objects.equals(this.creationStartedAt, vnfcSnapshotInfo.creationStartedAt) &&
				Objects.equals(this.creationFinishedAt, vnfcSnapshotInfo.creationFinishedAt) &&
				Objects.equals(this.vnfcResourceInfoId, vnfcSnapshotInfo.vnfcResourceInfoId) &&
				Objects.equals(this.computeSnapshotResource, vnfcSnapshotInfo.computeSnapshotResource) &&
				Objects.equals(this.storageSnapshotResources, vnfcSnapshotInfo.storageSnapshotResources) &&
				Objects.equals(this.userDefinedData, vnfcSnapshotInfo.userDefinedData);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, vnfcInstanceId, creationStartedAt, creationFinishedAt, vnfcResourceInfoId, computeSnapshotResource, storageSnapshotResources, userDefinedData);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class VnfcSnapshotInfo {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    vnfcInstanceId: ").append(toIndentedString(vnfcInstanceId)).append("\n");
		sb.append("    creationStartedAt: ").append(toIndentedString(creationStartedAt)).append("\n");
		sb.append("    creationFinishedAt: ").append(toIndentedString(creationFinishedAt)).append("\n");
		sb.append("    vnfcResourceInfoId: ").append(toIndentedString(vnfcResourceInfoId)).append("\n");
		sb.append("    computeSnapshotResource: ").append(toIndentedString(computeSnapshotResource)).append("\n");
		sb.append("    storageSnapshotResources: ").append(toIndentedString(storageSnapshotResources)).append("\n");
		sb.append("    userDefinedData: ").append(toIndentedString(userDefinedData)).append("\n");
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
