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
package com.ubiqube.etsi.mano.em.v261.model.vnflcm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;



/**
 * IpOverEthernetAddressInfoIpAddresses
 */
@Validated

public class IpOverEthernetAddressInfoIpAddresses {
	/**
	 * The type of the IP addresses. Permitted values: IPV4, IPV6.
	 */
	public enum TypeEnum {
		IPV4("IPV4"),

		IPV6("IPV6");

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
	@Valid
	private List<String> addresses = null;

	@JsonProperty("isDynamic")
	private Boolean isDynamic = null;

	@JsonProperty("addressRange")
	private IpOverEthernetAddressInfoAddressRange addressRange = null;

	@JsonProperty("subnetId")
	private String subnetId = null;

	public IpOverEthernetAddressInfoIpAddresses type(final TypeEnum type) {
		this.type = type;
		return this;
	}

	/**
	 * The type of the IP addresses. Permitted values: IPV4, IPV6.
	 *
	 * @return type
	 **/
	@Schema(required = true, description = "The type of the IP addresses. Permitted values: IPV4, IPV6. ")
	@NotNull

	public TypeEnum getType() {
		return type;
	}

	public void setType(final TypeEnum type) {
		this.type = type;
	}

	public IpOverEthernetAddressInfoIpAddresses addresses(final List<String> addresses) {
		this.addresses = addresses;
		return this;
	}

	public IpOverEthernetAddressInfoIpAddresses addAddressesItem(final String addressesItem) {
		if (this.addresses == null) {
			this.addresses = new ArrayList<>();
		}
		this.addresses.add(addressesItem);
		return this;
	}

	/**
	 * Fixed addresses assigned (from the subnet defined by \"subnetId\" if provided). Exactly one of \"addresses\" or \"addressRange\" shall be present.
	 *
	 * @return addresses
	 **/
	@Schema(description = "Fixed addresses assigned (from the subnet defined by \"subnetId\" if provided). Exactly one of \"addresses\" or \"addressRange\" shall be present. ")

	public List<String> getAddresses() {
		return addresses;
	}

	public void setAddresses(final List<String> addresses) {
		this.addresses = addresses;
	}

	public IpOverEthernetAddressInfoIpAddresses isDynamic(final Boolean isDynamic) {
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

	public IpOverEthernetAddressInfoIpAddresses addressRange(final IpOverEthernetAddressInfoAddressRange addressRange) {
		this.addressRange = addressRange;
		return this;
	}

	/**
	 * Get addressRange
	 *
	 * @return addressRange
	 **/
	@Schema(description = "")

	@Valid

	public IpOverEthernetAddressInfoAddressRange getAddressRange() {
		return addressRange;
	}

	public void setAddressRange(final IpOverEthernetAddressInfoAddressRange addressRange) {
		this.addressRange = addressRange;
	}

	public IpOverEthernetAddressInfoIpAddresses subnetId(final String subnetId) {
		this.subnetId = subnetId;
		return this;
	}

	/**
	 * Subnet defined by the identifier of the subnet resource in the VIM. In case this attribute is present, IP addresses are bound to that subnet.
	 *
	 * @return subnetId
	 **/
	@Schema(description = "Subnet defined by the identifier of the subnet resource in the VIM. In case this attribute is present, IP addresses are bound to that subnet. ")

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
		final IpOverEthernetAddressInfoIpAddresses ipOverEthernetAddressInfoIpAddresses = (IpOverEthernetAddressInfoIpAddresses) o;
		return Objects.equals(this.type, ipOverEthernetAddressInfoIpAddresses.type) &&
				Objects.equals(this.addresses, ipOverEthernetAddressInfoIpAddresses.addresses) &&
				Objects.equals(this.isDynamic, ipOverEthernetAddressInfoIpAddresses.isDynamic) &&
				Objects.equals(this.addressRange, ipOverEthernetAddressInfoIpAddresses.addressRange) &&
				Objects.equals(this.subnetId, ipOverEthernetAddressInfoIpAddresses.subnetId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, addresses, isDynamic, addressRange, subnetId);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class IpOverEthernetAddressInfoIpAddresses {\n");

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
