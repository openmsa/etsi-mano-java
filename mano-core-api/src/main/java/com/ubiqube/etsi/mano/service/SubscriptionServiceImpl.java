/**
 * Copyright (C) 2019-2024 Ubiqube.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.controller.subscription.ApiAndType;
import com.ubiqube.etsi.mano.dao.mano.version.ApiVersionType;
import com.ubiqube.etsi.mano.dao.subscription.SubscriptionType;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.exception.SeeOtherException;
import com.ubiqube.etsi.mano.grammar.BooleanExpression;
import com.ubiqube.etsi.mano.grammar.GrammarLabel;
import com.ubiqube.etsi.mano.grammar.GrammarNode;
import com.ubiqube.etsi.mano.grammar.GrammarNodeResult;
import com.ubiqube.etsi.mano.grammar.GrammarOperandType;
import com.ubiqube.etsi.mano.grammar.GrammarParser;
import com.ubiqube.etsi.mano.grammar.GrammarValue;
import com.ubiqube.etsi.mano.service.auth.model.ApiTypesEnum;
import com.ubiqube.etsi.mano.service.eval.EvalService;
import com.ubiqube.etsi.mano.service.event.Notifications;
import com.ubiqube.etsi.mano.service.event.model.EventMessage;
import com.ubiqube.etsi.mano.service.event.model.Subscription;
import com.ubiqube.etsi.mano.service.repository.SubscriptionRepositoryService;
import com.ubiqube.etsi.mano.service.rest.ServerAdapter;
import com.ubiqube.etsi.mano.service.search.ManoSearch;

import jakarta.annotation.Nullable;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
	private static final Logger LOG = LoggerFactory.getLogger(SubscriptionServiceImpl.class);

	private final SubscriptionRepositoryService subscriptionJpa;
	private final GrammarParser grammarParser;
	private final Notifications notifications;
	private final ServerService serverService;
	private final EvalService evalService;
	private final ManoSearch manoSearch;

	public SubscriptionServiceImpl(final SubscriptionRepositoryService repository, final GrammarParser grammarParser, final Notifications notifications,
			final @Lazy ServerService serverService, final EvalService evalService, final ManoSearch manoSearch) {
		this.subscriptionJpa = repository;
		this.grammarParser = grammarParser;
		this.notifications = notifications;
		this.serverService = serverService;
		this.evalService = evalService;
		this.manoSearch = manoSearch;
	}

	@Override
	public List<Subscription> query(final String filter, final ApiVersionType type) {
		final GrammarNodeResult nodes = grammarParser.parse(filter);
		final GrammarNode subscriptionTypeNode = new BooleanExpression(new GrammarLabel("subscriptionType"), GrammarOperandType.EQ, new GrammarValue(type.toString().toUpperCase()));
		final ArrayList<GrammarNode> criteriaNodes = new ArrayList<>(nodes.getNodes());
		criteriaNodes.add(subscriptionTypeNode);
		return manoSearch.getCriteria(criteriaNodes, Subscription.class);
	}

	@Override
	public Subscription save(final Subscription subscription) {
		ensureUniqueness(subscription);
		checkAvailability(subscription);
		return subscriptionJpa.save(subscription);
	}

	@Override
	public Subscription save(final Subscription subscriptionRequest, final Class<?> version, final ApiVersionType type) {
		final ApiAndType apiAndType = ServerService.apiVersionTosubscriptionType(type);
		subscriptionRequest.setSubscriptionType(apiAndType.type());
		subscriptionRequest.setApi(apiAndType.api());
		AuthChecker.checkAuthData(subscriptionRequest);
		ensureUniqueness(subscriptionRequest);
		subscriptionRequest.setNodeFilter(evalService.convertRequestToString(subscriptionRequest));
		subscriptionRequest.setVersion(VersionExtractor.extractVersion(version, apiAndType.type(), serverService));
		subscriptionRequest.setHeaderVersion(VersionExtractor.extractVersion(version));
		checkAvailability(subscriptionRequest);
		return subscriptionJpa.save(subscriptionRequest);
	}

	private void checkAvailability(final Subscription subscription) {
		final ServerAdapter server = serverService.buildServerAdapter(subscription);
		notifications.check(server, subscription.getCallbackUri(), subscription.getHeaderVersion());
	}

	private void ensureUniqueness(final Subscription subscription) {
		final List<Subscription> existingSubscriptions = findByApiAndCallbackUriSubscriptionType(subscription.getApi(), subscription.getCallbackUri(), subscription.getSubscriptionType());
		final Optional<Subscription> matchingSubscription = SubscriptionUtils.getMatchingSubscription(subscription, existingSubscriptions);
		if (matchingSubscription.isPresent()) {
			final URI uri = SubscriptionUtils.createSubscriptionLink(matchingSubscription.get(), serverService);
			throw new SeeOtherException(uri, "Subscription already exists.");
		}
	}

	@Override
	public void delete(final UUID subscriptionId, final ApiVersionType type) {
		LOG.info("Delete subscription {} / {}", type, subscriptionId);
		findById(subscriptionId, type);
		subscriptionJpa.deleteById(subscriptionId);
	}

	@Override
	public Subscription findById(final UUID subscriptionId, final ApiVersionType type) {
		return subscriptionJpa.findById(subscriptionId).orElseThrow(() -> new NotFoundException("Could not find subscription id: " + subscriptionId));
	}

	@Override
	public List<Subscription> selectNotifications(final EventMessage eventMessage) {
		final List<Subscription> subscriptions = subscriptionJpa.findEventAndVnfPkg(eventMessage.getNotificationEvent(), eventMessage.getObjectId().toString());
		return subscriptions.stream()
				.filter(subscription -> subscription.getFilters().stream().anyMatch(filter -> filter.getAttribute().startsWith("notificationTypes[") && filter.getValue().equals(SubscriptionUtils.convert(eventMessage.getNotificationEvent()))))
				.toList();
	}

	@Override
	public List<Subscription> findByApiAndCallbackUriSubscriptionType(final @Nullable ApiTypesEnum api, final URI callbackUri, final SubscriptionType subscriptionType) {
		return subscriptionJpa.findByApiAndCallbackUriAndSubscriptionType(api, callbackUri, subscriptionType);
	}
}
