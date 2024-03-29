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
package com.ubiqube.etsi.mec.meo.v211.model.grant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * &#x27;This type represents a grant. Refer to clause 9.5.2.3 of ETSI GS NFV-SOL 003 &#x27;
 */
@ApiModel(description = "'This type represents a grant. Refer to clause 9.5.2.3 of ETSI GS NFV-SOL 003 '")
@Validated
public class Grant {
	@JsonProperty("id")
	private String id = null;

	@JsonProperty("appInstanceId")
	private String appInstanceId = null;

	@JsonProperty("appLcmOpOccId")
	private String appLcmOpOccId = null;

	@JsonProperty("vimConnections")
	@Valid
	private List<VimConnectionInfo> vimConnections = null;

	@JsonProperty("zones")
	@Valid
	private List<ZoneInfo> zones = null;

	@JsonProperty("zoneGroups")
	@Valid
	private List<ZoneGroupInfo> zoneGroups = null;

	@JsonProperty("addResources")
	@Valid
	private List<GrantInfo> addResources = null;

	@JsonProperty("tempResources")
	@Valid
	private List<GrantInfo> tempResources = null;

	@JsonProperty("removeResources")
	@Valid
	private List<GrantInfo> removeResources = null;

	@JsonProperty("updateResources")
	@Valid
	private List<GrantInfo> updateResources = null;

	@JsonProperty("vimAssets")
	private VimAssets vimAssets = null;

	@JsonProperty("extVirtualLinks")
	@Valid
	private List<ExtVirtualLinkData> extVirtualLinks = null;

	@JsonProperty("additionalParams")
	private Map<String, String> additionalParams = null;

	@JsonProperty("_links")
	private GrantLinks _links = null;

	public Grant id(final String id) {
		this.id = id;
		return this;
	}

	/**
	 * Get id
	 *
	 * @return id
	 **/
	@ApiModelProperty(required = true, value = "")
	@NotNull

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public Grant appInstanceId(final String appInstanceId) {
		this.appInstanceId = appInstanceId;
		return this;
	}

	/**
	 * Get appInstanceId
	 *
	 * @return appInstanceId
	 **/
	@ApiModelProperty(required = true, value = "")
	@NotNull

	public String getAppInstanceId() {
		return appInstanceId;
	}

	public void setAppInstanceId(final String appInstanceId) {
		this.appInstanceId = appInstanceId;
	}

	public Grant appLcmOpOccId(final String appLcmOpOccId) {
		this.appLcmOpOccId = appLcmOpOccId;
		return this;
	}

	/**
	 * Get appLcmOpOccId
	 *
	 * @return appLcmOpOccId
	 **/
	@ApiModelProperty(required = true, value = "")
	@NotNull

	public String getAppLcmOpOccId() {
		return appLcmOpOccId;
	}

	public void setAppLcmOpOccId(final String appLcmOpOccId) {
		this.appLcmOpOccId = appLcmOpOccId;
	}

	public Grant vimConnections(final List<VimConnectionInfo> vimConnections) {
		this.vimConnections = vimConnections;
		return this;
	}

	public Grant addVimConnectionsItem(final VimConnectionInfo vimConnectionsItem) {
		if (this.vimConnections == null) {
			this.vimConnections = new ArrayList<>();
		}
		this.vimConnections.add(vimConnectionsItem);
		return this;
	}

	/**
	 * Get vimConnections
	 *
	 * @return vimConnections
	 **/
	@ApiModelProperty(value = "")
	@Valid
	public List<VimConnectionInfo> getVimConnections() {
		return vimConnections;
	}

	public void setVimConnections(final List<VimConnectionInfo> vimConnections) {
		this.vimConnections = vimConnections;
	}

	public Grant zones(final List<ZoneInfo> zones) {
		this.zones = zones;
		return this;
	}

	public Grant addZonesItem(final ZoneInfo zonesItem) {
		if (this.zones == null) {
			this.zones = new ArrayList<>();
		}
		this.zones.add(zonesItem);
		return this;
	}

	/**
	 * Get zones
	 *
	 * @return zones
	 **/
	@ApiModelProperty(value = "")
	@Valid
	public List<ZoneInfo> getZones() {
		return zones;
	}

