/**
 * Copyright (C) 2019-2025 Ubiqube.
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
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.service.boot;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.dao.mano.config.Configurations;
import com.ubiqube.etsi.mano.service.repository.ConfigurationsRepositoryService;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@ExtendWith(MockitoExtension.class)
@SuppressWarnings("static-method")
class K8sPkServiceTest {

	private static final Logger LOG = LoggerFactory.getLogger(K8sPkServiceTest.class);

	@Mock
	private ConfigurationsRepositoryService configurations;

	@Test
	void testNameMinimal() {
		final K8sPkService serv = new K8sPkService(configurations);
		final String str = serv.createCsr("CN=test,O=system:masters");
		assertNotNull(str);
		LOG.debug(str);
	}

	@Test
	void testNameHavePk() {
		Configurations conf = new Configurations();
		conf.setId("k8s.private-key");
		conf.setWalue("""
				-----BEGIN PRIVATE KEY-----
				MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAtDQ/EIYl+EaarADK
				aS893BmfgqTFc5PpXEFkf4fNmSiDfVJo2NxxxXNT3GdU/hR++szIMvlTQu3BN7p2
				7LS88wIDAQABAkAn6UZqb3urYF/TZp1t7HOj8tzcP4gn0AOoVidfA4HqwKnE++PY
				ehIO7C8gpo9IOgsZ00VhYfYeU4pwDU2kalO5AiEA7CQWoVOqC8e8zTzgXfkF+2nZ
				5Y50qT9MlhqyrRgQ9c0CIQDDW+KTJljR4FNaQGYt1s6TBLqIHlAUYJ4Rh4bZp+m9
				vwIhAJqWOXZdRem2VUPqgVsjhaYDspCTxI1zkdiGG8gzBA9VAiB+Brqlvukxi4t5
				/21rSNW+liBXS2bRnnqaSFm/88igNQIhAMlTBTsmFsoGBTL0eZPYTNqNb4t99o/1
				akC5+Ey4Z+xk
				-----END PRIVATE KEY-----
				""");
		when(configurations.findById("k8s.private-key")).thenReturn(Optional.of(conf));
		Configurations pub = new Configurations();
		pub.setId("k8s.public-key");
		pub.setWalue("""
				-----BEGIN PUBLIC KEY-----
				MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALQ0PxCGJfhGmqwAymkvPdwZn4KkxXOT
				6VxBZH+HzZkog31SaNjcccVzU9xnVP4UfvrMyDL5U0LtwTe6duy0vPMCAwEAAQ==
				-----END PUBLIC KEY-----
				""");
		when(configurations.findById("k8s.public-key")).thenReturn(Optional.of(pub));
		final K8sPkService serv = new K8sPkService(configurations);
		final String str = serv.createCsr("CN=test,O=system:masters");
		assertNotNull(str);
		LOG.debug(str);
	}
}
