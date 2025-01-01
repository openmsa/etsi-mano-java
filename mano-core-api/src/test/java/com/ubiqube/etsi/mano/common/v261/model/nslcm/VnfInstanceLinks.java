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

package com.ubiqube.etsi.mano.common.v261.model.nslcm;

import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.etsi.mano.common.v261.model.Link;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * Links to resources related to this resource.
 */
@Schema(description = "Links to resources related to this resource. ")
public class VnfInstanceLinks {
	@JsonProperty("self")
	private Link self = null;

	@JsonProperty("indicators")
	private Link indicators = null;

	@JsonProperty("instantiate")
	private Link instantiate = null;

	@JsonProperty("terminate")
	private Link terminate = null;

	@JsonProperty("scale")
	private Link scale = null;

	@JsonProperty("scaleToLevel")
	private Link scaleToLevel = null;

	@JsonProperty("changeFlavour")
	private Link changeFlavour = null;

	@JsonProperty("heal")
	private Link heal = null;

	@JsonProperty("operate")
	private Link operate = null;

	@JsonProperty("changeExtConn")
	private Link changeExtConn = null;

	public VnfInstanceLinks self(final Link self) {
		this.self = self;
		return this;
	}

	/**
	 * URI of this resource.
	 *
	 * @return self
	 **/
	@JsonProperty("self")
	@Schema(required = true, description = "URI of this resource.")
	@NotNull
	public Link getSelf() {
		return self;
	}

	public void setSelf(final Link self) {
		this.self = self;
	}

	public VnfInstanceLinks indicators(final Link indicators) {
		this.indicators = indicators;
		return this;
	}

	/**
	 * Indicators related to this VNF instance, if applicable.
	 *
	 * @return indicators
	 **/
	@JsonProperty("indicators")
	@Schema(description = "Indicators related to this VNF instance, if applicable.")
	public Link getIndicators() {
		return indicators;
	}

	public void setIndicators(final Link indicators) {
		this.indicators = indicators;
	}

	public VnfInstanceLinks instantiate(final Link instantiate) {
		this.instantiate = instantiate;
		return this;
	}

	/**
	 * Link to the \&quot;instantiate\&quot; task resource, if the related operation
	 * is possible based on the current status of this VNF instance resource (i.e.
	 * VNF instance in NOT_INSTANTIATED state).
	 *
	 * @return instantiate
	 **/
	@JsonProperty("instantiate")
	@Schema(description = "Link to the \"instantiate\" task resource, if the related operation is possible based on the current status of this VNF instance resource (i.e. VNF instance in NOT_INSTANTIATED state). ")
	public Link getInstantiate() {
		return instantiate;
	}

	public void setInstantiate(final Link instantiate) {
		this.instantiate = instantiate;
	}

	public VnfInstanceLinks terminate(final Link terminate) {
		this.terminate = terminate;
		return this;
	}

	/**
	 * Link to the \&quot;terminate\&quot; task resource, if the related operation
	 * is possible based on the current status of this VNF instance resource (i.e.
	 * VNF instance is in INSTANTIATED state).
	 *
	 * @return terminate
	 **/
	@JsonProperty("terminate")
	@Schema(description = "Link to the \"terminate\" task resource, if the related operation is possible based on the current status of this VNF instance resource (i.e. VNF instance is in INSTANTIATED state). ")
	public Link getTerminate() {
		return terminate;
	}

	public void setTerminate(final Link terminate) {
		this.terminate = terminate;
	}

	public VnfInstanceLinks scale(final Link scale) {
		this.scale = scale;
		return this;
	}

	/**
	 * Link to the \&quot;scale\&quot; task resource, if the related operation is
	 * supported for this VNF instance, and is possible based on the current status
	 * of this VNF instance resource (i.e. VNF instance is in INSTANTIATED state).
	 *
	 * @return scale
	 **/
	@JsonProperty("scale")
	@Schema(description = "Link to the \"scale\" task resource, if the related operation is supported for this VNF instance, and is possible based on the current status of this VNF instance resource (i.e. VNF instance is in INSTANTIATED state). ")
	public Link getScale() {
		return scale;
	}

	public void setScale(final Link scale) {
		this.scale = scale;
	}

	public VnfInstanceLinks scaleToLevel(final Link scaleToLevel) {
		this.scaleToLevel = scaleToLevel;
		return this;
	}

