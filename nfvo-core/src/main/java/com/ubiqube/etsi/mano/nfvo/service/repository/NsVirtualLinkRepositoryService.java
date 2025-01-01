package com.ubiqube.etsi.mano.nfvo.service.repository;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVirtualLink;
import com.ubiqube.etsi.mano.nfvo.jpa.NsVirtualLinkJpa;

@Service
public class NsVirtualLinkRepositoryService {
	private final NsVirtualLinkJpa nsVirtualLinkJpa;

	NsVirtualLinkRepositoryService(final NsVirtualLinkJpa nsVirtualLinkJpa) {
		this.nsVirtualLinkJpa = nsVirtualLinkJpa;
	}

	public Set<NsVirtualLink> findByNsdPackage(final NsdPackage nsdInfo) {
		return nsVirtualLinkJpa.findByNsdPackage(nsdInfo);
	}
}
