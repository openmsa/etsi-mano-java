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

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class OffsetDateTimeToDateConverter extends BidirectionalConverter<OffsetDateTime, Date> {

	@Override
	public Date convertTo(final OffsetDateTime source, final Type<Date> inDestinationType, final MappingContext mappingContext) {
		return Date.from(source.toInstant());
	}

	@Override
	public OffsetDateTime convertFrom(final Date source, final Type<OffsetDateTime> inDestinationType, final MappingContext mappingContext) {
		return OffsetDateTime.from(source.toInstant().atOffset(ZoneOffset.UTC));
	}

}
