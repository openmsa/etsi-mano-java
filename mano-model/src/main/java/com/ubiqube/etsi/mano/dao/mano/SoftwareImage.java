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
package com.ubiqube.etsi.mano.dao.mano;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.ubiqube.etsi.mano.dao.mano.common.Checksum;

import jakarta.annotation.Nullable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Setter
@Getter
@Entity
@EntityListeners(AuditListener.class)
public class SoftwareImage implements Auditable {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id = null;

	private String vimId;

	private String name;

	private String provider;

	private String version;

	@Embedded
	private Checksum checksum;

	private Boolean isEncrypted;

	@Nullable
	@Enumerated(EnumType.STRING)
	private ContainerFormatType containerFormat;

	@Nullable
	@Enumerated(EnumType.STRING)
	private DiskFormatType diskFormat = DiskFormatType.QCOW2;

	private OffsetDateTime createdAt;

	@Nullable
	private Long minDisk;

	@Nullable
	private Long minRam;

	@Nullable
	private Long size;

	/**
	 * Path in archive
	 */
	@Nullable
	private String imagePath;

	@Nullable
	private String imageUri;

	/**
	 * Path in NFVO repository.
	 */
	private String nfvoPath;

	@Nullable
	private String architecture;

	@Nullable
	private String repository;

	private Audit audit;

}
