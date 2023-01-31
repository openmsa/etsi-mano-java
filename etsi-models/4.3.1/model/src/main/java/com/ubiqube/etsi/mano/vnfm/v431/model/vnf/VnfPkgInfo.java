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
package com.ubiqube.etsi.mano.vnfm.v431.model.vnf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ubiqube.etsi.mano.nfvo.v431.model.vnfsnapshotpkgm.Checksum;
import com.ubiqube.etsi.mano.vnfm.v431.model.vrqan.ProblemDetails;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This type represents the information of a VNF package. NOTE 1: If the value
 * of the onboardingState attribute is not equal to \&quot;ONBOARDED\&quot;, the
 * value of the operationalState attribute shall be equal to
 * \&quot;DISABLED\&quot;. NOTE 2: If the value of the onboardingState attribute
 * is not equal to \&quot;ONBOARDED\&quot;, the value of the usageState
 * attribute shall be equal to \&quot;NOT_IN_USE\&quot;. NOTE 3: ETSI GS NFV-SOL
 * 001 specifies the structure and format of the VNFD based on TOSCA
 * specifications.
 */
@Schema(description = "This type represents the information of a VNF package. NOTE 1: If the value of the onboardingState attribute is not equal to \"ONBOARDED\", the value of the operationalState          attribute shall be equal to \"DISABLED\". NOTE 2: If the value of the onboardingState attribute is not equal to \"ONBOARDED\", the value of the usageState attribute          shall be equal to \"NOT_IN_USE\". NOTE 3: ETSI GS NFV-SOL 001 specifies the structure and format of the VNFD based on TOSCA specifications. ")
@Validated

public class VnfPkgInfo {
	@JsonProperty("id")
	private String id = null;

	@JsonProperty("vnfdId")
	private String vnfdId = null;

	@JsonProperty("vnfdExtInvariantId")
	private String vnfdExtInvariantId = null;

	@JsonProperty("vnfProvider")
	private String vnfProvider = null;

	@JsonProperty("vnfProductName")
	private String vnfProductName = null;

	@JsonProperty("vnfSoftwareVersion")
	private String vnfSoftwareVersion = null;

	@JsonProperty("vnfdVersion")
	private String vnfdVersion = null;

	@JsonProperty("compatibleSpecificationVersions")
	@Valid
	private List<String> compatibleSpecificationVersions = null;

	@JsonProperty("checksum")
	private Checksum checksum = null;

	/**
	 * Signals the security option used by the package as defined in clause 5.1 of
	 * ETSI GS NFV-SOL 004. It shall be present after the VNF package content has
	 * been on-boarded and absent otherwise. Valid values: OPTION_1, OPTION_2
	 */
	public enum PackageSecurityOptionEnum {
		_1("OPTION_1"),

		_2("OPTION_2");

		private final String value;

