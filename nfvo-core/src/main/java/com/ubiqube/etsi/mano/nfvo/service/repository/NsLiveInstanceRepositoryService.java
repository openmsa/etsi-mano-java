package com.ubiqube.etsi.mano.nfvo.service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;

@Service
public class NsLiveInstanceRepositoryService {
	private final NsLiveInstanceJpa nsLiveInstanceJpa;

	NsLiveInstanceRepositoryService(final NsLiveInstanceJpa nsLiveInstanceJpa) {
		this.nsLiveInstanceJpa = nsLiveInstanceJpa;
	}

	public List<NsLiveInstance> findByNsInstanceAndNsTaskToscaNameAndNsTaskClassGroupByNsTaskAlias(final NsdInstance nsInstance, final String toscaName, final Class<?> class1) {
		return nsLiveInstanceJpa.findByNsInstanceAndNsTaskToscaNameAndNsTaskClassGroupByNsTaskAlias(nsInstance, toscaName, class1);
	}

	public List<NsLiveInstance> findByNsInstanceId(final UUID nsUuid) {
		return nsLiveInstanceJpa.findByNsInstanceId(nsUuid);
	}

}
