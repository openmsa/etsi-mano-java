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

package com.ubiqube.etsi.mano.common.v261.model.nslcm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * This type represents information about a network address that has been assigned. It shall comply with the provisions defined in Table 6.5.3.18-1.
 */
@Schema(description = "This type represents information about a network address that has been assigned. It shall comply with the provisions defined in Table 6.5.3.18-1. ")
@Validated
public class IpOverEthernetAddressInfo {
	@JsonProperty("macAddress")
	private String macAddress = null;

	@JsonProperty("ipAddresses")
	@Valid
	private List<IpOverEthernetAddressInfoIpAddresses> ipAddresses = new ArrayList<>();

	/**
	 * The type of the IP addresses
	 */
	public enum TypeEnum {
		PV4("PV4"),

		PV6("PV6");

		private final String value;

		TypeEnum(final String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static TypeEnum fromValue(final String text) {
			for (final TypeEnum b : TypeEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	@JsonProperty("type")
	private TypeEnum type = null;

	@JsonProperty("addresses")
	private String addresses = null;

	@JsonProperty("isDynamic")
	private Boolean isDynamic = null;

	@JsonProperty("addressRange")
	private IpOverEthernetAddressInfoAddressRange addressRange = null;

	@JsonProperty("subnetId")
	private String subnetId = null;

	public IpOverEthernetAddressInfo macAddress(final String macAddress) {
		this.macAddress = macAddress;
		return this;
	}

	/**
	 * Assigned MAC address.
	 *
	 * @return macAddress
	 **/
	@Schema(required = true, description = "Assigned MAC address. ")
	@NotNull

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(final String macAddress) {
		this.macAddress = macAddress;
	}

	public IpOverEthernetAddressInfo ipAddresses(final List<IpOverEthernetAddressInfoIpAddresses> ipAddresses) {
		this.ipAddresses = ipAddresses;
		return this;
	}

	public IpOverEthernetAddressInfo addIpAddressesItem(final IpOverEthernetAddressInfoIpAddresses ipAddressesItem) {
		this.ipAddresses.add(ipAddressesItem);
		return this;
	}

	/**
	 * Addresses assigned to the CP instance. Each entry represents IP addresses assigned by fixed or dynamic IP address assignment per subnet.
	 *
	 * @return ipAddresses
	 **/
	@Schema(required = true, description = "Addresses assigned to the CP instance. Each entry represents IP addresses assigned by fixed or dynamic IP address assignment per subnet. ")
	@NotNull

	@Valid

	public List<IpOverEthernetAddressInfoIpAddresses> getIpAddresses() {
		return ipAddresses;
	}

	public void setIpAddresses(final List<IpOverEthernetAddressInfoIpAddresses> ipAddresses) {
		this.ipAddresses = ipAddresses;
	}

	public IpOverEthernetAddressInfo type(final TypeEnum type) {
		this.type = type;
		return this;
	}

	/**
	 * The type of the IP addresses
	 *
	 * @return type
	 **/
	@Schema(description = "The type of the IP addresses ")

	public TypeEnum getType() {
		return type;
	}

	public void setType(final TypeEnum type) {
		this.type = type;
	}

	public IpOverEthernetAddressInfo addresses(final String addresses) {
		this.addresses = addresses;
		return this;
	}

	/**
	 * Fixed addresses assigned (from the subnet defined by \"subnetId\" if provided). See note.
	 *
	 * @return addresses
	 **/
	@Schema(required = true, description = "Fixed addresses assigned (from the subnet defined by \"subnetId\" if provided). See note. ")
	@NotNull

	public String getAddresses() {
		return addresses;
	}

	public void setAddresses(final String addresses) {
		this.addresses = addresses;
	}

	public IpOverEthernetAddressInfo isDynamic(final Boolean isDynamic) {
		this.isDynamic = isDynamic;
		return this;
	}

	/**
	 * Indicates whether this set of addresses was assigned dynamically (true) or based on address information provided as input from the API consumer (false). Shall be present if \"addresses\" is present and shall be absent otherwise.
	 *
	 * @return isDynamic
	 **/
	@Schema(description = "Indicates whether this set of addresses was assigned dynamically (true) or based on address information provided as input from the API consumer (false). Shall be present if \"addresses\" is present and shall be absent otherwise. ")

	public Boolean isIsDynamic() {
		return isDynamic;
	}

	public void setIsDynamic(final Boolean isDynamic) {
		this.isDynamic = isDynamic;
	}

	public IpOverEthernetAddressInfo addressRange(final IpOverEthernetAddressInfoAddressRange addressRange) {
		this.addressRange = addressRange;
		return this;
	}

	/**
	 * Get addressRange
	 *
	 * @return addressRange
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid

	public IpOverEthernetAddressInfoAddressRange getAddressRange() {
		return addressRange;
	}

	public void setAddressRange(final IpOverEthernetAddressInfoAddressRange addressRange) {
		this.addressRange = addressRange;
	}

	/**
	 * Lowest IP address belonging to the range
	 *
	 * @return minAddress
	 **/

	public IpOverEthernetAddressInfo subnetId(final String subnetId) {
		this.subnetId = subnetId;
		return this;
	}

	/**
	 * Subnet defined by the identifier of the subnet resource in the VIM. In case this attribute is present, IP addresses are bound to that subnet.
	 *
	 * @return subnetId
	 **/
	@Schema(required = true, description = "Subnet defined by the identifier of the subnet resource in the VIM. In case this attribute is present, IP addresses are bound to that subnet. ")
	@NotNull

	public String getSubnetId() {
		return subnetId;
	}

	public void setSubnetId(final String subnetId) {
		this.subnetId = subnetId;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final IpOverEthernetAddressInfo ipOverEthernetAddressInfo = (IpOverEthernetAddressInfo) o;
		return Objects.equals(this.macAddress, ipOverEthernetAddressInfo.macAddress) &&
				Objects.equals(this.ipAddresses, ipOverEthernetAddressInfo.ipAddresses) &&
				Objects.equals(this.type, ipOverEthernetAddressInfo.type) &&
				Objects.equals(this.addresses, ipOverEthernetAddressInfo.addresses) &&
				Objects.equals(this.isDynamic, ipOverEthernetAddressInfo.isDynamic) &&
				Objects.equals(this.addressRange, ipOverEthernetAddressInfo.addressRange) &&
				Objects.equals(this.subnetId, ipOverEthernetAddressInfo.subnetId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(macAddress, ipAddresses, type, addresses, isDynamic, addressRange, subnetId);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class IpOverEthernetAddressInfo {\n");

		sb.append("    macAddress: ").append(toIndentedString(macAddress)).append("\n");
		sb.append("    ipAddresses: ").append(toIndentedString(ipAddresses)).append("\n");
		sb.append("    type: ").append(toIndentedString(type)).append("\n");
		sb.append("    addresses: ").append(toIndentedString(addresses)).append("\n");
		sb.append("    isDynamic: ").append(toIndentedString(isDynamic)).append("\n");
		sb.append("    addressRange: ").append(toIndentedString(addressRange)).append("\n");
		sb.append("    subnetId: ").append(toIndentedString(subnetId)).append("\n");
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
