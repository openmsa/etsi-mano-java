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

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.ubiqube.etsi.mano.nfvo.v431.model.nslcm.CpProtocolData;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * This type represents an externally provided link port, or a network attachment definition resource of secondary container cluster network, or network address information per  instance of a VNF external connection point. In the case of VM-based deployment of the VNFC exposing the external CP: In case a link port is provided, the NFVO shall use that link port when connecting the VNF external CP to the external VL. • In case a link port is not provided, the NFVO or VNFM shall create a link port on the external VL, and use that link port to connect the VNF external CP to the external VL. In the case of  container-based deployment of the VNFC exposing the external CP, the NFVO and VNFM shall use the network attachment definition resource of secondary container cluster  network when connecting the CP to the external VL. NOTE 1:  The following conditions apply to the attributes \&quot;linkPortId\&quot; and \&quot;cpProtocolData\&quot;   for an external CP instance connected or to be connected to a virtual network not   categorized as secondary container cluster network:   - At least one of the \&quot;linkPortId\&quot; and \&quot;cpProtocolData\&quot; attributes shall be present for   an external CP instance representing a subport that is to be created, or an external CP instance   that is to be created by creating the corresponding VNFC or VNF instance during the current or   a subsequent LCM operation, or for an existing external CP instance that is to be re-configured   or added to a particular external virtual link.   - If the \&quot;cpProtocolData\&quot; attribute is absent, the \&quot;linkPortId\&quot; attribute shall be provided  referencing a pre-created link port with pre-configured address information.   - If both \&quot;cpProtocolData\&quot; and \&quot;linkportId\&quot; are provided, the API consumer shall ensure that   the cpProtocolData can be used with the pre-created link port referenced by \&quot;linkPortId\&quot;. NOTE 2: In case the NFVO manages its own identifier space, the NFVO may remap this identifier when communicating with the VNFM. If the NFVO knows that there can be an identifier collision when communicating with the VNFM by using the identifier from the OSS/BSS, the NFVO shall remap it. NOTE 3: The following conditions apply to the attributes “netAttDefResourceId” and “cpProtocolData” for an external CP instance connected or to be connected to a secondary container cluster network; The \&quot;netAttDefResourceId\&quot; and \&quot;cpProtocolData\&quot; attributes shall both be absent for the deletion of an existing external CP instance addressed by \&quot;cpInstanceId\&quot;. At least one of these attributes shall be present for a to-be-created external CP instance or an existing external CP instance. NOTE 4: Cardinality greater than 1 is only applicable for specific cases where more than one network attachment definition resource is needed to fulfil the connectivity requirements of the external CP, e.g. to build a link redundant mated pair in SR-IOV cases. When more than one \&quot;netAttDefResourceId\&quot; is indicated, all shall belong to the same namespace as defined by the corresponding  \&quot;netAttDefResourceNamespace\&quot; attribute in the \&quot;NetAttDefResourceData\&quot;. NOTE 5: Either \&quot;linkPortId\&quot; or \&quot;netAttDefResourceId\&quot; may be included, but not both. 
 */
