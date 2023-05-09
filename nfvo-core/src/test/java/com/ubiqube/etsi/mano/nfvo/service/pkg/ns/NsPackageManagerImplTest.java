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
package com.ubiqube.etsi.mano.nfvo.service.pkg.ns;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.service.pkg.PackageDescriptor;
import com.ubiqube.etsi.mano.service.pkg.ns.NsPackageProvider;

@ExtendWith(MockitoExtension.class)
class NsPackageManagerImplTest {
	@Mock
	private PackageDescriptor<NsPackageProvider> nsPackageProvider;

	@Test
	void test() {
		final List<PackageDescriptor<NsPackageProvider>> lst = List.of(nsPackageProvider);
		final NsPackageManagerImpl srv = new NsPackageManagerImpl(lst);
		srv.getProviderFor(null);
		assertTrue(true);
	}

	@Test
	void test2() {
		final List<PackageDescriptor<NsPackageProvider>> lst = List.of(nsPackageProvider);
		final NsPackageManagerImpl srv = new NsPackageManagerImpl(lst);
		when(nsPackageProvider.isProcessable(any())).thenReturn(true);
		srv.getProviderFor(null);
		assertTrue(true);
	}
}
