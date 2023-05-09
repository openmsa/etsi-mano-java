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
package com.ubiqube.etsi.mano.mapper;

import java.util.EnumSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.util.Assert;

import jakarta.annotation.Nullable;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ManoLenientStringToEnum implements ConverterFactory<String, Enum> {

	private static final Logger LOG = LoggerFactory.getLogger(ManoLenientStringToEnum.class);

	@Override
	public <T extends Enum> Converter<String, T> getConverter(final Class<T> targetType) {
		return new StringToEnum(getEnumType(targetType));
	}

	private static Class<?> getEnumType(final Class<?> targetType) {
		Class<?> enumType = targetType;
		while ((enumType != null) && !enumType.isEnum()) {
			enumType = enumType.getSuperclass();
		}
		Assert.notNull(enumType, () -> "The target type " + targetType.getName() + " does not refer to an enum");
		return enumType;
	}

	private static class StringToEnum<T extends Enum> implements Converter<String, T> {

		private final Class<T> enumType;

		StringToEnum(final Class<T> enumType) {
			this.enumType = enumType;
		}

		@Override
		@Nullable
		public T convert(final String source) {
			if (source.isEmpty()) {
				// It's an empty enum identifier: reset the enum value to null.
				return null;
			}
			try {
				return (T) Enum.valueOf(this.enumType, source);
			} catch (final Exception ex) {
				LOG.trace("Enum problem", ex);
				return findEnum(source);
			}
		}

		private T findEnum(final String value) {
			final String name = getCanonicalName(value);
			for (final T candidate : (Set<T>) EnumSet.allOf(this.enumType)) {
				final String candidateName = getCanonicalName(candidate.name());
				if (name.equals(candidateName)) {
					return candidate;
				}
			}
			throw new IllegalArgumentException("No enum constant " + this.enumType.getCanonicalName() + "." + value);
		}

		private static String getCanonicalName(final String name) {
			final StringBuilder canonicalName = new StringBuilder(name.length());
			name.chars().filter(Character::isLetterOrDigit).map(Character::toLowerCase)
					.forEach(c -> canonicalName.append((char) c));
			return canonicalName.toString();
		}

	}

}