	public void setZones(final List<ZoneInfo> zones) {
		this.zones = zones;
	}

	public Grant zoneGroups(final List<ZoneGroupInfo> zoneGroups) {
		this.zoneGroups = zoneGroups;
		return this;
	}

	public Grant addZoneGroupsItem(final ZoneGroupInfo zoneGroupsItem) {
		if (this.zoneGroups == null) {
			this.zoneGroups = new ArrayList<>();
		}
		this.zoneGroups.add(zoneGroupsItem);
		return this;
	}

	/**
	 * Get zoneGroups
	 *
	 * @return zoneGroups
	 **/
	@ApiModelProperty(value = "")
	@Valid
	public List<ZoneGroupInfo> getZoneGroups() {
		return zoneGroups;
	}

	public void setZoneGroups(final List<ZoneGroupInfo> zoneGroups) {
		this.zoneGroups = zoneGroups;
	}

	public Grant addResources(final List<GrantInfo> addResources) {
		this.addResources = addResources;
		return this;
	}

	public Grant addAddResourcesItem(final GrantInfo addResourcesItem) {
		if (this.addResources == null) {
			this.addResources = new ArrayList<>();
		}
		this.addResources.add(addResourcesItem);
		return this;
	}

	/**
	 * Get addResources
	 *
	 * @return addResources
	 **/
	@ApiModelProperty(value = "")
	@Valid
	public List<GrantInfo> getAddResources() {
		return addResources;
	}

	public void setAddResources(final List<GrantInfo> addResources) {
		this.addResources = addResources;
	}

	public Grant tempResources(final List<GrantInfo> tempResources) {
		this.tempResources = tempResources;
		return this;
	}

	public Grant addTempResourcesItem(final GrantInfo tempResourcesItem) {
		if (this.tempResources == null) {
			this.tempResources = new ArrayList<>();
		}
		this.tempResources.add(tempResourcesItem);
		return this;
	}

	/**
	 * Get tempResources
	 *
	 * @return tempResources
	 **/
	@ApiModelProperty(value = "")
	@Valid
	public List<GrantInfo> getTempResources() {
		return tempResources;
	}

	public void setTempResources(final List<GrantInfo> tempResources) {
		this.tempResources = tempResources;
	}

	public Grant removeResources(final List<GrantInfo> removeResources) {
		this.removeResources = removeResources;
		return this;
	}

	public Grant addRemoveResourcesItem(final GrantInfo removeResourcesItem) {
		if (this.removeResources == null) {
			this.removeResources = new ArrayList<>();
		}
		this.removeResources.add(removeResourcesItem);
		return this;
	}

	/**
	 * Get removeResources
	 *
	 * @return removeResources
	 **/
	@ApiModelProperty(value = "")
	@Valid
	public List<GrantInfo> getRemoveResources() {
		return removeResources;
	}

	public void setRemoveResources(final List<GrantInfo> removeResources) {
		this.removeResources = removeResources;
	}

	public Grant updateResources(final List<GrantInfo> updateResources) {
		this.updateResources = updateResources;
		return this;
	}

	public Grant addUpdateResourcesItem(final GrantInfo updateResourcesItem) {
		if (this.updateResources == null) {
			this.updateResources = new ArrayList<>();
		}
		this.updateResources.add(updateResourcesItem);
		return this;
	}

	/**
	 * Get updateResources
	 *
	 * @return updateResources
	 **/
	@ApiModelProperty(value = "")
	@Valid
	public List<GrantInfo> getUpdateResources() {
		return updateResources;
	}

	public void setUpdateResources(final List<GrantInfo> updateResources) {
		this.updateResources = updateResources;
	}

	public Grant vimAssets(final VimAssets vimAssets) {
		this.vimAssets = vimAssets;
		return this;
	}

	/**
	 * Get vimAssets
	 *
	 * @return vimAssets
	 **/
	@ApiModelProperty(value = "")

	@Valid
	public VimAssets getVimAssets() {
		return vimAssets;
	}

	public void setVimAssets(final VimAssets vimAssets) {
		this.vimAssets = vimAssets;
	}

