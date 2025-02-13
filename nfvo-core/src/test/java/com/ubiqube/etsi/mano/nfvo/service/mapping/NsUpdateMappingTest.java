package com.ubiqube.etsi.mano.nfvo.service.mapping;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.ubiqube.etsi.mano.dao.mano.ExtManagedVirtualLinkDataEntity;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.ChangeExtVnfConnectivityData;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.ChangeVnfFlavourData;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.ExtManagedVirtualLinkData;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.InstantiateVnfData;
import com.ubiqube.etsi.mano.dao.mano.nsd.upd.OperateVnfData;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.dao.mano.vnfi.ChangeExtVnfConnRequest;
import com.ubiqube.etsi.mano.model.VnfOperateRequest;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

class NsUpdateMappingTest {
	private PodamFactory factory;
	private final NsUpdateMapping mapper = Mappers.getMapper(NsUpdateMapping.class);

	@BeforeEach
	void setUp() {
		factory = new PodamFactoryImpl();
		factory.getStrategy().setDefaultNumberOfCollectionElements(1);
	}

	@Test
	void test() {
		assertNull(mapper.map((InstantiateVnfData) null));
		InstantiateVnfData obj = factory.manufacturePojo(InstantiateVnfData.class);
		obj.getExtManagedVirtualLinks().forEach(vl -> vl.setId(UUID.randomUUID().toString()));
		NsBlueprint r = mapper.map(obj);
		assertNotNull(r);
	}

	@Test
	void testOperateVnfData() {
		assertNull(mapper.map((OperateVnfData) null));
		OperateVnfData obj = factory.manufacturePojo(OperateVnfData.class);
		VnfOperateRequest r = mapper.map(obj);
		assertNotNull(r);
	}

	@Test
	void testOperateVnfDataNotNull() {
		assertNull(mapper.map((OperateVnfData) null));
		OperateVnfData obj = factory.manufacturePojo(OperateVnfData.class);
		obj.setAdditionalParam(null);
		VnfOperateRequest r = mapper.map(obj);
		assertNotNull(r);
	}

	@Test
	void testExtManagedVirtualLinkData() {
		assertNull(mapper.map((ExtManagedVirtualLinkData) null));
		ExtManagedVirtualLinkData obj = factory.manufacturePojo(ExtManagedVirtualLinkData.class);
		obj.setId(UUID.randomUUID().toString());
		ExtManagedVirtualLinkDataEntity r = mapper.map(obj);
		assertNotNull(r);
	}

	@Test
	void testChangeVnfFlavourData() {
		assertNull(mapper.map((ChangeVnfFlavourData) null));
		ChangeVnfFlavourData obj = factory.manufacturePojo(ChangeVnfFlavourData.class);
		ChangeVnfFlavourData r = mapper.map(obj);
		assertNotNull(r);
	}

	@Test
	void testChangeVnfFlavourDataNull() {
		assertNull(mapper.map((ChangeVnfFlavourData) null));
		ChangeVnfFlavourData obj = factory.manufacturePojo(ChangeVnfFlavourData.class);
		obj.setAdditionalParams(null);
		obj.setExtensions(null);
		obj.setVnfConfigurableProperties(null);
		ChangeVnfFlavourData r = mapper.map(obj);
		assertNotNull(r);
	}

	@Test
	void testChangeExtVnfConnectivityData() {
		assertNull(mapper.map((ChangeExtVnfConnectivityData) null));
		ChangeExtVnfConnectivityData obj = factory.manufacturePojo(ChangeExtVnfConnectivityData.class);
		obj.getExtVirtualLinks().forEach(vl -> vl.setId(UUID.randomUUID().toString()));
		ChangeExtVnfConnRequest r = mapper.map(obj);
		assertNotNull(r);
	}
}
