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

import java.io.InputStream;
import java.util.UUID;

import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.repository.VirtualFileSystem;
import com.ubiqube.etsi.mano.service.pkg.PackageDescriptor;

public class TestPackageDescriptor implements PackageDescriptor<VnfPackageReader> {

	@Override
	public String getProviderName() {
		return "test";
	}

	@Override
	public boolean isProcessable(final ManoResource mr) {
		return true;
	}

	@Override
	public VnfPackageReader getNewReaderInstance(final InputStream mr, final UUID id) {
		return new TestVnfPackageReader();
	}

	@Override
	public VirtualFileSystem getFileSystem(final ManoResource res) {
		return null;
	}

}
