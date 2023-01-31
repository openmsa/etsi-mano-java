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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This type represents information about a network address that has been
 * assigned. NOTE 1: At least one of \&quot;macAddress\&quot; or
 * \&quot;ipAddresses\&quot; shall be present. NOTE 2: Exactly one of
 * \&quot;addresses\&quot; or \&quot;addressRange\&quot; shall be present. NOTE
 * 3: If the Cp instance represents a subport in a trunk,
 * \&quot;segmentationId\&quot; shall be present. Otherwise it shall not be
 * present. NOTE 4: Depending on the NFVI networking infrastructure, the
 * \&quot;segmentationId\&quot; may indicate the actual network segment value
 * (e.g. vlan Id, Vxlan segmentation id, etc.) used in the transport header of
 * the packets or it may be an identifier used between the application and the
 * NFVI networking infrastructure to identify the network sub-interface of the
 * trunk port in question. In the latter case the NFVI infrastructure will map
 * this local \&quot;segmentationId\&quot; to whatever
 * \&quot;segmentationId\&quot; is actually used by the NFVI&#x27;s transport
 * technology.
 */
@Schema(description = "This type represents information about a network address that has been assigned. NOTE 1: At least one of \"macAddress\" or \"ipAddresses\" shall be present. NOTE 2: Exactly one of \"addresses\" or \"addressRange\" shall be present. NOTE 3: If the Cp instance represents a subport in a trunk, \"segmentationId\" shall be present. Otherwise it shall not be present. NOTE 4: Depending on the NFVI networking infrastructure, the \"segmentationId\" may indicate the actual network segment value (e.g. vlan Id, Vxlan segmentation id, etc.) used in the transport header of the packets or it may be an identifier used between the application and the NFVI networking infrastructure to identify the network sub-interface of the trunk port in question. In the latter case the NFVI infrastructure will map this local \"segmentationId\" to whatever \"segmentationId\" is actually used by the NFVI's transport technology. ")
@Validated

public class IpOverEthernetAddressInfo implements AnyOfIpOverEthernetAddressInfo {
	@JsonProperty("macAddress")
	private String macAddress = null;

	@JsonProperty("segmentationId")
	private String segmentationId = null;

	@JsonProperty("ipAddresses")
	@Valid
	private List<IpOverEthernetAddressInfoIpAddresses> ipAddresses = null;

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
	private IpOverEthernetAddressInfoAddressRange1 addressRange = null;

	@JsonProperty("subnetId")
	private String subnetId = null;

	public IpOverEthernetAddressInfo macAddress(final String macAddress) {
		this.macAddress = macAddress;
		return this;
	}

	/**
	 * Get macAddress
	 *
	 * @return macAddress
	 **/
	@Schema(description = "")

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(final String macAddress) {
		this.macAddress = macAddress;
	}

	public IpOverEthernetAddressInfo segmentationId(final String segmentationId) {
		this.segmentationId = segmentationId;
		return this;
	}

	/**
	 * Identification of the network segment to which the Cp instance connects to.
	 * See note 3 and note 4.
	 *
	 * @return segmentationId
	 **/
	@Schema(description = "Identification of the network segment to which the Cp instance connects to. See note 3 and note 4. ")

	public String getSegmentationId() {
		return segmentationId;
	}

	public void setSegmentationId(final String segmentationId) {
		this.segmentationId = segmentationId;
	}

	public IpOverEthernetAddressInfo ipAddresses(final List<IpOverEthernetAddressInfoIpAddresses> ipAddresses) {
		this.ipAddresses = ipAddresses;
		return this;
	}

	public IpOverEthernetAddressInfo addIpAddressesItem(final IpOverEthernetAddressInfoIpAddresses ipAddressesItem) {
		if (this.ipAddresses == null) {
			this.ipAddresses = new ArrayList<>();
		}
		this.ipAddresses.add(ipAddressesItem);
		return this;
	}

	/**
	 * Addresses assigned to the CP instance. Each entry represents IP addresses
	 * assigned by fixed or dynamic IP address assignment per subnet. See note 1.
	 *
	 * @return ipAddresses
	 **/
	@Schema(description = "Addresses assigned to the CP instance. Each entry represents IP addresses assigned by fixed or dynamic IP address assignment per subnet. See note 1. ")
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
	 * Get addresses
	 *
	 * @return addresses
	 **/
	@Schema(description = "")

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
	 * Indicates whether this set of addresses was assigned dynamically (true) or
	 * based on address information provided as input from the API consumer (false).
	 * Shall be present if \"addresses\" is present and shall be absent otherwise.
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

	public IpOverEthernetAddressInfo addressRange(final IpOverEthernetAddressInfoAddressRange1 addressRange) {
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
	public IpOverEthernetAddressInfoAddressRange1 getAddressRange() {
		return addressRange;
	}

	public void setAddressRange(final IpOverEthernetAddressInfoAddressRange1 addressRange) {
		this.addressRange = addressRange;
	}

	public IpOverEthernetAddressInfo subnetId(final String subnetId) {
		this.subnetId = subnetId;
		return this;
	}

	/**
	 * Get subnetId
	 *
	 * @return subnetId
	 **/
	@Schema(description = "")

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
				Objects.equals(this.segmentationId, ipOverEthernetAddressInfo.segmentationId) &&
				Objects.equals(this.ipAddresses, ipOverEthernetAddressInfo.ipAddresses) &&
				Objects.equals(this.type, ipOverEthernetAddressInfo.type) &&
				Objects.equals(this.addresses, ipOverEthernetAddressInfo.addresses) &&
				Objects.equals(this.isDynamic, ipOverEthernetAddressInfo.isDynamic) &&
				Objects.equals(this.addressRange, ipOverEthernetAddressInfo.addressRange) &&
				Objects.equals(this.subnetId, ipOverEthernetAddressInfo.subnetId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(macAddress, segmentationId, ipAddresses, type, addresses, isDynamic, addressRange, subnetId);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class IpOverEthernetAddressInfo {\n");

		sb.append("    macAddress: ").append(toIndentedString(macAddress)).append("\n");
		sb.append("    segmentationId: ").append(toIndentedString(segmentationId)).append("\n");
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
