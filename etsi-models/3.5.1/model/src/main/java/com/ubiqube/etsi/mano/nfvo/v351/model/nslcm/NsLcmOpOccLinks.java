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
package com.ubiqube.etsi.mano.nfvo.v351.model.nslcm;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.etsi.mano.em.v351.model.lcmcoord.Link;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Links to resources related to this resource.
 */
@Schema(description = "Links to resources related to this resource. ")
@Validated

public class NsLcmOpOccLinks {
	@JsonProperty("self")
	private Link self = null;

	@JsonProperty("nsInstance")
	private Link nsInstance = null;

	@JsonProperty("cancel")
	private Link cancel = null;

	@JsonProperty("retry")
	private Link retry = null;

	@JsonProperty("rollback")
	private Link rollback = null;

	@JsonProperty("continue")
	private Link _continue = null;

	@JsonProperty("fail")
	private Link fail = null;

	public NsLcmOpOccLinks self(final Link self) {
		this.self = self;
		return this;
	}

	/**
	 * Get self
	 *
	 * @return self
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public Link getSelf() {
		return self;
	}

	public void setSelf(final Link self) {
		this.self = self;
	}

	public NsLcmOpOccLinks nsInstance(final Link nsInstance) {
		this.nsInstance = nsInstance;
		return this;
	}

	/**
	 * Get nsInstance
	 *
	 * @return nsInstance
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public Link getNsInstance() {
		return nsInstance;
	}

	public void setNsInstance(final Link nsInstance) {
		this.nsInstance = nsInstance;
	}

	public NsLcmOpOccLinks cancel(final Link cancel) {
		this.cancel = cancel;
		return this;
	}

	/**
	 * Get cancel
	 *
	 * @return cancel
	 **/
	@Schema(description = "")

	@Valid
	public Link getCancel() {
		return cancel;
	}

	public void setCancel(final Link cancel) {
		this.cancel = cancel;
	}

	public NsLcmOpOccLinks retry(final Link retry) {
		this.retry = retry;
		return this;
	}

	/**
	 * Get retry
	 *
	 * @return retry
	 **/
	@Schema(description = "")

	@Valid
	public Link getRetry() {
		return retry;
	}

	public void setRetry(final Link retry) {
		this.retry = retry;
	}

	public NsLcmOpOccLinks rollback(final Link rollback) {
		this.rollback = rollback;
		return this;
	}

	/**
	 * Get rollback
	 *
	 * @return rollback
	 **/
	@Schema(description = "")

	@Valid
	public Link getRollback() {
		return rollback;
	}

	public void setRollback(final Link rollback) {
		this.rollback = rollback;
	}

	public NsLcmOpOccLinks _continue(final Link _continue) {
		this._continue = _continue;
		return this;
	}

	/**
	 * Get _continue
	 *
	 * @return _continue
	 **/
	@Schema(description = "")

	@Valid
	public Link getContinue() {
		return _continue;
	}

	public void setContinue(final Link _continue) {
		this._continue = _continue;
	}

	public NsLcmOpOccLinks fail(final Link fail) {
		this.fail = fail;
		return this;
	}

	/**
	 * Get fail
	 *
	 * @return fail
	 **/
	@Schema(description = "")

	@Valid
	public Link getFail() {
		return fail;
	}

	public void setFail(final Link fail) {
		this.fail = fail;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final NsLcmOpOccLinks nsLcmOpOccLinks = (NsLcmOpOccLinks) o;
		return Objects.equals(this.self, nsLcmOpOccLinks.self) &&
				Objects.equals(this.nsInstance, nsLcmOpOccLinks.nsInstance) &&
				Objects.equals(this.cancel, nsLcmOpOccLinks.cancel) &&
				Objects.equals(this.retry, nsLcmOpOccLinks.retry) &&
				Objects.equals(this.rollback, nsLcmOpOccLinks.rollback) &&
				Objects.equals(this._continue, nsLcmOpOccLinks._continue) &&
				Objects.equals(this.fail, nsLcmOpOccLinks.fail);
	}

	@Override
	public int hashCode() {
		return Objects.hash(self, nsInstance, cancel, retry, rollback, _continue, fail);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class NsLcmOpOccLinks {\n");

		sb.append("    self: ").append(toIndentedString(self)).append("\n");
		sb.append("    nsInstance: ").append(toIndentedString(nsInstance)).append("\n");
		sb.append("    cancel: ").append(toIndentedString(cancel)).append("\n");
		sb.append("    retry: ").append(toIndentedString(retry)).append("\n");
		sb.append("    rollback: ").append(toIndentedString(rollback)).append("\n");
		sb.append("    _continue: ").append(toIndentedString(_continue)).append("\n");
		sb.append("    fail: ").append(toIndentedString(fail)).append("\n");
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
