package com.ubiqube.etsi.mano.em.v261.model.faultmngt;

import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * This type represents the escalated value of the perceived severity for an alarm.
 */
@Schema(description = "This type represents the escalated value of the perceived severity for an alarm. ")
@Validated

public class PerceivedSeverityRequest {
	@JsonProperty("proposedPerceivedSeverity")
	private PerceivedSeverityType proposedPerceivedSeverity = null;

	public PerceivedSeverityRequest proposedPerceivedSeverity(final PerceivedSeverityType proposedPerceivedSeverity) {
		this.proposedPerceivedSeverity = proposedPerceivedSeverity;
		return this;
	}

	/**
	 * Indicates the proposed escalated perceived severity for an alarm.
	 *
	 * @return proposedPerceivedSeverity
	 **/
	@Schema(required = true, description = "Indicates the proposed escalated perceived severity for an alarm. ")
	@NotNull

	@Valid

	public PerceivedSeverityType getProposedPerceivedSeverity() {
		return proposedPerceivedSeverity;
	}

	public void setProposedPerceivedSeverity(final PerceivedSeverityType proposedPerceivedSeverity) {
		this.proposedPerceivedSeverity = proposedPerceivedSeverity;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final PerceivedSeverityRequest perceivedSeverityRequest = (PerceivedSeverityRequest) o;
		return Objects.equals(this.proposedPerceivedSeverity, perceivedSeverityRequest.proposedPerceivedSeverity);
	}

	@Override
	public int hashCode() {
		return Objects.hash(proposedPerceivedSeverity);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class PerceivedSeverityRequest {\n");

		sb.append("    proposedPerceivedSeverity: ").append(toIndentedString(proposedPerceivedSeverity)).append("\n");
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
