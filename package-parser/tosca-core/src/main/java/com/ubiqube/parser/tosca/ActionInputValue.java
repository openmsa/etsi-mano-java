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
package com.ubiqube.parser.tosca;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ubiqube.parser.tosca.deserializer.ActionInputDeserializer;
import com.ubiqube.parser.tosca.deserializer.ActivityDeserializer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonDeserialize(using = ActionInputDeserializer.class)
public class ActionInputValue {

	private String value;
	
	public ActionInputValue(String value) {
		this.value = value;
	}
	
	
}