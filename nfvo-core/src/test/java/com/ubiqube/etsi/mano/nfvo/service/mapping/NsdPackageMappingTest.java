package com.ubiqube.etsi.mano.nfvo.service.mapping;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.service.pkg.bean.NsInformations;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

class NsdPackageMappingTest {
	private PodamFactory factory;
	private final NsdPackageMapping mapper = Mappers.getMapper(NsdPackageMapping.class);

	@BeforeEach
	void setUp() {
		factory = new PodamFactoryImpl();
		factory.getStrategy().setDefaultNumberOfCollectionElements(1);
	}

	@Test
	void test() {
		NsdPackage tgt = new NsdPackage();
		mapper.map(null, tgt);
		NsInformations src = new NsInformations();
		mapper.map(src, tgt);
		assertTrue(true);
	}

}
