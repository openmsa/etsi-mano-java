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
package com.ubiqube.etsi.mano.vnfm.v351.controller.vrqan;

import static com.ubiqube.etsi.mano.uri.ManoWebMvcLinkBuilder.linkTo;
import static com.ubiqube.etsi.mano.uri.ManoWebMvcLinkBuilder.methodOn;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.controller.SubscriptionFrontController;
import com.ubiqube.etsi.mano.em.v351.model.lcmcoord.Link;
import com.ubiqube.etsi.mano.service.event.model.SubscriptionType;
import com.ubiqube.etsi.mano.vnfm.v351.model.vrqan.VrQuotaAvailSubscription;
import com.ubiqube.etsi.mano.vnfm.v351.model.vrqan.VrQuotaAvailSubscriptionLinks;
import com.ubiqube.etsi.mano.vnfm.v351.model.vrqan.VrQuotaAvailSubscriptionRequest;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@RestController
public class VrQanSubscriptions351Sol003Controller implements VrQanSubscriptions351Sol003Api {
	private final SubscriptionFrontController subscriptionService;

	public VrQanSubscriptions351Sol003Controller(final SubscriptionFrontController subscriptionService) {
		this.subscriptionService = subscriptionService;
	}

	@Override
	public ResponseEntity<List<VrQuotaAvailSubscription>> subscriptionsGet(final MultiValueMap<String, String> requestParams, final String nextpageOpaqueMarker) {
		return subscriptionService.search(requestParams, VrQuotaAvailSubscription.class, VrQanSubscriptions351Sol003Controller::makeLinks, SubscriptionType.VRQAN);
	}

	@Override
	public ResponseEntity<VrQuotaAvailSubscription> subscriptionsPost(@Valid final VrQuotaAvailSubscriptionRequest vrQuotaAvailSubscriptionRequest) {
		return subscriptionService.create(vrQuotaAvailSubscriptionRequest, VrQuotaAvailSubscription.class, VrQanSubscriptions351Sol003Api.class, VrQanSubscriptions351Sol003Controller::makeLinks, VrQanSubscriptions351Sol003Controller::makeSelf, SubscriptionType.VRQAN);
	}

	@Override
	public ResponseEntity<Void> subscriptionsSubscriptionIdDelete(final String subscriptionId) {
		return subscriptionService.deleteById(subscriptionId, SubscriptionType.VRQAN);
	}

	@Override
	public ResponseEntity<VrQuotaAvailSubscription> subscriptionsSubscriptionIdGet(final String subscriptionId) {
		return subscriptionService.findById(subscriptionId, VrQuotaAvailSubscription.class, VrQanSubscriptions351Sol003Controller::makeLinks, SubscriptionType.VRQAN);
	}

	private static String makeSelf(final VrQuotaAvailSubscription subscription) {
		return linkTo(methodOn(VrQanSubscriptions351Sol003Api.class).subscriptionsSubscriptionIdGet(subscription.getId())).withSelfRel().getHref();
	}

	private static void makeLinks(final VrQuotaAvailSubscription subscription) {
		final VrQuotaAvailSubscriptionLinks links = new VrQuotaAvailSubscriptionLinks();
		final Link link = new Link();
		link.setHref(linkTo(methodOn(VrQanSubscriptions351Sol003Api.class).subscriptionsSubscriptionIdGet(subscription.getId())).withSelfRel().getHref());
		links.setSelf(link);
		subscription.setLinks(links);
	}
}
