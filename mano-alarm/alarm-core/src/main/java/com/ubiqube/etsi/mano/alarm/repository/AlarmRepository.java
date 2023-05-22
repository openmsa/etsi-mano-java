/**
 * This copy of Woodstox XML processor is licensed under the
 * Apache (Software) License, version 2.0 ("the License").
 * See the License for details about distribution rights, and the
 * specific rights regarding derivate works.
 *
 * You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/
 *
 * A copy is also included in the downloadable source code package
 * containing Woodstox, in file "ASL2.0", under the same directory
 * as this file.
 */
package com.ubiqube.etsi.mano.alarm.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.ubiqube.etsi.mano.alarm.entities.alarm.Alarm;

/**
 *
 * @author Olivier Vignaud
 *
 */
public interface AlarmRepository extends CrudRepository<Alarm, UUID> {

	List<Alarm> findByMetricsObjectIdAndMetricsKey(String objectId, String key);
}
