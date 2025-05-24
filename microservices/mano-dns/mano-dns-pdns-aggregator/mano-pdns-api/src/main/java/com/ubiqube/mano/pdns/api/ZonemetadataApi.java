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
import com.ubiqube.mano.pdns.model.Metadata;

import jakarta.annotation.Nonnull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-05-23T10:34:53.990305454+02:00[Europe/Paris]", comments = "Generator version: 7.13.0")
public class ZonemetadataApi {
	private ApiClient apiClient;

	public ZonemetadataApi() {
		this(new ApiClient());
	}

	@Autowired
	public ZonemetadataApi(final ApiClient apiClient) {
		this.apiClient = apiClient;
	}

	public ApiClient getApiClient() {
		return apiClient;
	}

	public void setApiClient(final ApiClient apiClient) {
		this.apiClient = apiClient;
	}

	/**
	 * Creates a set of metadata entries Creates a set of metadata entries of given
	 * kind for the zone. Existing metadata entries for the zone with the same kind
	 * are not overwritten.
	 * <p>
	 * <b>204</b> - OK
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
	 * @param zoneId   The zoneId parameter
	 * @param metadata Metadata object with list of values to create
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	private ResponseSpec createMetadataRequestCreation(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final Metadata metadata) throws WebClientResponseException {
		Object postBody = metadata;
		// verify the required parameter 'serverId' is set
		if (serverId == null) {
			throw new WebClientResponseException("Missing the required parameter 'serverId' when calling createMetadata", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'zoneId' is set
		if (zoneId == null) {
			throw new WebClientResponseException("Missing the required parameter 'zoneId' when calling createMetadata", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'metadata' is set
		if (metadata == null) {
			throw new WebClientResponseException("Missing the required parameter 'metadata' when calling createMetadata", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
		return apiClient.invokeAPI("/servers/{server_id}/zones/{zone_id}/metadata", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
	}

	/**
	 * Creates a set of metadata entries Creates a set of metadata entries of given
	 * kind for the zone. Existing metadata entries for the zone with the same kind
	 * are not overwritten.
	 * <p>
	 * <b>204</b> - OK
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
	 * @param zoneId   The zoneId parameter
	 * @param metadata Metadata object with list of values to create
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<Void> createMetadata(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final Metadata metadata) throws WebClientResponseException {
		ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return createMetadataRequestCreation(serverId, zoneId, metadata).bodyToMono(localVarReturnType);
	}

	/**
	 * Creates a set of metadata entries Creates a set of metadata entries of given
	 * kind for the zone. Existing metadata entries for the zone with the same kind
	 * are not overwritten.
	 * <p>
	 * <b>204</b> - OK
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
	 * @param zoneId   The zoneId parameter
	 * @param metadata Metadata object with list of values to create
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<ResponseEntity<Void>> createMetadataWithHttpInfo(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final Metadata metadata) throws WebClientResponseException {
		ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return createMetadataRequestCreation(serverId, zoneId, metadata).toEntity(localVarReturnType);
	}

	/**
	 * Creates a set of metadata entries Creates a set of metadata entries of given
	 * kind for the zone. Existing metadata entries for the zone with the same kind
	 * are not overwritten.
	 * <p>
	 * <b>204</b> - OK
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
	 * @param zoneId   The zoneId parameter
	 * @param metadata Metadata object with list of values to create
	 * @return ResponseSpec
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public ResponseSpec createMetadataWithResponseSpec(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final Metadata metadata) throws WebClientResponseException {
		return createMetadataRequestCreation(serverId, zoneId, metadata);
	}

	/**
	 * Delete all items of a single kind of domain metadata.
	 * 
	 * <p>
	 * <b>204</b> - OK
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId     The id of the server to retrieve
	 * @param zoneId       The id of the zone to retrieve
	 * @param metadataKind The kind of metadata
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	private ResponseSpec deleteMetadataRequestCreation(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final String metadataKind) throws WebClientResponseException {
		Object postBody = null;
		// verify the required parameter 'serverId' is set
		if (serverId == null) {
			throw new WebClientResponseException("Missing the required parameter 'serverId' when calling deleteMetadata", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'zoneId' is set
		if (zoneId == null) {
			throw new WebClientResponseException("Missing the required parameter 'zoneId' when calling deleteMetadata", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'metadataKind' is set
		if (metadataKind == null) {
			throw new WebClientResponseException("Missing the required parameter 'metadataKind' when calling deleteMetadata", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// create path and map variables
		final Map<String, Object> pathParams = new HashMap<>();

		pathParams.put("server_id", serverId);
		pathParams.put("zone_id", zoneId);
		pathParams.put("metadata_kind", metadataKind);

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
		return apiClient.invokeAPI("/servers/{server_id}/zones/{zone_id}/metadata/{metadata_kind}", HttpMethod.DELETE, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
	}

	/**
	 * Delete all items of a single kind of domain metadata.
	 * 
	 * <p>
	 * <b>204</b> - OK
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId     The id of the server to retrieve
	 * @param zoneId       The id of the zone to retrieve
	 * @param metadataKind The kind of metadata
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<Void> deleteMetadata(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final String metadataKind) throws WebClientResponseException {
		ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return deleteMetadataRequestCreation(serverId, zoneId, metadataKind).bodyToMono(localVarReturnType);
	}

	/**
	 * Delete all items of a single kind of domain metadata.
	 * 
	 * <p>
	 * <b>204</b> - OK
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId     The id of the server to retrieve
	 * @param zoneId       The id of the zone to retrieve
	 * @param metadataKind The kind of metadata
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<ResponseEntity<Void>> deleteMetadataWithHttpInfo(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final String metadataKind) throws WebClientResponseException {
		ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return deleteMetadataRequestCreation(serverId, zoneId, metadataKind).toEntity(localVarReturnType);
	}

	/**
	 * Delete all items of a single kind of domain metadata.
	 * 
	 * <p>
	 * <b>204</b> - OK
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId     The id of the server to retrieve
	 * @param zoneId       The id of the zone to retrieve
	 * @param metadataKind The kind of metadata
	 * @return ResponseSpec
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public ResponseSpec deleteMetadataWithResponseSpec(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final String metadataKind) throws WebClientResponseException {
		return deleteMetadataRequestCreation(serverId, zoneId, metadataKind);
	}

	/**
	 * Get the content of a single kind of domain metadata as a Metadata object.
	 * 
	 * <p>
	 * <b>200</b> - Metadata object with list of values
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId     The id of the server to retrieve
	 * @param zoneId       The id of the zone to retrieve
	 * @param metadataKind The kind of metadata
	 * @return Metadata
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	private ResponseSpec getMetadataRequestCreation(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final String metadataKind) throws WebClientResponseException {
		Object postBody = null;
		// verify the required parameter 'serverId' is set
		if (serverId == null) {
			throw new WebClientResponseException("Missing the required parameter 'serverId' when calling getMetadata", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'zoneId' is set
		if (zoneId == null) {
			throw new WebClientResponseException("Missing the required parameter 'zoneId' when calling getMetadata", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'metadataKind' is set
		if (metadataKind == null) {
			throw new WebClientResponseException("Missing the required parameter 'metadataKind' when calling getMetadata", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// create path and map variables
		final Map<String, Object> pathParams = new HashMap<>();

		pathParams.put("server_id", serverId);
		pathParams.put("zone_id", zoneId);
		pathParams.put("metadata_kind", metadataKind);

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

		ParameterizedTypeReference<Metadata> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return apiClient.invokeAPI("/servers/{server_id}/zones/{zone_id}/metadata/{metadata_kind}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
	}

	/**
	 * Get the content of a single kind of domain metadata as a Metadata object.
	 * 
	 * <p>
	 * <b>200</b> - Metadata object with list of values
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId     The id of the server to retrieve
	 * @param zoneId       The id of the zone to retrieve
	 * @param metadataKind The kind of metadata
	 * @return Metadata
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<Metadata> getMetadata(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final String metadataKind) throws WebClientResponseException {
		ParameterizedTypeReference<Metadata> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return getMetadataRequestCreation(serverId, zoneId, metadataKind).bodyToMono(localVarReturnType);
	}

	/**
	 * Get the content of a single kind of domain metadata as a Metadata object.
	 * 
	 * <p>
	 * <b>200</b> - Metadata object with list of values
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId     The id of the server to retrieve
	 * @param zoneId       The id of the zone to retrieve
	 * @param metadataKind The kind of metadata
	 * @return ResponseEntity&lt;Metadata&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<ResponseEntity<Metadata>> getMetadataWithHttpInfo(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final String metadataKind) throws WebClientResponseException {
		ParameterizedTypeReference<Metadata> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return getMetadataRequestCreation(serverId, zoneId, metadataKind).toEntity(localVarReturnType);
	}

	/**
	 * Get the content of a single kind of domain metadata as a Metadata object.
	 * 
	 * <p>
	 * <b>200</b> - Metadata object with list of values
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId     The id of the server to retrieve
	 * @param zoneId       The id of the zone to retrieve
	 * @param metadataKind The kind of metadata
	 * @return ResponseSpec
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public ResponseSpec getMetadataWithResponseSpec(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final String metadataKind) throws WebClientResponseException {
		return getMetadataRequestCreation(serverId, zoneId, metadataKind);
	}

	/**
	 * Get all the Metadata associated with the zone.
	 * 
	 * <p>
	 * <b>200</b> - List of Metadata objects
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
	 * @return List&lt;Metadata&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	private ResponseSpec listMetadataRequestCreation(@Nonnull final String serverId, @Nonnull final String zoneId) throws WebClientResponseException {
		Object postBody = null;
		// verify the required parameter 'serverId' is set
		if (serverId == null) {
			throw new WebClientResponseException("Missing the required parameter 'serverId' when calling listMetadata", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'zoneId' is set
		if (zoneId == null) {
			throw new WebClientResponseException("Missing the required parameter 'zoneId' when calling listMetadata", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

		ParameterizedTypeReference<Metadata> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return apiClient.invokeAPI("/servers/{server_id}/zones/{zone_id}/metadata", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
	}

	/**
	 * Get all the Metadata associated with the zone.
	 * 
	 * <p>
	 * <b>200</b> - List of Metadata objects
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
	 * @return List&lt;Metadata&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Flux<Metadata> listMetadata(@Nonnull final String serverId, @Nonnull final String zoneId) throws WebClientResponseException {
		ParameterizedTypeReference<Metadata> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return listMetadataRequestCreation(serverId, zoneId).bodyToFlux(localVarReturnType);
	}

	/**
	 * Get all the Metadata associated with the zone.
	 * 
	 * <p>
	 * <b>200</b> - List of Metadata objects
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
	 * @return ResponseEntity&lt;List&lt;Metadata&gt;&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<ResponseEntity<List<Metadata>>> listMetadataWithHttpInfo(@Nonnull final String serverId, @Nonnull final String zoneId) throws WebClientResponseException {
		ParameterizedTypeReference<Metadata> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return listMetadataRequestCreation(serverId, zoneId).toEntityList(localVarReturnType);
	}

	/**
	 * Get all the Metadata associated with the zone.
	 * 
	 * <p>
	 * <b>200</b> - List of Metadata objects
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
	public ResponseSpec listMetadataWithResponseSpec(@Nonnull final String serverId, @Nonnull final String zoneId) throws WebClientResponseException {
		return listMetadataRequestCreation(serverId, zoneId);
	}

	/**
	 * Replace the content of a single kind of domain metadata. Creates a set of
	 * metadata entries of given kind for the zone. Existing metadata entries for
	 * the zone with the same kind are removed.
	 * <p>
	 * <b>200</b> - Metadata object with list of values
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId     The id of the server to retrieve
	 * @param zoneId       The zoneId parameter
	 * @param metadataKind The kind of metadata
	 * @param metadata     metadata to add/create
	 * @return Metadata
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	private ResponseSpec modifyMetadataRequestCreation(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final String metadataKind, @Nonnull final Metadata metadata) throws WebClientResponseException {
		Object postBody = metadata;
		// verify the required parameter 'serverId' is set
		if (serverId == null) {
			throw new WebClientResponseException("Missing the required parameter 'serverId' when calling modifyMetadata", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'zoneId' is set
		if (zoneId == null) {
			throw new WebClientResponseException("Missing the required parameter 'zoneId' when calling modifyMetadata", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'metadataKind' is set
		if (metadataKind == null) {
			throw new WebClientResponseException("Missing the required parameter 'metadataKind' when calling modifyMetadata", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'metadata' is set
		if (metadata == null) {
			throw new WebClientResponseException("Missing the required parameter 'metadata' when calling modifyMetadata", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// create path and map variables
		final Map<String, Object> pathParams = new HashMap<>();

		pathParams.put("server_id", serverId);
		pathParams.put("zone_id", zoneId);
		pathParams.put("metadata_kind", metadataKind);

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

		ParameterizedTypeReference<Metadata> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return apiClient.invokeAPI("/servers/{server_id}/zones/{zone_id}/metadata/{metadata_kind}", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
	}

	/**
	 * Replace the content of a single kind of domain metadata. Creates a set of
	 * metadata entries of given kind for the zone. Existing metadata entries for
	 * the zone with the same kind are removed.
	 * <p>
	 * <b>200</b> - Metadata object with list of values
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId     The id of the server to retrieve
	 * @param zoneId       The zoneId parameter
	 * @param metadataKind The kind of metadata
	 * @param metadata     metadata to add/create
	 * @return Metadata
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<Metadata> modifyMetadata(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final String metadataKind, @Nonnull final Metadata metadata) throws WebClientResponseException {
		ParameterizedTypeReference<Metadata> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return modifyMetadataRequestCreation(serverId, zoneId, metadataKind, metadata).bodyToMono(localVarReturnType);
	}

	/**
	 * Replace the content of a single kind of domain metadata. Creates a set of
	 * metadata entries of given kind for the zone. Existing metadata entries for
	 * the zone with the same kind are removed.
	 * <p>
	 * <b>200</b> - Metadata object with list of values
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId     The id of the server to retrieve
	 * @param zoneId       The zoneId parameter
	 * @param metadataKind The kind of metadata
	 * @param metadata     metadata to add/create
	 * @return ResponseEntity&lt;Metadata&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<ResponseEntity<Metadata>> modifyMetadataWithHttpInfo(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final String metadataKind, @Nonnull final Metadata metadata) throws WebClientResponseException {
		ParameterizedTypeReference<Metadata> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return modifyMetadataRequestCreation(serverId, zoneId, metadataKind, metadata).toEntity(localVarReturnType);
	}

	/**
	 * Replace the content of a single kind of domain metadata. Creates a set of
	 * metadata entries of given kind for the zone. Existing metadata entries for
	 * the zone with the same kind are removed.
	 * <p>
	 * <b>200</b> - Metadata object with list of values
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId     The id of the server to retrieve
	 * @param zoneId       The zoneId parameter
	 * @param metadataKind The kind of metadata
	 * @param metadata     metadata to add/create
	 * @return ResponseSpec
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public ResponseSpec modifyMetadataWithResponseSpec(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final String metadataKind, @Nonnull final Metadata metadata) throws WebClientResponseException {
		return modifyMetadataRequestCreation(serverId, zoneId, metadataKind, metadata);
	}
}
