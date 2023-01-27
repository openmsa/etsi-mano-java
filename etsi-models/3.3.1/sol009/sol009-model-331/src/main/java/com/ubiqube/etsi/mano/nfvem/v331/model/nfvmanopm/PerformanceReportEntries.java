package com.ubiqube.etsi.mano.nfvem.v331.model.nfvmanopm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.ubiqube.etsi.mano.nfvem.v331.model.nfvmanopm.ManoManagedObjectReference;
import com.ubiqube.etsi.mano.nfvem.v331.model.nfvmanopm.PerformanceReportPerformanceValues;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * PerformanceReportEntries
 */
@Validated


public class PerformanceReportEntries   {
  @JsonProperty("objectType")
  private String objectType = null;

  @JsonProperty("objectInstanceId")
  private ManoManagedObjectReference objectInstanceId = null;

  @JsonProperty("subObjectInstanceId")
  private String subObjectInstanceId = null;

  @JsonProperty("performanceMetric")
  private String performanceMetric = null;

  @JsonProperty("performanceValues")
  @Valid
  private List<PerformanceReportPerformanceValues> performanceValues = new ArrayList<>();

  public PerformanceReportEntries objectType(String objectType) {
    this.objectType = objectType;
    return this;
  }

  /**
   * Type of measured object. The applicable measured object type for a measurement is  defined in clause 8.2 of ETSI GS NFV-IFA 031. 
   * @return objectType
   **/
  @Schema(required = true, description = "Type of measured object. The applicable measured object type for a measurement is  defined in clause 8.2 of ETSI GS NFV-IFA 031. ")
      @NotNull

    public String getObjectType() {
    return objectType;
  }

  public void setObjectType(String objectType) {
    this.objectType = objectType;
  }

  public PerformanceReportEntries objectInstanceId(ManoManagedObjectReference objectInstanceId) {
    this.objectInstanceId = objectInstanceId;
    return this;
  }

  /**
   * Get objectInstanceId
   * @return objectInstanceId
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public ManoManagedObjectReference getObjectInstanceId() {
    return objectInstanceId;
  }

  public void setObjectInstanceId(ManoManagedObjectReference objectInstanceId) {
    this.objectInstanceId = objectInstanceId;
  }

  public PerformanceReportEntries subObjectInstanceId(String subObjectInstanceId) {
    this.subObjectInstanceId = subObjectInstanceId;
    return this;
  }

  /**
   * Get subObjectInstanceId
   * @return subObjectInstanceId
   **/
  @Schema(description = "")
  
    public String getSubObjectInstanceId() {
    return subObjectInstanceId;
  }

  public void setSubObjectInstanceId(String subObjectInstanceId) {
    this.subObjectInstanceId = subObjectInstanceId;
  }

  public PerformanceReportEntries performanceMetric(String performanceMetric) {
    this.performanceMetric = performanceMetric;
    return this;
  }

  /**
   * Name of the metric collected. This attribute shall contain the related  \"Measurement Name\" value as defined in clause 8.4 of ETSI GS NFV-IFA 031. 
   * @return performanceMetric
   **/
  @Schema(required = true, description = "Name of the metric collected. This attribute shall contain the related  \"Measurement Name\" value as defined in clause 8.4 of ETSI GS NFV-IFA 031. ")
      @NotNull

    public String getPerformanceMetric() {
    return performanceMetric;
  }

  public void setPerformanceMetric(String performanceMetric) {
    this.performanceMetric = performanceMetric;
  }

  public PerformanceReportEntries performanceValues(List<PerformanceReportPerformanceValues> performanceValues) {
    this.performanceValues = performanceValues;
    return this;
  }

  public PerformanceReportEntries addPerformanceValuesItem(PerformanceReportPerformanceValues performanceValuesItem) {
    this.performanceValues.add(performanceValuesItem);
    return this;
  }

  /**
   * List of performance values with associated timestamp. 
   * @return performanceValues
   **/
  @Schema(required = true, description = "List of performance values with associated timestamp. ")
      @NotNull
    @Valid
  @Size(min=1)   public List<PerformanceReportPerformanceValues> getPerformanceValues() {
    return performanceValues;
  }

  public void setPerformanceValues(List<PerformanceReportPerformanceValues> performanceValues) {
    this.performanceValues = performanceValues;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PerformanceReportEntries performanceReportEntries = (PerformanceReportEntries) o;
    return Objects.equals(this.objectType, performanceReportEntries.objectType) &&
        Objects.equals(this.objectInstanceId, performanceReportEntries.objectInstanceId) &&
        Objects.equals(this.subObjectInstanceId, performanceReportEntries.subObjectInstanceId) &&
        Objects.equals(this.performanceMetric, performanceReportEntries.performanceMetric) &&
        Objects.equals(this.performanceValues, performanceReportEntries.performanceValues);
  }

  @Override
  public int hashCode() {
    return Objects.hash(objectType, objectInstanceId, subObjectInstanceId, performanceMetric, performanceValues);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PerformanceReportEntries {\n");
    
    sb.append("    objectType: ").append(toIndentedString(objectType)).append("\n");
    sb.append("    objectInstanceId: ").append(toIndentedString(objectInstanceId)).append("\n");
    sb.append("    subObjectInstanceId: ").append(toIndentedString(subObjectInstanceId)).append("\n");
    sb.append("    performanceMetric: ").append(toIndentedString(performanceMetric)).append("\n");
    sb.append("    performanceValues: ").append(toIndentedString(performanceValues)).append("\n");
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
