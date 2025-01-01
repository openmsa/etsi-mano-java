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
import jakarta.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * This type represents describes a group of CPs and/or SAPs pairs associated to
 * the same position in an NFP. It shall comply with the provisions defined in
 * Table 6.5.3.71-1.
 */
@Schema(description = "This type represents describes a group of CPs and/or SAPs pairs associated to  the same position in an NFP. It shall comply with the provisions defined in  Table 6.5.3.71-1. ")
@Validated


public class CpGroupInfo {
	@JsonProperty("cpPairInfo")
	@Valid
	private List<CpPairInfo> cpPairInfo = null;

	/**
	 * Identifies a rule to apply to forward traffic to the ingress CPs or SAPs of
	 * the group. Permitted values: * ALL = Traffic flows shall be forwarded
	 * simultaneously to all CPs or SAPs of the group. * LB = Traffic flows shall be
	 * forwarded to one CP or SAP of the group selected based on a loadbalancing
	 * algorithm.
	 */
	public enum ForwardingBehaviourEnum {
		ALL("ALL"),

		LB("LB");

		private final String value;

		ForwardingBehaviourEnum(final String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static ForwardingBehaviourEnum fromValue(final String text) {
			for (final ForwardingBehaviourEnum b : ForwardingBehaviourEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	@JsonProperty("forwardingBehaviour")
	private ForwardingBehaviourEnum forwardingBehaviour = null;

	@JsonProperty("forwardingBehaviourInputParameters")
	private ForwardingBehaviourInputParameters forwardingBehaviourInputParameters = null;

	public CpGroupInfo cpPairInfo(final List<CpPairInfo> cpPairInfo) {
		this.cpPairInfo = cpPairInfo;
		return this;
	}

	public CpGroupInfo addCpPairInfoItem(final CpPairInfo cpPairInfoItem) {
		if (this.cpPairInfo == null) {
			this.cpPairInfo = new ArrayList<>();
		}
		this.cpPairInfo.add(cpPairInfoItem);
		return this;
	}

	/**
	 * One or more pair(s) of ingress and egress CPs or SAPs which the NFP passes
	 * by. All CP or SAP pairs in a group shall be instantiated from connection
	 * point descriptors or service access point descriptors referenced in the
	 * corresponding NfpPositionDesc.
	 * 
	 * @return cpPairInfo
	 **/
	@Schema(description = "One or more pair(s) of ingress and egress CPs or SAPs which the NFP passes by. All CP or SAP pairs in a group shall be instantiated from connection point  descriptors or service access point descriptors referenced in the corresponding  NfpPositionDesc. ")

	@Valid
	@Size(min = 1)
	public List<CpPairInfo> getCpPairInfo() {
		return cpPairInfo;
	}

	public void setCpPairInfo(final List<CpPairInfo> cpPairInfo) {
		this.cpPairInfo = cpPairInfo;
	}

	public CpGroupInfo forwardingBehaviour(final ForwardingBehaviourEnum forwardingBehaviour) {
		this.forwardingBehaviour = forwardingBehaviour;
		return this;
	}

	/**
	 * Identifies a rule to apply to forward traffic to the ingress CPs or SAPs of
	 * the group. Permitted values: * ALL = Traffic flows shall be forwarded
	 * simultaneously to all CPs or SAPs of the group. * LB = Traffic flows shall be
	 * forwarded to one CP or SAP of the group selected based on a loadbalancing
	 * algorithm.
	 * 
	 * @return forwardingBehaviour
	 **/
	@Schema(description = "Identifies a rule to apply to forward traffic to the ingress CPs or SAPs of  the group. Permitted values: * ALL = Traffic flows shall be forwarded simultaneously to all CPs or SAPs  of the group. * LB = Traffic flows shall be forwarded to one CP or SAP of the group selected  based on a loadbalancing algorithm. ")

	public ForwardingBehaviourEnum getForwardingBehaviour() {
		return forwardingBehaviour;
	}

	public void setForwardingBehaviour(final ForwardingBehaviourEnum forwardingBehaviour) {
		this.forwardingBehaviour = forwardingBehaviour;
	}

	public CpGroupInfo forwardingBehaviourInputParameters(final ForwardingBehaviourInputParameters forwardingBehaviourInputParameters) {
		this.forwardingBehaviourInputParameters = forwardingBehaviourInputParameters;
		return this;
	}

	/**
	 * Provides input parameters to configure the forwarding behaviour (e.g.
	 * identifies a load balancing algorithm and criteria).
	 * 
	 * @return forwardingBehaviourInputParameters
	 **/
	@Schema(description = "Provides input parameters to configure the forwarding behaviour (e.g. identifies  a load balancing algorithm and criteria). ")

	@Valid

	public ForwardingBehaviourInputParameters getForwardingBehaviourInputParameters() {
		return forwardingBehaviourInputParameters;
	}

	public void setForwardingBehaviourInputParameters(final ForwardingBehaviourInputParameters forwardingBehaviourInputParameters) {
		this.forwardingBehaviourInputParameters = forwardingBehaviourInputParameters;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final CpGroupInfo cpGroupInfo = (CpGroupInfo) o;
		return Objects.equals(this.cpPairInfo, cpGroupInfo.cpPairInfo) &&
				Objects.equals(this.forwardingBehaviour, cpGroupInfo.forwardingBehaviour) &&
				Objects.equals(this.forwardingBehaviourInputParameters, cpGroupInfo.forwardingBehaviourInputParameters);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpPairInfo, forwardingBehaviour, forwardingBehaviourInputParameters);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class CpGroupInfo {\n");

		sb.append("    cpPairInfo: ").append(toIndentedString(cpPairInfo)).append("\n");
		sb.append("    forwardingBehaviour: ").append(toIndentedString(forwardingBehaviour)).append("\n");
		sb.append("    forwardingBehaviourInputParameters: ").append(toIndentedString(forwardingBehaviourInputParameters)).append("\n");
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
