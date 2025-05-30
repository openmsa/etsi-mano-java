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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.jspecify.annotations.Nullable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * RingStatisticItem
 */
@JsonPropertyOrder({
		RingStatisticItem.JSON_PROPERTY_NAME,
		RingStatisticItem.JSON_PROPERTY_TYPE,
		RingStatisticItem.JSON_PROPERTY_SIZE,
		RingStatisticItem.JSON_PROPERTY_VALUE
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-05-23T10:34:53.990305454+02:00[Europe/Paris]", comments = "Generator version: 7.13.0")
public class RingStatisticItem {
	public static final String JSON_PROPERTY_NAME = "name";
	@Nullable
	private String name;

	public static final String JSON_PROPERTY_TYPE = "type";
	@Nullable
	private String type;

	public static final String JSON_PROPERTY_SIZE = "size";
	@Nullable
	private Integer size;

	public static final String JSON_PROPERTY_VALUE = "value";
	@Nullable
	private List<SimpleStatisticItem> value = new ArrayList<>();

	public RingStatisticItem() {
	}

	public RingStatisticItem name(@Nullable final String name) {

		this.name = name;
		return this;
	}

	/**
	 * Item name
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

	public RingStatisticItem type(@Nullable final String type) {

		this.type = type;
		return this;
	}

	/**
	 * Set to \&quot;RingStatisticItem\&quot;
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

	public RingStatisticItem size(@Nullable final Integer size) {

		this.size = size;
		return this;
	}

	/**
	 * Ring size
	 *
	 * @return size
	 */
	@Nullable
	@JsonProperty(JSON_PROPERTY_SIZE)
	@JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

	public Integer getSize() {
		return size;
	}

	@JsonProperty(JSON_PROPERTY_SIZE)
	@JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
	public void setSize(@Nullable final Integer size) {
		this.size = size;
	}

	public RingStatisticItem value(@Nullable final List<SimpleStatisticItem> value) {

		this.value = value;
		return this;
	}

	public RingStatisticItem addValueItem(final SimpleStatisticItem valueItem) {
		if (this.value == null) {
			this.value = new ArrayList<>();
		}
		this.value.add(valueItem);
		return this;
	}

	/**
	 * Named values
	 *
	 * @return value
	 */
	@Nullable
	@JsonProperty(JSON_PROPERTY_VALUE)
	@JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

	public List<SimpleStatisticItem> getValue() {
		return value;
	}

	@JsonProperty(JSON_PROPERTY_VALUE)
	@JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
	public void setValue(@Nullable final List<SimpleStatisticItem> value) {
		this.value = value;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		RingStatisticItem ringStatisticItem = (RingStatisticItem) o;
		return Objects.equals(this.name, ringStatisticItem.name) &&
				Objects.equals(this.type, ringStatisticItem.type) &&
				Objects.equals(this.size, ringStatisticItem.size) &&
				Objects.equals(this.value, ringStatisticItem.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, type, size, value);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class RingStatisticItem {\n");
		sb.append("    name: ").append(toIndentedString(name)).append("\n");
		sb.append("    type: ").append(toIndentedString(type)).append("\n");
		sb.append("    size: ").append(toIndentedString(size)).append("\n");
		sb.append("    value: ").append(toIndentedString(value)).append("\n");
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
