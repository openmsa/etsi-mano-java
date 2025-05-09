/**
 * Copyright (C) 2019-2025 Ubiqube.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.mapper;

import org.jspecify.annotations.Nullable;

public interface BeanListener {

	void addProperty(@Nullable Object source);

	void startList(String name);

	void endList();

	void listElementStart(int i);

	void complexStart(String name);

	void complexEnd();

	void listElementEnd();

	void startMap(String name);

	void mapStartEntry(String key);

	void mapEndEntry(String key);

	void endMap(String name);

}
