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
package com.ubiqube.etsi.mano.service;

import java.util.Objects;

import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.service.auth.model.AuthParamBasic;
import com.ubiqube.etsi.mano.service.auth.model.AuthParamOauth2;
import com.ubiqube.etsi.mano.service.auth.model.AuthType;
import com.ubiqube.etsi.mano.service.auth.model.AuthentificationInformations;
import com.ubiqube.etsi.mano.service.auth.model.OAuth2GrantType;
import com.ubiqube.etsi.mano.service.event.model.Subscription;

import org.jspecify.annotations.Nullable;

public class AuthChecker {

	private AuthChecker() {
		//
	}

	public static void checkAuthData(final Subscription subscription) {
		final AuthentificationInformations authInfo = subscription.getAuthentication();
		if (authInfo == null) {
			return;
		}
		authInfo.getAuthType().forEach(authType -> check(authType, authInfo));
	}

	private static void check(final AuthType authType, final AuthentificationInformations authInfo) {
		switch (authType) {
		case BASIC -> checkBasic(authInfo.getAuthParamBasic());
		case OAUTH2_CLIENT_CREDENTIALS -> checkOauth2(authInfo.getAuthParamOauth2());
		case TLS_CERT -> checkTls(authInfo.getAuthTlsCert());
		default -> throw new IllegalArgumentException("Unexpected value: " + authType);
		}
	}

	private static void checkTls(final @Nullable String authTlsCert) {
		Objects.requireNonNull(authTlsCert, "TLS certificate should not be empty.");
	}

	private static void checkOauth2(final @Nullable AuthParamOauth2 authParamOauth2) {
		if (authParamOauth2 == null) {
			throw new GenericException("No OAuth2 parameters.");
		}
		if (OAuth2GrantType.CLIENT_CREDENTIAL.equals(authParamOauth2.getGrantType())) {
			Objects.requireNonNull(authParamOauth2.getClientId(), "Client ID must not be null");
			Objects.requireNonNull(authParamOauth2.getClientSecret(), "Client Secret must not be null");
		} else if (OAuth2GrantType.PASSWORD.equals(authParamOauth2.getGrantType())) {
			Objects.requireNonNull(authParamOauth2.getO2Username(), "Username must not be null.");
			Objects.requireNonNull(authParamOauth2.getO2Password(), "Password must not be null.");
		}
	}

	private static void checkBasic(final @Nullable AuthParamBasic authParamBasic) {
		Objects.requireNonNull(authParamBasic, "No basic parameters provided.");
		Objects.requireNonNull(authParamBasic.getUserName(), "Username must not be null.");
	}
}
