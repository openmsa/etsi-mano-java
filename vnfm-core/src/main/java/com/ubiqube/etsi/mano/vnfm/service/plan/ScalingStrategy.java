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
package com.ubiqube.etsi.mano.vnfm.service.plan;

import java.util.Set;

import com.ubiqube.etsi.mano.dao.mano.ScaleInfo;
import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;

import jakarta.annotation.Nullable;
import lombok.ToString;

public interface ScalingStrategy {
	@ToString
	class NumberOfCompute {
		private int current;
		private int wanted;
		private ScaleInfo scaleInfo;

		public NumberOfCompute(final int current, final int wanted, @Nullable final ScaleInfo scaleInfo) {
			this.current = current;
			this.wanted = wanted;
			this.scaleInfo = scaleInfo;
		}

		public int getCurrent() {
			return current;
		}

		public void setCurrent(final int current) {
			this.current = current;
		}

		public int getWanted() {
			return wanted;
		}

		public void setWanted(final int wanted) {
			this.wanted = wanted;
		}

		@Nullable
		public ScaleInfo getScaleInfo() {
			return scaleInfo;
		}

		public void setScaleInfo(final ScaleInfo scaleInfo) {
			this.scaleInfo = scaleInfo;
		}

	}

	NumberOfCompute getNumberOfCompute(VnfBlueprint plan, VnfPackage vnfPackage, Set<ScaleInfo> scaling, VnfCompute x, VnfInstance instance);
}
