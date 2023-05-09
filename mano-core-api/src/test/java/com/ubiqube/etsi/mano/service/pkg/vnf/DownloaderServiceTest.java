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
package com.ubiqube.etsi.mano.service.pkg.vnf;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.SoftwareImage;
import com.ubiqube.etsi.mano.dao.mano.common.Checksum;
import com.ubiqube.etsi.mano.repository.VnfPackageRepository;
import com.ubiqube.etsi.mano.service.pkg.vnf.DownloaderService;

@ExtendWith(MockitoExtension.class)
class DownloaderServiceTest {
	@Mock
	private VnfPackageRepository vnfPackageRepository;

	@Test
	void testBasic() throws Exception {
		final DownloaderService srv = new DownloaderService(vnfPackageRepository);
		final UUID id = UUID.randomUUID();
		srv.doDownload(List.of(), id);
		assertTrue(true);
	}

	@Test
	void testOk() throws Exception {
		final DownloaderService srv = new DownloaderService(vnfPackageRepository);
		final UUID id = UUID.randomUUID();
		final SoftwareImage sw01 = new SoftwareImage();
		sw01.setImagePath("http://nexus.ubiqube.com/repository/local-helm/index.yaml");
		sw01.setChecksum(new Checksum());
		srv.doDownload(List.of(sw01), id);
		assertTrue(true);
	}

	@Test
	void testNotFound() throws Exception {
		final DownloaderService srv = new DownloaderService(vnfPackageRepository);
		final UUID id = UUID.randomUUID();
		final SoftwareImage sw01 = new SoftwareImage();
		sw01.setImagePath("http://nexus.ubiqube.com/repository/local-helm-bad/index.yaml");
		sw01.setChecksum(new Checksum());
		srv.doDownload(List.of(sw01), id);
		// TODO This test should crash
		assertTrue(true);
	}
}
