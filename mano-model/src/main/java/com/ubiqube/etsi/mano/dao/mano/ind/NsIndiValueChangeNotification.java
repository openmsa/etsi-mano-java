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
package com.ubiqube.etsi.mano.dao.mano.ind;

import java.time.OffsetDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author olivier
 *
 */
@Setter
@Getter
@Entity
public class NsIndiValueChangeNotification {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private UUID nfvoId;

	private String notificationType;

	private String subscriptionId;

	private OffsetDateTime timeStamp;

	private String nsIndicatorId;

	private String name;

	private String value;

	private String nsInstanceId;
	
	private String nsdId;
}
