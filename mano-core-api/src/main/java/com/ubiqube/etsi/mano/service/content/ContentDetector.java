package com.ubiqube.etsi.mano.service.content;

import org.springframework.http.MediaType;

import com.ubiqube.etsi.mano.repository.ManoResource;

public interface ContentDetector {

	MediaType getMediaType(ManoResource mr);
}
