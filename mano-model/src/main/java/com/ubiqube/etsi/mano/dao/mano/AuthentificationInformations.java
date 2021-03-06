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
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

import com.ubiqube.etsi.mano.utils.ToStringUtil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Getter
@Setter
@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthentificationInformations implements Serializable {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	@FullTextField
	@ElementCollection
	private List<AuthType> authType;

	private AuthParamBasic authParamBasic;
	private AuthParamOauth2 authParamOauth2;

	@Column(length = 5000)
	private String authTlsCert;

	@Override
	public String toString() {
		return ToStringUtil.toString(this);
	}

}
