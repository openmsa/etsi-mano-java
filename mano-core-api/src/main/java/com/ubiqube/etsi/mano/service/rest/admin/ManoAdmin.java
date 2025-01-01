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
package com.ubiqube.etsi.mano.service.rest.admin;

import com.ubiqube.etsi.mano.service.rest.QueryParameters;
import com.ubiqube.etsi.mano.service.rest.admin.srv.ManoServer;
import com.ubiqube.etsi.mano.service.rest.admin.vim.ManoVim;

import org.jspecify.annotations.NonNull;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
public class ManoAdmin {
	@NonNull
	private final QueryParameters client;

	public ManoAdmin(final QueryParameters manoClient) {
		this.client = manoClient;
	}

	public ManoServer server() {
		return new ManoServer(client);
	}

	public ManoVim vim() {
		return new ManoVim(client);
	}

	public AdminVnfPackage vnfPackage() {
		return new AdminVnfPackage(client);
	}

	public ManoCir cir() {
		return new ManoCir(client);
	}
}
