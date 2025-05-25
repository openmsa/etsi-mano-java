package com.ubiqube.etsi.mano.dns.api;

import java.util.List;

import com.ubiqube.etsi.mano.dns.api.model.RrSet;

public class RecordApi {
	private ManoDnsFrontend frontend;

	public List<RrSet> list() {
		return frontend.recordList(null);
	}

	public boolean delete(final String rrSetName) {
		return frontend.recordDelete(null, null, rrSetName);
	}

	public boolean create(final RrSet rrSet) {
		return frontend.recordCreate(null, rrSet);
	}

	public boolean modify(final String rrSetName, final RrSet rrSet) {
		return frontend.recordModify(null, rrSetName, rrSet);
	}
}
