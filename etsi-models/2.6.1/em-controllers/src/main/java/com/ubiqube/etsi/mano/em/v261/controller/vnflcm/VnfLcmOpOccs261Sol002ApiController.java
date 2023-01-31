package com.ubiqube.etsi.mano.em.v261.controller.vnflcm;

import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class VnfLcmOpOccs261Sol002ApiController implements VnfLcmOpOccs261Sol002Api {

	private final ObjectMapper objectMapper;

	private final HttpServletRequest request;

	@org.springframework.beans.factory.annotation.Autowired
	public VnfLcmOpOccs261Sol002ApiController(final ObjectMapper objectMapper, final HttpServletRequest request) {
		this.objectMapper = objectMapper;
		this.request = request;
	}

	@Override
	public Optional<ObjectMapper> getObjectMapper() {
		return Optional.ofNullable(objectMapper);
	}

	@Override
	public Optional<HttpServletRequest> getRequest() {
		return Optional.ofNullable(request);
	}

}
