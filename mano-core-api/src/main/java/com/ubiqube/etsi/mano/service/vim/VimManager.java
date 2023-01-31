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
package com.ubiqube.etsi.mano.service.vim;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.search.engine.search.predicate.SearchPredicate;
import org.hibernate.search.engine.search.predicate.dsl.SearchPredicateFactory;
import org.hibernate.search.engine.search.query.dsl.SearchQuerySelectStep;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.common.EntityReference;
import org.hibernate.search.mapper.orm.search.loading.dsl.SearchLoadingOptionsStep;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.SoftwareImage;
import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.cnf.CnfServer;
import com.ubiqube.etsi.mano.dao.mano.common.GeoPoint;
import com.ubiqube.etsi.mano.dao.mano.vnfi.CnfInformations;
import com.ubiqube.etsi.mano.dao.mano.vnfi.VimCapability;
import com.ubiqube.etsi.mano.dao.mano.vrqan.VrQan;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.CnfServerJpa;
import com.ubiqube.etsi.mano.jpa.VimConnectionInformationJpa;
import com.ubiqube.etsi.mano.jpa.VrQanJpa;
import com.ubiqube.etsi.mano.service.SystemService;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.event.model.NotificationEvent;
import com.ubiqube.etsi.mano.vim.dto.SwImage;

