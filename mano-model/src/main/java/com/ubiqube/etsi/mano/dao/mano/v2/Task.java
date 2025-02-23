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
package com.ubiqube.etsi.mano.dao.mano.v2;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ubiqube.etsi.mano.dao.mano.Auditable;
import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.ScaleInfo;
import com.ubiqube.etsi.mano.dao.mano.ToscaEntity;

import org.jspecify.annotations.NonNull;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public interface Task extends ToscaEntity, Auditable {

	ChangeType getChangeType();

	String getAlias();

	@Override
	String getToscaName();

	void setStartDate(LocalDateTime now);

	void setStatus(PlanStatusType started);

	void setEndDate(LocalDateTime now);

	void setVimResourceId(String res);

	String getVimResourceId();

	String getVimConnectionId();

	ResourceTypeEnum getType();

	ScaleInfo getScaleInfo();

	@NonNull
	UUID getRemovedLiveInstance();

	void setChangeType(ChangeType removed);

}
