/**
 *     Copyright (C) 2019-2023 Ubiqube.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.service.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.v2.Blueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.BlueprintParameters;
import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.exception.VnfmException;
import com.ubiqube.etsi.mano.jpa.JujuCloudJpa;
import com.ubiqube.etsi.mano.orchestrator.OrchExecutionResults;
import com.ubiqube.etsi.mano.orchestrator.ResultType;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTaskV3;
import com.ubiqube.etsi.mano.service.NsScaleStrategyV3;
import com.ubiqube.etsi.mano.service.TestBluePrint;
import com.ubiqube.etsi.mano.service.TestInstance;
import com.ubiqube.etsi.mano.service.TestTask;
import com.ubiqube.etsi.mano.service.VimResourceService;
import com.ubiqube.etsi.mano.service.juju.cli.JujuRemoteService;
import com.ubiqube.etsi.mano.service.juju.entities.JujuCloud;
import com.ubiqube.etsi.mano.service.juju.entities.JujuCredential;
import com.ubiqube.etsi.mano.service.juju.entities.JujuMetadata;
import com.ubiqube.etsi.mano.service.juju.entities.JujuModel;

@ExtendWith(MockitoExtension.class)
class AbstractGenericActionV3Test {
	@Mock
	private WorkflowV3 workflow;
	@Mock
	private VimResourceService vimResourceService;
	@Mock
	private OrchestrationAdapter<?, ?> orchestrationAdapter;
	@Mock
	private NsScaleStrategyV3 nsScaleStrategy;
	@Mock
	private JujuCloudJpa jujuCloudJpa;
	@Mock
	private JujuRemoteService remoteService;
	
	@Test
	void test_jujuInstantiate() throws Exception {
		final TestGenericAction srv = new TestGenericAction(workflow, vimResourceService, orchestrationAdapter, nsScaleStrategy, remoteService, jujuCloudJpa);		final UUID id = UUID.randomUUID();
		JujuMetadata jujuMetadata = new JujuMetadata();
		JujuCredential jujuCredential = new JujuCredential();
		JujuModel model = new JujuModel("test", "test", "test");
		List<JujuModel> models = new ArrayList<>();
		models.add(model);
		jujuCredential.setName("test");
		jujuMetadata.setName("test");
		jujuMetadata.setModels(models);
		JujuCloud jCloud = new JujuCloud();
		jCloud.setName("test");
		jCloud.setCredential(jujuCredential);
		jCloud.setMetadata(jujuMetadata);
		Mockito.when(jujuCloudJpa.findById(id)).thenReturn(Optional.of(jCloud));
		ResponseEntity<String> responseobject = new ResponseEntity<String>("test", HttpStatus.OK);
		Mockito.when(remoteService.controllerDetail(Mockito.anyString())).thenReturn(responseobject);
		srv.jujuInstantiate(id);
		assertTrue(true);
	}

	@Test
	void test_jujuInstantiate_throws_exception() throws Exception {
		final TestGenericAction srv = new TestGenericAction(workflow, vimResourceService, orchestrationAdapter, nsScaleStrategy, remoteService, jujuCloudJpa);		final UUID id = UUID.randomUUID();
		Exception exception = assertThrows(VnfmException.class, () -> {
			srv.jujuInstantiate(id);
		});
		String expectedMsg = "Could not find Juju Cloud";
		String actualMsg = exception.getMessage();
		assertTrue(actualMsg.contains(expectedMsg));
	}

	@Test
	void test_jujuInstantiate_throws_exception_when_cloudname_null() throws Exception {
		final TestGenericAction srv = new TestGenericAction(workflow, vimResourceService, orchestrationAdapter, nsScaleStrategy, remoteService, jujuCloudJpa);		final UUID id = UUID.randomUUID();
		JujuCloud jCloud = new JujuCloud();
		Mockito.when(jujuCloudJpa.findById(id)).thenReturn(Optional.of(jCloud));
		Exception exception = assertThrows(VnfmException.class, () -> {
			srv.jujuInstantiate(id);
		});
		String expectedMsg = "Error";
		String actualMsg = exception.getMessage();
		assertTrue(actualMsg.contains(expectedMsg));
	}

	@Test
	void test_jujuInstantiate_throws_exception_when_no_controller_found() throws Exception {
		final TestGenericAction srv = new TestGenericAction(workflow, vimResourceService, orchestrationAdapter, nsScaleStrategy, remoteService, jujuCloudJpa);		final UUID id = UUID.randomUUID();
		JujuMetadata jujuMetadata = new JujuMetadata();
		JujuCredential jujuCredential = new JujuCredential();
		jujuCredential.setName("test");
		jujuMetadata.setName("test");
		JujuCloud jCloud = new JujuCloud();
		jCloud.setName("test");
		jCloud.setCredential(jujuCredential);
		jCloud.setMetadata(jujuMetadata);
		Mockito.when(jujuCloudJpa.findById(id)).thenReturn(Optional.of(jCloud));
		ResponseEntity<String> responseobject = new ResponseEntity<String>("ERROR", HttpStatus.NOT_FOUND);
		Mockito.when(remoteService.controllerDetail(Mockito.anyString())).thenReturn(responseobject);
		Exception exception = assertThrows(VnfmException.class, () -> {
			srv.jujuInstantiate(id);
		});
		String expectedMsg = "Error";
		String actualMsg = exception.getMessage();
		assertTrue(actualMsg.contains(expectedMsg));
	}

	@Test
	void test_jujuterminate() throws Exception {
		final TestGenericAction srv = new TestGenericAction(workflow, vimResourceService, orchestrationAdapter, nsScaleStrategy, remoteService, jujuCloudJpa);		final UUID id = UUID.randomUUID();
		JujuMetadata jujuMetadata = new JujuMetadata();
		jujuMetadata.setName("test");
		JujuCloud jCloud = new JujuCloud();
		jCloud.setMetadata(jujuMetadata);
		Mockito.when(jujuCloudJpa.findById(id)).thenReturn(Optional.of(jCloud));
		srv.jujuTerminate(id);
		assertTrue(true);
	}

	@Test
	void test_jujuterminate_throws_exception() throws Exception {
		final TestGenericAction srv = new TestGenericAction(workflow, vimResourceService, orchestrationAdapter, nsScaleStrategy, remoteService, jujuCloudJpa);		final UUID id = UUID.randomUUID();
		Exception exception = assertThrows(VnfmException.class, () -> {
			srv.jujuTerminate(id);
		});
		String expectedMsg = "Could not find Juju Cloud";
		String actualMsg = exception.getMessage();
		assertTrue(actualMsg.contains(expectedMsg));
	}


	@Test
	void testInstantiateFail() throws Exception {
		final TestGenericAction srv = new TestGenericAction(workflow, vimResourceService, orchestrationAdapter, nsScaleStrategy, remoteService, jujuCloudJpa);
		final UUID id = UUID.randomUUID();
		final Blueprint bp = new TestBluePrint();
		final TestBluePrint tp2 = (TestBluePrint) bp;
		final TestInstance inst = new TestInstance();
		inst.setId(id);
		tp2.setId(id);
		tp2.setInstance(inst);
		when(orchestrationAdapter.getBluePrint(id)).thenReturn(bp);
		when(orchestrationAdapter.getInstance(id)).thenReturn(inst);
		srv.instantiate(id);
		assertEquals(OperationStatusType.FAILED, tp2.getOperationStatus());
	}

	static <T, U, R> Arguments akkkrgs(final BiFunction<T, U, R> method, final U expected) {
		return Arguments.of(method, expected);
	}

	private static Stream<Arguments> providerClass() {
		return Stream.of(
				Arguments.of(args.of((srv, i) -> srv.instantiate(i))),
				Arguments.of(args.of((srv, i) -> srv.terminate(i))),
				Arguments.of(args.of((srv, i) -> srv.scale(i))),
				Arguments.of(args.of((srv, i) -> srv.scaleToLevel(i))));
	}

	@ParameterizedTest
	@MethodSource("providerClass")
	void testActionOk(final args arg) throws Exception {
		final TestGenericAction srv = new TestGenericAction(workflow, vimResourceService, orchestrationAdapter, nsScaleStrategy, remoteService, jujuCloudJpa);
		final UUID id = UUID.randomUUID();
		final Blueprint bp = new TestBluePrint();
		final TestBluePrint tp2 = (TestBluePrint) bp;
		final TestInstance inst = new TestInstance();
		final BlueprintParameters instVnfInfo = new BlueprintParameters();
		instVnfInfo.setScaleStatus(new LinkedHashSet<>());
		inst.setInstantiatedVnfInfo(instVnfInfo);
		inst.setId(id);
		tp2.setId(id);
		tp2.setInstance(inst);
		tp2.getParameters().setScaleStatus(new HashSet<>());
		tp2.setVimConnections(new LinkedHashSet<>());
		final OrchExecutionResults results = new TestOrchExecutionResults<>();
		when(orchestrationAdapter.getBluePrint(id)).thenReturn(bp);
		when(orchestrationAdapter.getInstance(id)).thenReturn(inst);
		when(orchestrationAdapter.save(bp)).thenReturn(bp);
		when(orchestrationAdapter.updateState(bp, OperationStatusType.PROCESSING)).thenReturn(bp);
		when(workflow.execute(null, bp)).thenReturn(results);
		//
		arg.func().accept(srv, id);
		assertEquals(OperationStatusType.COMPLETED, tp2.getOperationStatus());
	}

	@Test
	void testLiveStatusOk() throws Exception {
		final TestGenericAction srv = new TestGenericAction(workflow, vimResourceService, orchestrationAdapter, nsScaleStrategy, remoteService, jujuCloudJpa);
		final UUID id = UUID.randomUUID();
		final Blueprint bp = new TestBluePrint();
		final TestBluePrint tp2 = (TestBluePrint) bp;
		final TestInstance inst = new TestInstance();
		final BlueprintParameters instVnfInfo = new BlueprintParameters();
		instVnfInfo.setScaleStatus(new LinkedHashSet<>());
		inst.setInstantiatedVnfInfo(instVnfInfo);
		inst.setId(id);
		tp2.setId(id);
		tp2.setInstance(inst);
		tp2.getParameters().setScaleStatus(new HashSet<>());
		tp2.setVimConnections(new LinkedHashSet<>());
		final TestOrchExecutionResults results = new TestOrchExecutionResults<>();
		final TestUnitOfWorkV3 uow = new TestUnitOfWorkV3();
		final VirtualTaskV3<TestTask> vt = new TestVirtualTaskV3();
		final TestTask tt = new TestTask(ResourceTypeEnum.COMPUTE);
		tt.setChangeType(ChangeType.ADDED);
		vt.setTemplateParameters(tt);
		uow.setTask(vt);
		results.addSuccess(new TestOrchExecutionResult(uow, ResultType.SUCCESS, ""));
		results.addAll(results);
		when(orchestrationAdapter.getBluePrint(id)).thenReturn(bp);
		when(orchestrationAdapter.getInstance(id)).thenReturn(inst);
		when(orchestrationAdapter.save(bp)).thenReturn(bp);
		when(orchestrationAdapter.updateState(bp, OperationStatusType.PROCESSING)).thenReturn(bp);
		when(workflow.execute(null, bp)).thenReturn(results);
		//
		srv.instantiate(id);
		assertEquals(OperationStatusType.COMPLETED, tp2.getOperationStatus());
	}

}

record args(BiConsumer<TestGenericAction, UUID> func) {
	public static args of(final BiConsumer<TestGenericAction, UUID> f) {
		return new args(f);
	}
}
