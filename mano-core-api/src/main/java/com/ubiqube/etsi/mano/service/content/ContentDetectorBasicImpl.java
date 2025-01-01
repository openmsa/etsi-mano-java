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
package com.ubiqube.etsi.mano.service.content;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.repository.ManoResource;

@Service
public class ContentDetectorBasicImpl implements ContentDetector {

	@Override
	public MediaType getMediaType(final ManoResource mr) {
		final byte[] buff = new byte[16];
		try (InputStream is = mr.getInputStream()) {
			is.read(buff);
		} catch (final IOException e) {
			throw new GenericException(e);
		}
		final byte[] zipHeader = { (byte) 'P', (byte) 'K', 3, 4 };
		if (Arrays.equals(Arrays.copyOfRange(buff, 0, 4), zipHeader)) {
			return MediaType.valueOf("application/zip");
		}
		return MediaType.APPLICATION_OCTET_STREAM;
	}

}
