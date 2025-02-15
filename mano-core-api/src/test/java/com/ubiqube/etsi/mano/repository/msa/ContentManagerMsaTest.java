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
package com.ubiqube.etsi.mano.repository.msa;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ubiqube.etsi.mano.repository.Low;
import com.ubiqube.etsi.mano.repository.ManoResource;

class ContentManagerMsaTest {

	private ContentManagerMsa contentManagerMsa;

	@Mock
	private Low lowDriver;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		contentManagerMsa = new ContentManagerMsa(lowDriver);
	}

	@Test
	void testStore() {
		Path path = Paths.get("testfile.txt");
		InputStream stream = new ByteArrayInputStream("test content".getBytes());

		contentManagerMsa.store(path, stream);

		verify(lowDriver, times(1)).add(path.toString(), stream);
	}

	@Test
	void testLoad() {
		Path path = Paths.get("testfile.txt");
		ManoResource resource = mock(ManoResource.class);

		when(lowDriver.get(path.toString())).thenReturn(resource);

		ManoResource result = contentManagerMsa.load(path);

		verify(lowDriver, times(1)).get(path.toString());
		assertSame(resource, result);
	}

	@Test
	void testMkdir() {
		Path path = Paths.get("testdir");

		contentManagerMsa.mkdir(path);

		verify(lowDriver, times(1)).mkdir(path.toString());
	}

	@Test
	void testDelete() {
		Path path = Paths.get("testfile.txt");

		contentManagerMsa.delete(path);

		verify(lowDriver, times(1)).delete(path.toString());
	}
}
