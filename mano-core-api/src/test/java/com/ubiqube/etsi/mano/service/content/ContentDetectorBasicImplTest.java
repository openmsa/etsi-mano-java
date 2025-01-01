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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;

import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.repository.ByteArrayResource;
import com.ubiqube.etsi.mano.repository.ManoResource;

class ContentDetectorBasicImplTest {

	ContentDetectorBasicImpl createService() {
		return new ContentDetectorBasicImpl();
	}

	@Test
	void test2() {
		final ContentDetectorBasicImpl srv = createService();
		final byte[] src = { 'P', 'K', 3, 4, 'A', 'B', 'C' };
		final ManoResource mr = new ByteArrayResource(src, "file.jpeg");
		final MediaType res = srv.getMediaType(mr);
		assertEquals(MediaType.valueOf("application/zip"), res);
	}

	@Test
	void test() {
		final ContentDetectorBasicImpl srv = createService();
		final byte[] src = { '?', '?', 3, 4, 'A', 'B', 'C' };
		final ManoResource mr = new ByteArrayResource(src, "file.jpeg");
		final MediaType res = srv.getMediaType(mr);
		assertEquals(MediaType.APPLICATION_OCTET_STREAM, res);
	}

	@Test
	void TestIO() throws IOException {
		final ContentDetectorBasicImpl srv = createService();
		final ManoResource mr = Mockito.mock(ManoResource.class);
		final InputStream is = Mockito.mock(InputStream.class);
		when(mr.getInputStream()).thenReturn(is);
		when(is.read(any())).thenThrow(IOException.class);
		assertThrows(GenericException.class, () -> srv.getMediaType(mr));
	}
}
