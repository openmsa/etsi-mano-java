/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
package com.ubiqube.etsi.mano.vnfm.v351.controller.vnfind;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.controller.SubscriptionFrontController;
import com.ubiqube.etsi.mano.em.v351.model.lcmcoord.Link;
import com.ubiqube.etsi.mano.em.v351.model.vnfind.VnfIndicatorSubscription;
import com.ubiqube.etsi.mano.em.v351.model.vnfind.VnfIndicatorSubscriptionLinks;
import com.ubiqube.etsi.mano.em.v351.model.vnfind.VnfIndicatorSubscriptionRequest;
import com.ubiqube.etsi.mano.service.event.model.SubscriptionType;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@RestController
@ConditionalOnMissingClass("com.ubiqube.etsi.mano.vnfm.v331.controller.vnfind.VnfIndSubscriptions331Sol003Api")
public class VnfIndSubscriptions351Sol003Controller implements VnfIndSubscriptions351Sol003Api {
	private final SubscriptionFrontController subscriptionService;

	public VnfIndSubscriptions351Sol003Controller(final SubscriptionFrontController subscriptionService) {
		this.subscriptionService = subscriptionService;
	}

	@Override
	public ResponseEntity<List<VnfIndicatorSubscription>> subscriptionsGet(final MultiValueMap<String, String> requestParams, @Valid final String nextpageOpaqueMarker) {
		return subscriptionService.search(requestParams, VnfIndicatorSubscription.class, VnfIndSubscriptions351Sol003Controller::makeLinks, SubscriptionType.VNFIND);
	}

	@Override
	public ResponseEntity<VnfIndicatorSubscription> subscriptionsPost(@Valid final VnfIndicatorSubscriptionRequest vnfIndicatorSubscriptionRequest) {
		return subscriptionService.create(vnfIndicatorSubscriptionRequest, VnfIndicatorSubscription.class, VnfIndSubscriptions351Sol003Api.class, VnfIndSubscriptions351Sol003Controller::makeLinks, VnfIndSubscriptions351Sol003Controller::makeSelf, SubscriptionType.VNFPM);
	}

	private static String makeSelf(final VnfIndicatorSubscription subscription) {
		// linkTo(methodOn(VnfIndSubscriptions351Sol003Api.class).subscriptionsSubscriptionIdGet(subscription.getId())).withSelfRel().getHref();
		return "";
	}

	private static void makeLinks(final VnfIndicatorSubscription subscription) {
		final VnfIndicatorSubscriptionLinks links = new VnfIndicatorSubscriptionLinks();
		final Link link = new Link();
		// link.setHref(linkTo(methodOn(VnfIndSubscriptions351Sol003Api.class).subscriptionsSubscriptionIdGet(subscription.getId())).withSelfRel().getHref());
		links.setSelf(link);
		subscription.setLinks(links);
	}

}
