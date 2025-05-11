package com.ubiqube.etsi.mano.vnfm.service.recons;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.auth.config.TenantHolder;
import com.ubiqube.etsi.mano.dao.mano.InstantiationState;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.vim.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.service.vim.verify.ResourceVerifier;
import com.ubiqube.etsi.mano.vnfm.jpa.VnfLiveInstanceJpa;
import com.ubiqube.etsi.mano.vnfm.service.VnfInstanceService;

@Service
public class Reconsiliator {
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(Reconsiliator.class);

	private final VnfLiveInstanceJpa vnfLiveInstanceJpa;
	private final VnfInstanceService vnfInstanceService;
	private final Map<ResourceTypeEnum, ResourceVerifier> verifiers;

	public Reconsiliator(final VnfLiveInstanceJpa vnfLiveInstanceJpa, final List<ResourceVerifier> resourceVerifiers, final VnfInstanceService vnfInstanceService) {
		this.vnfLiveInstanceJpa = vnfLiveInstanceJpa;
		this.vnfInstanceService = vnfInstanceService;
		verifiers = resourceVerifiers.stream().collect(Collectors.toMap(ResourceVerifier::getResourceType, x -> x));
	}

	@Scheduled(fixedDelay = 60_000)
	void loop() {
		try {
			TenantHolder.setTenantId("ROOT");
			LOG.trace("Starting reconsiliator.");
			vnfInstanceService.findByInstantiationState(InstantiationState.INSTANTIATED).forEach(this::item);
		} finally {
			TenantHolder.clear();
		}
	}

	void item(final VnfInstance vnfInstance) {
		Set<VimConnectionInformation> vims = vnfInstance.getVimConnectionInfo();
		List<VnfLiveInstance> resourcess = vnfLiveInstanceJpa.findByVnfInstance(vnfInstance);
		resourcess.forEach(vnfLiveInstance -> {
			vnfLiveInstance.getResourceId();
			vnfLiveInstance.getTask().getType();
			ResourceVerifier ver = verifiers.get(vnfLiveInstance.getTask().getType());
			if (ver != null) {
				boolean res = ver.verify(vims.iterator().next(), vnfLiveInstance.getResourceId());
				if (!res) {
					LOG.warn("Resource {}/{} is not available anymore", vnfLiveInstance.getTask().getType(), vnfLiveInstance.getResourceId());
				}
			} else {
				LOG.warn("No verifier for {}", vnfLiveInstance.getTask().getType());
			}
		});
	}

}