		PackageSecurityOptionEnum(final String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static PackageSecurityOptionEnum fromValue(final String text) {
			for (final PackageSecurityOptionEnum b : PackageSecurityOptionEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	@JsonProperty("packageSecurityOption")
	private PackageSecurityOptionEnum packageSecurityOption = null;

	@JsonProperty("signingCertificate")
	private String signingCertificate = null;

	@JsonProperty("softwareImages")
	@Valid
	private List<VnfPackageSoftwareImageInfo> softwareImages = null;

	@JsonProperty("additionalArtifacts")
	@Valid
	private List<VnfPackageArtifactInfo> additionalArtifacts = null;

	@JsonProperty("onboardingState")
	private PackageOnboardingStateType onboardingState = null;

	@JsonProperty("operationalState")
	private PackageOperationalStateType operationalState = null;

	@JsonProperty("usageState")
	private PackageUsageStateType usageState = null;

	@JsonProperty("vnfmInfo")
	private List<String> vnfmInfo = null;

	@JsonProperty("userDefinedData")
	private Map<String, String> userDefinedData = null;

	@JsonProperty("onboardingFailureDetails")
	private ProblemDetails onboardingFailureDetails = null;

	@JsonProperty("_links")
	private VnfPkgInfoLinks _links = null;

	public VnfPkgInfo id(final String id) {
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

	public VnfPkgInfo vnfdId(final String vnfdId) {
		this.vnfdId = vnfdId;
		return this;
	}

	/**
	 * Get vnfdId
	 *
	 * @return vnfdId
	 **/
	@Schema(description = "")

	public String getVnfdId() {
		return vnfdId;
	}

	public void setVnfdId(final String vnfdId) {
		this.vnfdId = vnfdId;
	}

	public VnfPkgInfo vnfdExtInvariantId(final String vnfdExtInvariantId) {
		this.vnfdExtInvariantId = vnfdExtInvariantId;
		return this;
	}

	/**
	 * Get vnfdExtInvariantId
	 *
	 * @return vnfdExtInvariantId
	 **/
	@Schema(description = "")

	public String getVnfdExtInvariantId() {
		return vnfdExtInvariantId;
	}

	public void setVnfdExtInvariantId(final String vnfdExtInvariantId) {
		this.vnfdExtInvariantId = vnfdExtInvariantId;
	}

	public VnfPkgInfo vnfProvider(final String vnfProvider) {
		this.vnfProvider = vnfProvider;
		return this;
	}

	/**
	 * Get vnfProvider
	 *
	 * @return vnfProvider
	 **/
	@Schema(description = "")

	public String getVnfProvider() {
		return vnfProvider;
	}

	public void setVnfProvider(final String vnfProvider) {
		this.vnfProvider = vnfProvider;
	}

	public VnfPkgInfo vnfProductName(final String vnfProductName) {
		this.vnfProductName = vnfProductName;
		return this;
	}

	/**
	 * Get vnfProductName
	 *
	 * @return vnfProductName
	 **/
	@Schema(description = "")

	public String getVnfProductName() {
		return vnfProductName;
	}

	public void setVnfProductName(final String vnfProductName) {
		this.vnfProductName = vnfProductName;
	}

	public VnfPkgInfo vnfSoftwareVersion(final String vnfSoftwareVersion) {
		this.vnfSoftwareVersion = vnfSoftwareVersion;
		return this;
	}

	/**
	 * Get vnfSoftwareVersion
	 *
	 * @return vnfSoftwareVersion
	 **/
	@Schema(description = "")

	public String getVnfSoftwareVersion() {
		return vnfSoftwareVersion;
	}

	public void setVnfSoftwareVersion(final String vnfSoftwareVersion) {
		this.vnfSoftwareVersion = vnfSoftwareVersion;
	}

	public VnfPkgInfo vnfdVersion(final String vnfdVersion) {
		this.vnfdVersion = vnfdVersion;
		return this;
	}

	/**
	 * Get vnfdVersion
	 *
	 * @return vnfdVersion
	 **/
	@Schema(description = "")

	public String getVnfdVersion() {
		return vnfdVersion;
	}

	public void setVnfdVersion(final String vnfdVersion) {
		this.vnfdVersion = vnfdVersion;
	}

	public VnfPkgInfo compatibleSpecificationVersions(final List<String> compatibleSpecificationVersions) {
		this.compatibleSpecificationVersions = compatibleSpecificationVersions;
		return this;
	}

	public VnfPkgInfo addCompatibleSpecificationVersionsItem(final String compatibleSpecificationVersionsItem) {
		if (this.compatibleSpecificationVersions == null) {
			this.compatibleSpecificationVersions = new ArrayList<>();
		}
		this.compatibleSpecificationVersions.add(compatibleSpecificationVersionsItem);
		return this;
	}

	/**
	 * Indicates which versions of the ETSI GS NFV-SOL 004 specification the package
	 * complies to, as defined in the manifest of the package. Each entry shall be
	 * formatted as defined in clause 4.3.2 of ETSI GS NFV-SOL 004.
	 *
	 * @return compatibleSpecificationVersions
	 **/
	@Schema(description = "Indicates which versions of the ETSI GS NFV-SOL 004 specification the package complies to, as defined in the manifest of the package. Each entry shall be formatted as defined in clause 4.3.2 of ETSI GS NFV-SOL 004. ")

	public List<String> getCompatibleSpecificationVersions() {
		return compatibleSpecificationVersions;
	}

	public void setCompatibleSpecificationVersions(final List<String> compatibleSpecificationVersions) {
		this.compatibleSpecificationVersions = compatibleSpecificationVersions;
	}

	public VnfPkgInfo checksum(final Checksum checksum) {
		this.checksum = checksum;
		return this;
	}

	/**
	 * Get checksum
	 *
	 * @return checksum
	 **/
	@Schema(description = "")

	public Checksum getChecksum() {
		return checksum;
	}

	public void setChecksum(final Checksum checksum) {
		this.checksum = checksum;
	}

	public VnfPkgInfo packageSecurityOption(final PackageSecurityOptionEnum packageSecurityOption) {
		this.packageSecurityOption = packageSecurityOption;
		return this;
	}

	/**
	 * Signals the security option used by the package as defined in clause 5.1 of
	 * ETSI GS NFV-SOL 004. It shall be present after the VNF package content has
	 * been on-boarded and absent otherwise. Valid values: OPTION_1, OPTION_2
	 *
	 * @return packageSecurityOption
	 **/
	@Schema(description = "Signals the security option used by the package as defined in clause 5.1 of ETSI GS NFV-SOL 004. It shall be present after the VNF package content has been on-boarded and absent otherwise. Valid values: OPTION_1, OPTION_2 ")

	public PackageSecurityOptionEnum getPackageSecurityOption() {
		return packageSecurityOption;
	}

	public void setPackageSecurityOption(final PackageSecurityOptionEnum packageSecurityOption) {
		this.packageSecurityOption = packageSecurityOption;
	}

	public VnfPkgInfo signingCertificate(final String signingCertificate) {
		this.signingCertificate = signingCertificate;
		return this;
	}

	/**
	 * Get signingCertificate
	 *
	 * @return signingCertificate
	 **/
	@Schema(description = "")

	public String getSigningCertificate() {
		return signingCertificate;
	}

	public void setSigningCertificate(final String signingCertificate) {
		this.signingCertificate = signingCertificate;
	}

	public VnfPkgInfo softwareImages(final List<VnfPackageSoftwareImageInfo> softwareImages) {
		this.softwareImages = softwareImages;
		return this;
	}

	public VnfPkgInfo addSoftwareImagesItem(final VnfPackageSoftwareImageInfo softwareImagesItem) {
		if (this.softwareImages == null) {
			this.softwareImages = new ArrayList<>();
		}
		this.softwareImages.add(softwareImagesItem);
		return this;
	}

	/**
	 * Information about VNF package artifacts that are software images. This
	 * attribute shall not be present before the VNF package content is on-boarded.
	 * Otherwise, this attribute shall be present unless it has been requested to be
	 * excluded per attribute selector.
	 *
	 * @return softwareImages
	 **/
	@Schema(description = "Information about VNF package artifacts that are software images. This attribute shall not be present before the VNF package content is on-boarded. Otherwise, this attribute shall be present unless it has been requested to be excluded per attribute selector. ")
	@Valid
	public List<VnfPackageSoftwareImageInfo> getSoftwareImages() {
		return softwareImages;
	}

	public void setSoftwareImages(final List<VnfPackageSoftwareImageInfo> softwareImages) {
		this.softwareImages = softwareImages;
	}

	public VnfPkgInfo additionalArtifacts(final List<VnfPackageArtifactInfo> additionalArtifacts) {
		this.additionalArtifacts = additionalArtifacts;
		return this;
	}

	public VnfPkgInfo addAdditionalArtifactsItem(final VnfPackageArtifactInfo additionalArtifactsItem) {
		if (this.additionalArtifacts == null) {
			this.additionalArtifacts = new ArrayList<>();
		}
		this.additionalArtifacts.add(additionalArtifactsItem);
		return this;
	}

	/**
	 * Information about VNF package artifacts contained in the VNF package that are
	 * not software images. Every local and external artifact declared in the
	 * manifest shall be included, except the software images and the files that
	 * make up the parts of the VNFD (see clause 10.4.4.3.2). Signature files and
	 * certificate files are not considered as artifacts, however, the content of
	 * the \"Licenses\" and \"Testing\" directories in the VNF package is. This
	 * attribute shall not be present before the VNF package content is on-boarded.
	 * Otherwise, this attribute shall be present if the VNF package contains
	 * additional artifacts.
	 *
	 * @return additionalArtifacts
	 **/
	@Schema(description = "Information about VNF package artifacts contained in the VNF package that are not software images. Every local and external artifact declared in the manifest shall be included, except the software images and the files that make up the parts of the VNFD (see clause 10.4.4.3.2). Signature files and certificate files are not considered as artifacts, however, the content of the \"Licenses\" and \"Testing\" directories in the VNF package is. This attribute shall not be present before the VNF package content is on-boarded. Otherwise, this attribute shall be present if the VNF package contains additional artifacts. ")
	@Valid
	public List<VnfPackageArtifactInfo> getAdditionalArtifacts() {
		return additionalArtifacts;
	}

	public void setAdditionalArtifacts(final List<VnfPackageArtifactInfo> additionalArtifacts) {
		this.additionalArtifacts = additionalArtifacts;
	}

	public VnfPkgInfo onboardingState(final PackageOnboardingStateType onboardingState) {
		this.onboardingState = onboardingState;
		return this;
	}

	/**
	 * Get onboardingState
	 *
	 * @return onboardingState
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public PackageOnboardingStateType getOnboardingState() {
		return onboardingState;
	}

	public void setOnboardingState(final PackageOnboardingStateType onboardingState) {
		this.onboardingState = onboardingState;
	}

	public VnfPkgInfo operationalState(final PackageOperationalStateType operationalState) {
		this.operationalState = operationalState;
		return this;
	}

	/**
	 * Get operationalState
	 *
	 * @return operationalState
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public PackageOperationalStateType getOperationalState() {
		return operationalState;
	}

	public void setOperationalState(final PackageOperationalStateType operationalState) {
		this.operationalState = operationalState;
	}

	public VnfPkgInfo usageState(final PackageUsageStateType usageState) {
		this.usageState = usageState;
		return this;
	}

	/**
	 * Get usageState
	 *
	 * @return usageState
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public PackageUsageStateType getUsageState() {
		return usageState;
	}

	public void setUsageState(final PackageUsageStateType usageState) {
		this.usageState = usageState;
	}

	public VnfPkgInfo vnfmInfo(final List<String> vnfmInfo) {
		this.vnfmInfo = vnfmInfo;
		return this;
	}

	/**
	 * Get vnfmInfo
	 *
	 * @return vnfmInfo
	 **/
	@Schema(required = true, description = "")
	@NotNull

	public List<String> getVnfmInfo() {
		return vnfmInfo;
	}

	public void setVnfmInfo(final List<String> vnfmInfo) {
		this.vnfmInfo = vnfmInfo;
	}

	public VnfPkgInfo userDefinedData(final Map<String, String> userDefinedData) {
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

	public VnfPkgInfo onboardingFailureDetails(final ProblemDetails onboardingFailureDetails) {
		this.onboardingFailureDetails = onboardingFailureDetails;
		return this;
	}

	/**
	 * Get onboardingFailureDetails
	 *
	 * @return onboardingFailureDetails
	 **/
	@Schema(description = "")

	@Valid
	public ProblemDetails getOnboardingFailureDetails() {
		return onboardingFailureDetails;
	}

	public void setOnboardingFailureDetails(final ProblemDetails onboardingFailureDetails) {
		this.onboardingFailureDetails = onboardingFailureDetails;
	}

	public VnfPkgInfo _links(final VnfPkgInfoLinks _links) {
		this._links = _links;
		return this;
	}

	/**
	 * Get _links
	 *
	 * @return _links
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public VnfPkgInfoLinks getLinks() {
		return _links;
	}

	public void setLinks(final VnfPkgInfoLinks _links) {
		this._links = _links;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final VnfPkgInfo vnfPkgInfo = (VnfPkgInfo) o;
		return Objects.equals(this.id, vnfPkgInfo.id) &&
				Objects.equals(this.vnfdId, vnfPkgInfo.vnfdId) &&
				Objects.equals(this.vnfdExtInvariantId, vnfPkgInfo.vnfdExtInvariantId) &&
				Objects.equals(this.vnfProvider, vnfPkgInfo.vnfProvider) &&
				Objects.equals(this.vnfProductName, vnfPkgInfo.vnfProductName) &&
				Objects.equals(this.vnfSoftwareVersion, vnfPkgInfo.vnfSoftwareVersion) &&
				Objects.equals(this.vnfdVersion, vnfPkgInfo.vnfdVersion) &&
				Objects.equals(this.compatibleSpecificationVersions, vnfPkgInfo.compatibleSpecificationVersions) &&
				Objects.equals(this.checksum, vnfPkgInfo.checksum) &&
				Objects.equals(this.packageSecurityOption, vnfPkgInfo.packageSecurityOption) &&
				Objects.equals(this.signingCertificate, vnfPkgInfo.signingCertificate) &&
				Objects.equals(this.softwareImages, vnfPkgInfo.softwareImages) &&
				Objects.equals(this.additionalArtifacts, vnfPkgInfo.additionalArtifacts) &&
				Objects.equals(this.onboardingState, vnfPkgInfo.onboardingState) &&
				Objects.equals(this.operationalState, vnfPkgInfo.operationalState) &&
				Objects.equals(this.usageState, vnfPkgInfo.usageState) &&
				Objects.equals(this.vnfmInfo, vnfPkgInfo.vnfmInfo) &&
				Objects.equals(this.userDefinedData, vnfPkgInfo.userDefinedData) &&
				Objects.equals(this.onboardingFailureDetails, vnfPkgInfo.onboardingFailureDetails) &&
				Objects.equals(this._links, vnfPkgInfo._links);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, vnfdId, vnfdExtInvariantId, vnfProvider, vnfProductName, vnfSoftwareVersion, vnfdVersion, compatibleSpecificationVersions, checksum, packageSecurityOption, signingCertificate, softwareImages, additionalArtifacts, onboardingState, operationalState, usageState, vnfmInfo, userDefinedData, onboardingFailureDetails, _links);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class VnfPkgInfo {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    vnfdId: ").append(toIndentedString(vnfdId)).append("\n");
		sb.append("    vnfdExtInvariantId: ").append(toIndentedString(vnfdExtInvariantId)).append("\n");
		sb.append("    vnfProvider: ").append(toIndentedString(vnfProvider)).append("\n");
		sb.append("    vnfProductName: ").append(toIndentedString(vnfProductName)).append("\n");
		sb.append("    vnfSoftwareVersion: ").append(toIndentedString(vnfSoftwareVersion)).append("\n");
		sb.append("    vnfdVersion: ").append(toIndentedString(vnfdVersion)).append("\n");
		sb.append("    compatibleSpecificationVersions: ").append(toIndentedString(compatibleSpecificationVersions)).append("\n");
		sb.append("    checksum: ").append(toIndentedString(checksum)).append("\n");
		sb.append("    packageSecurityOption: ").append(toIndentedString(packageSecurityOption)).append("\n");
		sb.append("    signingCertificate: ").append(toIndentedString(signingCertificate)).append("\n");
		sb.append("    softwareImages: ").append(toIndentedString(softwareImages)).append("\n");
		sb.append("    additionalArtifacts: ").append(toIndentedString(additionalArtifacts)).append("\n");
		sb.append("    onboardingState: ").append(toIndentedString(onboardingState)).append("\n");
		sb.append("    operationalState: ").append(toIndentedString(operationalState)).append("\n");
		sb.append("    usageState: ").append(toIndentedString(usageState)).append("\n");
		sb.append("    vnfmInfo: ").append(toIndentedString(vnfmInfo)).append("\n");
		sb.append("    userDefinedData: ").append(toIndentedString(userDefinedData)).append("\n");
		sb.append("    onboardingFailureDetails: ").append(toIndentedString(onboardingFailureDetails)).append("\n");
		sb.append("    _links: ").append(toIndentedString(_links)).append("\n");
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
