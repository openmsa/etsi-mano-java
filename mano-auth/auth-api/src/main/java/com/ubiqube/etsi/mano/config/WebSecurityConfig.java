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
package com.ubiqube.etsi.mano.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.ubiqube.etsi.mano.AuthException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	private final SecutiryConfig secutiryConfig;

	public WebSecurityConfig(final SecutiryConfig secutiryConfig) {
		this.secutiryConfig = secutiryConfig;
	}

	/**
	 * All request must be authenticated, No login page.
	 */
	@Bean
	public SecurityFilterChain configure(final HttpSecurity http) {
		try {
			http.headers().frameOptions().sameOrigin();
			http.csrf().disable();
			final var res = http.authorizeRequests()
					.requestMatchers("/").permitAll()
					.requestMatchers("/api/**").permitAll()
					.requestMatchers("/ui/**").permitAll()
					.requestMatchers("/webjars/**").permitAll()
					.requestMatchers("/npm/**").permitAll()
					.requestMatchers("/error").permitAll()
					.requestMatchers("/h2-console/**").permitAll()
					.requestMatchers("/download/**").permitAll()
					.requestMatchers("/actuator/**").permitAll()
					.requestMatchers("/swagger-ui.html").permitAll()
					.requestMatchers("/swagger-ui/**").permitAll()
					.requestMatchers("/api-docs/**").permitAll()
					.requestMatchers("/v3/**").permitAll()
					.anyRequest().authenticated();
			secutiryConfig.configure(res);
			return http.build();
		} catch (final Exception e) {
			throw new AuthException(e);
		}
	}
}
