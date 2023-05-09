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
package com.ubiqube.etsi.mano.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.common.v261.model.vnf.PackageOperationalStateType;
import com.ubiqube.etsi.mano.common.v261.model.vnf.PkgmNotificationsFilter;
import com.ubiqube.etsi.mano.common.v261.model.vnf.PkgmNotificationsFilter.NotificationTypesEnum;
import com.ubiqube.etsi.mano.common.v261.model.vnf.PkgmNotificationsFilterVnfProductsFromProviders;
import com.ubiqube.etsi.mano.common.v261.model.vnf.PkgmSubscription;
import com.ubiqube.etsi.mano.common.v261.model.vnf.PkgmSubscriptionLinks;
import com.ubiqube.etsi.mano.common.v261.model.vnf.PkgmSubscriptionRequest;
import com.ubiqube.etsi.mano.service.event.model.FilterAttributes;
import com.ubiqube.etsi.mano.service.event.model.Subscription;
import com.ubiqube.etsi.mano.service.rest.model.ApiTypesEnum;
import com.ubiqube.etsi.mano.service.rest.model.AuthParamBasic;
import com.ubiqube.etsi.mano.service.rest.model.AuthentificationInformations;
import com.ubiqube.etsi.mano.vnfm.v261.OrikaMapperVnfm261;
import com.ubiqube.etsi.mano.vnfm.v261.model.nslcm.LccnSubscriptionRequest;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.impl.generator.EclipseJdtCompilerStrategy;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class SubscriptionTest {
	private final DefaultMapperFactory mapperFactory;
	private final PodamFactoryImpl podam;

	public SubscriptionTest() {
		final OrikaMapperVnfm261 orikaConfiguration = new OrikaMapperVnfm261();
		mapperFactory = new DefaultMapperFactory.Builder().compilerStrategy(new EclipseJdtCompilerStrategy()).build();
		orikaConfiguration.configure(mapperFactory);
		podam = new PodamFactoryImpl();
		podam.getStrategy().addOrReplaceTypeManufacturer(String.class, new UUIDManufacturer());
	}

	@Test
	void testJsonToJpa() throws Exception {
		final MapperFacade mapper = mapperFactory.getMapperFacade();
		final PkgmSubscription subsJson = new PkgmSubscription();
		subsJson.setCallbackUri("http://callbackUri/");
		final PkgmNotificationsFilter filter = new PkgmNotificationsFilter();
		filter.setNotificationTypes(Arrays.asList(NotificationTypesEnum.VnfPackageChangeNotification));
		final List<PkgmNotificationsFilterVnfProductsFromProviders> vnfProductsFromProviders = new ArrayList<>();
		final PkgmNotificationsFilterVnfProductsFromProviders subProv = new PkgmNotificationsFilterVnfProductsFromProviders();
		subProv.addOperationalStateItem(PackageOperationalStateType.DISABLED);
		vnfProductsFromProviders.add(subProv);
		filter.setVnfProductsFromProviders(vnfProductsFromProviders);
		subsJson.setFilter(filter);
		subsJson.setId(UUID.randomUUID().toString());
		final PkgmSubscriptionLinks links = new PkgmSubscriptionLinks();
		subsJson.setLinks(links);
		final PkgmSubscriptionRequest so = new PkgmSubscriptionRequest();
		so.setFilter(filter);
		final Subscription subsDb = mapper.map(so, Subscription.class);
		final List<FilterAttributes> filters = subsDb.getFilters();
		assertEquals(2, filters.size()); // Should be 2
		checkFilter(filters.get(0), "notificationTypes[0]", "VnfPackageChangeNotification");
		checkFilter(filters.get(1), "vnfProductsFromProviders[0].operationalState[0]", "DISABLED");
	}

	private static void checkFilter(final FilterAttributes filterAttributes, final String attr, final String value) {
		assertEquals(attr, filterAttributes.getAttribute());
		assertEquals(value, filterAttributes.getValue());
	}

	@Test
	void testDbToJson() throws Exception {
		final MapperFacade mapper = mapperFactory.getMapperFacade();
		final Subscription subsDb = new Subscription();
		subsDb.setApi(ApiTypesEnum.SOL005);
		final AuthentificationInformations authentificationInformations = new AuthentificationInformations();
		final AuthParamBasic authParamBasic = new AuthParamBasic();
		authParamBasic.setUserName("user");
		authParamBasic.setPassword("pass");
		authentificationInformations.setAuthParamBasic(authParamBasic);

		subsDb.setAuthentication(authentificationInformations);
		final UUID uuid = UUID.randomUUID();
		subsDb.setId(uuid);
		final List<FilterAttributes> subscriptionFilter = new ArrayList<>();
		final FilterAttributes fa = new FilterAttributes();
		fa.setAttribute("vnfProductsFromProviders.0.operationalState.0");
		fa.setValue("DISABLED");
		subscriptionFilter.add(fa);
		// subscriptionQuery.setSubscriptionFilter(subscriptionFilter);
		subsDb.setFilters(subscriptionFilter);
		final PkgmSubscription subsJson = mapper.map(subsDb, PkgmSubscription.class);
		assertNotNull(subsJson.getId());
	}

	@Test
	void testEnumMapping() throws Exception {
		final MapperFacade mapper = mapperFactory.getMapperFacade();
		final LccnSubscriptionRequest pojo = podam.manufacturePojo(LccnSubscriptionRequest.class);
		final Subscription res = mapper.map(pojo, Subscription.class);
		mapper.map(res, LccnSubscriptionRequest.class);
		assertTrue(true);
	}
}
