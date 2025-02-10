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
package com.ubiqube.etsi.mano;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.ubiqube.etsi.mano.docker.HelmDockerService;
import com.ubiqube.etsi.mano.docker.HelmOciDockerService;
import com.ubiqube.etsi.mano.docker.JibDockerService;
import com.ubiqube.etsi.mano.orchestrator.v4.Q4Workflow;
import com.ubiqube.etsi.mano.orchestrator.v4.Q4WorkflowImpl;

@SpringBootApplication
@EnableScheduling
@EnableJms
@SuppressWarnings("static-method")
public class VnfmApplication extends SpringBootServletInitializer {

	public static void main(final String[] args) {
		Security.addProvider(new BouncyCastleProvider());
		SpringApplication.run(VnfmApplication.class, args);
	}

	@Bean
	JibDockerService jibDocker() {
		return new JibDockerService();
	}

	@Bean
	HelmOciDockerService helmOciDockerService() {
		return new HelmOciDockerService();
	}

	@Bean
	HelmDockerService helmDockerService() {
		return new HelmDockerService();
	}

	@Bean
	Q4Workflow q4Workflow() {
		return new Q4WorkflowImpl();
	}
}
