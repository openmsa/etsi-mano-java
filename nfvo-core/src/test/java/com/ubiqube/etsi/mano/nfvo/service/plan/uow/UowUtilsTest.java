package com.ubiqube.etsi.mano.nfvo.service.plan.uow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.VnfInstance;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.nfvo.NsBlueprint;
import com.ubiqube.etsi.mano.nfvo.jpa.NsBlueprintJpa;

class UowUtilsTest {

	@Mock
	private BiFunction<Servers, UUID, VnfBlueprint> vnfFunc;

	@Mock
	private BiFunction<Servers, UUID, List<VnfBlueprint>> vnfListFunc;

	@Mock
	private Servers server;

	@Mock
	private NsBlueprintJpa nsBlueprintJpa;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testWaitLcmCompletion() {
		UUID vnfId = UUID.randomUUID();
		VnfBlueprint vnfBlueprint = mock(VnfBlueprint.class);
		when(vnfBlueprint.getId()).thenReturn(vnfId);
		when(vnfBlueprint.getOperationStatus()).thenReturn(OperationStatusType.PROCESSING, OperationStatusType.COMPLETED);

		when(vnfFunc.apply(server, vnfId)).thenReturn(vnfBlueprint);

		VnfBlueprint result = UowUtils.waitLcmCompletion(vnfBlueprint, vnfFunc, server);

		assertEquals(OperationStatusType.COMPLETED, result.getOperationStatus());
	}

	@Test
	void testWaitNSLcmCompletion() {
		UUID nsId = UUID.randomUUID();
		NsBlueprint nsBlueprint = mock(NsBlueprint.class);
		when(nsBlueprint.getId()).thenReturn(nsId);
		when(nsBlueprint.getOperationStatus()).thenReturn(OperationStatusType.PROCESSING, OperationStatusType.COMPLETED);

		when(nsBlueprintJpa.findById(nsId)).thenReturn(Optional.of(nsBlueprint));

		NsBlueprint result = UowUtils.waitNSLcmCompletion(nsBlueprint, nsBlueprintJpa);

		assertEquals(OperationStatusType.COMPLETED, result.getOperationStatus());
	}

	@Test
	void testIsVnfLcmRunning() {
		UUID vnfInstanceId = UUID.randomUUID();
		VnfBlueprint vnfBlueprint = new VnfBlueprint();
		vnfBlueprint.setOperationStatus(OperationStatusType.STARTING);
		vnfBlueprint.setVnfInstance(new VnfInstance());
		vnfBlueprint.getVnfInstance().setId(vnfInstanceId);

		List<VnfBlueprint> vnfBlueprints = List.of(vnfBlueprint);
		when(vnfListFunc.apply(server, vnfInstanceId)).thenReturn(vnfBlueprints);

		boolean result = UowUtils.isVnfLcmRunning(vnfInstanceId, vnfListFunc, server);

		assertTrue(result);
	}

	@Test
	void testIsNSLcmRunning() {
		UUID nsInstanceId = UUID.randomUUID();
		NsdInstance nsInstance = new NsdInstance();
		nsInstance.setId(nsInstanceId);

		NsBlueprint nsBlueprint = mock(NsBlueprint.class);
		when(nsBlueprint.getOperationStatus()).thenReturn(OperationStatusType.STARTING);

		List<NsBlueprint> nsBlueprints = List.of(nsBlueprint);
		when(nsBlueprintJpa.findByNsInstance(nsInstance)).thenReturn(nsBlueprints);

		boolean result = UowUtils.isNSLcmRunning(nsInstanceId, nsBlueprintJpa);

		assertFalse(result);
	}
}
