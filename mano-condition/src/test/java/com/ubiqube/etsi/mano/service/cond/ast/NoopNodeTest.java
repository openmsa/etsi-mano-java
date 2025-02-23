package com.ubiqube.etsi.mano.service.cond.ast;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.service.cond.visitor.PrintVisitor;

class NoopNodeTest {

	@Test
	void test() {
		NoopNode node = new NoopNode();
		final PrintVisitor visitor = new PrintVisitor();
		node.accept(visitor, null);
		assertNotNull(node);
	}

}
