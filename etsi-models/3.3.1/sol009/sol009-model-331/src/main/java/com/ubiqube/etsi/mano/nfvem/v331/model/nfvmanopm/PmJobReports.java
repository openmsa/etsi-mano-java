package com.ubiqube.etsi.mano.nfvem.v331.model.nfvmanopm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * PmJobReports
 */
@Validated


public class PmJobReports   {
  @JsonProperty("href")
  private String href = null;

  @JsonProperty("readyTime")
  private OffsetDateTime readyTime = null;

  @JsonProperty("expiryTime")
  private OffsetDateTime expiryTime = null;

  @JsonProperty("fileSize")
  private BigDecimal fileSize = null;

  public PmJobReports href(String href) {
    this.href = href;
    return this;
  }

  /**
   * Get href
   * @return href
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public PmJobReports readyTime(OffsetDateTime readyTime) {
    this.readyTime = readyTime;
    return this;
  }

  /**
   * Get readyTime
   * @return readyTime
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public OffsetDateTime getReadyTime() {
    return readyTime;
  }

  public void setReadyTime(OffsetDateTime readyTime) {
    this.readyTime = readyTime;
  }

  public PmJobReports expiryTime(OffsetDateTime expiryTime) {
    this.expiryTime = expiryTime;
    return this;
  }

  /**
   * Get expiryTime
   * @return expiryTime
   **/
  @Schema(description = "")
  
    @Valid
    public OffsetDateTime getExpiryTime() {
    return expiryTime;
  }

  public void setExpiryTime(OffsetDateTime expiryTime) {
    this.expiryTime = expiryTime;
  }

  public PmJobReports fileSize(BigDecimal fileSize) {
    this.fileSize = fileSize;
    return this;
  }

  /**
   * Get fileSize
   * @return fileSize
   **/
  @Schema(description = "")
  
    @Valid
    public BigDecimal getFileSize() {
    return fileSize;
  }

  public void setFileSize(BigDecimal fileSize) {
    this.fileSize = fileSize;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PmJobReports pmJobReports = (PmJobReports) o;
    return Objects.equals(this.href, pmJobReports.href) &&
        Objects.equals(this.readyTime, pmJobReports.readyTime) &&
        Objects.equals(this.expiryTime, pmJobReports.expiryTime) &&
        Objects.equals(this.fileSize, pmJobReports.fileSize);
  }

  @Override
  public int hashCode() {
    return Objects.hash(href, readyTime, expiryTime, fileSize);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PmJobReports {\n");
    
    sb.append("    href: ").append(toIndentedString(href)).append("\n");
    sb.append("    readyTime: ").append(toIndentedString(readyTime)).append("\n");
    sb.append("    expiryTime: ").append(toIndentedString(expiryTime)).append("\n");
    sb.append("    fileSize: ").append(toIndentedString(fileSize)).append("\n");
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
