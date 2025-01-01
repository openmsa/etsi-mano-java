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
package com.ubiqube.etsi.mano.jpa;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.cnf.ConnectionType;

/**
 * Repository interface for ConnectionInformation
 *
 * @version 1.0
 */
public interface ConnectionInformationJpa extends CrudRepository<ConnectionInformation, UUID> {
	/**
	 * Finds a list of ConnectionInformation entities by their connection type.
	 *
	 * @param type the connection type to search for
	 * @return a list of ConnectionInformation entities with the specified connection type
	 */
	List<ConnectionInformation> findByConnType(ConnectionType type);

	/**
	 * Finds a ConnectionInformation entity by its name.
	 *
	 * @param repository the name of the ConnectionInformation entity to search for
	 * @return the ConnectionInformation entity with the specified name, or null if not found
	 */
	ConnectionInformation findByName(String repository);

}
