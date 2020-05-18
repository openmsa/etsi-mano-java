package com.ubiqube.etsi.mano.repository.msa;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubiqube.etsi.mano.dao.mano.VnfPackage;
import com.ubiqube.etsi.mano.grammar.JsonBeanUtil;
import com.ubiqube.etsi.mano.grammar.JsonFilter;
import com.ubiqube.etsi.mano.model.lcmgrant.sol003.Grant;
import com.ubiqube.etsi.mano.repository.DefaultNamingStrategy;
import com.ubiqube.etsi.mano.repository.Low;
import com.ubiqube.etsi.mano.repository.NamingStrategy;
import com.ubiqube.etsi.mano.service.Configuration;
import com.ubiqube.etsi.mano.service.PropertiesConfiguration;
import com.ubiqube.etsi.mano.service.ejb.EjbProvider;
import com.ubiqube.etsi.mano.service.ejb.RepositoryServiceEjb;

@Tag("Remote")
public class VnfPackageMsaTest {

	private final VnfPackageMsa vnfPackageMsa;

	public VnfPackageMsaTest() {
		final JsonFilter jsonFilter = new JsonFilter(new JsonBeanUtil());
		final ObjectMapper mapper = new ObjectMapper();
		final RepositoryServiceEjb repositoryService = new RepositoryServiceEjb(new EjbProvider(new PropertiesConfiguration()));
		final Low low = new LowMsa(repositoryService);
		final Configuration configuration = new PropertiesConfiguration();
		final NamingStrategy namingStrategy = new DefaultNamingStrategy(configuration);
		vnfPackageMsa = new VnfPackageMsa(mapper, jsonFilter, low, namingStrategy);
	}

	@Test
	void testName() throws Exception {

		VnfPackage entity = new VnfPackage();
		vnfPackageMsa.save(entity);
		assertNotNull(entity.getId());

		entity = vnfPackageMsa.get(entity.getId());
		assertNotNull(entity);

		List<VnfPackage> res = vnfPackageMsa.query(null);
		assertNotNull(res);
		final int num = res.size();
		assertTrue(num >= 1);

		vnfPackageMsa.delete(entity.getId());

		res = vnfPackageMsa.query(null);
		assertNotNull(res);
		assertEquals(num - 1, res.size());
	}

	@Test
	public void testStoreScenario() {
		final VnfPackage entity = new VnfPackage();
		vnfPackageMsa.save(entity);
		assertNotNull(entity.getId());

		vnfPackageMsa.storeObject(entity.getId(), "grant", new Grant());
		vnfPackageMsa.loadObject(entity.getId(), "grant", Grant.class);
		vnfPackageMsa.delete(entity.getId());
	}

	@Test
	public void testBinaryScenario() throws FileNotFoundException, NoSuchAlgorithmException {
		final VnfPackage entity = new VnfPackage();
		vnfPackageMsa.save(entity);
		assertNotNull(entity.getId());

		final InputStream stream = new FileInputStream("src/test/resources/pack.zip");
		vnfPackageMsa.storeBinary(entity.getId(), "file", stream);

		byte[] bytes = vnfPackageMsa.getBinary(entity.getId(), "file");
		final MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(bytes);
		assertEquals("4d251f6f44b12f8e6a0b2e9e7e69e603", DatatypeConverter.printHexBinary(md5.digest()).toLowerCase());

		bytes = vnfPackageMsa.getBinary(entity.getId(), "file", 0, 2L);

		assertEquals(2, bytes.length);
		assertEquals('P', bytes[0]);
		assertEquals('K', bytes[1]);
		vnfPackageMsa.delete(entity.getId());
	}

}
