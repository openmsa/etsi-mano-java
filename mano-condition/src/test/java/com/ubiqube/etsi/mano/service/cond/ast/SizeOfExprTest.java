package com.ubiqube.etsi.mano.service.cond.ast;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class SizeOfExprTest {

	@Test
	void test() {
		SizeOfExpr expr = SizeOfExpr.of(new NoopNode());
		expr.setLeft(null);
		assertNotNull(expr);
		assertNotNull(expr.toString());
	}

}