@Schema(description = "This type represents an externally provided link port, or a network attachment definition resource of secondary container cluster network, or network address information per  instance of a VNF external connection point. In the case of VM-based deployment of the VNFC exposing the external CP: In case a link port is provided, the NFVO shall use that link port when connecting the VNF external CP to the external VL. • In case a link port is not provided, the NFVO or VNFM shall create a link port on the external VL, and use that link port to connect the VNF external CP to the external VL. In the case of  container-based deployment of the VNFC exposing the external CP, the NFVO and VNFM shall use the network attachment definition resource of secondary container cluster  network when connecting the CP to the external VL. NOTE 1:  The following conditions apply to the attributes \"linkPortId\" and \"cpProtocolData\"   for an external CP instance connected or to be connected to a virtual network not   categorized as secondary container cluster network:   - At least one of the \"linkPortId\" and \"cpProtocolData\" attributes shall be present for   an external CP instance representing a subport that is to be created, or an external CP instance   that is to be created by creating the corresponding VNFC or VNF instance during the current or   a subsequent LCM operation, or for an existing external CP instance that is to be re-configured   or added to a particular external virtual link.   - If the \"cpProtocolData\" attribute is absent, the \"linkPortId\" attribute shall be provided  referencing a pre-created link port with pre-configured address information.   - If both \"cpProtocolData\" and \"linkportId\" are provided, the API consumer shall ensure that   the cpProtocolData can be used with the pre-created link port referenced by \"linkPortId\". NOTE 2: In case the NFVO manages its own identifier space, the NFVO may remap this identifier when communicating with the VNFM. If the NFVO knows that there can be an identifier collision when communicating with the VNFM by using the identifier from the OSS/BSS, the NFVO shall remap it. NOTE 3: The following conditions apply to the attributes “netAttDefResourceId” and “cpProtocolData” for an external CP instance connected or to be connected to a secondary container cluster network; The \"netAttDefResourceId\" and \"cpProtocolData\" attributes shall both be absent for the deletion of an existing external CP instance addressed by \"cpInstanceId\". At least one of these attributes shall be present for a to-be-created external CP instance or an existing external CP instance. NOTE 4: Cardinality greater than 1 is only applicable for specific cases where more than one network attachment definition resource is needed to fulfil the connectivity requirements of the external CP, e.g. to build a link redundant mated pair in SR-IOV cases. When more than one \"netAttDefResourceId\" is indicated, all shall belong to the same namespace as defined by the corresponding  \"netAttDefResourceNamespace\" attribute in the \"NetAttDefResourceData\". NOTE 5: Either \"linkPortId\" or \"netAttDefResourceId\" may be included, but not both. ")
@Validated


public class VnfExtCpConfig  implements OneOfVnfExtCpConfig {
  @JsonProperty("parentCpConfigId")
  private String parentCpConfigId = null;

  @JsonProperty("linkPortId")
  private String linkPortId = null;

  @JsonProperty("createExtLinkPort")
  private Boolean createExtLinkPort = null;

  @JsonProperty("cpProtocolData")
  @Valid
  private List<CpProtocolData> cpProtocolData = null;

  @JsonProperty("netAttDefResourceId")
  @Valid
  private List<String> netAttDefResourceId = null;

  public VnfExtCpConfig parentCpConfigId(String parentCpConfigId) {
    this.parentCpConfigId = parentCpConfigId;
    return this;
  }

  /**
   * Get parentCpConfigId
   * @return parentCpConfigId
   **/
  @Schema(description = "")
  
    public String getParentCpConfigId() {
    return parentCpConfigId;
  }

  public void setParentCpConfigId(String parentCpConfigId) {
    this.parentCpConfigId = parentCpConfigId;
  }

  public VnfExtCpConfig linkPortId(String linkPortId) {
    this.linkPortId = linkPortId;
    return this;
  }

  /**
   * Get linkPortId
   * @return linkPortId
   **/
  @Schema(description = "")
  
    public String getLinkPortId() {
    return linkPortId;
  }

  public void setLinkPortId(String linkPortId) {
    this.linkPortId = linkPortId;
  }

  public VnfExtCpConfig createExtLinkPort(Boolean createExtLinkPort) {
    this.createExtLinkPort = createExtLinkPort;
    return this;
  }

  /**
   * Indicates the need to create a dedicated link port for the external CP. If set to True, a link port is created. If set to False, no link port is created. This attribute is only applicable for external CP instances without a floating IP address that expose a VIP CP instance for which a dedicated IP address is allocated. It shall be present in that case and shall be absent otherwise. 
   * @return createExtLinkPort
   **/
  @Schema(description = "Indicates the need to create a dedicated link port for the external CP. If set to True, a link port is created. If set to False, no link port is created. This attribute is only applicable for external CP instances without a floating IP address that expose a VIP CP instance for which a dedicated IP address is allocated. It shall be present in that case and shall be absent otherwise. ")
  
