package com.ubiqube.etsi.mano.nfvo.service.event;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.service.grant.GrantAction;

@ExtendWith(MockitoExtension.class)
class GrantActionDispatcherImplTest {
	@Mock
	private GrantAction grantAction;

	@Test
	void test() {
		GrantActionDispatcherImpl grantActionDispatcherImpl = new GrantActionDispatcherImpl(grantAction);
		assertNotNull(grantActionDispatcherImpl);
		grantActionDispatcherImpl.dispatch(null);
		assertTrue(true);
	}

}
