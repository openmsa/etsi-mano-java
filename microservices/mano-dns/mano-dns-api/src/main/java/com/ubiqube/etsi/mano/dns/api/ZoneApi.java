package com.ubiqube.etsi.mano.dns.api;

import java.util.List;

import com.ubiqube.etsi.mano.dns.api.model.Zone;

public class ZoneApi {
	private ManoDnsFrontend frontend;

	public List<Zone> list() {
		return frontend.zoneList();
	}

	public boolean delete(final String zoneId) {
		return frontend.zoneDelete(zoneId);
	}

	public Zone create(final Zone zone) {
		return frontend.zoneCreate(zone);
	}

	public Zone modify(final String zoneId, final Zone zone) {
		return frontend.zoneModify(zoneId, zone);
	}

	public boolean exists(final String zoneId) {
		return frontend.zoneExists(zoneId);
	}

	public RecordApi rrset() {
		return new RecordApi();
	}
}
