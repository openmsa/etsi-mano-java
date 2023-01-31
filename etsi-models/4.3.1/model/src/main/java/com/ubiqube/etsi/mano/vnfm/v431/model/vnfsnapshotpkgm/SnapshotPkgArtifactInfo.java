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
package com.ubiqube.etsi.mano.vnfm.v431.model.vnfsnapshotpkgm;

import java.util.Map;
import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This type represents an artifact other than a software image which is
 * contained in a VNF snapshot package.
 */
@Schema(description = "This type represents an artifact other than a software image which is contained in  a VNF snapshot package. ")
@Validated

public class SnapshotPkgArtifactInfo {
	@JsonProperty("artifactPath")
	private String artifactPath = null;

	@JsonProperty("artifactUri")
	private String artifactUri = null;

	@JsonProperty("checksum")
	private String checksum = null;

	@JsonProperty("isEncrypted")
	private Boolean isEncrypted = null;

	@JsonProperty("metadata")
	private Map<String, String> metadata = null;

	public SnapshotPkgArtifactInfo artifactPath(final String artifactPath) {
		this.artifactPath = artifactPath;
		return this;
	}

	/**
	 * Get artifactPath
	 *
	 * @return artifactPath
	 **/
	@Schema(description = "")

	public String getArtifactPath() {
		return artifactPath;
	}

	public void setArtifactPath(final String artifactPath) {
		this.artifactPath = artifactPath;
	}

	public SnapshotPkgArtifactInfo artifactUri(final String artifactUri) {
		this.artifactUri = artifactUri;
		return this;
	}

	/**
	 * Get artifactUri
	 *
	 * @return artifactUri
	 **/
	@Schema(description = "")

	public String getArtifactUri() {
		return artifactUri;
	}

	public void setArtifactUri(final String artifactUri) {
		this.artifactUri = artifactUri;
	}

	public SnapshotPkgArtifactInfo checksum(final String checksum) {
		this.checksum = checksum;
		return this;
	}

	/**
	 * Get checksum
	 *
	 * @return checksum
	 **/
	@Schema(required = true, description = "")
	@NotNull

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(final String checksum) {
		this.checksum = checksum;
	}

	public SnapshotPkgArtifactInfo isEncrypted(final Boolean isEncrypted) {
		this.isEncrypted = isEncrypted;
		return this;
	}

	/**
	 * Get isEncrypted
	 *
	 * @return isEncrypted
	 **/
	@Schema(required = true, description = "")
	@NotNull

	public Boolean getIsEncrypted() {
		return isEncrypted;
	}

	public void setIsEncrypted(final Boolean isEncrypted) {
		this.isEncrypted = isEncrypted;
	}

	public SnapshotPkgArtifactInfo metadata(final Map<String, String> metadata) {
		this.metadata = metadata;
		return this;
	}

	/**
	 * Get metadata
	 *
	 * @return metadata
	 **/
	@Schema(description = "")

	@Valid
	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(final Map<String, String> metadata) {
		this.metadata = metadata;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final SnapshotPkgArtifactInfo snapshotPkgArtifactInfo = (SnapshotPkgArtifactInfo) o;
		return Objects.equals(this.artifactPath, snapshotPkgArtifactInfo.artifactPath) &&
				Objects.equals(this.artifactUri, snapshotPkgArtifactInfo.artifactUri) &&
				Objects.equals(this.checksum, snapshotPkgArtifactInfo.checksum) &&
				Objects.equals(this.isEncrypted, snapshotPkgArtifactInfo.isEncrypted) &&
				Objects.equals(this.metadata, snapshotPkgArtifactInfo.metadata);
	}

	@Override
	public int hashCode() {
		return Objects.hash(artifactPath, artifactUri, checksum, isEncrypted, metadata);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class SnapshotPkgArtifactInfo {\n");

		sb.append("    artifactPath: ").append(toIndentedString(artifactPath)).append("\n");
		sb.append("    artifactUri: ").append(toIndentedString(artifactUri)).append("\n");
		sb.append("    checksum: ").append(toIndentedString(checksum)).append("\n");
		sb.append("    isEncrypted: ").append(toIndentedString(isEncrypted)).append("\n");
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
