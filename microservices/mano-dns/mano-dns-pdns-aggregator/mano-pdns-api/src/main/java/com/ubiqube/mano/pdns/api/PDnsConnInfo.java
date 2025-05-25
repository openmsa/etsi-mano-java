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
