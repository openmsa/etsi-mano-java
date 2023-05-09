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
package com.ubiqube.etsi.mano.service;

import java.io.File;
import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.common.ApiVersionType;
import com.ubiqube.etsi.mano.dao.mano.config.RemoteSubscription;
import com.ubiqube.etsi.mano.dao.mano.config.Servers;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanStatusType;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.jpa.config.ServersJpa;
import com.ubiqube.etsi.mano.service.event.ActionType;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.event.model.Subscription;
import com.ubiqube.etsi.mano.service.event.model.SubscriptionType;
import com.ubiqube.etsi.mano.service.rest.FluxRest;
import com.ubiqube.etsi.mano.service.rest.ServerAdapter;
import com.ubiqube.etsi.mano.service.rest.TracingFluxRest;
import com.ubiqube.etsi.mano.utils.Version;

import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
@Transactional
public class ServerService {

	private static final Logger LOG = LoggerFactory.getLogger(ServerService.class);

	private final ServersJpa serversJpa;
	private final EventManager eventManager;
	private final List<HttpGateway> httpGateway;

	private final ConfigurableApplicationContext springContext;

	public ServerService(final ServersJpa serversJpa, final EventManager eventManager, final List<HttpGateway> httpGateway, final ConfigurableApplicationContext springContext) {
		this.serversJpa = serversJpa;
		this.eventManager = eventManager;
		this.httpGateway = httpGateway.stream().sorted(Comparator.comparing(HttpGateway::getVersion)).toList();
		this.springContext = springContext;
	}

	public List<Servers> findAll(final Pageable pageable) {
		return serversJpa.findAll();
	}

	public Servers findById(final UUID id) {
		return serversJpa.findById(id).orElseThrow(() -> new GenericException("Could not find server id " + id));
	}

	/**
	 * Cannot be transactional because of CLOG field on servers.tlsCert.
	 *
	 * @param servers
	 * @return
	 */
	public Servers createServer(final Servers servers) {
		servers.setServerStatus(PlanStatusType.NOT_STARTED);
		serversJpa.findByUrl(servers.getUrl()).ifPresent(x -> {
			throw new GenericException("duplicate Server: " + x.getId() + " url=" + servers.getUrl());
		});
		return serversJpa.save(servers);
	}

	public void deleteById(final UUID id) {
		final Servers server = serversJpa.findById(id).orElseThrow(() -> new GenericException("Could not find server id " + id));
		final FluxRest rest = new FluxRest(server);
		server.getRemoteSubscriptions().forEach(x -> unregister(rest, x));
		serversJpa.deleteById(id);
	}

	private void unregister(final FluxRest rest, final RemoteSubscription x) {
		final Servers srv = findById(x.getRemoteServerId());
		final HttpGateway hg = filterServer(srv);
		final ApiVersionType sType = subscriptionTypeToApiVersion(x.getSubscriptionType());
		final String uri = "/" + new File(hg.getUrlFor(sType), "subscriptions/{id}");
		final URI resp = rest.uriBuilder().path(uri).build(x.getRemoteSubscriptionId());
		try {
			final String version = hg.getHeaderVersion(sType).orElse(null);
			rest.deleteWithReturn(resp, null, version);
		} catch (final RuntimeException e) {
			LOG.debug("", e);
			LOG.warn("Could not remove subscription: {}", x.getRemoteSubscriptionId());
		}
	}

	public ServerAdapter findNearestServer() {
		final List<Servers> lst = serversJpa.findByServerStatusIn(List.of(PlanStatusType.SUCCESS));
		if (lst.isEmpty()) {
			throw new GenericException("Unable to find a valid remote server.");
		}
		if (lst.size() > 1) {
			LOG.warn("More than one server exist, picking the first one.");
		}
		final Servers server = lst.get(0);
		final HttpGateway hg = filterServer(server);
		return new ServerAdapter(hg, server, new TracingFluxRest(server, springContext));
	}

	public void retryById(final UUID id) {
		eventManager.sendAction(ActionType.REGISTER_SERVER, id);
	}

	public ServerAdapter buildServerAdapter(final Subscription subscription) {
		final Servers server = Servers.builder()
				.authentification(subscription.getAuthentication())
				.url(subscription.getCallbackUri())
				.version(subscription.getVersion())
				.build();
		if (null == server.getVersion()) {
			return new ServerAdapter(httpGateway.get(0), server, new TracingFluxRest(server, springContext));
		}
		final HttpGateway hg = filterServer(server);
		return new ServerAdapter(hg, server, new TracingFluxRest(server, springContext));
	}

	public ServerAdapter buildServerAdapter(final Servers servers) {
		final HttpGateway hg = filterServer(servers);
		return new ServerAdapter(hg, servers, new TracingFluxRest(servers, springContext));
	}

	private HttpGateway filterServer(final Servers servers) {
		return httpGateway.stream()
				.filter(x -> x.getVersion().toString().equals(servers.getVersion()))
				.findAny()
				.orElseThrow(() -> new GenericException("Unable to find version " + servers.getVersion()));
	}

	@Nullable
	public String convertFeVersionToMano(final ApiVersionType verType, @Nullable final String version) {
		if (version == null) {
			return "2.6.1";
		}
		final Optional<Version> res = httpGateway.stream().filter(x -> x.isMatching(verType, version)).map(HttpGateway::getVersion).findFirst();
		return res.map(Version::toString).orElse(null);
	}

	public Optional<Version> convertManoVersionToFe(final SubscriptionType subscriptionType, @Nullable final String version) {
		final ApiVersionType av = subscriptionTypeToApiVersion(subscriptionType);
		return httpGateway.stream().filter(x -> x.isMatching(av, version)).findFirst().map(HttpGateway::getVersion);
	}

	private static ApiVersionType subscriptionTypeToApiVersion(final SubscriptionType subscriptionType) {
		return switch (subscriptionType) {
		case VNF -> ApiVersionType.SOL003_VNFPKGM;
		case VNFLCM -> ApiVersionType.SOL003_VNFLCM;
		case VNFIND -> ApiVersionType.SOL003_VNFIND;
		case VRQAN -> ApiVersionType.SOL003_VRQAN;
		case VNFPM -> ApiVersionType.SOL003_VNFPM;
		default -> throw new GenericException("Unable to find " + subscriptionType);
		};
	}
}
