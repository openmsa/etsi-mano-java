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

import java.io.InputStream;
import java.util.UUID;

import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.repository.VirtualFileSystem;
import com.ubiqube.etsi.mano.service.pkg.PackageDescriptor;
import com.ubiqube.etsi.mano.service.pkg.ns.NsPackageProvider;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class DefaultNsPackageDescriptor implements PackageDescriptor<NsPackageProvider> {

	@Override
	public String getProviderName() {
		return "DEFAULT NSD";
	}

	@Override
	public boolean isProcessable(final ManoResource data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public NsPackageProvider getNewReaderInstance(final InputStream data, final UUID id) {
		return null;
	}

	@Override
	public VirtualFileSystem getFileSystem(final ManoResource res) {
		return null;
	}

}
