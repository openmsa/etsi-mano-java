package com.ubiqube.etsi.mano.service.cond.ast;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.service.cond.visitor.PrintVisitor;

class ArrayValueExprTest {

	@Test
	void test() {
		ArrayValueExpr<String> expr = new ArrayValueExpr<>(List.of());
		PrintVisitor visitor = new PrintVisitor();
		String res = expr.accept(visitor, 0);
		assertNotNull(res);
	}

}
