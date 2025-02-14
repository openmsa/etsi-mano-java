package com.ubiqube.etsi.mano.nfvo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.dao.mano.alarm.AckState;
import com.ubiqube.etsi.mano.dao.mano.alarm.Alarms;
import com.ubiqube.etsi.mano.exception.ConflictException;
import com.ubiqube.etsi.mano.service.search.SearchParamBuilder;
import com.ubiqube.etsi.mano.service.search.SearchableService;
import com.ubiqube.etsi.mano.vnfm.jpa.AlarmsJpa;

class NfvoAlarmServiceTest {

	@Mock
	private AlarmsJpa alarmJpa;

	@Mock
	private SearchableService searchableService;

	@InjectMocks
	private NfvoAlarmService nfvoAlarmService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {
		UUID id = UUID.randomUUID();
		Alarms alarm = new Alarms();
		when(alarmJpa.findById(id)).thenReturn(Optional.of(alarm));

		Alarms result = nfvoAlarmService.findById(id);

		assertEquals(alarm, result);
		verify(alarmJpa).findById(id);
	}

	@Test
	void testModify() {
		UUID id = UUID.randomUUID();
		Alarms alarm = new Alarms();
		alarm.setVersion(1);
		when(alarmJpa.findById(id)).thenReturn(Optional.of(alarm));
		when(alarmJpa.save(alarm)).thenReturn(alarm);

		Alarms result = nfvoAlarmService.modify(id, AckState.ACKNOWLEDGED, "1");

		assertEquals(AckState.ACKNOWLEDGED, result.getAckState());
		verify(alarmJpa).findById(id);
		verify(alarmJpa).save(alarm);
	}

	@Test
	void testModifyConflictException() {
		UUID id = UUID.randomUUID();
		Alarms alarm = new Alarms();
		alarm.setVersion(1);
		when(alarmJpa.findById(id)).thenReturn(Optional.of(alarm));

		assertThrows(ConflictException.class, () -> {
			nfvoAlarmService.modify(id, AckState.ACKNOWLEDGED, "2");
		});
	}

	@Test
	void testSearch() {
		MultiValueMap<String, String> requestParams = mock(MultiValueMap.class);
		Function<Alarms, Object> mapper = mock(Function.class);
		Consumer<Object> makeLinks = mock(Consumer.class);
		Set<String> mandatoryFields = mock(Set.class);
		Class<Object> frontClass = Object.class;
		SearchParamBuilder params = mock(SearchParamBuilder.class);
		ResponseEntity<String> responseEntity = ResponseEntity.ok("result");

		when(searchableService.search(any())).thenReturn(responseEntity);

		ResponseEntity<String> result = nfvoAlarmService.search(requestParams, mapper, "excludeFields", mandatoryFields, makeLinks, frontClass);

		assertEquals(responseEntity, result);
		verify(searchableService).search(any());
	}
}
