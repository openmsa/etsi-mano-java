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
package com.ubiqube.mano.pdns.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.ubiqube.mano.pdns.invoker.ApiClient;
import com.ubiqube.mano.pdns.model.Zone;

import jakarta.annotation.Nonnull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-05-23T10:34:53.990305454+02:00[Europe/Paris]", comments = "Generator version: 7.13.0")
public class ZonesApi {
	private ApiClient apiClient;

	public ZonesApi() {
		this(new ApiClient());
	}

	@Autowired
	public ZonesApi(final ApiClient apiClient) {
		this.apiClient = apiClient;
	}

	public ApiClient getApiClient() {
		return apiClient;
	}

	public void setApiClient(final ApiClient apiClient) {
		this.apiClient = apiClient;
	}

	/**
	 * Returns the zone in AXFR format.
	 *
	 * <p>
	 * <b>200</b> - OK
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId The id of the server to retrieve
	 * @param zoneId   The id of the zone to retrieve
	 * @return String
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	private ResponseSpec axfrExportZoneRequestCreation(@Nonnull final String serverId, @Nonnull final String zoneId) throws WebClientResponseException {
		Object postBody = null;
		// verify the required parameter 'serverId' is set
		if (serverId == null) {
			throw new WebClientResponseException("Missing the required parameter 'serverId' when calling axfrExportZone", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'zoneId' is set
		if (zoneId == null) {
			throw new WebClientResponseException("Missing the required parameter 'zoneId' when calling axfrExportZone", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// create path and map variables
		final Map<String, Object> pathParams = new HashMap<>();

		pathParams.put("server_id", serverId);
		pathParams.put("zone_id", zoneId);

		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		final HttpHeaders headerParams = new HttpHeaders();
		final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
		final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

		final String[] localVarAccepts = {
				"application/json"
		};
		final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
		final String[] localVarContentTypes = {};
		final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

		String[] localVarAuthNames = { "APIKeyHeader" };

		ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return apiClient.invokeAPI("/servers/{server_id}/zones/{zone_id}/export", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
	}

	/**
	 * Returns the zone in AXFR format.
	 *
	 * <p>
	 * <b>200</b> - OK
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId The id of the server to retrieve
	 * @param zoneId   The id of the zone to retrieve
	 * @return String
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<String> axfrExportZone(@Nonnull final String serverId, @Nonnull final String zoneId) throws WebClientResponseException {
		ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return axfrExportZoneRequestCreation(serverId, zoneId).bodyToMono(localVarReturnType);
	}

	/**
	 * Returns the zone in AXFR format.
	 *
	 * <p>
	 * <b>200</b> - OK
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId The id of the server to retrieve
	 * @param zoneId   The id of the zone to retrieve
	 * @return ResponseEntity&lt;String&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<ResponseEntity<String>> axfrExportZoneWithHttpInfo(@Nonnull final String serverId, @Nonnull final String zoneId) throws WebClientResponseException {
		ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return axfrExportZoneRequestCreation(serverId, zoneId).toEntity(localVarReturnType);
	}

	/**
	 * Returns the zone in AXFR format.
	 *
	 * <p>
	 * <b>200</b> - OK
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId The id of the server to retrieve
	 * @param zoneId   The id of the zone to retrieve
	 * @return ResponseSpec
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public ResponseSpec axfrExportZoneWithResponseSpec(@Nonnull final String serverId, @Nonnull final String zoneId) throws WebClientResponseException {
		return axfrExportZoneRequestCreation(serverId, zoneId);
	}

	/**
	 * Retrieve slave zone from its master. Fails when zone kind is not Slave, or
	 * slave is disabled in the configuration. Clients MUST NOT send a body.
	 * <p>
	 * <b>200</b> - OK
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId The id of the server to retrieve
	 * @param zoneId   The id of the zone to retrieve
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	private ResponseSpec axfrRetrieveZoneRequestCreation(@Nonnull final String serverId, @Nonnull final String zoneId) throws WebClientResponseException {
		Object postBody = null;
		// verify the required parameter 'serverId' is set
		if (serverId == null) {
			throw new WebClientResponseException("Missing the required parameter 'serverId' when calling axfrRetrieveZone", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'zoneId' is set
		if (zoneId == null) {
			throw new WebClientResponseException("Missing the required parameter 'zoneId' when calling axfrRetrieveZone", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// create path and map variables
		final Map<String, Object> pathParams = new HashMap<>();

		pathParams.put("server_id", serverId);
		pathParams.put("zone_id", zoneId);

		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		final HttpHeaders headerParams = new HttpHeaders();
		final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
		final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

		final String[] localVarAccepts = {
				"application/json"
		};
		final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
		final String[] localVarContentTypes = {};
		final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

		String[] localVarAuthNames = { "APIKeyHeader" };

		ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return apiClient.invokeAPI("/servers/{server_id}/zones/{zone_id}/axfr-retrieve", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
	}

	/**
	 * Retrieve slave zone from its master. Fails when zone kind is not Slave, or
	 * slave is disabled in the configuration. Clients MUST NOT send a body.
	 * <p>
	 * <b>200</b> - OK
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId The id of the server to retrieve
	 * @param zoneId   The id of the zone to retrieve
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<Void> axfrRetrieveZone(@Nonnull final String serverId, @Nonnull final String zoneId) throws WebClientResponseException {
		ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return axfrRetrieveZoneRequestCreation(serverId, zoneId).bodyToMono(localVarReturnType);
	}

	/**
	 * Retrieve slave zone from its master. Fails when zone kind is not Slave, or
	 * slave is disabled in the configuration. Clients MUST NOT send a body.
	 * <p>
	 * <b>200</b> - OK
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId The id of the server to retrieve
	 * @param zoneId   The id of the zone to retrieve
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<ResponseEntity<Void>> axfrRetrieveZoneWithHttpInfo(@Nonnull final String serverId, @Nonnull final String zoneId) throws WebClientResponseException {
		ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return axfrRetrieveZoneRequestCreation(serverId, zoneId).toEntity(localVarReturnType);
	}

	/**
	 * Retrieve slave zone from its master. Fails when zone kind is not Slave, or
	 * slave is disabled in the configuration. Clients MUST NOT send a body.
	 * <p>
	 * <b>200</b> - OK
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId The id of the server to retrieve
	 * @param zoneId   The id of the zone to retrieve
	 * @return ResponseSpec
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public ResponseSpec axfrRetrieveZoneWithResponseSpec(@Nonnull final String serverId, @Nonnull final String zoneId) throws WebClientResponseException {
		return axfrRetrieveZoneRequestCreation(serverId, zoneId);
	}

	/**
	 * Creates a new domain, returns the Zone on creation.
	 *
	 * <p>
	 * <b>201</b> - A zone
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId   The id of the server to retrieve
	 * @param zoneStruct The zone struct to patch with
	 * @param rrsets     “true” (default) or “false”, whether to include the
	 *                   “rrsets” in the response Zone object.
	 * @return Zone
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	private ResponseSpec createZoneRequestCreation(@Nonnull final String serverId, @Nonnull final Zone zoneStruct, @Nullable final Boolean rrsets) throws WebClientResponseException {
		Object postBody = zoneStruct;
		// verify the required parameter 'serverId' is set
		if (serverId == null) {
			throw new WebClientResponseException("Missing the required parameter 'serverId' when calling createZone", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'zoneStruct' is set
		if (zoneStruct == null) {
			throw new WebClientResponseException("Missing the required parameter 'zoneStruct' when calling createZone", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// create path and map variables
		final Map<String, Object> pathParams = new HashMap<>();

		pathParams.put("server_id", serverId);

		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		final HttpHeaders headerParams = new HttpHeaders();
		final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
		final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

		queryParams.putAll(apiClient.parameterToMultiValueMap(null, "rrsets", rrsets));

		final String[] localVarAccepts = {
				"application/json"
		};
		final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
		final String[] localVarContentTypes = {
				"application/json"
		};
		final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

		String[] localVarAuthNames = { "APIKeyHeader" };

		ParameterizedTypeReference<Zone> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return apiClient.invokeAPI("/servers/{server_id}/zones", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
	}

	/**
	 * Creates a new domain, returns the Zone on creation.
	 *
	 * <p>
	 * <b>201</b> - A zone
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId   The id of the server to retrieve
	 * @param zoneStruct The zone struct to patch with
	 * @param rrsets     “true” (default) or “false”, whether to include the
	 *                   “rrsets” in the response Zone object.
	 * @return Zone
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<Zone> createZone(@Nonnull final String serverId, @Nonnull final Zone zoneStruct, @Nullable final Boolean rrsets) throws WebClientResponseException {
		ParameterizedTypeReference<Zone> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return createZoneRequestCreation(serverId, zoneStruct, rrsets).bodyToMono(localVarReturnType);
	}

	/**
	 * Creates a new domain, returns the Zone on creation.
	 *
	 * <p>
	 * <b>201</b> - A zone
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId   The id of the server to retrieve
	 * @param zoneStruct The zone struct to patch with
	 * @param rrsets     “true” (default) or “false”, whether to include the
	 *                   “rrsets” in the response Zone object.
	 * @return ResponseEntity&lt;Zone&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<ResponseEntity<Zone>> createZoneWithHttpInfo(@Nonnull final String serverId, @Nonnull final Zone zoneStruct, @Nullable final Boolean rrsets) throws WebClientResponseException {
		ParameterizedTypeReference<Zone> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return createZoneRequestCreation(serverId, zoneStruct, rrsets).toEntity(localVarReturnType);
	}

	/**
	 * Creates a new domain, returns the Zone on creation.
	 *
	 * <p>
	 * <b>201</b> - A zone
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId   The id of the server to retrieve
	 * @param zoneStruct The zone struct to patch with
	 * @param rrsets     “true” (default) or “false”, whether to include the
	 *                   “rrsets” in the response Zone object.
	 * @return ResponseSpec
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public ResponseSpec createZoneWithResponseSpec(@Nonnull final String serverId, @Nonnull final Zone zoneStruct, @Nullable final Boolean rrsets) throws WebClientResponseException {
		return createZoneRequestCreation(serverId, zoneStruct, rrsets);
	}

	/**
	 * Deletes this zone, all attached metadata and rrsets.
	 *
	 * <p>
	 * <b>204</b> - Returns 204 No Content on success.
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId The id of the server to retrieve
	 * @param zoneId   The id of the zone to retrieve
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	private ResponseSpec deleteZoneRequestCreation(@Nonnull final String serverId, @Nonnull final String zoneId) throws WebClientResponseException {
		Object postBody = null;
		// verify the required parameter 'serverId' is set
		if (serverId == null) {
			throw new WebClientResponseException("Missing the required parameter 'serverId' when calling deleteZone", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'zoneId' is set
		if (zoneId == null) {
			throw new WebClientResponseException("Missing the required parameter 'zoneId' when calling deleteZone", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// create path and map variables
		final Map<String, Object> pathParams = new HashMap<>();

		pathParams.put("server_id", serverId);
		pathParams.put("zone_id", zoneId);

		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		final HttpHeaders headerParams = new HttpHeaders();
		final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
		final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

		final String[] localVarAccepts = {
				"application/json"
		};
		final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
		final String[] localVarContentTypes = {};
		final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

		String[] localVarAuthNames = { "APIKeyHeader" };

		ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return apiClient.invokeAPI("/servers/{server_id}/zones/{zone_id}", HttpMethod.DELETE, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
	}

	/**
	 * Deletes this zone, all attached metadata and rrsets.
	 *
	 * <p>
	 * <b>204</b> - Returns 204 No Content on success.
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId The id of the server to retrieve
	 * @param zoneId   The id of the zone to retrieve
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<Void> deleteZone(@Nonnull final String serverId, @Nonnull final String zoneId) throws WebClientResponseException {
		ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return deleteZoneRequestCreation(serverId, zoneId).bodyToMono(localVarReturnType);
	}

	/**
	 * Deletes this zone, all attached metadata and rrsets.
	 *
	 * <p>
	 * <b>204</b> - Returns 204 No Content on success.
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId The id of the server to retrieve
	 * @param zoneId   The id of the zone to retrieve
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<ResponseEntity<Void>> deleteZoneWithHttpInfo(@Nonnull final String serverId, @Nonnull final String zoneId) throws WebClientResponseException {
		ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return deleteZoneRequestCreation(serverId, zoneId).toEntity(localVarReturnType);
	}

	/**
	 * Deletes this zone, all attached metadata and rrsets.
	 *
	 * <p>
	 * <b>204</b> - Returns 204 No Content on success.
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId The id of the server to retrieve
	 * @param zoneId   The id of the zone to retrieve
	 * @return ResponseSpec
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public ResponseSpec deleteZoneWithResponseSpec(@Nonnull final String serverId, @Nonnull final String zoneId) throws WebClientResponseException {
		return deleteZoneRequestCreation(serverId, zoneId);
	}

	/**
	 * zone managed by a server
	 *
	 * <p>
	 * <b>200</b> - A Zone
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId        The id of the server to retrieve
	 * @param zoneId          The id of the zone to retrieve
	 * @param rrsets          “true” (default) or “false”, whether to include the
	 *                        “rrsets” in the response Zone object.
	 * @param rrsetName       Limit output to RRsets for this name.
	 * @param rrsetType       Limit output to the RRset of this type. Can only be
	 *                        used together with rrset_name.
	 * @param includeDisabled “true” (default) or “false”, whether to include
	 *                        disabled RRsets in the response.
	 * @return Zone
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	private ResponseSpec listZoneRequestCreation(@Nonnull final String serverId, @Nonnull final String zoneId, @Nullable final Boolean rrsets, @Nullable final String rrsetName, @Nullable final String rrsetType, @Nullable final Boolean includeDisabled) throws WebClientResponseException {
		Object postBody = null;
		// verify the required parameter 'serverId' is set
		if (serverId == null) {
			throw new WebClientResponseException("Missing the required parameter 'serverId' when calling listZone", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'zoneId' is set
		if (zoneId == null) {
			throw new WebClientResponseException("Missing the required parameter 'zoneId' when calling listZone", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// create path and map variables
		final Map<String, Object> pathParams = new HashMap<>();

		pathParams.put("server_id", serverId);
		pathParams.put("zone_id", zoneId);

		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		final HttpHeaders headerParams = new HttpHeaders();
		final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
		final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

		queryParams.putAll(apiClient.parameterToMultiValueMap(null, "rrsets", rrsets));
		queryParams.putAll(apiClient.parameterToMultiValueMap(null, "rrset_name", rrsetName));
		queryParams.putAll(apiClient.parameterToMultiValueMap(null, "rrset_type", rrsetType));
		queryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_disabled", includeDisabled));

		final String[] localVarAccepts = {
				"application/json"
		};
		final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
		final String[] localVarContentTypes = {};
		final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

		String[] localVarAuthNames = { "APIKeyHeader" };

		ParameterizedTypeReference<Zone> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return apiClient.invokeAPI("/servers/{server_id}/zones/{zone_id}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
	}

	/**
	 * zone managed by a server
	 *
	 * <p>
	 * <b>200</b> - A Zone
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId        The id of the server to retrieve
	 * @param zoneId          The id of the zone to retrieve
	 * @param rrsets          “true” (default) or “false”, whether to include the
	 *                        “rrsets” in the response Zone object.
	 * @param rrsetName       Limit output to RRsets for this name.
	 * @param rrsetType       Limit output to the RRset of this type. Can only be
	 *                        used together with rrset_name.
	 * @param includeDisabled “true” (default) or “false”, whether to include
	 *                        disabled RRsets in the response.
	 * @return Zone
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<Zone> listZone(@Nonnull final String serverId, @Nonnull final String zoneId, @Nullable final Boolean rrsets, @Nullable final String rrsetName, @Nullable final String rrsetType, @Nullable final Boolean includeDisabled) throws WebClientResponseException {
		ParameterizedTypeReference<Zone> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return listZoneRequestCreation(serverId, zoneId, rrsets, rrsetName, rrsetType, includeDisabled).bodyToMono(localVarReturnType);
	}

	/**
	 * zone managed by a server
	 *
	 * <p>
	 * <b>200</b> - A Zone
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId        The id of the server to retrieve
	 * @param zoneId          The id of the zone to retrieve
	 * @param rrsets          “true” (default) or “false”, whether to include the
	 *                        “rrsets” in the response Zone object.
	 * @param rrsetName       Limit output to RRsets for this name.
	 * @param rrsetType       Limit output to the RRset of this type. Can only be
	 *                        used together with rrset_name.
	 * @param includeDisabled “true” (default) or “false”, whether to include
	 *                        disabled RRsets in the response.
	 * @return ResponseEntity&lt;Zone&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<ResponseEntity<Zone>> listZoneWithHttpInfo(@Nonnull final String serverId, @Nonnull final String zoneId, @Nullable final Boolean rrsets, @Nullable final String rrsetName, @Nullable final String rrsetType, @Nullable final Boolean includeDisabled) throws WebClientResponseException {
		ParameterizedTypeReference<Zone> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return listZoneRequestCreation(serverId, zoneId, rrsets, rrsetName, rrsetType, includeDisabled).toEntity(localVarReturnType);
	}

	/**
	 * zone managed by a server
	 *
	 * <p>
	 * <b>200</b> - A Zone
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId        The id of the server to retrieve
	 * @param zoneId          The id of the zone to retrieve
	 * @param rrsets          “true” (default) or “false”, whether to include the
	 *                        “rrsets” in the response Zone object.
	 * @param rrsetName       Limit output to RRsets for this name.
	 * @param rrsetType       Limit output to the RRset of this type. Can only be
	 *                        used together with rrset_name.
	 * @param includeDisabled “true” (default) or “false”, whether to include
	 *                        disabled RRsets in the response.
	 * @return ResponseSpec
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public ResponseSpec listZoneWithResponseSpec(@Nonnull final String serverId, @Nonnull final String zoneId, @Nullable final Boolean rrsets, @Nullable final String rrsetName, @Nullable final String rrsetType, @Nullable final Boolean includeDisabled) throws WebClientResponseException {
		return listZoneRequestCreation(serverId, zoneId, rrsets, rrsetName, rrsetType, includeDisabled);
	}

	/**
	 * List all Zones in a server
	 *
	 * <p>
	 * <b>200</b> - An array of Zones
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId The id of the server to retrieve
	 * @param zone     When set to the name of a zone, only this zone is returned.
	 *                 If no zone with that name exists, the response is an empty
	 *                 array. This can e.g. be used to check if a zone exists in the
	 *                 database without having to guess/encode the zone&#39;s id or
	 *                 to check if a zone exists.
	 * @param dnssec   “true” (default) or “false”, whether to include the “dnssec”
	 *                 and ”edited_serial” fields in the Zone objects. Setting this
	 *                 to ”false” will make the query a lot faster.
	 * @return List&lt;Zone&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	private ResponseSpec listZonesRequestCreation(@Nonnull final String serverId, @Nullable final String zone, @Nullable final Boolean dnssec) throws WebClientResponseException {
		Object postBody = null;
		// verify the required parameter 'serverId' is set
		if (serverId == null) {
			throw new WebClientResponseException("Missing the required parameter 'serverId' when calling listZones", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// create path and map variables
		final Map<String, Object> pathParams = new HashMap<>();

		pathParams.put("server_id", serverId);

		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		final HttpHeaders headerParams = new HttpHeaders();
		final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
		final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

		queryParams.putAll(apiClient.parameterToMultiValueMap(null, "zone", zone));
		queryParams.putAll(apiClient.parameterToMultiValueMap(null, "dnssec", dnssec));

		final String[] localVarAccepts = {
				"application/json"
		};
		final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
		final String[] localVarContentTypes = {};
		final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

		String[] localVarAuthNames = { "APIKeyHeader" };

		ParameterizedTypeReference<Zone> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return apiClient.invokeAPI("/servers/{server_id}/zones", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
	}

	/**
	 * List all Zones in a server
	 *
	 * <p>
	 * <b>200</b> - An array of Zones
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId The id of the server to retrieve
	 * @param zone     When set to the name of a zone, only this zone is returned.
	 *                 If no zone with that name exists, the response is an empty
	 *                 array. This can e.g. be used to check if a zone exists in the
	 *                 database without having to guess/encode the zone&#39;s id or
	 *                 to check if a zone exists.
	 * @param dnssec   “true” (default) or “false”, whether to include the “dnssec”
	 *                 and ”edited_serial” fields in the Zone objects. Setting this
	 *                 to ”false” will make the query a lot faster.
	 * @return List&lt;Zone&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Flux<Zone> listZones(@Nonnull final String serverId, @Nullable final String zone, @Nullable final Boolean dnssec) throws WebClientResponseException {
		ParameterizedTypeReference<Zone> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return listZonesRequestCreation(serverId, zone, dnssec).bodyToFlux(localVarReturnType);
	}

	/**
	 * List all Zones in a server
	 *
	 * <p>
	 * <b>200</b> - An array of Zones
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId The id of the server to retrieve
	 * @param zone     When set to the name of a zone, only this zone is returned.
	 *                 If no zone with that name exists, the response is an empty
	 *                 array. This can e.g. be used to check if a zone exists in the
	 *                 database without having to guess/encode the zone&#39;s id or
	 *                 to check if a zone exists.
	 * @param dnssec   “true” (default) or “false”, whether to include the “dnssec”
	 *                 and ”edited_serial” fields in the Zone objects. Setting this
	 *                 to ”false” will make the query a lot faster.
	 * @return ResponseEntity&lt;List&lt;Zone&gt;&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<ResponseEntity<List<Zone>>> listZonesWithHttpInfo(@Nonnull final String serverId, @Nullable final String zone, @Nullable final Boolean dnssec) throws WebClientResponseException {
		ParameterizedTypeReference<Zone> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return listZonesRequestCreation(serverId, zone, dnssec).toEntityList(localVarReturnType);
	}

	/**
	 * List all Zones in a server
	 *
	 * <p>
	 * <b>200</b> - An array of Zones
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId The id of the server to retrieve
	 * @param zone     When set to the name of a zone, only this zone is returned.
	 *                 If no zone with that name exists, the response is an empty
	 *                 array. This can e.g. be used to check if a zone exists in the
	 *                 database without having to guess/encode the zone&#39;s id or
	 *                 to check if a zone exists.
	 * @param dnssec   “true” (default) or “false”, whether to include the “dnssec”
	 *                 and ”edited_serial” fields in the Zone objects. Setting this
	 *                 to ”false” will make the query a lot faster.
	 * @return ResponseSpec
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public ResponseSpec listZonesWithResponseSpec(@Nonnull final String serverId, @Nullable final String zone, @Nullable final Boolean dnssec) throws WebClientResponseException {
		return listZonesRequestCreation(serverId, zone, dnssec);
	}

	/**
	 * Send a DNS NOTIFY to all slaves. Fails when zone kind is not Master or Slave,
	 * or master and slave are disabled in the configuration. Only works for Slave
	 * if renotify is on. Clients MUST NOT send a body.
	 * <p>
	 * <b>200</b> - OK
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId The id of the server to retrieve
	 * @param zoneId   The id of the zone to retrieve
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	private ResponseSpec notifyZoneRequestCreation(@Nonnull final String serverId, @Nonnull final String zoneId) throws WebClientResponseException {
		Object postBody = null;
		// verify the required parameter 'serverId' is set
		if (serverId == null) {
			throw new WebClientResponseException("Missing the required parameter 'serverId' when calling notifyZone", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'zoneId' is set
		if (zoneId == null) {
			throw new WebClientResponseException("Missing the required parameter 'zoneId' when calling notifyZone", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// create path and map variables
		final Map<String, Object> pathParams = new HashMap<>();

		pathParams.put("server_id", serverId);
		pathParams.put("zone_id", zoneId);

		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		final HttpHeaders headerParams = new HttpHeaders();
		final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
		final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

		final String[] localVarAccepts = {
				"application/json"
		};
		final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
		final String[] localVarContentTypes = {};
		final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

		String[] localVarAuthNames = { "APIKeyHeader" };

		ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return apiClient.invokeAPI("/servers/{server_id}/zones/{zone_id}/notify", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
	}

	/**
	 * Send a DNS NOTIFY to all slaves. Fails when zone kind is not Master or Slave,
	 * or master and slave are disabled in the configuration. Only works for Slave
	 * if renotify is on. Clients MUST NOT send a body.
	 * <p>
	 * <b>200</b> - OK
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId The id of the server to retrieve
	 * @param zoneId   The id of the zone to retrieve
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<Void> notifyZone(@Nonnull final String serverId, @Nonnull final String zoneId) throws WebClientResponseException {
		ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return notifyZoneRequestCreation(serverId, zoneId).bodyToMono(localVarReturnType);
	}

	/**
	 * Send a DNS NOTIFY to all slaves. Fails when zone kind is not Master or Slave,
	 * or master and slave are disabled in the configuration. Only works for Slave
	 * if renotify is on. Clients MUST NOT send a body.
	 * <p>
	 * <b>200</b> - OK
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId The id of the server to retrieve
	 * @param zoneId   The id of the zone to retrieve
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<ResponseEntity<Void>> notifyZoneWithHttpInfo(@Nonnull final String serverId, @Nonnull final String zoneId) throws WebClientResponseException {
		ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return notifyZoneRequestCreation(serverId, zoneId).toEntity(localVarReturnType);
	}

	/**
	 * Send a DNS NOTIFY to all slaves. Fails when zone kind is not Master or Slave,
	 * or master and slave are disabled in the configuration. Only works for Slave
	 * if renotify is on. Clients MUST NOT send a body.
	 * <p>
	 * <b>200</b> - OK
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId The id of the server to retrieve
	 * @param zoneId   The id of the zone to retrieve
	 * @return ResponseSpec
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public ResponseSpec notifyZoneWithResponseSpec(@Nonnull final String serverId, @Nonnull final String zoneId) throws WebClientResponseException {
		return notifyZoneRequestCreation(serverId, zoneId);
	}

	/**
	 * Creates/modifies/deletes RRsets present in the payload and their comments.
	 * Returns 204 No Content on success.
	 *
	 * <p>
	 * <b>204</b> - Returns 204 No Content on success.
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId   The id of the server to retrieve
	 * @param zoneId     The zoneId parameter
	 * @param zoneStruct The zone struct to patch with
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	private ResponseSpec patchZoneRequestCreation(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final Zone zoneStruct) throws WebClientResponseException {
		Object postBody = zoneStruct;
		// verify the required parameter 'serverId' is set
		if (serverId == null) {
			throw new WebClientResponseException("Missing the required parameter 'serverId' when calling patchZone", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'zoneId' is set
		if (zoneId == null) {
			throw new WebClientResponseException("Missing the required parameter 'zoneId' when calling patchZone", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'zoneStruct' is set
		if (zoneStruct == null) {
			throw new WebClientResponseException("Missing the required parameter 'zoneStruct' when calling patchZone", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// create path and map variables
		final Map<String, Object> pathParams = new HashMap<>();

		pathParams.put("server_id", serverId);
		pathParams.put("zone_id", zoneId);

		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		final HttpHeaders headerParams = new HttpHeaders();
		final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
		final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

		final String[] localVarAccepts = {
				"application/json"
		};
		final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
		final String[] localVarContentTypes = {
				"application/json"
		};
		final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

		String[] localVarAuthNames = { "APIKeyHeader" };

		ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return apiClient.invokeAPI("/servers/{server_id}/zones/{zone_id}", HttpMethod.PATCH, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
	}

	/**
	 * Creates/modifies/deletes RRsets present in the payload and their comments.
	 * Returns 204 No Content on success.
	 *
	 * <p>
	 * <b>204</b> - Returns 204 No Content on success.
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId   The id of the server to retrieve
	 * @param zoneId     The zoneId parameter
	 * @param zoneStruct The zone struct to patch with
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<Void> patchZone(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final Zone zoneStruct) throws WebClientResponseException {
		ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return patchZoneRequestCreation(serverId, zoneId, zoneStruct).bodyToMono(localVarReturnType);
	}

	/**
	 * Creates/modifies/deletes RRsets present in the payload and their comments.
	 * Returns 204 No Content on success.
	 *
	 * <p>
	 * <b>204</b> - Returns 204 No Content on success.
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId   The id of the server to retrieve
	 * @param zoneId     The zoneId parameter
	 * @param zoneStruct The zone struct to patch with
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<ResponseEntity<Void>> patchZoneWithHttpInfo(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final Zone zoneStruct) throws WebClientResponseException {
		ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return patchZoneRequestCreation(serverId, zoneId, zoneStruct).toEntity(localVarReturnType);
	}

	/**
	 * Creates/modifies/deletes RRsets present in the payload and their comments.
	 * Returns 204 No Content on success.
	 *
	 * <p>
	 * <b>204</b> - Returns 204 No Content on success.
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId   The id of the server to retrieve
	 * @param zoneId     The zoneId parameter
	 * @param zoneStruct The zone struct to patch with
	 * @return ResponseSpec
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public ResponseSpec patchZoneWithResponseSpec(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final Zone zoneStruct) throws WebClientResponseException {
		return patchZoneRequestCreation(serverId, zoneId, zoneStruct);
	}

	/**
	 * Modifies basic zone data. The only fields in the zone structure which can be
	 * modified are: kind, masters, catalog, account, soa_edit, soa_edit_api,
	 * api_rectify, dnssec, and nsec3param. All other fields are ignored.
	 * <p>
	 * <b>204</b> - Returns 204 No Content on success.
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId   The id of the server to retrieve
	 * @param zoneId     The zoneId parameter
	 * @param zoneStruct The zone struct to patch with
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	private ResponseSpec putZoneRequestCreation(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final Zone zoneStruct) throws WebClientResponseException {
		Object postBody = zoneStruct;
		// verify the required parameter 'serverId' is set
		if (serverId == null) {
			throw new WebClientResponseException("Missing the required parameter 'serverId' when calling putZone", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'zoneId' is set
		if (zoneId == null) {
			throw new WebClientResponseException("Missing the required parameter 'zoneId' when calling putZone", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'zoneStruct' is set
		if (zoneStruct == null) {
			throw new WebClientResponseException("Missing the required parameter 'zoneStruct' when calling putZone", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// create path and map variables
		final Map<String, Object> pathParams = new HashMap<>();

		pathParams.put("server_id", serverId);
		pathParams.put("zone_id", zoneId);

		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		final HttpHeaders headerParams = new HttpHeaders();
		final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
		final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

		final String[] localVarAccepts = {
				"application/json"
		};
		final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
		final String[] localVarContentTypes = {
				"application/json"
		};
		final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

		String[] localVarAuthNames = { "APIKeyHeader" };

		ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return apiClient.invokeAPI("/servers/{server_id}/zones/{zone_id}", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
	}

	/**
	 * Modifies basic zone data. The only fields in the zone structure which can be
	 * modified are: kind, masters, catalog, account, soa_edit, soa_edit_api,
	 * api_rectify, dnssec, and nsec3param. All other fields are ignored.
	 * <p>
	 * <b>204</b> - Returns 204 No Content on success.
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId   The id of the server to retrieve
	 * @param zoneId     The zoneId parameter
	 * @param zoneStruct The zone struct to patch with
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<Void> putZone(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final Zone zoneStruct) throws WebClientResponseException {
		ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return putZoneRequestCreation(serverId, zoneId, zoneStruct).bodyToMono(localVarReturnType);
	}

	/**
	 * Modifies basic zone data. The only fields in the zone structure which can be
	 * modified are: kind, masters, catalog, account, soa_edit, soa_edit_api,
	 * api_rectify, dnssec, and nsec3param. All other fields are ignored.
	 * <p>
	 * <b>204</b> - Returns 204 No Content on success.
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId   The id of the server to retrieve
	 * @param zoneId     The zoneId parameter
	 * @param zoneStruct The zone struct to patch with
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<ResponseEntity<Void>> putZoneWithHttpInfo(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final Zone zoneStruct) throws WebClientResponseException {
		ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return putZoneRequestCreation(serverId, zoneId, zoneStruct).toEntity(localVarReturnType);
	}

	/**
	 * Modifies basic zone data. The only fields in the zone structure which can be
	 * modified are: kind, masters, catalog, account, soa_edit, soa_edit_api,
	 * api_rectify, dnssec, and nsec3param. All other fields are ignored.
	 * <p>
	 * <b>204</b> - Returns 204 No Content on success.
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId   The id of the server to retrieve
	 * @param zoneId     The zoneId parameter
	 * @param zoneStruct The zone struct to patch with
	 * @return ResponseSpec
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public ResponseSpec putZoneWithResponseSpec(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final Zone zoneStruct) throws WebClientResponseException {
		return putZoneRequestCreation(serverId, zoneId, zoneStruct);
	}

	/**
	 * Rectify the zone data. This does not take into account the API-RECTIFY
	 * metadata. Fails on slave zones and zones that do not have DNSSEC.
	 * <p>
	 * <b>200</b> - OK
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId The id of the server to retrieve
	 * @param zoneId   The id of the zone to retrieve
	 * @return String
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	private ResponseSpec rectifyZoneRequestCreation(@Nonnull final String serverId, @Nonnull final String zoneId) throws WebClientResponseException {
		Object postBody = null;
		// verify the required parameter 'serverId' is set
		if (serverId == null) {
			throw new WebClientResponseException("Missing the required parameter 'serverId' when calling rectifyZone", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'zoneId' is set
		if (zoneId == null) {
			throw new WebClientResponseException("Missing the required parameter 'zoneId' when calling rectifyZone", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// create path and map variables
		final Map<String, Object> pathParams = new HashMap<>();

		pathParams.put("server_id", serverId);
		pathParams.put("zone_id", zoneId);

		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		final HttpHeaders headerParams = new HttpHeaders();
		final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
		final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

		final String[] localVarAccepts = {
				"application/json"
		};
		final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
		final String[] localVarContentTypes = {};
		final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

		String[] localVarAuthNames = { "APIKeyHeader" };

		ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return apiClient.invokeAPI("/servers/{server_id}/zones/{zone_id}/rectify", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
	}

	/**
	 * Rectify the zone data. This does not take into account the API-RECTIFY
	 * metadata. Fails on slave zones and zones that do not have DNSSEC.
	 * <p>
	 * <b>200</b> - OK
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId The id of the server to retrieve
	 * @param zoneId   The id of the zone to retrieve
	 * @return String
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<String> rectifyZone(@Nonnull final String serverId, @Nonnull final String zoneId) throws WebClientResponseException {
		ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return rectifyZoneRequestCreation(serverId, zoneId).bodyToMono(localVarReturnType);
	}

	/**
	 * Rectify the zone data. This does not take into account the API-RECTIFY
	 * metadata. Fails on slave zones and zones that do not have DNSSEC.
	 * <p>
	 * <b>200</b> - OK
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId The id of the server to retrieve
	 * @param zoneId   The id of the zone to retrieve
	 * @return ResponseEntity&lt;String&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<ResponseEntity<String>> rectifyZoneWithHttpInfo(@Nonnull final String serverId, @Nonnull final String zoneId) throws WebClientResponseException {
		ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return rectifyZoneRequestCreation(serverId, zoneId).toEntity(localVarReturnType);
	}

	/**
	 * Rectify the zone data. This does not take into account the API-RECTIFY
	 * metadata. Fails on slave zones and zones that do not have DNSSEC.
	 * <p>
	 * <b>200</b> - OK
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId The id of the server to retrieve
	 * @param zoneId   The id of the zone to retrieve
	 * @return ResponseSpec
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public ResponseSpec rectifyZoneWithResponseSpec(@Nonnull final String serverId, @Nonnull final String zoneId) throws WebClientResponseException {
		return rectifyZoneRequestCreation(serverId, zoneId);
	}
}
