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
/*
 * PowerDNS Authoritative HTTP API
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 0.0.15
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.ubiqube.mano.pdns.model;

import java.util.Objects;

import org.jspecify.annotations.Nullable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * SearchResultRecord
 */
@JsonPropertyOrder({
		SearchResultRecord.JSON_PROPERTY_CONTENT,
		SearchResultRecord.JSON_PROPERTY_DISABLED,
		SearchResultRecord.JSON_PROPERTY_NAME,
		SearchResultRecord.JSON_PROPERTY_OBJECT_TYPE,
		SearchResultRecord.JSON_PROPERTY_ZONE_ID,
		SearchResultRecord.JSON_PROPERTY_ZONE,
		SearchResultRecord.JSON_PROPERTY_TYPE,
		SearchResultRecord.JSON_PROPERTY_TTL
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-05-23T10:34:53.990305454+02:00[Europe/Paris]", comments = "Generator version: 7.13.0")
public class SearchResultRecord {
	public static final String JSON_PROPERTY_CONTENT = "content";
	@Nullable
	private String content;

	public static final String JSON_PROPERTY_DISABLED = "disabled";
	@Nullable
	private Boolean disabled;

	public static final String JSON_PROPERTY_NAME = "name";
	@Nullable
	private String name;

	public static final String JSON_PROPERTY_OBJECT_TYPE = "object_type";
	@Nullable
	private String objectType;

	public static final String JSON_PROPERTY_ZONE_ID = "zone_id";
	@Nullable
	private String zoneId;

	public static final String JSON_PROPERTY_ZONE = "zone";
	@Nullable
	private String zone;

	public static final String JSON_PROPERTY_TYPE = "type";
	@Nullable
	private String type;

	public static final String JSON_PROPERTY_TTL = "ttl";
	@Nullable
	private Integer ttl;

	public SearchResultRecord() {
	}

	public SearchResultRecord content(@Nullable final String content) {

		this.content = content;
		return this;
	}

	/**
	 * Get content
	 *
	 * @return content
	 */
	@Nullable
	@JsonProperty(JSON_PROPERTY_CONTENT)
	@JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

	public String getContent() {
		return content;
	}

	@JsonProperty(JSON_PROPERTY_CONTENT)
	@JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
	public void setContent(@Nullable final String content) {
		this.content = content;
	}

	public SearchResultRecord disabled(@Nullable final Boolean disabled) {

		this.disabled = disabled;
		return this;
	}

	/**
	 * Get disabled
	 *
	 * @return disabled
	 */
	@Nullable
	@JsonProperty(JSON_PROPERTY_DISABLED)
	@JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

	public Boolean getDisabled() {
		return disabled;
	}

	@JsonProperty(JSON_PROPERTY_DISABLED)
	@JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
	public void setDisabled(@Nullable final Boolean disabled) {
		this.disabled = disabled;
	}

	public SearchResultRecord name(@Nullable final String name) {

		this.name = name;
		return this;
	}

	/**
	 * Get name
	 *
	 * @return name
	 */
	@Nullable
	@JsonProperty(JSON_PROPERTY_NAME)
	@JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

	public String getName() {
		return name;
	}

	@JsonProperty(JSON_PROPERTY_NAME)
	@JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
	public void setName(@Nullable final String name) {
		this.name = name;
	}

	public SearchResultRecord objectType(@Nullable final String objectType) {

		this.objectType = objectType;
		return this;
	}

	/**
	 * set to \&quot;record\&quot;
	 *
	 * @return objectType
	 */
	@Nullable
	@JsonProperty(JSON_PROPERTY_OBJECT_TYPE)
	@JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

	public String getObjectType() {
		return objectType;
	}

	@JsonProperty(JSON_PROPERTY_OBJECT_TYPE)
	@JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
	public void setObjectType(@Nullable final String objectType) {
		this.objectType = objectType;
	}

	public SearchResultRecord zoneId(@Nullable final String zoneId) {

		this.zoneId = zoneId;
		return this;
	}

	/**
	 * Get zoneId
	 *
	 * @return zoneId
	 */
	@Nullable
	@JsonProperty(JSON_PROPERTY_ZONE_ID)
	@JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

	public String getZoneId() {
		return zoneId;
	}

	@JsonProperty(JSON_PROPERTY_ZONE_ID)
	@JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
	public void setZoneId(@Nullable final String zoneId) {
		this.zoneId = zoneId;
	}

	public SearchResultRecord zone(@Nullable final String zone) {

		this.zone = zone;
		return this;
	}

	/**
	 * Get zone
	 *
	 * @return zone
	 */
	@Nullable
	@JsonProperty(JSON_PROPERTY_ZONE)
	@JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

	public String getZone() {
		return zone;
	}

	@JsonProperty(JSON_PROPERTY_ZONE)
	@JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
	public void setZone(@Nullable final String zone) {
		this.zone = zone;
	}

	public SearchResultRecord type(@Nullable final String type) {

		this.type = type;
		return this;
	}

	/**
	 * Get type
	 *
	 * @return type
	 */
	@Nullable
	@JsonProperty(JSON_PROPERTY_TYPE)
	@JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

	public String getType() {
		return type;
	}

	@JsonProperty(JSON_PROPERTY_TYPE)
	@JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
	public void setType(@Nullable final String type) {
		this.type = type;
	}

	public SearchResultRecord ttl(@Nullable final Integer ttl) {

		this.ttl = ttl;
		return this;
	}

	/**
	 * Get ttl
	 *
	 * @return ttl
	 */
	@Nullable
	@JsonProperty(JSON_PROPERTY_TTL)
	@JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

	public Integer getTtl() {
		return ttl;
	}

	@JsonProperty(JSON_PROPERTY_TTL)
	@JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
	public void setTtl(@Nullable final Integer ttl) {
		this.ttl = ttl;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		SearchResultRecord searchResultRecord = (SearchResultRecord) o;
		return Objects.equals(this.content, searchResultRecord.content) &&
				Objects.equals(this.disabled, searchResultRecord.disabled) &&
				Objects.equals(this.name, searchResultRecord.name) &&
				Objects.equals(this.objectType, searchResultRecord.objectType) &&
				Objects.equals(this.zoneId, searchResultRecord.zoneId) &&
				Objects.equals(this.zone, searchResultRecord.zone) &&
				Objects.equals(this.type, searchResultRecord.type) &&
				Objects.equals(this.ttl, searchResultRecord.ttl);
	}

	@Override
	public int hashCode() {
		return Objects.hash(content, disabled, name, objectType, zoneId, zone, type, ttl);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class SearchResultRecord {\n");
		sb.append("    content: ").append(toIndentedString(content)).append("\n");
		sb.append("    disabled: ").append(toIndentedString(disabled)).append("\n");
		sb.append("    name: ").append(toIndentedString(name)).append("\n");
		sb.append("    objectType: ").append(toIndentedString(objectType)).append("\n");
		sb.append("    zoneId: ").append(toIndentedString(zoneId)).append("\n");
		sb.append("    zone: ").append(toIndentedString(zone)).append("\n");
		sb.append("    type: ").append(toIndentedString(type)).append("\n");
		sb.append("    ttl: ").append(toIndentedString(ttl)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(final Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}

}
