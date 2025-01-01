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
package com.ubiqube.etsi.mano.nfvo.service.pkg.ns;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.OnboardingStateType;
import com.ubiqube.etsi.mano.dao.mano.PackageOperationalState;
import com.ubiqube.etsi.mano.nfvo.service.repository.NsdPackageRepositoryService;
import com.ubiqube.etsi.mano.repository.ManoResource;
import com.ubiqube.etsi.mano.repository.NsdRepository;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.pkg.PackageDescriptor;
import com.ubiqube.etsi.mano.service.pkg.ns.NsPackageProvider;

import org.jspecify.annotations.NonNull;

@ExtendWith(MockitoExtension.class)
class NsPackageOnboardingImplTest {
	@Mock
	@NonNull
	private EventManager eventManager;
	@Mock
	@NonNull
	private NsPackageManager packageManager;
	@Mock
	@NonNull
	private NsdRepository nsdRepository;
	@Mock
	@NonNull
	private NsOnboardingMapperService nsOnboardingMapperService;
	@Mock
	@NonNull
	private NsdPackageRepositoryService nsdPackageJpa;
	@Mock
	@NonNull
	private NsPackageProvider packageProvider;
	@Mock
	@NonNull
	private PackageDescriptor<NsPackageProvider> pkgDescr;
	@Mock
	@NonNull
	private ManoResource manoResource;
	@Mock
	@NonNull
	private InputStream inputStream;

	@Test
	void testMapNsPacakge() {
		final NsPackageOnboardingImpl pkg = new NsPackageOnboardingImpl(eventManager, packageManager, nsdRepository, nsOnboardingMapperService, nsdPackageJpa);
		final NsdPackage nsPkg = new NsdPackage();
		pkg.mapNsPackage(packageProvider, nsPkg);
		assertTrue(true);
	}

	@Test
	void testNsOnboarding() {
		final NsPackageOnboardingImpl pkg = new NsPackageOnboardingImpl(eventManager, packageManager, nsdRepository, nsOnboardingMapperService, nsdPackageJpa);
		final NsdPackage nsPkg = new NsdPackage();
		final UUID id = UUID.randomUUID();
		when(nsdPackageJpa.findById(id)).thenReturn(nsPkg);
		pkg.nsOnboarding(id);
		assertEquals(OnboardingStateType.ONBOARDED, nsPkg.getNsdOnboardingState());
		assertEquals(PackageOperationalState.ENABLED, nsPkg.getOperationalState());
	}

	@Test
	void testNsOnboardingFail() throws IOException {
		final NsPackageOnboardingImpl pkg = new NsPackageOnboardingImpl(eventManager, packageManager, nsdRepository, nsOnboardingMapperService, nsdPackageJpa);
		final NsdPackage nsPkg = new NsdPackage();
		final UUID id = UUID.randomUUID();
		// when(nsdPackageJpa.findById(id)).thenReturn(Optional.of(nsPkg));
		when(nsdRepository.getBinary(any(), any())).thenReturn(manoResource);
		when(packageManager.getProviderFor(any())).thenReturn(pkgDescr);
		when(manoResource.getInputStream()).thenReturn(inputStream);
		doThrow(IOException.class).when(inputStream).close();
		when(nsdPackageJpa.findById(any())).thenReturn(nsPkg);
		pkg.nsOnboarding(id);
		assertEquals(OnboardingStateType.ERROR, nsPkg.getNsdOnboardingState());
		assertEquals(PackageOperationalState.DISABLED, nsPkg.getOperationalState());
	}

	@Test
	void testNsOnboardingSucess() {
		final NsPackageOnboardingImpl pkg = new NsPackageOnboardingImpl(eventManager, packageManager, nsdRepository, nsOnboardingMapperService, nsdPackageJpa);
		final NsdPackage nsPkg = new NsdPackage();
		final UUID id = UUID.randomUUID();
		// when(nsdPackageJpa.findById(id)).thenReturn(Optional.of(nsPkg));
		when(nsdRepository.getBinary(any(), any())).thenReturn(manoResource);
		when(packageManager.getProviderFor(any())).thenReturn(pkgDescr);
		when(manoResource.getInputStream()).thenReturn(inputStream);
		when(nsdPackageJpa.findById(any())).thenReturn(nsPkg);
		when(pkgDescr.getNewReaderInstance(inputStream, null)).thenReturn(packageProvider);
		pkg.nsOnboarding(id);
		assertEquals(OnboardingStateType.ONBOARDED, nsPkg.getNsdOnboardingState());
		assertEquals(PackageOperationalState.ENABLED, nsPkg.getOperationalState());
	}
}
