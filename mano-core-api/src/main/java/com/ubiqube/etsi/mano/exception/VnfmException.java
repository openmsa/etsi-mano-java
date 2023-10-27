package com.ubiqube.etsi.mano.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VnfmException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public VnfmException(final String message) {
		super(message);
	}

}
