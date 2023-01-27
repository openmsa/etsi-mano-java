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

/*
 * SOL003 - VNF Lifecycle Management interface
 * SOL003 - VNF Lifecycle Management interface definition  IMPORTANT: Please note that this file might be not aligned to the current version of the ETSI Group Specification it refers to. In case of discrepancies the published ETSI Group Specification takes precedence.  In clause 4.3.2 of ETSI GS NFV-SOL 003 v2.4.1, an attribute-based filtering mechanism is defined. This mechanism is currently not included in the corresponding OpenAPI design for this GS version. Changes to the attribute-based filtering mechanism are being considered in v2.5.1 of this GS for inclusion in the corresponding future ETSI NFV OpenAPI design. Please report bugs to https://forge.etsi.org/bugzilla/buglist.cgi?component=Nfv-Openapis&list_id=61&product=NFV&resolution=
 *
 * OpenAPI spec version: 1.1.0
 *
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package com.ubiqube.etsi.mano.vnfm.v261.model.nslcm;

import java.util.Map;

import jakarta.annotation.Nonnull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.etsi.mano.common.v261.model.ResourceHandle;
import com.ubiqube.etsi.mano.common.v261.model.nslcm.ChangeTypeEnum;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * This type provides information about added, deleted, modified and temporary
 * virtual storage resources.
 */
@Schema(description = "This type provides information about added, deleted, modified and temporary virtual storage resources. ")

public class AffectedVirtualStorage {
	@JsonProperty("id")
	private String id = null;

	@JsonProperty("virtualStorageDescId")
	private String virtualStorageDescId = null;

	@JsonProperty("changeType")
	private ChangeTypeEnum changeType = null;

	@JsonProperty("storageResource")
	private ResourceHandle storageResource = null;

	@JsonProperty("metadata")
	private Map<String, String> metadata = null;

	public AffectedVirtualStorage id(final String id) {
		this.id = id;
		return this;
	}

	/**
	 * Identifier of the storage instance, identifying the applicable
	 * \&quot;virtualStorageResourceInfo\&quot; entry in the
	 * \&quot;VnfInstance\&quot; data type.
	 *
	 * @return id
	 **/
	@JsonProperty("id")
	@Schema(required = true, description = "Identifier of the storage instance, identifying the applicable \"virtualStorageResourceInfo\" entry in the \"VnfInstance\" data type. ")
	@Nonnull
	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public AffectedVirtualStorage virtualStorageDescId(final String virtualStorageDescId) {
		this.virtualStorageDescId = virtualStorageDescId;
		return this;
	}

	/**
	 * Identifier of the related VirtualStorage descriptor in the VNFD.
	 *
	 * @return virtualStorageDescId
	 **/
	@JsonProperty("virtualStorageDescId")
	@Schema(required = true, description = "Identifier of the related VirtualStorage descriptor in the VNFD. ")
	@Nonnull
	public String getVirtualStorageDescId() {
		return virtualStorageDescId;
	}

	public void setVirtualStorageDescId(final String virtualStorageDescId) {
		this.virtualStorageDescId = virtualStorageDescId;
	}

	public AffectedVirtualStorage changeType(final ChangeTypeEnum changeType) {
		this.changeType = changeType;
		return this;
	}

	/**
	 * Signals the type of change. Permitted values: * ADDED * REMOVED * MODIFIED *
	 * TEMPORARY For a temporary resource, an AffectedVirtualStorage structure
	 * exists as long as the temporary resource exists.
	 *
	 * @return changeType
	 **/
	@JsonProperty("changeType")
	@Schema(required = true, description = "Signals the type of change. Permitted values: * ADDED * REMOVED * MODIFIED * TEMPORARY For a temporary resource, an AffectedVirtualStorage structure exists as long as the temporary resource exists. ")
	@Nonnull
	public ChangeTypeEnum getChangeType() {
		return changeType;
	}

	public void setChangeType(final ChangeTypeEnum changeType) {
		this.changeType = changeType;
	}

	public AffectedVirtualStorage storageResource(final ResourceHandle storageResource) {
		this.storageResource = storageResource;
		return this;
	}

	/**
	 * Reference to the VirtualStorage resource. Detailed information is (for new
	 * and modified resources) or has been (for removed resources) available from
	 * the VIM.
	 *
	 * @return storageResource
	 **/
	@JsonProperty("storageResource")
	@Schema(required = true, description = "Reference to the VirtualStorage resource. Detailed information is  (for new and modified resources) or has been (for removed resources) available from the VIM. ")
	@Nonnull
	public ResourceHandle getStorageResource() {
		return storageResource;
	}

	public void setStorageResource(final ResourceHandle storageResource) {
		this.storageResource = storageResource;
	}

	public AffectedVirtualStorage metadata(final Map<String, String> metadata) {
		this.metadata = metadata;
		return this;
	}

	/**
	 * Metadata about this resource. The content of this attribute shall be a copy
	 * of the content of the \&quot;metadata\&quot; attribute of the
	 * VirtualStorageResourceInfo structure.
	 *
	 * @return metadata
	 **/
	@JsonProperty("metadata")
	@Schema(description = "Metadata about this resource. The content of this attribute shall be a copy of the content of the \"metadata\" attribute of the VirtualStorageResourceInfo structure. ")
	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(final Map<String, String> metadata) {
		this.metadata = metadata;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class AffectedVirtualStorage {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    virtualStorageDescId: ").append(toIndentedString(virtualStorageDescId)).append("\n");
		sb.append("    changeType: ").append(toIndentedString(changeType)).append("\n");
		sb.append("    storageResource: ").append(toIndentedString(storageResource)).append("\n");
		sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
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
