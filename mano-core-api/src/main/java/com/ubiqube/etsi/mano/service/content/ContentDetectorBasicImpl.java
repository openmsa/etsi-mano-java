package com.ubiqube.etsi.mano.service.content;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.repository.ManoResource;

@Service
public class ContentDetectorBasicImpl implements ContentDetector {

	@Override
	public MediaType getMediaType(final ManoResource mr) {
		final byte[] buff = new byte[16];
		try (InputStream is = mr.getInputStream()) {
			is.read(buff);
		} catch (final IOException e) {
			throw new GenericException(e);
		}
		final byte[] zipHeader = { (byte) 'P', (byte) 'K', 3, 4 };
		if (Arrays.equals(Arrays.copyOfRange(buff, 0, 4), zipHeader)) {
			return MediaType.valueOf("application/zip");
		}
		return MediaType.APPLICATION_OCTET_STREAM;
	}

}
