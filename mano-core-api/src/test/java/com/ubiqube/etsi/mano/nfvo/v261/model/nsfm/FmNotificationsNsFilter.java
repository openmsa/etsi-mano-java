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
package com.ubiqube.etsi.mano.nfvo.v261.model.nsfm;

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
 * This type represents a subscription filter related to notifications about NS
 * faults. It shall comply with the provisions defined in Table 8.5.3.2-1. At a
 * particular nesting level in the filter structure, the following applies: All
 * attributes shall match in order for the filter to match (logical
 * \&quot;and\&quot; between different filter attributes). If an attribute is an
 * array, the attribute shall match if at least one of the values in the array
 * matches (logical \&quot;or\&quot; between the values of one filter
 * attribute)..
 */
@Schema(description = "This type represents a subscription filter related to notifications about NS faults.  It shall comply with the provisions defined in Table 8.5.3.2-1. At a particular nesting level in the filter structure, the following applies:  All attributes shall match in order for the filter to match (logical \"and\" between different filter attributes).  If an attribute is an array, the attribute shall match if at least one of the values in the array matches (logical \"or\" between the values of one filter attribute).. ")
@Validated
public class FmNotificationsNsFilter {
	@JsonProperty("nsInstanceSubscriptionFilter")
	private NsInstanceSubscriptionFilter nsInstanceSubscriptionFilter = null;

	/**
	 * Gets or Sets notificationTypes
	 */
	public enum NotificationTypesEnum {
		ALARMNOTIFICATION("AlarmNotification"),

		ALARMCLEAREDNOTIFICATION("AlarmClearedNotification"),

		ALARMLISTREBUILTNOTIFICATION("AlarmListRebuiltNotification");

		private final String value;

