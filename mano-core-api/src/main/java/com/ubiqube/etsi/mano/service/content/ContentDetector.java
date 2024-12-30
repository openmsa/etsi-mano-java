package com.ubiqube.etsi.mano.service.content;

import org.springframework.http.MediaType;

import com.ubiqube.etsi.mano.repository.ManoResource;

public interface ContentDetector {

	/**
	 * Determines the media type of the given ManoResource.
	 *
	 * @param mr the ManoResource for which the media type is to be determined
	 * @return the MediaType of the given ManoResource
	 */
	MediaType getMediaType(ManoResource mr);
}
