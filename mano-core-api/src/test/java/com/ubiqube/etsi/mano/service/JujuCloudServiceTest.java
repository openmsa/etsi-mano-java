package com.ubiqube.etsi.mano.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.jpa.JujuCloudJpa;
import com.ubiqube.etsi.mano.jpa.JujuCredentialJpa;
import com.ubiqube.etsi.mano.jpa.JujuMetadataJpa;
import com.ubiqube.etsi.mano.service.juju.entities.JujuCloud;

@ExtendWith(MockitoExtension.class)
public class JujuCloudServiceTest {

	@Mock
	private JujuCloudJpa jujuCloudJpa;

	@Mock
	private JujuCredentialJpa jujuCredentialJpa;

	@Mock
	private JujuMetadataJpa jujuMetadataJpa;

	private JujuCloudService jujuCloudService;

	@BeforeEach
	void init() {
		jujuCloudService = new JujuCloudService(jujuCloudJpa, jujuCredentialJpa, jujuMetadataJpa);
	}

	@Test
	public void test_SaveCloud() throws Exception {
		JujuCloud jCloud = new JujuCloud();
		jujuCloudService.saveCloud(jCloud);
		assertTrue(true);
	}

	@Test
	public void test_FindByControllerName() throws Exception {
		String controllerName = "testcontroller1";
		JujuCloud jujuCloud1 = new JujuCloud();
		List<JujuCloud> expectedResult = Arrays.asList(jujuCloud1);
		when(jujuCloudJpa.findByControllerName(controllerName)).thenReturn(expectedResult);
		assertEquals(1, jujuCloudService.findByControllerName(controllerName).size());
	}
}