		NotificationTypesEnum(final String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static NotificationTypesEnum fromValue(final String text) {
			for (final NotificationTypesEnum b : NotificationTypesEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	@JsonProperty("notificationTypes")
	@Valid
	private List<NotificationTypesEnum> notificationTypes = null;

	@JsonProperty("faultyResourceTypes")
	@Valid
	private List<FaultyResourceType> faultyResourceTypes = null;

	@JsonProperty("perceivedSeverities")
	@Valid
	private List<PerceivedSeverityType> perceivedSeverities = null;

	@JsonProperty("eventTypes")
	@Valid
	private List<EventType> eventTypes = null;

	@JsonProperty("probableCauses")
	@Valid
	private List<String> probableCauses = null;

	public FmNotificationsNsFilter nsInstanceSubscriptionFilter(final NsInstanceSubscriptionFilter nsInstanceSubscriptionFilter) {
		this.nsInstanceSubscriptionFilter = nsInstanceSubscriptionFilter;
		return this;
	}

	/**
	 * Get nsInstanceSubscriptionFilter
	 *
	 * @return nsInstanceSubscriptionFilter
	 **/
	@Schema(description = "")

	@Valid
	public NsInstanceSubscriptionFilter getNsInstanceSubscriptionFilter() {
		return nsInstanceSubscriptionFilter;
	}

	public void setNsInstanceSubscriptionFilter(final NsInstanceSubscriptionFilter nsInstanceSubscriptionFilter) {
		this.nsInstanceSubscriptionFilter = nsInstanceSubscriptionFilter;
	}

	public FmNotificationsNsFilter notificationTypes(final List<NotificationTypesEnum> notificationTypes) {
		this.notificationTypes = notificationTypes;
		return this;
	}

	public FmNotificationsNsFilter addNotificationTypesItem(final NotificationTypesEnum notificationTypesItem) {
		if (this.notificationTypes == null) {
			this.notificationTypes = new ArrayList<>();
		}
		this.notificationTypes.add(notificationTypesItem);
		return this;
	}

	/**
	 * Match particular notification types. Permitted values: - AlarmNotification -
	 * AlarmClearedNotification - AlarmListRebuiltNotification.
	 *
	 * @return notificationTypes
	 **/
	@Schema(description = "Match particular notification types. Permitted values: - AlarmNotification - AlarmClearedNotification - AlarmListRebuiltNotification. ")

	public List<NotificationTypesEnum> getNotificationTypes() {
		return notificationTypes;
	}

	public void setNotificationTypes(final List<NotificationTypesEnum> notificationTypes) {
		this.notificationTypes = notificationTypes;
	}

	public FmNotificationsNsFilter faultyResourceTypes(final List<FaultyResourceType> faultyResourceTypes) {
		this.faultyResourceTypes = faultyResourceTypes;
		return this;
	}

	public FmNotificationsNsFilter addFaultyResourceTypesItem(final FaultyResourceType faultyResourceTypesItem) {
		if (this.faultyResourceTypes == null) {
			this.faultyResourceTypes = new ArrayList<>();
		}
		this.faultyResourceTypes.add(faultyResourceTypesItem);
		return this;
	}

	/**
	 * Match alarms related to NSs with a faulty resource type listed in this
	 * attribute.
	 *
	 * @return faultyResourceTypes
	 **/
	@Schema(description = "Match alarms related to NSs with a faulty resource type listed in this attribute. ")
	@Valid
	public List<FaultyResourceType> getFaultyResourceTypes() {
		return faultyResourceTypes;
	}

	public void setFaultyResourceTypes(final List<FaultyResourceType> faultyResourceTypes) {
		this.faultyResourceTypes = faultyResourceTypes;
	}

	public FmNotificationsNsFilter perceivedSeverities(final List<PerceivedSeverityType> perceivedSeverities) {
		this.perceivedSeverities = perceivedSeverities;
		return this;
	}

	public FmNotificationsNsFilter addPerceivedSeveritiesItem(final PerceivedSeverityType perceivedSeveritiesItem) {
		if (this.perceivedSeverities == null) {
			this.perceivedSeverities = new ArrayList<>();
		}
		this.perceivedSeverities.add(perceivedSeveritiesItem);
		return this;
	}

	/**
	 * Match VNF alarms with a perceived severity listed in this attribute.
	 *
	 * @return perceivedSeverities
	 **/
	@Schema(description = "Match VNF alarms with a perceived severity listed in this attribute. ")
	@Valid
	public List<PerceivedSeverityType> getPerceivedSeverities() {
		return perceivedSeverities;
	}

	public void setPerceivedSeverities(final List<PerceivedSeverityType> perceivedSeverities) {
		this.perceivedSeverities = perceivedSeverities;
	}

	public FmNotificationsNsFilter eventTypes(final List<EventType> eventTypes) {
		this.eventTypes = eventTypes;
		return this;
	}

	public FmNotificationsNsFilter addEventTypesItem(final EventType eventTypesItem) {
		if (this.eventTypes == null) {
			this.eventTypes = new ArrayList<>();
		}
		this.eventTypes.add(eventTypesItem);
		return this;
	}

	/**
	 * Match VNF alarms related to NSs with an event type listed in this attribute.
	 *
	 * @return eventTypes
	 **/
	@Schema(description = "Match VNF alarms related to NSs with an event type listed  in this attribute. ")
	@Valid
	public List<EventType> getEventTypes() {
		return eventTypes;
	}

	public void setEventTypes(final List<EventType> eventTypes) {
		this.eventTypes = eventTypes;
	}

	public FmNotificationsNsFilter probableCauses(final List<String> probableCauses) {
		this.probableCauses = probableCauses;
		return this;
	}

	public FmNotificationsNsFilter addProbableCausesItem(final String probableCausesItem) {
		if (this.probableCauses == null) {
			this.probableCauses = new ArrayList<>();
		}
		this.probableCauses.add(probableCausesItem);
		return this;
	}

	/**
	 * Match VNF alarms with a probable cause listed in this attribute.
	 *
	 * @return probableCauses
	 **/
	@Schema(description = "Match VNF alarms with a probable cause listed in this attribute. ")

	public List<String> getProbableCauses() {
		return probableCauses;
	}

	public void setProbableCauses(final List<String> probableCauses) {
		this.probableCauses = probableCauses;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final FmNotificationsNsFilter fmNotificationsFilter = (FmNotificationsNsFilter) o;
		return Objects.equals(this.nsInstanceSubscriptionFilter, fmNotificationsFilter.nsInstanceSubscriptionFilter) &&
				Objects.equals(this.notificationTypes, fmNotificationsFilter.notificationTypes) &&
				Objects.equals(this.faultyResourceTypes, fmNotificationsFilter.faultyResourceTypes) &&
				Objects.equals(this.perceivedSeverities, fmNotificationsFilter.perceivedSeverities) &&
				Objects.equals(this.eventTypes, fmNotificationsFilter.eventTypes) &&
				Objects.equals(this.probableCauses, fmNotificationsFilter.probableCauses);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nsInstanceSubscriptionFilter, notificationTypes, faultyResourceTypes, perceivedSeverities, eventTypes, probableCauses);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class FmNotificationsFilter {\n");

		sb.append("    nsInstanceSubscriptionFilter: ").append(toIndentedString(nsInstanceSubscriptionFilter)).append("\n");
		sb.append("    notificationTypes: ").append(toIndentedString(notificationTypes)).append("\n");
		sb.append("    faultyResourceTypes: ").append(toIndentedString(faultyResourceTypes)).append("\n");
		sb.append("    perceivedSeverities: ").append(toIndentedString(perceivedSeverities)).append("\n");
		sb.append("    eventTypes: ").append(toIndentedString(eventTypes)).append("\n");
		sb.append("    probableCauses: ").append(toIndentedString(probableCauses)).append("\n");
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
