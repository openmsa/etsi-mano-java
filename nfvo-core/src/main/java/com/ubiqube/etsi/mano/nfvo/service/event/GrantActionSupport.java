/**
 *     Copyright (C) 2019-2023 Ubiqube.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.nfvo.service.event;

import static com.ubiqube.etsi.mano.Constants.getSafeUUID;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ExtManagedVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.GrantInformationExt;
import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfStorage;
import com.ubiqube.etsi.mano.dao.mano.common.ListKeyPair;
import com.ubiqube.etsi.mano.dao.mano.nsd.ForwarderMapping;
import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainer;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVirtualLinkTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfTask;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.GrantsResponseJpa;
import com.ubiqube.etsi.mano.nfvo.jpa.NsLiveInstanceJpa;
import com.ubiqube.etsi.mano.service.VnfPackageService;
import com.ubiqube.etsi.mano.service.event.GrantSupport;
import com.ubiqube.etsi.mano.service.event.PreVimSelection;
import com.ubiqube.etsi.mano.service.event.QuotaNeeded;
import com.ubiqube.etsi.mano.service.vim.NetworkObject;
import com.ubiqube.etsi.mano.service.vim.ResourceQuota;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.service.vim.VimManager;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class GrantActionSupport implements GrantSupport {

	private static final Logger LOG = LoggerFactory.getLogger(GrantActionSupport.class);

	private final GrantsResponseJpa grantJpa;

	private final VimManager vimManager;

	private final VnfPackageService vnfPackageService;

	private final Random rnd;

	private final NsLiveInstanceJpa nsLiveInstanceJpa;

	private final PreVimSelection preVimSelection;

	public GrantActionSupport(final GrantsResponseJpa grantJpa, final VimManager vimManager, final VnfPackageService vnfPackageService,
			final NsLiveInstanceJpa nsLiveInstanceJpa, final PreVimSelection preVimSelection) {
		this.grantJpa = grantJpa;
		this.vimManager = vimManager;
		this.vnfPackageService = vnfPackageService;
		this.nsLiveInstanceJpa = nsLiveInstanceJpa;
		this.preVimSelection = preVimSelection;
		this.rnd = new Random();
	}

	@Override
	public @Nonnull Set<VnfCompute> getVnfCompute(final UUID objectId) {
		final GrantResponse grant = grantJpa.findById(objectId).orElseThrow(() -> new GenericException("Could not find: " + objectId));
		final VnfPackage pkg = vnfPackageService.findByVnfdId(UUID.fromString(grant.getVnfdId()));
		return pkg.getVnfCompute();
	}

	@Override
	public @Nonnull Set<VnfStorage> getVnfStorage(final UUID objectId) {
		final GrantResponse grant = grantJpa.findById(objectId).orElseThrow();
		final VnfPackage pkg = vnfPackageService.findByVnfdId(UUID.fromString(grant.getVnfdId()));
		return pkg.getVnfStorage();
	}

	@Override
	public Set<OsContainer> getOsContainer(final UUID objectId) {
		final GrantResponse grant = grantJpa.findById(objectId).orElseThrow();
		final VnfPackage pkg = vnfPackageService.findByVnfdId(UUID.fromString(grant.getVnfdId()));
		return pkg.getOsContainer();
	}

	private VimConnectionInformation electVim(@Nullable final String vnfPackageVimId, final GrantResponse grantResponse, final VnfPackage vnfPackage) {
		final Set<VimConnectionInformation> vimConns = grantResponse.getVimConnections();
		String vimId;
		if ((null != vimConns) && !vimConns.isEmpty()) {
			LOG.info("Selecting vim via Given one.");
			vimId = vimConns.iterator().next().getVimId();
			return vimManager.findVimByVimId(vimId);
		}
		// XXX: Do some real elections.
		final Set<VimConnectionInformation> vims;
		if (null != vnfPackageVimId) {
			LOG.debug("Getting MSA 2.x VIM");
			vims = vimManager.getVimByType("MSA_20");
			return vims.iterator().next();
		}
		LOG.debug("Getting OS v3 VIM");
		vims = vimManager.getVimByType("OPENSTACK_V3");
		if (vims.isEmpty()) {
			throw new GenericException("Couldn't find a VIM.");
		}
		return doNormalElection(vnfPackage, grantResponse, vims);
	}

	private VimConnectionInformation doNormalElection(final VnfPackage vnfPackage, final GrantResponse grantResponse, final Set<VimConnectionInformation> vims) {
		final QuotaNeeded needed = summarizeResources(grantResponse, vnfPackage);
		final List<VimConnectionInformation> vimsSelected = new ArrayList<>();
		vims.parallelStream().forEach(x -> {
			final Vim vim = vimManager.getVimById(x.getId());
			final ResourceQuota quota = vim.getQuota(x);
			if (needed.getRam() > quota.getRamFree()) {
				LOG.debug("Removing vim {}: RAM needed: {} free: {}", x.getVimId(), needed.getRam(), quota.getRamFree());
				return;
			}
			if (needed.getVcpu() > quota.getVcpuFree()) {
				LOG.debug("Removing vim {}: Vcpu needed: {} free: {}", x.getVimId(), needed.getVcpu(), quota.getVcpuFree());
				return;
			}
			vimsSelected.add(x);
		});
		if (vimsSelected.isEmpty()) {
			throw new GenericException("No Vim found, after quota filtering.");
		}
		return vimsSelected.get(rnd.nextInt(vimsSelected.size()));
	}

	private static QuotaNeeded summarizeResources(final GrantResponse grantResponse, final VnfPackage vnfPackage) {
		final Set<GrantInformationExt> adds = grantResponse.getAddResources();
		int disk = 0;
		int vcpu = 0;
		int ram = 0;
		for (final GrantInformationExt grantInformationExt : adds) {
			if (grantInformationExt.getType() == ResourceTypeEnum.COMPUTE) {
				final VnfCompute compute = findCompute(vnfPackage, grantInformationExt.getResourceTemplateId());
				disk += compute.getDiskSize();
				vcpu += compute.getVirtualCpu().getNumVirtualCpu();
				ram += compute.getVirtualMemory().getVirtualMemSize();
			} else if (grantInformationExt.getType() == ResourceTypeEnum.STORAGE) {
				// Cinder.
			}
		}
		return new QuotaNeeded(disk, vcpu, ram);
	}

	private static VnfCompute findCompute(final VnfPackage vnfPackage, final String vduId) {
		return vnfPackage.getVnfCompute().stream()
				.filter(x -> x.getToscaName().equals(vduId))
				.findFirst()
				.orElseThrow(() -> new NotFoundException("VduId not found " + vduId));
	}

	@Override
	public void getUnmanagedNetworks(final GrantResponse grants, final Vim vim, final VimConnectionInformation vimConnectionInformation) {
		final String vnfInstanceId = grants.getVnfInstanceId();
		final List<NsLiveInstance> resList = nsLiveInstanceJpa.findByResourceId(vnfInstanceId);
		if (resList.size() != 1) {
			LOG.warn("No NS instance found {} Found {}", vnfInstanceId, resList.size());
			return;
		}
		final NsLiveInstance res = resList.get(0);
		final NsBlueprint bluePrint = res.getNsBlueprint();
		final NsdInstance nsdInstance = bluePrint.getNsInstance();
		final NsVnfTask inst = (NsVnfTask) res.getNsTask();
		final Set<ForwarderMapping> fwMapping = inst.getNsPackageVnfPackage().getForwardMapping();
		final String vduName = inst.getNsPackageVnfPackage().getToscaName();
		final List<ForwarderMapping> mappings = findMappingsByVdu(fwMapping, vduName);
		// Get VL pointing on OS
		final List<ListKeyPair> vl = inst.getNsPackageVnfPackage().getVirtualLinks().stream()
				.filter(x -> x.getValue() != null)
				.toList();
		vl.forEach(x -> {
			final Optional<ForwarderMapping> single = findMappingByFwName(mappings, x.getValue());
			single.ifPresent(y -> x.setValue(y.getVlName()));
		});

		final List<NetworkObject> vlList = vim.network(vimConnectionInformation)
				.searchByName(vl.stream()
						.map(ListKeyPair::getValue)
						.filter(Objects::nonNull)
						.toList());
		vlList.forEach(x -> grants.getExtManagedVirtualLinks().add(createVl(x, vl, vimConnectionInformation, grants)));
		final List<NsLiveInstance> vlLive = nsLiveInstanceJpa.findByNsdInstanceAndClass(nsdInstance, NsVirtualLinkTask.class);
		vlLive.stream().forEach(x -> {
			final NsVirtualLinkTask vlt = (NsVirtualLinkTask) x.getNsTask();
			final List<ListKeyPair> usedVl = inst.getNsPackageVnfPackage().getVirtualLinks().stream()
					.filter(v -> v.getValue().equals(vlt.getNsVirtualLink().getToscaName()))
					.toList();
			usedVl.forEach(y -> {
				final ExtManagedVirtualLinkDataEntity extVl = new ExtManagedVirtualLinkDataEntity();
				extVl.setGrants(grants);
				extVl.setResourceId(x.getResourceId());
				extVl.setResourceProviderId(x.getResourceProviderId());
				extVl.setVimConnectionId(x.getVimConnectionId());
				extVl.setVimLevelResourceType(x.getVimLevelResourceType());
				// extVl.setVnfInstance()
				extVl.setVnfVirtualLinkDescId(mapToVl(y.getIdx()));
				grants.getExtManagedVirtualLinks().add(extVl);
			});
			final List<ForwarderMapping> fm = findMappingsBytoscaName(mappings, x.getNsTask().getToscaName());
			if (fm.isEmpty()) {
				LOG.info("No forward ports for {}", x.getNsTask().getToscaName());
				return;
			}
			fm.forEach(y -> {
				final ExtManagedVirtualLinkDataEntity extVl = new ExtManagedVirtualLinkDataEntity();
				extVl.setGrants(grants);
				extVl.setResourceId(x.getResourceId());
				extVl.setResourceProviderId(x.getResourceProviderId());
				extVl.setVimConnectionId(x.getVimConnectionId());
				extVl.setVimLevelResourceType(x.getVimLevelResourceType());
				// extVl.setVnfInstance()
				extVl.setVnfVirtualLinkDescId(mapToVl(y.getVlId()));
				grants.getExtManagedVirtualLinks().add(extVl);
			});
		});
	}

	private static String mapToVl(final int vlId) {
		if (0 == vlId) {
			return "virtual_link";
		}
		return "virtual_link_" + vlId;
	}

	private static List<ForwarderMapping> findMappingsBytoscaName(final List<ForwarderMapping> mappings, final String toscaName) {
		return mappings.stream().filter(x -> x.getVlName().equals(toscaName)).toList();
	}

	private static Optional<ForwarderMapping> findMappingByFwName(final List<ForwarderMapping> mappings, final String value) {
		return mappings.stream().filter(x -> x.getForwardingName().equals(value)).findFirst();
	}

	private static List<ForwarderMapping> findMappingsByVdu(final Set<ForwarderMapping> fwMapping, final String vduName) {
		return fwMapping.stream().filter(x -> x.getVduName().equals(vduName)).toList();
	}

	private static ExtManagedVirtualLinkDataEntity createVl(final NetworkObject networkObject, final List<ListKeyPair> vl, final VimConnectionInformation vimConnectionInformation, final GrantResponse grants) {
		final ExtManagedVirtualLinkDataEntity ret = new ExtManagedVirtualLinkDataEntity();
		ret.setVnfVirtualLinkDescId(mapToVl(networkObject, vl));
		ret.setResourceId(networkObject.name());
		ret.setResourceProviderId(vimConnectionInformation.getVimType());
		ret.setVimConnectionId(vimConnectionInformation.getVimId());
		ret.setGrants(grants);
		return ret;
	}

	private static String mapToVl(final NetworkObject no, final List<ListKeyPair> vl) {
		final ListKeyPair kp = vl.stream().filter(x -> x.getValue().equals(no.id())).findFirst().orElseThrow();
		if (kp.getIdx() == 0) {
			return "virtual_link";
		}
		return "virtual_link_" + kp.getIdx();
	}

	@Override
	public UUID convertVnfdToId(final String vnfdId) {
		return vnfPackageService.findByVnfdId(UUID.fromString(vnfdId)).getId();
	}

	@Override
	public List<VimConnectionInformation> getVims(final GrantResponse grants) {
		final VnfPackage vnfPackage = vnfPackageService.findByVnfdId(getSafeUUID(grants.getVnfdId()));
		final QuotaNeeded needed = summarizeResources(grants, vnfPackage);
		return preVimSelection.selectVims(vnfPackage, grants, needed);
	}
}
