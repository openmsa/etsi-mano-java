package com.ubiqube.mano.pdns.api;

import java.util.List;

import org.springframework.web.reactive.function.client.WebClientResponseException.NotFound;

import com.ubiqube.etsi.mano.dns.api.ManoDnsFrontend;
import com.ubiqube.etsi.mano.dns.api.model.RrSet;
import com.ubiqube.etsi.mano.dns.api.model.Zone;
import com.ubiqube.mano.pdns.invoker.ApiClient;
import com.ubiqube.mano.pdns.invoker.auth.ApiKeyAuth;
import com.ubiqube.mano.pdns.model.Comment;
import com.ubiqube.mano.pdns.model.RRSet;
import com.ubiqube.mano.pdns.model.Record;
import com.ubiqube.mano.pdns.model.Zone.KindEnum;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PdnsManoDnsFrontend implements ManoDnsFrontend {

	private final ApiClient api;
	private final String serverId;

	public PdnsManoDnsFrontend(final PDnsConnInfo connInfo) {
		this.api = createClient(connInfo);
		this.serverId = connInfo.getServerId();
	}

	private ApiClient createClient(final PDnsConnInfo connInfo) {
		ApiClient defaultClient = new ApiClient();
		defaultClient.setBasePath(connInfo.getBaseUrl().toString());
		ApiKeyAuth authKey = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
		authKey.setApiKey(connInfo.getApiKey());
		return defaultClient;
	}

	@Override
	public List<RrSet> recordList(final String zoneId) {
		ZonesApi zoneApi = new ZonesApi(api);
		Mono<com.ubiqube.mano.pdns.model.Zone> res = zoneApi.listZone(serverId, zoneId, true, null, null, null);
		return res.map(com.ubiqube.mano.pdns.model.Zone::getRrsets)
				.flatMapMany(Flux::fromIterable)
				.map(rrSet -> RrSet.builder()
						.name(rrSet.getName())
						.type(rrSet.getType())
						.ttl(rrSet.getTtl())
						.records(rrSet.getRecords().stream().map(Record::getContent).toList())
						.build())
				.collectList()
				.block();
	}

	@Override
	public boolean recordDelete(final String zoneId, final String rrSetName, final String type) {
		com.ubiqube.mano.pdns.model.Zone zone = new com.ubiqube.mano.pdns.model.Zone();
		RRSet rrs = new RRSet();
		rrs.setName(rrSetName);
		rrs.setType(type);
		rrs.changetype("DELETE");
		zone.addRrsetsItem(rrs);
		ZonesApi zoneApi = new ZonesApi(api);
		zoneApi.patchZone(serverId, zoneId, zone).block();
		return true;
	}

	@Override
	public boolean recordCreate(final String zoneId, final RrSet rrSet) {
		com.ubiqube.mano.pdns.model.Zone zone = new com.ubiqube.mano.pdns.model.Zone();
		RRSet rrs = new RRSet();
		rrs.setName(rrSet.getName());
		rrs.setType(rrSet.getType());
		rrs.setTtl(rrSet.getTtl());
		for (String record : rrSet.getRecords()) {
			Record rec = new Record();
			rec.setContent(record);
			rec.disabled(false);
			rrs.addRecordsItem(rec);
		}
		if (null != rrSet.getComments()) {
			for (String comment : rrSet.getComments()) {
				Comment comm = new Comment();
				comm.content(comment);
				rrs.addCommentsItem(comm);
			}
		}
		rrs.changetype("REPLACE");
		zone.addRrsetsItem(rrs);
		ZonesApi zoneApi = new ZonesApi(api);
		zoneApi.patchZone(serverId, zoneId, zone).block();
		return true;
	}

	@Override
	public boolean recordModify(final String zoneId, final String rrSetName, final RrSet rrSet) {
		return recordCreate(zoneId, rrSet);
	}

	@Override
	public List<Zone> zoneList() {
		ZonesApi zoneApi = new ZonesApi(api);
		Flux<com.ubiqube.mano.pdns.model.Zone> res = zoneApi.listZones(serverId, null, null);
		return map(res);
	}

	private List<Zone> map(final Flux<com.ubiqube.mano.pdns.model.Zone> res) {
		return res.map(this::map).collectList().block();

	}

	private Zone map(final com.ubiqube.mano.pdns.model.Zone res) {
		Zone zone = Zone.builder()
				.name(res.getName())
				.id(res.getId())
				.build();
		zone.setId(res.getId());
		zone.setName(res.getName());
		return zone;
	}

	@Override
	public boolean zoneDelete(final String zoneId) {
		ZonesApi zoneApi = new ZonesApi(api);
		zoneApi.deleteZone(serverId, zoneId).block();
		return true;
	}

	@Override
	public Zone zoneCreate(final Zone zone) {
		ZonesApi zoneApi = new ZonesApi(api);
		com.ubiqube.mano.pdns.model.Zone pdnsZone = new com.ubiqube.mano.pdns.model.Zone();
		pdnsZone.setName(zone.getName());
		pdnsZone.setId(zone.getId());
		pdnsZone.setKind(KindEnum.MASTER);
		pdnsZone.setNameservers(List.of("ns1." + zone.getName(), "ns2." + zone.getName()));
		Mono<com.ubiqube.mano.pdns.model.Zone> res = zoneApi.createZone(serverId, pdnsZone, true);
		return map(res.block());
	}

	@Override
	public Zone zoneModify(final String zoneId, final Zone zone) {
		ZonesApi zoneApi = new ZonesApi(api);
		com.ubiqube.mano.pdns.model.Zone pdnsZone = new com.ubiqube.mano.pdns.model.Zone();
		pdnsZone.setName(zone.getName());
		pdnsZone.setId(zone.getId());
		zoneApi.patchZone(serverId, zoneId, pdnsZone).block();
		return zone;
	}

	@Override
	public boolean zoneExists(final String zoneId) {
		ZonesApi zoneApi = new ZonesApi(api);
		try {
			com.ubiqube.mano.pdns.model.Zone res = zoneApi.listZone(serverId, zoneId, false, null, null, null).block();
			return res != null;
		} catch (NotFound e) {
			return false;
		}
	}

}
