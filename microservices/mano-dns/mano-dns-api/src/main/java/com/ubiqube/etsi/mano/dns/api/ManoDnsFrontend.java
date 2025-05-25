package com.ubiqube.etsi.mano.dns.api;

import java.util.List;

import com.ubiqube.etsi.mano.dns.api.model.RrSet;
import com.ubiqube.etsi.mano.dns.api.model.Zone;

public interface ManoDnsFrontend {

	/**
	 * List all RR sets.
	 *
	 * @return a list of RR sets
	 */
	List<RrSet> recordList(String zoneId);

	boolean recordDelete(String zoneId, String rrSetName, String type);

	boolean recordCreate(String zoneId, RrSet rrSet);

	boolean recordModify(String zoneId, String rrSetName, RrSet rrSet);

	List<Zone> zoneList();

	boolean zoneDelete(String zoneId);

	Zone zoneCreate(Zone zone);

	Zone zoneModify(String zoneId, Zone zone);

	boolean zoneExists(String zoneId);

}