    public Boolean isCreateExtLinkPort() {
    return createExtLinkPort;
  }

  public void setCreateExtLinkPort(Boolean createExtLinkPort) {
    this.createExtLinkPort = createExtLinkPort;
  }

  public VnfExtCpConfig cpProtocolData(List<CpProtocolData> cpProtocolData) {
    this.cpProtocolData = cpProtocolData;
    return this;
  }

  public VnfExtCpConfig addCpProtocolDataItem(CpProtocolData cpProtocolDataItem) {
    if (this.cpProtocolData == null) {
      this.cpProtocolData = new ArrayList<>();
    }
    this.cpProtocolData.add(cpProtocolDataItem);
    return this;
  }

  /**
   * Parameters for configuring the network protocols on the link port that connects the CP to a VL. See notes 1 and 3. 
   * @return cpProtocolData
   **/
  @Schema(description = "Parameters for configuring the network protocols on the link port that connects the CP to a VL. See notes 1 and 3. ")
      @Valid
    public List<CpProtocolData> getCpProtocolData() {
    return cpProtocolData;
  }

  public void setCpProtocolData(List<CpProtocolData> cpProtocolData) {
    this.cpProtocolData = cpProtocolData;
  }

  public VnfExtCpConfig netAttDefResourceId(List<String> netAttDefResourceId) {
    this.netAttDefResourceId = netAttDefResourceId;
    return this;
  }

  public VnfExtCpConfig addNetAttDefResourceIdItem(String netAttDefResourceIdItem) {
    if (this.netAttDefResourceId == null) {
      this.netAttDefResourceId = new ArrayList<>();
    }
    this.netAttDefResourceId.add(netAttDefResourceIdItem);
    return this;
  }

  /**
   * Identifier of the \"NetAttDefResourceData\" structure that provides the specification of the interface to attach the external CP to a secondary container cluster network.  It is only applicable if the external CP is connected or to be connected to a secondary container cluster network. It shall not be present if the external CP is related to a virtual network not categorized as secondary container cluster network.  See notes 3, 4 and 5. 
   * @return netAttDefResourceId
   **/
  @Schema(description = "Identifier of the \"NetAttDefResourceData\" structure that provides the specification of the interface to attach the external CP to a secondary container cluster network.  It is only applicable if the external CP is connected or to be connected to a secondary container cluster network. It shall not be present if the external CP is related to a virtual network not categorized as secondary container cluster network.  See notes 3, 4 and 5. ")
  
    public List<String> getNetAttDefResourceId() {
    return netAttDefResourceId;
  }

  public void setNetAttDefResourceId(List<String> netAttDefResourceId) {
    this.netAttDefResourceId = netAttDefResourceId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VnfExtCpConfig vnfExtCpConfig = (VnfExtCpConfig) o;
    return Objects.equals(this.parentCpConfigId, vnfExtCpConfig.parentCpConfigId) &&
        Objects.equals(this.linkPortId, vnfExtCpConfig.linkPortId) &&
        Objects.equals(this.createExtLinkPort, vnfExtCpConfig.createExtLinkPort) &&
        Objects.equals(this.cpProtocolData, vnfExtCpConfig.cpProtocolData) &&
        Objects.equals(this.netAttDefResourceId, vnfExtCpConfig.netAttDefResourceId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(parentCpConfigId, linkPortId, createExtLinkPort, cpProtocolData, netAttDefResourceId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VnfExtCpConfig {\n");
    
    sb.append("    parentCpConfigId: ").append(toIndentedString(parentCpConfigId)).append("\n");
    sb.append("    linkPortId: ").append(toIndentedString(linkPortId)).append("\n");
    sb.append("    createExtLinkPort: ").append(toIndentedString(createExtLinkPort)).append("\n");
    sb.append("    cpProtocolData: ").append(toIndentedString(cpProtocolData)).append("\n");
    sb.append("    netAttDefResourceId: ").append(toIndentedString(netAttDefResourceId)).append("\n");
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
