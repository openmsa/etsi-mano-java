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
package com.ubiqube.etsi.mano.nfvo.v261.model.nsd.sol005.notification;

import java.time.OffsetDateTime;
import java.util.Objects;

import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * This type represents an NSD management notification, which informs the
 * receiver of the deletion of an on-boarded NSD. The notification shall comply
 * with the provisions defined in Table 5.5.2.12-1. The support of this
 * notification is mandatory. The notification shall be triggered by the NFVO
 * when it has deleted an on-boarded NSD.
 */
@Schema(description = "This type represents an NSD management notification, which informs the receiver of the deletion of an on-boarded NSD. The notification shall comply with the provisions defined in Table 5.5.2.12-1. The support of this notification is mandatory. The notification shall be triggered by the NFVO when it has deleted an on-boarded NSD. ")
@Validated


public class NsdDeletionNotification {
	@JsonProperty("id")
	private String id = null;

	@JsonProperty("notificationType")
	private String notificationType = null;

	@JsonProperty("subscriptionId")
	private String subscriptionId = null;

	@JsonProperty("timeStamp")
	private OffsetDateTime timeStamp = null;

	@JsonProperty("nsdInfoId")
	private String nsdInfoId = null;

	@JsonProperty("nsdId")
	private String nsdId = null;

	@JsonProperty("_links")
	private NsdmLinks links = null;

	public NsdDeletionNotification id(final String id) {
		this.id = id;
		return this;
	}

	/**
	 * Get id
	 * 
	 * @return id
	 **/
	@Schema(required = true, description = "")
	@NonNull

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public NsdDeletionNotification notificationType(final String notificationType) {
		this.notificationType = notificationType;
		return this;
	}

	/**
	 * Discriminator for the different notification types. Shall be set to
	 * \"NsdDeletionNotification \" for this notification type.
	 * 
	 * @return notificationType
	 **/
	@Schema(required = true, description = "Discriminator for the different notification types. Shall be set to \"NsdDeletionNotification \" for this notification type. ")
	@NonNull

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(final String notificationType) {
		this.notificationType = notificationType;
	}

	public NsdDeletionNotification subscriptionId(final String subscriptionId) {
		this.subscriptionId = subscriptionId;
		return this;
	}

	/**
	 * Get subscriptionId
	 * 
	 * @return subscriptionId
	 **/
	@Schema(required = true, description = "")
	@NonNull

	public String getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(final String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public NsdDeletionNotification timeStamp(final OffsetDateTime timeStamp) {
		this.timeStamp = timeStamp;
		return this;
	}

	/**
	 * Date-time of the generation of the notification.
	 * 
	 * @return timeStamp
	 **/
	@Schema(required = true, description = "Date-time of the generation of the notification. ")
	@NonNull

	@Valid

	public OffsetDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(final OffsetDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public NsdDeletionNotification nsdInfoId(final String nsdInfoId) {
		this.nsdInfoId = nsdInfoId;
		return this;
	}

	/**
	 * Get nsdInfoId
	 * 
	 * @return nsdInfoId
	 **/
	@Schema(required = true, description = "")
	@NonNull

	public String getNsdInfoId() {
		return nsdInfoId;
	}

	public void setNsdInfoId(final String nsdInfoId) {
		this.nsdInfoId = nsdInfoId;
	}

	public NsdDeletionNotification nsdId(final String nsdId) {
		this.nsdId = nsdId;
		return this;
	}

	/**
	 * Get nsdId
	 * 
	 * @return nsdId
	 **/
	@Schema(required = true, description = "")
	@NonNull

	public String getNsdId() {
		return nsdId;
	}

	public void setNsdId(final String nsdId) {
		this.nsdId = nsdId;
	}

	public NsdDeletionNotification links(final NsdmLinks links) {
		this.links = links;
		return this;
	}

	/**
	 * Get links
	 * 
	 * @return links
	 **/
	@Schema(required = true, description = "")
	@NonNull

	@Valid

	public NsdmLinks getLinks() {
		return links;
	}

	public void setLinks(final NsdmLinks links) {
		this.links = links;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final NsdDeletionNotification nsdDeletionNotification = (NsdDeletionNotification) o;
		return Objects.equals(this.id, nsdDeletionNotification.id) &&
				Objects.equals(this.notificationType, nsdDeletionNotification.notificationType) &&
				Objects.equals(this.subscriptionId, nsdDeletionNotification.subscriptionId) &&
				Objects.equals(this.timeStamp, nsdDeletionNotification.timeStamp) &&
				Objects.equals(this.nsdInfoId, nsdDeletionNotification.nsdInfoId) &&
				Objects.equals(this.nsdId, nsdDeletionNotification.nsdId) &&
				Objects.equals(this.links, nsdDeletionNotification.links);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, notificationType, subscriptionId, timeStamp, nsdInfoId, nsdId, links);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class NsdDeletionNotification {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    notificationType: ").append(toIndentedString(notificationType)).append("\n");
		sb.append("    subscriptionId: ").append(toIndentedString(subscriptionId)).append("\n");
		sb.append("    timeStamp: ").append(toIndentedString(timeStamp)).append("\n");
		sb.append("    nsdInfoId: ").append(toIndentedString(nsdInfoId)).append("\n");
		sb.append("    nsdId: ").append(toIndentedString(nsdId)).append("\n");
		sb.append("    links: ").append(toIndentedString(links)).append("\n");
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
