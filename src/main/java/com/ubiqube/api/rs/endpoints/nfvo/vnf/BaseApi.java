package com.ubiqube.api.rs.endpoints.nfvo.vnf;

import javax.naming.InitialContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubiqube.api.exception.ServiceException;
import com.ubiqube.api.interfaces.repository.RepositoryService;
import com.ubiqube.api.rs.endpoints.nfvo.ConfiguredObjectMapper;
import com.ubiqube.api.rs.endpoints.nfvo.patch.Patcher;
import com.ubiqube.api.rs.endpoints.nfvo.patch.WeakPatcher;
import com.ubiqube.api.rs.endpoints.nfvo.repositories.SubscriptionRepository;
import com.ubiqube.api.rs.endpoints.nfvo.repositories.VnfPackageRepository;
import com.ubiqube.api.rs.exception.etsi.GenericException;

public class BaseApi {
	protected static final String NCROOT = "ncroot";
	protected static final String MANO = "MANO";
	protected static final String PROCESS_BASE_PATH = "Process";
	protected static final String PROCESS_NFVO_BASE_PATH = PROCESS_BASE_PATH + "/NFVO";
	protected static final String PROCESS_VNF_VNF_PCKGM_BASE_PATH = PROCESS_NFVO_BASE_PATH + "/VNF_PCKGM";
	protected static final String DATAFILE_BASE_PATH = "Datafiles";
	protected static final String NVFO_DATAFILE_BASE_PATH = "Datafiles/NFVO";
	protected static final String REPOSITORY_NVFO_DATAFILE_BASE_PATH = "Datafiles/NFVO/vnf_packages";
	protected static final String REPOSITORY_SUBSCRIPTION_BASE_PATH = NVFO_DATAFILE_BASE_PATH + "/subscriptions";
	protected static final String REPOSITORY_NSD_BASE_PATH = NVFO_DATAFILE_BASE_PATH + "/nsd";

	// Should be injected.
	protected final Patcher patcher = new WeakPatcher();

	protected final ObjectMapper mapper;
	protected final SubscriptionRepository subscriptionRepository = new SubscriptionRepository();
	protected final VnfPackageRepository vnfPackageRepository = new VnfPackageRepository();
	protected RepositoryService repositoryService;

	public BaseApi() {
		mapper = ConfiguredObjectMapper.getMapper();
		try {
			final InitialContext jndiContext = new InitialContext();
			// Use jmx-console service=JNDIView/list
			repositoryService = (RepositoryService) jndiContext.lookup("ubi-jentreprise/RepositoryManagerBean/remote-com.ubiqube.api.interfaces.repository.RepositoryService");
			init();
		} catch (final Exception e) {
			throw new GenericException(e);
		}
	}

	/**
	 * MSA related stuff.
	 *
	 * @throws ServiceException
	 */
	private void init() throws ServiceException {
		if (!repositoryService.exists(PROCESS_BASE_PATH)) {
			repositoryService.addDirectory(PROCESS_BASE_PATH, "", MANO, NCROOT);
		}
		if (!repositoryService.exists(PROCESS_NFVO_BASE_PATH)) {
			repositoryService.addDirectory(PROCESS_NFVO_BASE_PATH, "", MANO, NCROOT);
		}

		if (!repositoryService.exists(PROCESS_VNF_VNF_PCKGM_BASE_PATH)) {
			repositoryService.addDirectory(PROCESS_VNF_VNF_PCKGM_BASE_PATH, "", MANO, NCROOT);
		}

		if (!repositoryService.exists(DATAFILE_BASE_PATH)) {
			repositoryService.addDirectory(DATAFILE_BASE_PATH, "", MANO, NCROOT);
		}
		if (!repositoryService.exists(NVFO_DATAFILE_BASE_PATH)) {
			repositoryService.addDirectory(NVFO_DATAFILE_BASE_PATH, "", MANO, NCROOT);
		}
		if (!repositoryService.exists(REPOSITORY_NVFO_DATAFILE_BASE_PATH)) {
			repositoryService.addDirectory(REPOSITORY_NVFO_DATAFILE_BASE_PATH, "", MANO, NCROOT);
		}
		if (!repositoryService.exists(REPOSITORY_SUBSCRIPTION_BASE_PATH)) {
			repositoryService.addDirectory(REPOSITORY_SUBSCRIPTION_BASE_PATH, "", MANO, NCROOT);
		}
		if (!repositoryService.exists(REPOSITORY_NSD_BASE_PATH)) {
			repositoryService.addDirectory(REPOSITORY_NSD_BASE_PATH, "", MANO, NCROOT);
		}

	}

	/**
	 * Simple wrapper for removing Exceptions, and make sure that we serialize using
	 * correst latest.
	 *
	 * @param <T>
	 * @param input
	 * @param clazz
	 * @return
	 */
	protected <T> T string2Object(String input, Class<T> clazz) {
		try {
			return mapper.readValue(input, clazz);
		} catch (final Exception e) {
			throw new GenericException(e);
		}
	}

	protected <T> String object2String(T obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (final JsonProcessingException e) {
			throw new GenericException(e);
		}
	}

	/**
	 * Prevent directory traversal.
	 *
	 * @param fileName
	 * @return
	 */
	protected String sanitize(String fileName) {
		return fileName.replaceAll("\\.\\.", "");
	}
}
