/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
package com.ubiqube.etsi.mano.service.event;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.dao.mano.ExtManagedVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.GrantVimAssetsEntity;
import com.ubiqube.etsi.mano.dao.mano.SoftwareImage;
import com.ubiqube.etsi.mano.dao.mano.VimComputeResourceFlavourEntity;
import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.VimSoftwareImageEntity;
import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfStorage;
import com.ubiqube.etsi.mano.dao.mano.ZoneGroupInformation;
import com.ubiqube.etsi.mano.dao.mano.ZoneInfoEntity;
import com.ubiqube.etsi.mano.dao.mano.pkg.VirtualCpu;
import com.ubiqube.etsi.mano.dao.mano.pkg.VirtualMemory;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.GrantsResponseJpa;
import com.ubiqube.etsi.mano.service.event.elect.VimElection;
import com.ubiqube.etsi.mano.service.sys.ServerGroup;
import com.ubiqube.etsi.mano.service.vim.Vim;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mano.vim.dto.SwImage;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public abstract class AbstractGrantAction {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractGrantAction.class);

	private final GrantsResponseJpa grantJpa;

	private final VimManager vimManager;

	private final VimElection vimElection;

	protected AbstractGrantAction(final GrantsResponseJpa grantJpa, final VimManager vimManager, final VimElection vimElection) {
		this.vimManager = vimManager;
		this.vimElection = vimElection;
		this.grantJpa = grantJpa;
	}

	@Nonnull
	protected abstract Set<VnfCompute> getVnfCompute(UUID objectId);

	@Nonnull
	protected abstract Set<VnfStorage> getVnfStorage(UUID objectId);

	public final void grantRequest(final UUID objectId) {
		try {
			grantRequestException(objectId);
		} catch (final RuntimeException e) {
			LOG.error("Removing Grand id: {}", objectId, e);
			grantJpa.deleteById(objectId);
		}
	}

	public final void grantRequestException(final UUID objectId) {
		LOG.info("Evaluating grant {}", objectId);
		final GrantResponse grants = getGrant(objectId);
		removeResources(grants);
		final VimConnectionInformation vimInfo = vimElection.doElection(null, getVnfCompute(objectId), getVnfStorage(objectId));
		if (vimInfo == null) {
			throw new GenericException("No suitable VIM after election.");
		}
		getVimInformations(vimInfo, grants);
		grants.setAvailable(Boolean.TRUE);

		LOG.debug("Saving...");
		grantJpa.save(grants);
		LOG.info("Grant {} Available.", grants.getId());
	}

	private void removeResources(final GrantResponse grants) {
		grants.getRemoveResources().forEach(x -> {
			if (x.getReservationId() != null) {
				final VimConnectionInformation vci = vimManager.findVimById(UUID.fromString(x.getVimConnectionId()));
				final Vim vim = vimManager.getVimById(UUID.fromString(x.getVimConnectionId()));
				vim.freeResources(vci, x);
			}
		});
	}

	private GrantResponse getGrant(final UUID objectId) {
		final Optional<GrantResponse> grantsOpt = grantJpa.findById(objectId);
		return grantsOpt.orElseThrow(() -> new NotFoundException("Grant ID " + objectId + " Not found."));
	}

	protected final void getVimInformations(final VimConnectionInformation vimInfo, final GrantResponse grants) {
		final ExecutorService executorService = Executors.newFixedThreadPool(10);
		Exception throwable = null;
		try {
			getGrantInformationsImpl(executorService, vimInfo, grants);
		} catch (final RuntimeException | InterruptedException | ExecutionException e) {
			throwable = e;
			LOG.info("", e);
			Thread.currentThread().interrupt();
		}
		LOG.debug("Shutdown Grant executor.");
		executorService.shutdown();
		try {
			executorService.awaitTermination(5, TimeUnit.MINUTES);
		} catch (final InterruptedException e) {
			LOG.info("", e);
			Thread.currentThread().interrupt();
		}
		if (null != throwable) {
			throw new GenericException(throwable);
		}
	}

	private void getGrantInformationsImpl(final ExecutorService executorService, final VimConnectionInformation vimInfo, final GrantResponse grants) throws InterruptedException, ExecutionException {
		// Zones.
		final Future<String> futureZone = executorService.submit(getZone(vimInfo, grants));
		// Zone Group
		final Vim vim = vimManager.getVimById(vimInfo.getId());
		final Future<ZoneGroupInformation> futureSg = executorService.submit(getServerGroup(grants));

		grants.setVimConnections(Collections.singleton(vimInfo));

		final GrantVimAssetsEntity grantVimAssetsEntity = new GrantVimAssetsEntity();
		grants.setVimAssets(grantVimAssetsEntity);
		// XXX Push only needed ones. ( in case of terminate no need to push assets.)
		final Runnable getSoftwareImages = () -> grantVimAssetsEntity.setSoftwareImages(getSoftwareImageSafe(vimInfo, vim, grants));
		executorService.submit(getSoftwareImages);

		final Runnable getComputeResourceFlavours = () -> {
			try {
				grantVimAssetsEntity.getComputeResourceFlavours().addAll(getFlavors(vimInfo, vim, grants.getId()));
			} catch (final RuntimeException e) {
				LOG.error("", e);
			}
			LOG.debug("           {}", grantVimAssetsEntity.getComputeResourceFlavours());
		};
		executorService.submit(getComputeResourceFlavours);

		// Add public networks.
		vim.network(vimInfo).getPublicNetworks().entrySet().forEach(x -> {
			final ExtManagedVirtualLinkDataEntity extVl = new ExtManagedVirtualLinkDataEntity();
			extVl.setResourceId(x.getValue());
			extVl.setResourceProviderId(vim.getType());
			extVl.setVimConnectionId(vimInfo.getVimId());
			extVl.setVnfVirtualLinkDescId(x.getKey());
			extVl.setGrants(grants);
			grants.addExtManagedVl(extVl);
		});
		getUnmanagedNetworks(grants, vim, vimInfo);
		final String zoneId = futureZone.get();
		final ZoneGroupInformation zgi = futureSg.get();
		// XXX It depends on Grant policy GRANT_RESERVE_SINGLE.
		grants.getAddResources().forEach(x -> {
			vim.allocateResources(vimInfo, x);
			x.setResourceProviderId(vim.getType());
			x.setVimConnectionId(vimInfo.getVimId());
			x.setZoneId(zoneId);
			x.setResourceGroupId(zgi.getZoneId().iterator().next());
		});

	}

	protected abstract void getUnmanagedNetworks(GrantResponse grants, Vim vim, VimConnectionInformation vimInfo);

	private static ZoneInfoEntity mapZone(final String zoneId, final VimConnectionInformation vimInfo) {
		final ZoneInfoEntity zoneInfoEntity = new ZoneInfoEntity();
		zoneInfoEntity.setVimConnectionId(vimInfo.getVimId());
		zoneInfoEntity.setResourceProviderId(vimInfo.getVimType());
		zoneInfoEntity.setZoneId(zoneId);
		return zoneInfoEntity;
	}

	private String chooseZone(final VimConnectionInformation vimInfo) {
		final Vim vim = vimManager.getVimById(vimInfo.getId());
		final List<String> list = vim.getZoneAvailableList(vimInfo);
		return list.get(0);
	}

	private List<VimComputeResourceFlavourEntity> getFlavors(final VimConnectionInformation vimConnectionInformation, final Vim vim, final UUID id) {
		final List<VimComputeResourceFlavourEntity> listVcrfe = new ArrayList<>();
		final Map<String, VimComputeResourceFlavourEntity> cache = new HashMap<>();
		getVnfCompute(id).forEach(x -> {
			final VirtualCpu vCpu = x.getVirtualCpu();
			final VirtualMemory vMem = x.getVirtualMemory();
			final String key = vCpu.getNumVirtualCpu() + "-" + vMem.getVirtualMemSize() + "-" + x.getDiskSize();
			final VimComputeResourceFlavourEntity vcretmp = cache.computeIfAbsent(key, y -> {
				final String flavorId = vim.getOrCreateFlavor(vimConnectionInformation, x.getName(), (int) vCpu.getNumVirtualCpu(), vMem.getVirtualMemSize(), x.getDiskSize(), vCpu.getVduCpuRequirements());
				final VimComputeResourceFlavourEntity vcrfe = new VimComputeResourceFlavourEntity();
				vcrfe.setVimConnectionId(vimConnectionInformation.getVimId());
				vcrfe.setResourceProviderId(vim.getType());
				vcrfe.setVimFlavourId(flavorId);
				return vcrfe;
			});
			final VimComputeResourceFlavourEntity vcre = new VimComputeResourceFlavourEntity(vcretmp);
			vcre.setVnfdVirtualComputeDescId(x.getToscaName());
			listVcrfe.add(vcre);
		});
		return listVcrfe;
	}

	private Set<VimSoftwareImageEntity> getSoftwareImageSafe(final VimConnectionInformation vimInfo, final Vim vim, final GrantResponse grants) {
		try {
			return getSoftwareImage(vimInfo, vim, grants);
		} catch (final RuntimeException e) {
			LOG.error("getImage error", e);
		}
		return new HashSet<>();
	}

	private Set<VimSoftwareImageEntity> getSoftwareImage(final VimConnectionInformation vimInfo, final Vim vim, final GrantResponse grants) {
		final Set<VimSoftwareImageEntity> listVsie = new HashSet<>();
		final Map<String, SwImage> cache = new HashMap<>();
		getVnfCompute(grants.getId()).forEach(x -> {
			final SoftwareImage img = x.getSoftwareImage();
			if (null != img) {
				// Get Vim or create vim resource via Or-Vi
				final SwImage imgCached = cache.computeIfAbsent(img.getName(), y -> {
					final Optional<SwImage> newImg = vim.storage(vimInfo).getSwImageMatching(img);
					return newImg.orElseGet(() -> uploadImage(vimInfo, vim, x, grants.getVnfdId()));
				});
				listVsie.add(mapSoftwareImage(imgCached, img.getName(), vimInfo, vim));
			}
		});
		final Set<VnfStorage> storage = getVnfStorage(grants.getId());
		storage.forEach(x -> {
			final SoftwareImage img = x.getSoftwareImage();
			if (null != img) {
				final SwImage imgCached = cache.get(img.getName());
				listVsie.add(mapSoftwareImage(imgCached, img.getName(), vimInfo, vim));
			}
		});
		return listVsie;
	}

	private SwImage uploadImage(final VimConnectionInformation vimInfo, final Vim vim, final VnfCompute x, final String vnfdId) {
		final SoftwareImage img = x.getSoftwareImage();
		try (final InputStream is = findImage(x.getSoftwareImage(), vnfdId)) {
			// Use or-vi, Vim is not on the same server. Path is given in tosca file.
			return vim.storage(vimInfo).uploadSoftwareImage(is, img);
		} catch (final IOException e) {
			throw new GenericException(e);
		}
	}

	protected abstract InputStream findImage(final SoftwareImage softwareImage, final String vnfdId);

	private static VimSoftwareImageEntity mapSoftwareImage(final SwImage softwareImage, final String vduId, final VimConnectionInformation vimInfo, final Vim vim) {
		final VimSoftwareImageEntity vsie = new VimSoftwareImageEntity();
		vsie.setVimSoftwareImageId(softwareImage.getVimResourceId());
		vsie.setVnfdSoftwareImageId(vduId);
		vsie.setVimConnectionId(vimInfo.getVimId());
		vsie.setResourceProviderId(vim.getType());
		return vsie;
	}

	public class QuotaNeeded {
		private int disk = 0;
		private int vcpu = 0;
		private int ram = 0;

		public int getDisk() {
			return disk;
		}

		public void setDisk(final int disk) {
			this.disk = disk;
		}

		public int getVcpu() {
			return vcpu;
		}

		public void setVcpu(final int vcpu) {
			this.vcpu = vcpu;
		}

		public int getRam() {
			return ram;
		}

		public void setRam(final int ram) {
			this.ram = ram;
		}

		public QuotaNeeded(final int disk, final int vcpu, final int ram) {
			super();
			this.disk = disk;
			this.vcpu = vcpu;
			this.ram = ram;
		}

	}

	private Callable<String> getZone(final VimConnectionInformation vimInfo, final GrantResponse grants) {
		return () -> {
			final String zoneId = chooseZone(vimInfo);
			final ZoneInfoEntity zones = mapZone(zoneId, vimInfo);
			grants.addZones(zones);
			return zoneId;
		};
	}

	/**
	 * XXX Check Vim caps. ZG are not availlable on all vims.
	 *
	 * @param grants
	 * @return
	 */
	private static Callable<ZoneGroupInformation> getServerGroup(final GrantResponse grants) {
		return () -> {
			final List<ServerGroup> sg = new ArrayList<>();
			final ServerGroup serverGroup = new ServerGroup("1", "az", "az");
			sg.add(serverGroup);
			final Set<String> sgList = sg.stream().map(ServerGroup::getId).collect(Collectors.toSet());
			final ZoneGroupInformation zgi = new ZoneGroupInformation();
			zgi.setZoneId(sgList);
			grants.setZoneGroups(Collections.singleton(zgi));
			return zgi;
		};
	}

}
