package com.ubiqube.etsi.mano.nfvo.service.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.NsdPackageVnfPackage;
import com.ubiqube.etsi.mano.nfvo.jpa.NsdPackageVnfPackageJpa;

class NsdPackageVnfPackageRepositoryServiceTest {

	@Mock
	private NsdPackageVnfPackageJpa nsdPackageVnfPackageJpa;

	@InjectMocks
	private NsdPackageVnfPackageRepositoryService nsdPackageVnfPackageRepositoryService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindByNsdPackage() {
		NsdPackage nsdPackage = new NsdPackage();
		Set<NsdPackageVnfPackage> expectedSet = new HashSet<>();
		when(nsdPackageVnfPackageJpa.findByNsdPackage(nsdPackage)).thenReturn(expectedSet);

		Set<NsdPackageVnfPackage> resultSet = nsdPackageVnfPackageRepositoryService.findByNsdPackage(nsdPackage);

		assertEquals(expectedSet, resultSet);
		verify(nsdPackageVnfPackageJpa).findByNsdPackage(nsdPackage);
	}

	@Test
	void testFindByVnfdId() {
		String vnfdId = "vnfdId";
		Set<NsdPackageVnfPackage> expectedSet = new HashSet<>();
		when(nsdPackageVnfPackageJpa.findByVnfdId(vnfdId)).thenReturn(expectedSet);

		Set<NsdPackageVnfPackage> resultSet = nsdPackageVnfPackageRepositoryService.findByVnfdId(vnfdId);

		assertEquals(expectedSet, resultSet);
		verify(nsdPackageVnfPackageJpa).findByVnfdId(vnfdId);
	}
}
