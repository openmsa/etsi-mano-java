package com.ubiqube.etsi.mano.em.v261.controller.faultmngt;

import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class FaultMngtSubscriptions261Sol002ApiController implements FaultmngtSubscriptions261Sol002Api {

	private final ObjectMapper objectMapper;

	private final HttpServletRequest request;

	@org.springframework.beans.factory.annotation.Autowired
	public FaultMngtSubscriptions261Sol002ApiController(final ObjectMapper objectMapper, final HttpServletRequest request) {
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
