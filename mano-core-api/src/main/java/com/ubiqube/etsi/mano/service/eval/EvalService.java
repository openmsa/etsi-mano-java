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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.service.eval;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.mapper.BeanWalker;
import com.ubiqube.etsi.mano.mapper.QueryFilterListener;
import com.ubiqube.etsi.mano.mapper.QueryFilterListener.ListRecord;
import com.ubiqube.etsi.mano.service.cond.BooleanOperatorEnum;
import com.ubiqube.etsi.mano.service.cond.Context;
import com.ubiqube.etsi.mano.service.cond.Node;
import com.ubiqube.etsi.mano.service.cond.Operator;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanExpression;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanListExpr;
import com.ubiqube.etsi.mano.service.cond.ast.BooleanValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.GenericCondition;
import com.ubiqube.etsi.mano.service.cond.ast.LabelExpression;
import com.ubiqube.etsi.mano.service.cond.ast.NoopNode;
import com.ubiqube.etsi.mano.service.cond.ast.NumberValueExpr;
import com.ubiqube.etsi.mano.service.cond.ast.TestValueExpr;
import com.ubiqube.etsi.mano.service.cond.visitor.BooleanListExprRemoverVisitor;
import com.ubiqube.etsi.mano.service.cond.visitor.EvaluatorVisitor;
import com.ubiqube.etsi.mano.service.event.model.SubscriptionType;
import com.ubiqube.etsi.mano.utils.ReflectionUtils;

@Service
public class EvalService {
	private static final Logger LOG = LoggerFactory.getLogger(EvalService.class);

	private final BeanWalker beanWalker;
	private final ObjectMapper mapper;
	private final ContextBuilderService contextBuilderService;

	public EvalService(final ContextBuilderService contextBuilderService) {
		beanWalker = new BeanWalker();
		mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		this.contextBuilderService = contextBuilderService;
	}

	public Node convertRequestToNode(final Object obj) {
		final Method meth = ReflectionUtils.getMethod(obj.getClass(), "getFilter");
		LOG.debug("{}", obj.getClass());
		final Object res = ReflectionUtils.invoke(meth, obj);
		Objects.requireNonNull(res, "Null when calling getFilter() method.");
		return convertToNode(res);
	}

	public String convertRequestToString(final Object obj) {
		final Node res = convertRequestToNode(obj);
		try {
			return mapper.writeValueAsString(res);
		} catch (final JsonProcessingException e) {
			throw new GenericException(e);
		}
	}

	public Node convertStringToNode(final String filter) {
		try {
			return mapper.readValue(filter, Node.class);
		} catch (final JsonProcessingException e) {
			throw new GenericException(e);
		}
	}

	public Node convertToNode(final Object obj) {
		final QueryFilterListener beanListener = new QueryFilterListener();
		beanWalker.walk(obj, beanListener);
		final List<ListRecord> attrs = beanListener.getResults();
		return toNodes(attrs);
	}

	private static Node toNodes(final List<ListRecord> attrs) {
		final BooleanExpression node = toManoCondition(attrs);
		final BooleanListExprRemoverVisitor visitor = new BooleanListExprRemoverVisitor();
		final Node tmp = node.accept(visitor, null);
		if (null == tmp) {
			return new NoopNode();
		}
		return tmp.accept(visitor, null);
	}

	private static BooleanExpression toManoCondition(final List<ListRecord> lrs) {
		final List<BooleanExpression> res = new ArrayList<>();
		lrs.forEach(x -> {
			if (x.getChild().isEmpty()) {
				res.add(createSimpleEqual(x));
			} else {
				res.addAll(handleTree(x));
			}
		});
		return new BooleanListExpr(BooleanOperatorEnum.AND, res);
	}

	private static List<BooleanExpression> handleTree(final ListRecord lr) {
		final ArrayList<BooleanExpression> ret = new ArrayList<>();
		if (!lr.getList().isEmpty()) {
			ret.add(createSimpleEqual(lr));
		}
		ret.add(toManoCondition(lr.getChild()));
		return ret;
	}

	private static BooleanListExpr createSimpleEqual(final ListRecord lr) {
		final List<? extends BooleanExpression> lst = lr.getList().stream()
				.map(x -> new GenericCondition(LabelExpression.of(lr.getName()), Operator.EQUAL, convert(x)))
				.toList();
		return new BooleanListExpr(BooleanOperatorEnum.OR, (List<BooleanExpression>) lst);
	}

	private static Node convert(final Object obj) {
		if (obj instanceof final Enum<?> e) {
			return new TestValueExpr(e.toString());
		}
		if (obj instanceof final String s) {
			return new TestValueExpr(s);
		}
		if (obj instanceof final Double d) {
			return new NumberValueExpr(d);
		}
		if (obj instanceof final Boolean b) {
			return new BooleanValueExpr(b);
		}
		throw new IllegalArgumentException("Could not convert: " + obj.getClass());
	}

	public boolean evaluate(final Node nodes, final UUID objectId, final SubscriptionType subscriptionType, final String eventName) {
		final EvaluatorVisitor eval = new EvaluatorVisitor();
		final Context ctx = contextBuilderService.build(subscriptionType, objectId, eventName);
		return nodes.accept(eval, ctx);
	}

}