	/**
	 * Link to the \&quot;scale_to_level\&quot; task resource, if the related
	 * operation is supported for this VNF instance, and is possible based on the
	 * current status of this VNF instance resource (i.e. VNF instance is in
	 * INSTANTIATED state).
	 *
	 * @return scaleToLevel
	 **/
	@JsonProperty("scaleToLevel")
	@Schema(description = "Link to the \"scale_to_level\" task resource, if the related operation is supported for this VNF instance, and is possible based on the current status of this VNF instance resource (i.e. VNF instance is in INSTANTIATED state). ")
	public Link getScaleToLevel() {
		return scaleToLevel;
	}

	public void setScaleToLevel(final Link scaleToLevel) {
		this.scaleToLevel = scaleToLevel;
	}

	public VnfInstanceLinks changeFlavour(final Link changeFlavour) {
		this.changeFlavour = changeFlavour;
		return this;
	}

	/**
	 * Link to the \&quot;change_flavour\&quot; task resource, if the related
	 * operation is supported for this VNF instance, and is possible based on the
	 * current status of this VNF instance resource (i.e. VNF instance is in
	 * INSTANTIATED state).
	 *
	 * @return changeFlavour
	 **/
	@JsonProperty("changeFlavour")
	@Schema(description = "Link to the \"change_flavour\" task resource, if the related operation is supported for this VNF instance, and is possible based on the current status of this VNF instance resource (i.e. VNF instance is in INSTANTIATED state). ")
	public Link getChangeFlavour() {
		return changeFlavour;
	}

	public void setChangeFlavour(final Link changeFlavour) {
		this.changeFlavour = changeFlavour;
	}

	public VnfInstanceLinks heal(final Link heal) {
		this.heal = heal;
		return this;
	}

	/**
	 * Link to the \&quot;heal\&quot; task resource, if the related operation is
	 * supported for this VNF instance, and is possible based on the current status
	 * of this VNF instance resource (i.e. VNF instance is in INSTANTIATED state).
	 *
	 * @return heal
	 **/
	@JsonProperty("heal")
	@Schema(description = "Link to the \"heal\" task resource, if the related operation is supported for this VNF instance, and is possible based on the current status of this VNF instance resource (i.e. VNF instance is in INSTANTIATED state). ")
	public Link getHeal() {
		return heal;
	}

	public void setHeal(final Link heal) {
		this.heal = heal;
	}

	public VnfInstanceLinks operate(final Link operate) {
		this.operate = operate;
		return this;
	}

	/**
	 * Link to the \&quot;operate\&quot; task resource, if the related operation is
	 * supported for this VNF instance, and is possible based on the current status
	 * of this VNF instance resource (i.e. VNF instance is in INSTANTIATED state).
	 *
	 * @return operate
	 **/
	@JsonProperty("operate")
	@Schema(description = "Link to the \"operate\" task resource, if the related operation is supported for this VNF instance, and is possible based on the current status of this VNF instance resource (i.e. VNF instance is in INSTANTIATED state). ")
	public Link getOperate() {
		return operate;
	}

	public void setOperate(final Link operate) {
		this.operate = operate;
	}

	public VnfInstanceLinks changeExtConn(final Link changeExtConn) {
		this.changeExtConn = changeExtConn;
		return this;
	}

	/**
	 * Link to the \&quot;change_ext_conn\&quot; task resource, if the related
	 * operation is possible based on the current status of this VNF instance
	 * resource (i.e. VNF instance is in INSTANTIATED state).
	 *
	 * @return changeExtConn
	 **/
	@JsonProperty("changeExtConn")
	@Schema(description = "Link to the \"change_ext_conn\" task resource, if the related operation is possible based on the current status of this VNF instance resource (i.e. VNF instance is in INSTANTIATED state). ")
	public Link getChangeExtConn() {
		return changeExtConn;
	}

	public void setChangeExtConn(final Link changeExtConn) {
		this.changeExtConn = changeExtConn;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class VnfInstanceLinks {\n");

		sb.append("    self: ").append(toIndentedString(self)).append("\n");
		sb.append("    indicators: ").append(toIndentedString(indicators)).append("\n");
		sb.append("    instantiate: ").append(toIndentedString(instantiate)).append("\n");
		sb.append("    terminate: ").append(toIndentedString(terminate)).append("\n");
		sb.append("    scale: ").append(toIndentedString(scale)).append("\n");
		sb.append("    scaleToLevel: ").append(toIndentedString(scaleToLevel)).append("\n");
		sb.append("    changeFlavour: ").append(toIndentedString(changeFlavour)).append("\n");
		sb.append("    heal: ").append(toIndentedString(heal)).append("\n");
		sb.append("    operate: ").append(toIndentedString(operate)).append("\n");
		sb.append("    changeExtConn: ").append(toIndentedString(changeExtConn)).append("\n");
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
