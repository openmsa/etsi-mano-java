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
package com.ubiqube.etsi.mano.dao.mano.vnfi;

import java.io.Serializable;

import com.ubiqube.etsi.mano.utils.ToStringUtil;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/**
 * Information missing from Sol001/ osContainer
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Getter
@Setter
@Embeddable
public class CnfInformations implements Serializable {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	private String masterFlavorId;

	@NonNull
	private String clusterTemplate;

	@Nullable
	private String dnsServer;

	@Nullable
	private String keyPair;

	@Nullable
	private String networkDriver = "flannel";

	@Nullable
	private VmServerType serverType = VmServerType.VM;

	@Override
	public String toString() {
		return ToStringUtil.toString(this);
	}

}
