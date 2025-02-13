package com.ubiqube.etsi.mano.service.mapping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.dao.mano.Attributes;
import com.ubiqube.etsi.mano.dao.mano.MonitoringParams;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.pkg.VnfProfile;
import com.ubiqube.etsi.mano.service.pkg.bean.ProviderData;
import com.ubiqube.etsi.mano.service.pkg.bean.ScaleInfo;

import uk.co.jemos.podam.api.PodamFactoryImpl;

class VnfPackageMappingTest {

	private final VnfPackageMappingImpl mapper = new VnfPackageMappingImpl();

	@Test
	void testMap_NullInput() {
		ProviderData providerData = null;
		VnfPackage result = mapper.map(providerData);
		assertNull(result);
	}

	@Test
	void testMap_ValidInput() {
		ProviderData providerData = new ProviderData();
		providerData.setDescriptorId("descriptorId");
		providerData.setAttributes(Arrays.asList(new Attributes(), new Attributes()));
		providerData.setDefaultLocalizationLanguage("en");
		providerData.setDescriptorVersion("1.0");
		providerData.setFlavorId("flavorId");
		providerData.setFlavourDescription("flavourDescription");
		providerData.setLocalizationLanguages(new HashSet<>(Arrays.asList("en", "fr")));
		providerData.setMonitoringParameters(new HashSet<>(Arrays.asList(new MonitoringParams(), new MonitoringParams())));
		providerData.setProductInfoDescription("productInfoDescription");
		providerData.setScaleStatus(new HashSet<>(Arrays.asList(new ScaleInfo(), new ScaleInfo())));
		providerData.setVnfProductName("vnfProductName");
		providerData.setVnfProfile(new VnfProfile());
		providerData.setVnfProvider("vnfProvider");
		providerData.setVnfSoftwareVersion("1.0");
		providerData.setVnfdVersion("1.0");
		providerData.setVnfmInfo(new HashSet<>(Arrays.asList("vnfm1", "vnfm2")));

		VnfPackage result = mapper.map(providerData);

		assertNotNull(result);
		assertEquals("descriptorId", result.getVnfdId());
		assertEquals(2, result.getAttributes().size());
		assertEquals("en", result.getDefaultLocalizationLanguage());
		assertEquals("1.0", result.getDescriptorVersion());
		assertEquals("flavorId", result.getFlavorId());
		assertEquals("flavourDescription", result.getFlavourDescription());
		assertEquals(2, result.getLocalizationLanguages().size());
		assertEquals(2, result.getMonitoringParameters().size());
		assertEquals("productInfoDescription", result.getProductInfoDescription());
		assertEquals(1, result.getScaleStatus().size());
		assertEquals("vnfProductName", result.getVnfProductName());
		assertEquals("vnfProvider", result.getVnfProvider());
		assertEquals("1.0", result.getVnfSoftwareVersion());
		assertEquals("1.0", result.getVnfdVersion());
		assertEquals(2, result.getVnfmInfo().size());
	}

	@Test
	void testMapProviderData() {
		PodamFactoryImpl podam = new PodamFactoryImpl();
		podam.getStrategy().setDefaultNumberOfCollectionElements(1);
		ProviderData pd = podam.manufacturePojo(ProviderData.class);
		VnfPackage dst = new VnfPackage();
		mapper.map(pd, dst);
		assertTrue(true);
	}

	@Test
	void testMapProviderDataFill() {
		PodamFactoryImpl podam = new PodamFactoryImpl();
		podam.getStrategy().setDefaultNumberOfCollectionElements(1);
		ProviderData pd = podam.manufacturePojo(ProviderData.class);
		VnfPackage dst = new VnfPackage();
		dst.setAttributes(new ArrayList<>());
		dst.setLocalizationLanguages(new HashSet<>());
		dst.setMonitoringParameters(new HashSet<>());
		dst.setScaleStatus(new HashSet<>());
		dst.setVnfmInfo(new HashSet<>());
		mapper.map(pd, dst);
		assertTrue(true);
	}
}
