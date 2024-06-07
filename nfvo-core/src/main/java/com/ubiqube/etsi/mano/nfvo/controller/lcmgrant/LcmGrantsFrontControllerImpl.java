/**
 *     Copyright (C) 2019-2024 Ubiqube.
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
package com.ubiqube.etsi.mano.nfvo.controller.lcmgrant;

import static com.ubiqube.etsi.mano.Constants.getSafeUUID;

import java.net.URI;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import com.ubiqube.etsi.mano.dao.rfc7807.FailureDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.controller.lcmgrant.GrantManagement;
import com.ubiqube.etsi.mano.controller.lcmgrant.LcmGrantsFrontController;
import com.ubiqube.etsi.mano.dao.mano.AccessInfo;
import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.InterfaceInfo;
import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.service.vim.VimTypeConverter;

import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class LcmGrantsFrontControllerImpl implements LcmGrantsFrontController {
	private final GrantManagement grantManagement;
	private final VimTypeConverter vimTypeConverter;

	public LcmGrantsFrontControllerImpl(final @Nonnull GrantManagement grantManagement, final VimTypeConverter vimTypeConverter) {
		this.grantManagement = grantManagement;
		this.vimTypeConverter = vimTypeConverter;
	}

	@Override
	public <U> ResponseEntity<U> grantsGrantIdGet(final String grantId, final Function<GrantResponse, U> func, final Consumer<U> makeLink) {
		final GrantResponse grants = grantManagement.get(getSafeUUID(grantId));
		if (!grants.getAvailable().equals(Boolean.TRUE)) {
			return ResponseEntity.accepted().build();
		}
		final Set<VimConnectionInformation<? extends InterfaceInfo, ? extends AccessInfo>> cism = grants.getCismConnections();
		if (cism != null) {
			grants.getVimConnections().addAll(cism);
		}
		grants.getVimConnections().forEach(vimTypeConverter::setToExternalType);
		final U jsonGrant = func.apply(grants);
		// Only self link, other links came from request (mapping).
		makeLink.accept(jsonGrant);
		final Optional<Long> optError = Optional.ofNullable(grants.getError()).map(
                FailureDetails::getStatus);
		if (optError.isEmpty()) {
			return ResponseEntity.ok(jsonGrant);
		}
		return (ResponseEntity<U>) ResponseEntity.internalServerError().body(grants.getError());
	}

	@Override
	public <U> ResponseEntity<U> grantsPost(@Valid final GrantResponse grantRequest, final Function<GrantResponse, U> func, final Function<U, String> getSelfLink) {
		final GrantResponse resp = grantManagement.post(grantRequest);
		final U res = func.apply(resp);
		final URI location = URI.create(getSelfLink.apply(res));
		return ResponseEntity.accepted().location(location).build();
	}

}
