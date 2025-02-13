package com.ubiqube.etsi.mano.nfvo.service.mapping;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.ubiqube.etsi.mano.dao.mano.NsLiveInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.ScaleInfo;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.dao.mano.VnfScaleInfo;
import com.ubiqube.etsi.mano.dao.mano.VnfcResourceInfoEntity;
import com.ubiqube.etsi.mano.dao.mano.alarm.ResourceHandle;
import com.ubiqube.etsi.mano.dao.mano.dto.nsi.NsInstanceDto;
import com.ubiqube.etsi.mano.dao.mano.dto.nsi.VnfInstanceDto;
import com.ubiqube.etsi.mano.dao.mano.dto.nsi.VnfInstanceInstantiatedVnfInfoDto;
import com.ubiqube.etsi.mano.dao.mano.nfvo.NsVnfInstance;
import com.ubiqube.etsi.mano.dao.mano.nsd.VnffgDescriptor;
import com.ubiqube.etsi.mano.dao.mano.nslcm.scale.NsScaleInfo;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

class NsInstanceDtoMappingTest {
	private PodamFactory factory;
	private final NsInstanceDtoMapping mapper = Mappers.getMapper(NsInstanceDtoMapping.class);

	@BeforeEach
	void setUp() {
		factory = new PodamFactoryImpl();
		factory.getStrategy().setDefaultNumberOfCollectionElements(1);
	}

	@Test
	void testNsdPackageEmpty() {
		assertNull(mapper.map((NsdPackage) null));
		NsdPackage obj = new NsdPackage();
		NsInstanceDto r = mapper.map(obj);
		assertNotNull(r);
	}

	@Test
	void testNsdPackageNsInstanceDtoEmpty() {
		NsInstanceDto tgt = new NsInstanceDto();
		NsdInstance obj = new NsdInstance();
		mapper.map(obj, tgt);
		assertNotNull(tgt);
		mapper.map(null, tgt);
	}

	@Test
	void testNsdPackageNsInstanceDto() {
		NsInstanceDto tgt = new NsInstanceDto();
		NsdInstance obj = new NsdInstance();
		obj.setId(UUID.randomUUID());
		obj.setNsScaleStatus(List.of(factory.manufacturePojo(NsScaleInfo.class)));
		obj.setVnfInstance(List.of(new NsVnfInstance()));
		obj.setVnffgInfo(Set.of(factory.manufacturePojo(VnffgDescriptor.class)));
		mapper.map(obj, tgt);
		assertNotNull(tgt);
	}

	@Test
	void testNsdPackageNsInstanceDtoListNull() {
		NsInstanceDto tgt = new NsInstanceDto();
		NsdInstance obj = new NsdInstance();
		tgt.setNsScaleStatus(null);
		tgt.setVnfInstance(null);
		tgt.setVnffgInfo(null);
		mapper.map(obj, tgt);
		assertNotNull(tgt);
	}

	@Test
	void testNsVnfInstance() {
		assertNull(mapper.map((NsVnfInstance) null));
		NsVnfInstance obj = factory.manufacturePojo(NsVnfInstance.class);
		VnfInstanceDto r = mapper.map(obj);
		assertNotNull(r);
	}

	@Test
	void testNsScaleInfo() {
		assertNull(mapper.map((NsScaleInfo) null));
	}

	@Test
	void testVnfInstance() {
		assertNull(mapper.map((VnfInstance) null));
		VnfInstance obj = new VnfInstance();
		VnfInstanceDto r = mapper.map(obj);
		assertNotNull(r);
	}

	@Test
	void testVnfInstanceNotNull() {
		assertNull(mapper.map((VnfInstance) null));
		VnfInstance obj = new VnfInstance();
		obj.setId(UUID.randomUUID());
		obj.setExtensions(Map.of());
		obj.setMetadata(Map.of());
		obj.setVnfConfigurableProperties(Map.of());
		VnfPackage obj2 = new VnfPackage();
		obj2.setId(UUID.randomUUID());
		obj.setVnfPkg(obj2);
		VnfInstanceDto r = mapper.map(obj);
		assertNotNull(r);
	}

	@Test
	void testBlueprintParameters() {
		assertNull(mapper.map((BlueprintParameters) null));
		BlueprintParameters obj = new BlueprintParameters();
		VnfInstanceInstantiatedVnfInfoDto r = mapper.map(obj);
		assertNotNull(r);
	}

	@Test
	void testBlueprintParametersWithVnfcResourceInfo() {
		assertNull(mapper.map((BlueprintParameters) null));
		BlueprintParameters obj = new BlueprintParameters();
		VnfcResourceInfoEntity vrie = factory.manufacturePojo(VnfcResourceInfoEntity.class);
		vrie.getComputeResource().setVimConnectionId(UUID.randomUUID().toString());
		vrie.getComputeResource().setResourceId(UUID.randomUUID().toString());
		obj.setVnfcResourceInfo(Set.of(vrie));
		VnfInstanceInstantiatedVnfInfoDto r = mapper.map(obj);
		assertNotNull(r);
	}

	@Test
	void testScaleInfo() {
		assertNull(mapper.map((ScaleInfo) null));
		ScaleInfo obj = new ScaleInfo();
		VnfScaleInfo r = mapper.map(obj);
		assertNotNull(r);
	}

	@Test
	void testNsLiveInstance() {
		assertNull(mapper.map((NsLiveInstance) null));
		NsLiveInstance obj = new NsLiveInstance();
		ResourceHandle r = mapper.map(obj);
		assertNotNull(r);
	}

	@Test
	void testNsLiveInstanceNotNull() {
		assertNull(mapper.map((NsLiveInstance) null));
		NsLiveInstance obj = new NsLiveInstance();
		obj.setResourceId(UUID.randomUUID().toString());
		obj.setVimConnectionId(UUID.randomUUID().toString());
		ResourceHandle r = mapper.map(obj);
		assertNotNull(r);
	}
}
