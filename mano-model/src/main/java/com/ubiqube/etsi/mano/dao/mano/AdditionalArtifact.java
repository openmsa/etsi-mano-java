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
package com.ubiqube.etsi.mano.dao.mano;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import com.ubiqube.etsi.mano.dao.mano.common.Checksum;
import com.ubiqube.etsi.mano.dao.mano.pkg.ArtifactClassificationType;
import com.ubiqube.etsi.mano.utils.ToStringUtil;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Getter
@Setter
@Indexed
@Entity
public class AdditionalArtifact implements Serializable {
	/** Serial. */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@DocumentId
	private UUID id;

	@FullTextField
	private String artifactPath;

	@Embedded
	private Checksum checksum;

	// 2.7.1
	private Boolean isEncrypted = Boolean.FALSE;
	// 2.7.1
	private String nonManoSetIndentifier;
	// 2.7.1 XXX: Multiple URIs ?
	// 2.8.1
	private String artifactURI;
	// 2.7.1
	private ArtifactClassificationType artifactClassification;

	@ElementCollection(fetch = FetchType.EAGER)
	private Map<String, String> metadata;

	private String repository;

	private String signature;

	private String certificate;

	// 2.7.1
	private String nonManoArtifactSetId;

	@Override
	public String toString() {
		return ToStringUtil.toString(this);
	}

}
