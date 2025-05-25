package com.ubiqube.etsi.mano.dns.api.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RrSet {
	private String name;
	private String type;
	private Integer ttl;
	private List<String> records = new ArrayList<>();
	private List<String> comments = new ArrayList<>();
}
