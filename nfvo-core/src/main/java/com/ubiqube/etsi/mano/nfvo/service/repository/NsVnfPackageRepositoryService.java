package com.ubiqube.etsi.mano.nfvo.service.repository;

import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.nfvo.jpa.NsVnfPackageJpa;

@Service
public class NsVnfPackageRepositoryService {
	private final NsVnfPackageJpa vnfPackageJpa;

	NsVnfPackageRepositoryService(final NsVnfPackageJpa vnfPackageJpa) {
		this.vnfPackageJpa = vnfPackageJpa;
	}

	public Set<VnfPackage> findByNsdPackages_NsdPackage_Id(final UUID id) {
		return vnfPackageJpa.findByNsdPackages_NsdPackage_Id(id);
	}
}
