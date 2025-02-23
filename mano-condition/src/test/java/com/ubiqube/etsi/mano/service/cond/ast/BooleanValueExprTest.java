package com.ubiqube.etsi.mano.service.cond.ast;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.service.cond.visitor.PrintVisitor;

class BooleanValueExprTest {

	@Test
	void test() {
		BooleanValueExpr expr = new BooleanValueExpr(true);
		PrintVisitor visitor = new PrintVisitor();
		String res = expr.accept(visitor, 0);
		assertNotNull(res);
	}

}