import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class VimManager {

	private static final Logger LOG = LoggerFactory.getLogger(VimManager.class);

	private final List<Vim> vims;

	private final VimConnectionInformationJpa vimConnectionInformationJpa;

	private final Map<UUID, Vim> vimAssociation;

	private final EntityManager entityManager;

	private final SystemService systemService;

	private final CnfServerJpa cnfServerJpa;

	private final VrQanJpa vrQanJpa;

	private final EventManager em;

	public VimManager(final List<Vim> vims, final VimConnectionInformationJpa vimConnectionInformationJpa, final EntityManager entityManager, final SystemService systemService,
			final CnfServerJpa cnfServerJpa, final VrQanJpa vrQanJpa, final EventManager em) {
		this.vims = vims;
		this.vimAssociation = new HashMap<>();
		this.vimConnectionInformationJpa = vimConnectionInformationJpa;
		this.entityManager = entityManager;
		this.systemService = systemService;
		this.cnfServerJpa = cnfServerJpa;
		this.vrQanJpa = vrQanJpa;
		this.em = em;
		init();
	}

	private void init() {
		vims.forEach(x -> {
			final Set<VimConnectionInformation> vimsId = vimConnectionInformationJpa.findByVimType(x.getType());
			associateVims(vimsId, x);
		});
	}

	private void associateVims(final Set<VimConnectionInformation> vimsIs, final Vim vim) {
		vimsIs.forEach(x -> vimAssociation.put(x.getId(), vim));
	}

	@Nonnull
	public Vim getVimById(final UUID id) {
		return Optional.ofNullable(vimAssociation.get(id)).orElseThrow(() -> new NotFoundException("No such Vim: " + id));
	}

	public void rebuildCache() {
		init();
	}

	public VimConnectionInformation findVimById(final UUID id) {
		return vimConnectionInformationJpa.findById(id).orElseThrow(() -> new NotFoundException("No connection Id " + id));
	}

	public VimConnectionInformation findVimByVimId(final String id) {
		return vimConnectionInformationJpa.findByVimId(id).orElseThrow(() -> new NotFoundException("No connection vimId " + id));
	}

	@Nonnull
	public Set<VimConnectionInformation> getVimByType(final String type) {
		return vimConnectionInformationJpa.findByVimType(type);
	}

	public VimConnectionInformation save(final VimConnectionInformation x) {
		return vimConnectionInformationJpa.save(x);
	}

	public Optional<VimConnectionInformation> findOptionalVimByVimId(final String vimId) {
		return vimConnectionInformationJpa.findByVimId(vimId);
	}

	public Iterable<VimConnectionInformation> findAllVimconnections() {
		return vimConnectionInformationJpa.findAll();
	}

	public void getVimByDistance(final GeoPoint point) {
		final SearchSession session = Search.session(entityManager);
		final SearchQuerySelectStep<?, EntityReference, VimConnectionInformation, SearchLoadingOptionsStep, ?, ?> ss = session.search(VimConnectionInformation.class);
		final SearchPredicateFactory pf = session.scope(VimConnectionInformation.class).predicate();
		final SearchPredicate pr = pf.spatial().within().fields("").circle(point.getLat(), point.getLng(), 10000).toPredicate();
		ss.where(pr);
	}

	@Transactional(TxType.REQUIRED)
	public VimConnectionInformation registerIfNeeded(final VimConnectionInformation x) {
		synchronized (VimManager.class) {
			final Optional<VimConnectionInformation> vim = vimConnectionInformationJpa.findByVimId(x.getVimId());
			if (vim.isPresent()) {
				return vim.get();
			}
			return registerVim(x);
		}
	}

	@Transactional
	public void deleteVim(final UUID id) {
		vimConnectionInformationJpa.deleteById(id);
		systemService.deleteByVimOrigin(id);
		rebuildCache();
	}

	@Transactional
	public VimConnectionInformation register(final VimConnectionInformation vci) {
		checkUniqueness(vci);
		checkVimConnectivity(vci);
		registerVim(vci);
		return callVrqan(vci);
	}

	private void checkUniqueness(final VimConnectionInformation vci) {
		vimConnectionInformationJpa.findByVimId(vci.getVimId()).ifPresent(x -> {
			throw new GenericException("Vim " + x.getVimId() + " is already present.");
		});
	}

	private void checkVimConnectivity(final VimConnectionInformation vci) {
		final Vim vim = findVim(vci);
		vim.authenticate(vci);
	}

	private Vim findVim(final VimConnectionInformation vci) {
		return vims.stream()
				.filter(x -> x.getType().equals(vci.getVimType())).findFirst()
				.orElseThrow(() -> new GenericException("Could not find vim [" + vci.getVimType() + "]"));
	}

	private VimConnectionInformation registerVim(final VimConnectionInformation vci) {
		extractCapabilities(vci);
		mergeCnf(vci);
		final VimConnectionInformation n = vimConnectionInformationJpa.save(vci);
		systemService.registerVim(n);
		init();
		return n;
	}

	private void mergeCnf(final VimConnectionInformation vci) {
		final Optional<CnfServer> cnfServer = cnfServerJpa.findById(UUID.fromString(vci.getVimId()));
		if (cnfServer.isEmpty()) {
			LOG.info("No CNF information for vim: {}", vci.getVimId());
			return;
		}
		final CnfInformations cnfi = cnfServer.get().getInfo();
		final CnfInformations cni = new CnfInformations();
		cni.setClusterTemplate(cnfi.getClusterTemplate());
		cni.setDnsServer(cnfi.getDnsServer());
		cni.setKeyPair(cnfi.getKeyPair());
		cni.setMasterFlavorId(cnfi.getMasterFlavorId());
		cni.setNetworkDriver(cnfi.getNetworkDriver());
		cni.setServerType(cnfi.getServerType());
		vci.setCnfInfo(cni);
	}

	private void extractCapabilities(final VimConnectionInformation vci) {
		final Vim vim = findVim(vci);
		final List<VimCapability> caps = vim.getCaps(vci);
		vci.setVimCapabilities(caps.stream().collect(Collectors.toSet()));
	}

	public VimConnectionInformation refresh(final UUID id) {
		final VimConnectionInformation vci = vimConnectionInformationJpa.findById(id).orElseThrow(() -> new GenericException("Unable to find vim " + id));
		extractCapabilities(vci);
		return vimConnectionInformationJpa.save(vci);
	}

	public List<SoftwareImage> getDetailedImageList(final VimConnectionInformation vimConn) {
		final Vim vim = findVim(vimConn);
		@Nonnull
		final Storage storage = vim.storage(vimConn);
		final List<SwImage> preList = storage.getImageList();
		return preList.stream().map(x -> mapper(storage, x)).toList();
	}

	private static SoftwareImage mapper(final Storage storage, final SwImage x) {
		return storage.getImageDetail(x.getVimResourceId());
	}

	@Transactional
	public VimConnectionInformation callVrqan(final VimConnectionInformation vci) {

		try {
			final Optional<VrQan> ovrqan = vrQanJpa.findByVimId(vci.getId());
			final VrQan vrqan = ovrqan.orElseGet(() -> {
				final VrQan vq = new VrQan(vci.getId());
				return vrQanJpa.save(vq);
			});

			final Vim vim = getVimById(vci.getId());
			final ResourceQuota pr = vim.getQuota(vci);
			final VrQan diff = VimUtils.compare(pr, vrqan);
			if (diff.haveValue()) {
				LOG.info("Send notification for vim: {} with diff {}", vci.getId(), diff);
				VimUtils.copy(pr, vrqan);
				vrqan.setLastChange(ZonedDateTime.now());
				vrqan.setLastCheck(ZonedDateTime.now());
				vrQanJpa.save(vrqan);
				em.sendNotification(NotificationEvent.VRQAN, vci.getId(), Map.of());
			} else {
				vrqan.setLastCheck(ZonedDateTime.now());
				vrQanJpa.save(vrqan);
			}
		} catch (final RuntimeException e) {
			LOG.error("", e);
		}
		return vci;
	}
}
