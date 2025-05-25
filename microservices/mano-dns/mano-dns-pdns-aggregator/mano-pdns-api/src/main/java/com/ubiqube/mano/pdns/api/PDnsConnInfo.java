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
package com.ubiqube.mano.pdns.api;

import java.net.URI;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PDnsConnInfo {
	// The base URL of the PowerDNS API, which is used to connect to the PowerDNS
	// server.
	private URI baseUrl;
	// The API key is used for authentication with the PowerDNS API.
	private String apiKey;
	// The server ID is used to identify the specific PowerDNS server instance.
	private String serverId;

}