	public Grant extVirtualLinks(final List<ExtVirtualLinkData> extVirtualLinks) {
		this.extVirtualLinks = extVirtualLinks;
		return this;
	}

	public Grant addExtVirtualLinksItem(final ExtVirtualLinkData extVirtualLinksItem) {
		if (this.extVirtualLinks == null) {
			this.extVirtualLinks = new ArrayList<>();
		}
		this.extVirtualLinks.add(extVirtualLinksItem);
		return this;
	}

	/**
	 * Get extVirtualLinks
	 *
	 * @return extVirtualLinks
	 **/
	@ApiModelProperty(value = "")
	@Valid
	public List<ExtVirtualLinkData> getExtVirtualLinks() {
		return extVirtualLinks;
	}

	public void setExtVirtualLinks(final List<ExtVirtualLinkData> extVirtualLinks) {
		this.extVirtualLinks = extVirtualLinks;
	}

	public Grant additionalParams(final Map<String, String> additionalParams) {
		this.additionalParams = additionalParams;
		return this;
	}

	/**
	 * Get additionalParams
	 *
	 * @return additionalParams
	 **/
	@ApiModelProperty(value = "")

	@Valid
	public Map<String, String> getAdditionalParams() {
		return additionalParams;
	}

	public void setAdditionalParams(final Map<String, String> additionalParams) {
		this.additionalParams = additionalParams;
	}

	public Grant _links(final GrantLinks _links) {
		this._links = _links;
		return this;
	}

	/**
	 * Get _links
	 *
	 * @return _links
	 **/
	@ApiModelProperty(required = true, value = "")
	@NotNull

	@Valid
	public GrantLinks getLinks() {
		return _links;
	}

	public void setLinks(final GrantLinks _links) {
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
		final Grant grant = (Grant) o;
		return Objects.equals(this.id, grant.id) &&
				Objects.equals(this.appInstanceId, grant.appInstanceId) &&
				Objects.equals(this.appLcmOpOccId, grant.appLcmOpOccId) &&
				Objects.equals(this.vimConnections, grant.vimConnections) &&
				Objects.equals(this.zones, grant.zones) &&
				Objects.equals(this.zoneGroups, grant.zoneGroups) &&
				Objects.equals(this.addResources, grant.addResources) &&
				Objects.equals(this.tempResources, grant.tempResources) &&
				Objects.equals(this.removeResources, grant.removeResources) &&
				Objects.equals(this.updateResources, grant.updateResources) &&
				Objects.equals(this.vimAssets, grant.vimAssets) &&
				Objects.equals(this.extVirtualLinks, grant.extVirtualLinks) &&
				Objects.equals(this.additionalParams, grant.additionalParams) &&
				Objects.equals(this._links, grant._links);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, appInstanceId, appLcmOpOccId, vimConnections, zones, zoneGroups, addResources, tempResources, removeResources, updateResources, vimAssets, extVirtualLinks, additionalParams, _links);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class Grant {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    appInstanceId: ").append(toIndentedString(appInstanceId)).append("\n");
		sb.append("    appLcmOpOccId: ").append(toIndentedString(appLcmOpOccId)).append("\n");
		sb.append("    vimConnections: ").append(toIndentedString(vimConnections)).append("\n");
		sb.append("    zones: ").append(toIndentedString(zones)).append("\n");
		sb.append("    zoneGroups: ").append(toIndentedString(zoneGroups)).append("\n");
		sb.append("    addResources: ").append(toIndentedString(addResources)).append("\n");
		sb.append("    tempResources: ").append(toIndentedString(tempResources)).append("\n");
		sb.append("    removeResources: ").append(toIndentedString(removeResources)).append("\n");
		sb.append("    updateResources: ").append(toIndentedString(updateResources)).append("\n");
		sb.append("    vimAssets: ").append(toIndentedString(vimAssets)).append("\n");
		sb.append("    extVirtualLinks: ").append(toIndentedString(extVirtualLinks)).append("\n");
		sb.append("    additionalParams: ").append(toIndentedString(additionalParams)).append("\n");
		sb.append("    _links: ").append(toIndentedString(_links)).append("\n");
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
