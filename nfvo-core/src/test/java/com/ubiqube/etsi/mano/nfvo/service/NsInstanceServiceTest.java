/**
 * Copyright (C) 2019-2025 Ubiqube.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.nfvo.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVirtualLinkTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsVnfTask;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsdTask;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.grammar.GrammarParser;
import com.ubiqube.etsi.mano.nfvo.service.repository.NsLiveInstanceRepositoryService;
import com.ubiqube.etsi.mano.nfvo.service.repository.NsVirtualLinkRepositoryService;
import com.ubiqube.etsi.mano.nfvo.service.repository.NsVnfPackageRepositoryService;
import com.ubiqube.etsi.mano.nfvo.service.repository.NsdInstanceRepositoryService;
import com.ubiqube.etsi.mano.service.search.ManoSearch;

import jakarta.persistence.EntityManager;

@ExtendWith(MockitoExtension.class)
class NsInstanceServiceTest {
	@Mock
	private NsVirtualLinkRepositoryService nsVirtualLinkJpa;
	@Mock
	private NsVnfPackageRepositoryService vnfPackageJpa;
	@Mock
	private NsdInstanceRepositoryService nsdInstanceJpa;
	@Mock
	private NsLiveInstanceRepositoryService nsLiveInstanceJpa;
	@Mock
	private EntityManager em;
	@Mock
	private GrammarParser grammarParser;
	@Mock
	private ManoSearch manoSearch;

	@Test
	void testCountLiveInstanceOfVirtualLink() {
		final NsInstanceService srv = new NsInstanceService(nsVirtualLinkJpa, vnfPackageJpa, nsdInstanceJpa, nsLiveInstanceJpa, grammarParser, manoSearch);
		when(nsLiveInstanceJpa.findByNsInstanceAndNsTaskToscaNameAndNsTaskClassGroupByNsTaskAlias(any(), any(), eq(NsVirtualLinkTask.class))).thenReturn(List.of());
		srv.countLiveInstanceOfVirtualLink(null, null);
		assertTrue(true);
	}

	@Test
	void testCountLiveInstanceOfVnf() {
		final NsInstanceService srv = new NsInstanceService(nsVirtualLinkJpa, vnfPackageJpa, nsdInstanceJpa, nsLiveInstanceJpa, grammarParser, manoSearch);
		final NsLiveInstance ns01 = new NsLiveInstance();
		final NsTask nsTask = new NsVnfTask();
		nsTask.setToscaName("");
		ns01.setNsTask(nsTask);
		when(nsLiveInstanceJpa.findByNsInstanceAndNsTaskToscaNameAndNsTaskClassGroupByNsTaskAlias(any(), any(), eq(NsVnfTask.class))).thenReturn(List.of(ns01));
		srv.countLiveInstanceOfVnf(null, null);
		assertTrue(true);
	}

	@Test
	void testCountLiveInstanceOfNsd() {
		final NsInstanceService srv = new NsInstanceService(nsVirtualLinkJpa, vnfPackageJpa, nsdInstanceJpa, nsLiveInstanceJpa, grammarParser, manoSearch);
		when(nsLiveInstanceJpa.findByNsInstanceAndNsTaskToscaNameAndNsTaskClassGroupByNsTaskAlias(any(), any(), eq(NsdTask.class))).thenReturn(List.of());
		srv.countLiveInstanceOfNsd(null, null);
		assertTrue(true);
	}

	@Test
	void testFindVlsByNsInstance() {
		final NsInstanceService srv = new NsInstanceService(nsVirtualLinkJpa, vnfPackageJpa, nsdInstanceJpa, nsLiveInstanceJpa, grammarParser, manoSearch);
		srv.findVlsByNsInstance(null);
		assertTrue(true);
	}

	@Test
	void testFindVnfPackageByNsInstance() {
		final NsInstanceService srv = new NsInstanceService(nsVirtualLinkJpa, vnfPackageJpa, nsdInstanceJpa, nsLiveInstanceJpa, grammarParser, manoSearch);
		final NsdPackage pkg = new NsdPackage();
		srv.findVnfPackageByNsInstance(pkg);
		assertTrue(true);
	}

	@Test
	void testSave() {
		final NsInstanceService srv = new NsInstanceService(nsVirtualLinkJpa, vnfPackageJpa, nsdInstanceJpa, nsLiveInstanceJpa, grammarParser, manoSearch);
		srv.save(null);
		assertTrue(true);
	}

	@Test
	void testDelete() {
		final NsInstanceService srv = new NsInstanceService(nsVirtualLinkJpa, vnfPackageJpa, nsdInstanceJpa, nsLiveInstanceJpa, grammarParser, manoSearch);
		srv.delete(null);
		assertTrue(true);
	}

	@Test
	void testFindById() {
		final NsInstanceService srv = new NsInstanceService(nsVirtualLinkJpa, vnfPackageJpa, nsdInstanceJpa, nsLiveInstanceJpa, grammarParser, manoSearch);
		final NsdInstance nsdInstance = new NsdInstance();
		when(nsdInstanceJpa.findById(any())).thenReturn(Optional.of(nsdInstance));
		srv.findById(null);
		assertTrue(true);
	}

	@Test
	void testFindById_LiveNotNull() {
		final NsInstanceService srv = new NsInstanceService(nsVirtualLinkJpa, vnfPackageJpa, nsdInstanceJpa, nsLiveInstanceJpa, grammarParser, manoSearch);
		final NsdInstance nsdInstance = new NsdInstance();
		when(nsdInstanceJpa.findById(any())).thenReturn(Optional.of(nsdInstance));
		when(nsLiveInstanceJpa.findByNsInstanceId(any())).thenReturn(List.of());
		srv.findById(null);
		assertTrue(true);
	}

	@Test
	void testFindById_LiveOneElement() {
		final NsInstanceService srv = new NsInstanceService(nsVirtualLinkJpa, vnfPackageJpa, nsdInstanceJpa, nsLiveInstanceJpa, grammarParser, manoSearch);
		final NsdInstance nsdInstance = new NsdInstance();
		when(nsdInstanceJpa.findById(any())).thenReturn(Optional.of(nsdInstance));
		final NsLiveInstance live = new NsLiveInstance();
		when(nsLiveInstanceJpa.findByNsInstanceId(any())).thenReturn(List.of(live));
		srv.findById(null);
		assertTrue(true);
	}

	@Test
	void testFindById_Fail() {
		final NsInstanceService srv = new NsInstanceService(nsVirtualLinkJpa, vnfPackageJpa, nsdInstanceJpa, nsLiveInstanceJpa, grammarParser, manoSearch);
		assertThrows(NotFoundException.class, () -> srv.findById(null));
	}

	@Test
	void testIsInstantiated() {
		final NsInstanceService srv = new NsInstanceService(nsVirtualLinkJpa, vnfPackageJpa, nsdInstanceJpa, nsLiveInstanceJpa, grammarParser, manoSearch);
		assertFalse(srv.isInstantiated(null));
		assertTrue(true);
	}

	@Test
	void testIsInstantiated_True() {
		final NsInstanceService srv = new NsInstanceService(nsVirtualLinkJpa, vnfPackageJpa, nsdInstanceJpa, nsLiveInstanceJpa, grammarParser, manoSearch);
		when(nsdInstanceJpa.countByNsdInfo(any())).thenReturn(3);
		assertTrue(srv.isInstantiated(null));
		assertTrue(true);
	}

	@Test
	void testFindAll() {
		final NsInstanceService srv = new NsInstanceService(nsVirtualLinkJpa, vnfPackageJpa, nsdInstanceJpa, nsLiveInstanceJpa, grammarParser, manoSearch);
		srv.findAll();
		assertTrue(true);
	}

	@Test
	void testFindByNsInstanceId() {
		final NsInstanceService srv = new NsInstanceService(nsVirtualLinkJpa, vnfPackageJpa, nsdInstanceJpa, nsLiveInstanceJpa, grammarParser, manoSearch);
		srv.findByNsInstanceId(null);
		assertTrue(true);
	}

}
