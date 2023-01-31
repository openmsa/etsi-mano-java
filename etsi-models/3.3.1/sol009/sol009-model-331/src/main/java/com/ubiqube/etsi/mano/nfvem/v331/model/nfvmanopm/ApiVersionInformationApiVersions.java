package com.ubiqube.etsi.mano.nfvem.v331.model.nfvmanopm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * ApiVersionInformationApiVersions
 */
@Validated


public class ApiVersionInformationApiVersions   {
  @JsonProperty("version")
  private String version = null;

  @JsonProperty("isDeprecated")
  private Boolean isDeprecated = null;

  @JsonProperty("retirementDate")
  private OffsetDateTime retirementDate = null;

  public ApiVersionInformationApiVersions version(String version) {
    this.version = version;
    return this;
  }

  /**
   * Identifies a supported version. The value of the version attribute shall be a version identifier as specified in clause 9.1 (SOL013). 
   * @return version
   **/
  @Schema(required = true, description = "Identifies a supported version. The value of the version attribute shall be a version identifier as specified in clause 9.1 (SOL013). ")
      @NotNull

    public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public ApiVersionInformationApiVersions isDeprecated(Boolean isDeprecated) {
    this.isDeprecated = isDeprecated;
    return this;
  }

  /**
   * If such information is available, this attribute indicates whether use of the version signaled by the version attribute is deprecated (true) or not (false). A deprecated version is still supported by the API producer but is recommended not to be used any longer. When a version is no longer supported, it does not appear in the response body. 
   * @return isDeprecated
   **/
  @Schema(description = "If such information is available, this attribute indicates whether use of the version signaled by the version attribute is deprecated (true) or not (false). A deprecated version is still supported by the API producer but is recommended not to be used any longer. When a version is no longer supported, it does not appear in the response body. ")
  
    public Boolean isIsDeprecated() {
    return isDeprecated;
  }

  public void setIsDeprecated(Boolean isDeprecated) {
    this.isDeprecated = isDeprecated;
  }

  public ApiVersionInformationApiVersions retirementDate(OffsetDateTime retirementDate) {
    this.retirementDate = retirementDate;
    return this;
  }

  /**
   * Get retirementDate
   * @return retirementDate
   **/
  @Schema(description = "")
  
    @Valid
    public OffsetDateTime getRetirementDate() {
    return retirementDate;
  }

  public void setRetirementDate(OffsetDateTime retirementDate) {
    this.retirementDate = retirementDate;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiVersionInformationApiVersions apiVersionInformationApiVersions = (ApiVersionInformationApiVersions) o;
    return Objects.equals(this.version, apiVersionInformationApiVersions.version) &&
        Objects.equals(this.isDeprecated, apiVersionInformationApiVersions.isDeprecated) &&
        Objects.equals(this.retirementDate, apiVersionInformationApiVersions.retirementDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(version, isDeprecated, retirementDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiVersionInformationApiVersions {\n");
    
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    isDeprecated: ").append(toIndentedString(isDeprecated)).append("\n");
    sb.append("    retirementDate: ").append(toIndentedString(retirementDate)).append("\n");
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
