package com.ubiqube.etsi.mano.service.event;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionType;
import com.ubiqube.etsi.mano.docker.HelmService;
import com.ubiqube.etsi.mano.docker.RegistryInformations;
import com.ubiqube.etsi.mano.exception.GenericException;

@Service
public class CirServerChecker {
	private final Map<ConnectionType, HelmService> helmServices;

	public CirServerChecker(final List<HelmService> helmServices) {
		this.helmServices = helmServices.stream().collect(Collectors.toMap(x -> ConnectionType.valueOf(x.getConnectionType()), x -> x));
	}

	public void verify(final ConnectionInformation conn) {
		final HelmService hs = helmServices.get(conn.getConnType());
		if (null == hs) {
			throw new GenericException("Unknown CIR: " + conn.getConnType());
		}
		final RegistryInformations reg = toRegistry(conn);
		hs.verifyConnection(reg);
	}

	private static RegistryInformations toRegistry(final ConnectionInformation conn) {
		return RegistryInformations.builder()
				.server(conn.getUrl().toString())
				.username(conn.getAuthentification().getAuthParamBasic().getUserName())
				.password(conn.getAuthentification().getAuthParamBasic().getPassword())
				.build();
	}

}
