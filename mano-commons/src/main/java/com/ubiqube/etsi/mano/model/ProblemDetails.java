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
package com.ubiqube.etsi.mano.model;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * The definition of the general \"ProblemDetails\" data structure from IETF RFC
 * 7807 [19] is reproduced inthis structure. Compared to the general framework
 * defined in IETF RFC 7807 [19], the \"status\" and \"detail\" attributes are
 * mandated to be included by the present document, to ensure that the response
 * contains additional textual information about an error. IETF RFC 7807 [19]
 * foresees extensibility of the \"ProblemDetails\" type. It is possible that
 * particular APIs in the present document, or particular implementations,
 * define extensions to define additional attributes that provide more
 * information about the error. The description column only provides some
 * explanation of the meaning to Facilitate understanding of the design. For a
 * full description, see IETF RFC 7807 [19].
 **/
@Schema(description = "The definition of the general \"ProblemDetails\" data structure from IETF RFC 7807 [19] is reproduced inthis structure. Compared to the general framework defined in IETF RFC 7807 [19], the \"status\" and \"detail\" attributes are mandated to be included by the present document, to ensure that the response contains additional textual information about an error. IETF RFC 7807 [19] foresees extensibility of the \"ProblemDetails\" type. It is possible that particular APIs in the present document, or particular implementations, define extensions to define additional attributes that provide more information about the error. The description column only provides some explanation of the meaning to Facilitate understanding of the design. For a full description, see IETF RFC 7807 [19]. ")
public class ProblemDetails {

	@Schema(description = "A URI reference according to IETF RFC 3986 [5] that identifies the problem type. It is encouraged that the URI provides human-readable documentation for the problem (e.g. using HTML) when dereferenced. When this member is not present, its value is assumed to be \"about:blank\". ")
	/**
	 * A URI reference according to IETF RFC 3986 [5] that identifies the problem
	 * type. It is encouraged that the URI provides human-readable documentation for
	 * the problem (e.g. using HTML) when dereferenced. When this member is not
	 * present, its value is assumed to be \"about:blank\".
	 **/
	private URI type = URI.create("about:blank");

	@Schema(description = "A short, human-readable summary of the problem type. It should not change from occurrence to occurrence of the problem, except for purposes of localization. If type is given and other than \"about:blank\", this attribute shall also be provided. A short, human-readable summary of the problem type.  It SHOULD NOT change from occurrence to occurrence of the problem, except for purposes of localization (e.g., using proactive content negotiation; see [RFC7231], Section 3.4). ")
	/**
	 * A short, human-readable summary of the problem type. It should not change
	 * from occurrence to occurrence of the problem, except for purposes of
	 * localization. If type is given and other than \"about:blank\", this attribute
	 * shall also be provided. A short, human-readable summary of the problem type.
	 * It SHOULD NOT change from occurrence to occurrence of the problem, except for
	 * purposes of localization (e.g., using proactive content negotiation; see
	 * [RFC7231], Section 3.4).
	 **/
	private String title = null;

	@Schema(required = true, description = "The HTTP status code for this occurrence of the problem. The HTTP status code ([RFC7231], Section 6) generated by the origin server for this occurrence of the problem. ")
	/**
	 * The HTTP status code for this occurrence of the problem. The HTTP status code
	 * ([RFC7231], Section 6) generated by the origin server for this occurrence of
	 * the problem.
	 **/
	private Integer status = 500;

	@Schema(required = true, description = "A human-readable explanation specific to this occurrence of the problem. ")
	/**
	 * A human-readable explanation specific to this occurrence of the problem.
	 **/
	private String detail = null;

	@Schema(description = "A URI reference that identifies the specific occurrence of the problem. It may yield further information if dereferenced. ")
	/**
	 * A URI reference that identifies the specific occurrence of the problem. It
	 * may yield further information if dereferenced.
	 **/
	private URI instance = null;

	/**
	 * A URI reference according to IETF RFC 3986 [5] that identifies the problem
	 * type. It is encouraged that the URI provides human-readable documentation for
	 * the problem (e.g. using HTML) when dereferenced. When this member is not
	 * present, its value is assumed to be \&quot;about:blank\&quot;.
	 *
	 * @return type
	 **/
	@JsonProperty("type")
	public URI getType() {
		return type;
	}

	public ProblemDetails() {
		try {
			instance = URI.create("http://" + InetAddress.getLocalHost().getCanonicalHostName());
		} catch (final UnknownHostException e) {
			// Nothing.
		}
	}

	public ProblemDetails(final Integer status, final String detail) {
		this();
		this.status = status;
		this.detail = detail;
	}

	public void setType(final URI typeIn) {
		this.type = typeIn;
	}

	public ProblemDetails type(final URI typeIn) {
		this.type = typeIn;
		return this;
	}

	/**
	 * A short, human-readable summary of the problem type. It should not change
	 * from occurrence to occurrence of the problem, except for purposes of
	 * localization. If type is given and other than \&quot;about:blank\&quot;, this
	 * attribute shall also be provided. A short, human-readable summary of the
	 * problem type. It SHOULD NOT change from occurrence to occurrence of the
	 * problem, except for purposes of localization (e.g., using proactive content
	 * negotiation; see [RFC7231], Section 3.4).
	 *
	 * @return title
	 **/
	@JsonProperty("title")
	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public ProblemDetails title(final String _title) {
		this.title = _title;
		return this;
	}

	/**
	 * The HTTP status code for this occurrence of the problem. The HTTP status code
	 * ([RFC7231], Section 6) generated by the origin server for this occurrence of
	 * the problem.
	 *
	 * @return status
	 **/
	@JsonProperty("status")
	@NotNull
	public Integer getStatus() {
		return status;
	}

	public void setStatus(final Integer status) {
		this.status = status;
	}

	public ProblemDetails status(final Integer _status) {
		this.status = _status;
		return this;
	}

	/**
	 * A human-readable explanation specific to this occurrence of the problem.
	 *
	 * @return detail
	 **/
	@JsonProperty("detail")
	@NotNull
	public String getDetail() {
		return detail;
	}

	public void setDetail(final String detail) {
		this.detail = detail;
	}

	public ProblemDetails detail(final String _detail) {
		this.detail = _detail;
		return this;
	}

	/**
	 * A URI reference that identifies the specific occurrence of the problem. It
	 * may yield further information if dereferenced.
	 *
	 * @return instance
	 **/
	@JsonProperty("instance")
	public URI getInstance() {
		return instance;
	}

	public void setInstance(final URI instance) {
		this.instance = instance;
	}

	public ProblemDetails instance(final URI instanceIn) {
		this.instance = instanceIn;
		return this;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class ProblemDetails {\n");

		sb.append("    type: ").append(toIndentedString(type)).append("\n");
		sb.append("    title: ").append(toIndentedString(title)).append("\n");
		sb.append("    status: ").append(toIndentedString(status)).append("\n");
		sb.append("    detail: ").append(toIndentedString(detail)).append("\n");
		sb.append("    instance: ").append(toIndentedString(instance)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private static String toIndentedString(final Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
