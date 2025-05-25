package com.ubiqube.etsi.mano.dns.api.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Zone {

	private String id;
	private String name;
	private int ttl;
	private List<String> masters;
	/**
	 * Simple list of strings of nameserver names, including the trailing dot. Not
	 * required for slave zones.
	 */
	private List<String> nameservers;
}
