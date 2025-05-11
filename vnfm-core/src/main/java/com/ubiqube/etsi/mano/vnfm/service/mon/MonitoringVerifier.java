package com.ubiqube.etsi.mano.vnfm.service.mon;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.vim.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.service.vim.verify.ResourceVerifier;
import com.ubiqube.etsi.mano.vnfm.service.repository.PmJobsRepositoryService;

@Service
public class MonitoringVerifier implements ResourceVerifier {
	private final PmJobsRepositoryService pmJobsJpa;

	public MonitoringVerifier(final PmJobsRepositoryService pmJobsJpa) {
		this.pmJobsJpa = pmJobsJpa;
	}

	@Override
	public boolean verify(final VimConnectionInformation vim, final String resourceId) {
		return pmJobsJpa.findById(UUID.fromString(resourceId)).isPresent();
	}

	@Override
	public ResourceTypeEnum getResourceType() {
		return ResourceTypeEnum.MONITORING;
	}

}
