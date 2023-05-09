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
package com.ubiqube.etsi.mano.auth.config;

import java.io.IOException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Landing JSON page in case of login error.
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class Http403EntryPoint implements AuthenticationEntryPoint {
	/** Logger instance. */
	private static final Logger LOG = LoggerFactory.getLogger(Http403EntryPoint.class);
	/** Injected JSON object mapper. */
	private final ObjectMapper mapper;

	/**
	 * Constructor
	 *
	 * @param _mapper JSON Object mapper.
	 */
	public Http403EntryPoint(final ObjectMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException) throws IOException, ServletException {
		LOG.error("Auth failed {}", request.getRemoteAddr());
		response.setContentType(MediaType.APPLICATION_PROBLEM_JSON.toString());
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.addHeader("WWW-Authenticate", "Basic realm=\"ETSI-MANO API Realm\"");
		try (PrintWriter out = response.getWriter()) {
			final ProblemDetails problemDetails = new ProblemDetails(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized.");
			out.print(mapper.writeValueAsString(problemDetails));
		}
	}

}
