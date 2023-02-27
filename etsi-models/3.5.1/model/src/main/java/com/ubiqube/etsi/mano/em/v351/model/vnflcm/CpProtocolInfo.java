/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
package com.ubiqube.etsi.mano.em.v351.model.vnflcm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ubiqube.etsi.mano.em.v351.model.vnflcm.IpOverEthernetAddressInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * This type describes the protocol layer(s) that a CP uses together with protocol-related information, like addresses.  It shall comply with the provisions defined in table 5.5.3.9b-1. NOTE: This attribute allows to signal the addition of further types of layer and protocol in future versions of the        present document in a backwards-compatible way. In the current version of the present document, only IP over        Ethernet is supported. 
 */
@Schema(description = "This type describes the protocol layer(s) that a CP uses together with protocol-related information, like addresses.  It shall comply with the provisions defined in table 5.5.3.9b-1. NOTE: This attribute allows to signal the addition of further types of layer and protocol in future versions of the        present document in a backwards-compatible way. In the current version of the present document, only IP over        Ethernet is supported. ")
@Validated


public class CpProtocolInfo   {
  /**
   * The identifier of layer(s) and protocol(s) associated to the network address information. Permitted values: IP_OVER_ETHERNET See note. 
   */
  public enum LayerProtocolEnum {
    ETHERNET("IP_OVER_ETHERNET");

    private String value;

    LayerProtocolEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static LayerProtocolEnum fromValue(String text) {
      for (LayerProtocolEnum b : LayerProtocolEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("layerProtocol")
  private LayerProtocolEnum layerProtocol = null;

  @JsonProperty("ipOverEthernet")
  private IpOverEthernetAddressInfo ipOverEthernet = null;

  public CpProtocolInfo layerProtocol(LayerProtocolEnum layerProtocol) {
    this.layerProtocol = layerProtocol;
    return this;
  }

  /**
   * The identifier of layer(s) and protocol(s) associated to the network address information. Permitted values: IP_OVER_ETHERNET See note. 
   * @return layerProtocol
   **/
  @Schema(required = true, description = "The identifier of layer(s) and protocol(s) associated to the network address information. Permitted values: IP_OVER_ETHERNET See note. ")
      @NotNull

    public LayerProtocolEnum getLayerProtocol() {
    return layerProtocol;
  }

  public void setLayerProtocol(LayerProtocolEnum layerProtocol) {
    this.layerProtocol = layerProtocol;
  }

  public CpProtocolInfo ipOverEthernet(IpOverEthernetAddressInfo ipOverEthernet) {
    this.ipOverEthernet = ipOverEthernet;
    return this;
  }

  /**
   * Get ipOverEthernet
   * @return ipOverEthernet
   **/
  @Schema(description = "")
  
    @Valid
    public IpOverEthernetAddressInfo getIpOverEthernet() {
    return ipOverEthernet;
  }

  public void setIpOverEthernet(IpOverEthernetAddressInfo ipOverEthernet) {
    this.ipOverEthernet = ipOverEthernet;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CpProtocolInfo cpProtocolInfo = (CpProtocolInfo) o;
    return Objects.equals(this.layerProtocol, cpProtocolInfo.layerProtocol) &&
        Objects.equals(this.ipOverEthernet, cpProtocolInfo.ipOverEthernet);
  }

  @Override
  public int hashCode() {
    return Objects.hash(layerProtocol, ipOverEthernet);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CpProtocolInfo {\n");
    
    sb.append("    layerProtocol: ").append(toIndentedString(layerProtocol)).append("\n");
    sb.append("    ipOverEthernet: ").append(toIndentedString(ipOverEthernet)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
