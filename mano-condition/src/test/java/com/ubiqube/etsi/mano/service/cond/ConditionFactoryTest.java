package com.ubiqube.etsi.mano.service.cond;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ubiqube.etsi.mano.service.cond.ast.ConditionExpr;

class ConditionFactoryTest {

	@Test
	void test() {
		assertThrows(AstException.class, () -> {
			ConditionFactory.createConditionExpr("bad-key", null);
		});
	}

	@Test
	void test2() {
		JsonNode jsonNode = BooleanNode.TRUE;
		ConditionExpr res = ConditionFactory.createConditionExpr("equal", jsonNode);
		assertNotNull(res);
	}

	@Test
	void testArray() {
		JsonNode jsonNode = new ArrayNode(null);
		ConditionExpr res = ConditionFactory.createConditionExpr("equal", jsonNode);
		assertNotNull(res);
	}

	@Test
	void testArrayWithStringElement() {
		JsonNodeFactory fact = new JsonNodeFactory(false);
		ArrayNode jsonNode = new ArrayNode(fact);
		jsonNode.add("test");
		ConditionExpr res = ConditionFactory.createConditionExpr("equal", jsonNode);
		assertNotNull(res);
	}

	@Test
	void testArrayWithNumericElement() {
		JsonNodeFactory fact = new JsonNodeFactory(false);
		ArrayNode jsonNode = new ArrayNode(fact);
		jsonNode.add(1234);
		ConditionExpr res = ConditionFactory.createConditionExpr("equal", jsonNode);
		assertNotNull(res);
	}

	@Test
	void testArrayWithArrayElement() {
		JsonNodeFactory fact = new JsonNodeFactory(false);
		ArrayNode jsonNode = new ArrayNode(fact);
		jsonNode.add(new ArrayNode(fact));
		assertThrows(AstException.class, () -> ConditionFactory.createConditionExpr("equal", jsonNode));
	}

	@Test
	void testNumeric() {
		JsonNode jsonNode = new ObjectNode(null);
		ConditionExpr res = ConditionFactory.createConditionExpr("equal", jsonNode);
		assertNotNull(res);
	}
}
