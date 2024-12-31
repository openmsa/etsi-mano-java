package com.ubiqube.etsi.mano.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.controller.subscription.ApiAndType;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.service.event.model.FilterAttributes;
import com.ubiqube.etsi.mano.service.event.model.NotificationEvent;
import com.ubiqube.etsi.mano.service.event.model.Subscription;

import jakarta.annotation.Nullable;

public class SubscriptionUtils {
	private static final Logger LOG = LoggerFactory.getLogger(SubscriptionUtils.class);

	private SubscriptionUtils() {
		//
	}

	public static Optional<Subscription> getMatchingSubscription(final Subscription subscription, final List<Subscription> existingSubscriptions) {
		final List<FilterAttributes> filters = Optional.ofNullable(subscription.getFilters()).orElseGet(List::of);
		if (filters.isEmpty()) {
			return existingSubscriptions.stream().filter(sub -> sub.getFilters().isEmpty()).findFirst();
		}
		return existingSubscriptions.stream()
				.filter(sub -> areFiltersMatching(sub, filters))
				.findFirst();
	}

	public static boolean areFiltersMatching(final Subscription subscription, final List<FilterAttributes> filters) {
		final List<FilterAttributes> subscriptionFilters = subscription.getFilters();
		final List<FilterAttributes> intersection = new ArrayList<>(subscriptionFilters);
		intersection.retainAll(filters);
		return filters.size() == intersection.size();
	}

	public static URI createSubscriptionLink(final Subscription subscription, final ServerService serverService) {
		final Optional<HttpGateway> optionalGateway = serverService.getHttpGatewayFromManoVersion(subscription.getVersion());
		if (optionalGateway.isEmpty()) {
			throw new GenericException("Could not find gateway for " + subscription.getSubscriptionType() + "/" + subscription.getVersion());
		}
		final HttpGateway gateway = optionalGateway.get();
		final ApiAndType apiAndType = ApiAndType.of(subscription.getApi(), subscription.getSubscriptionType());
		final String subscriptionUri = gateway.getSubscriptionUriFor(apiAndType, subscription.getId().toString());
		return URI.create(subscriptionUri);
	}

	@Nullable
	public static String convert(final NotificationEvent notificationEvent) {
		return switch (notificationEvent) {
		case VNF_PKG_ONBOARDING -> "VnfPackageOnboardingNotification";
		case VNF_PKG_ONCHANGE, VNF_PKG_ONDELETION -> "VnfPackageChangeNotification";
		case NS_PKG_ONBOARDING -> "NsdOnBoardingNotification";
		case NS_PKG_ONBOARDING_FAILURE -> "NsdOnboardingFailureNotification";
		case NS_PKG_ONCHANGE -> "NsdChangeNotification";
		case NS_PKG_ONDELETION -> "NsdDeletionNotification";
		case NS_INSTANCE_CREATE -> "NsIdentifierCreationNotification";
		case NS_INSTANCE_DELETE -> "NsIdentifierDeletionNotification";
		case NS_INSTANTIATE, NS_TERMINATE -> "NsLcmOperationOccurrenceNotification";
		case VNF_INSTANCE_DELETE -> "VnfIdentifierDeletionNotification";
		case VNF_INSTANCE_CREATE -> "VnfIdentifierCreationNotification";
		case VNF_INSTANCE_CHANGED, VNF_TERMINATE -> "VnfLcmOperationOccurrenceNotification";
		case VNF_INDICATOR_VALUE_CHANGED -> "VnfIndicatorValueChangeNotification";
		case VRQAN -> "VrQuotaAvailNotification";
		default -> {
			LOG.warn("Unexpected value: {}", notificationEvent);
			yield null;
		}
		};
	}
}
