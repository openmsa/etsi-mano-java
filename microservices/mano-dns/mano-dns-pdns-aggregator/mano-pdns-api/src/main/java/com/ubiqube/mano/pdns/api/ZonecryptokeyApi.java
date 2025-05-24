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
import com.ubiqube.mano.pdns.model.Cryptokey;

import jakarta.annotation.Nonnull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-05-23T10:34:53.990305454+02:00[Europe/Paris]", comments = "Generator version: 7.13.0")
public class ZonecryptokeyApi {
	private ApiClient apiClient;

	public ZonecryptokeyApi() {
		this(new ApiClient());
	}

	@Autowired
	public ZonecryptokeyApi(final ApiClient apiClient) {
		this.apiClient = apiClient;
	}

	public ApiClient getApiClient() {
		return apiClient;
	}

	public void setApiClient(final ApiClient apiClient) {
		this.apiClient = apiClient;
	}

	/**
	 * Creates a Cryptokey This method adds a new key to a zone. The key can either
	 * be generated or imported by supplying the content parameter. if content, bits
	 * and algo are null, a key will be generated based on the default-ksk-algorithm
	 * and default-ksk-size settings for a KSK and the default-zsk-algorithm and
	 * default-zsk-size options for a ZSK.
	 * <p>
	 * <b>201</b> - Created
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId  The id of the server to retrieve
	 * @param zoneId    The zoneId parameter
	 * @param cryptokey Add a Cryptokey
	 * @return Cryptokey
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	private ResponseSpec createCryptokeyRequestCreation(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final Cryptokey cryptokey) throws WebClientResponseException {
		Object postBody = cryptokey;
		// verify the required parameter 'serverId' is set
		if (serverId == null) {
			throw new WebClientResponseException("Missing the required parameter 'serverId' when calling createCryptokey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'zoneId' is set
		if (zoneId == null) {
			throw new WebClientResponseException("Missing the required parameter 'zoneId' when calling createCryptokey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'cryptokey' is set
		if (cryptokey == null) {
			throw new WebClientResponseException("Missing the required parameter 'cryptokey' when calling createCryptokey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

		ParameterizedTypeReference<Cryptokey> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return apiClient.invokeAPI("/servers/{server_id}/zones/{zone_id}/cryptokeys", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
	}

	/**
	 * Creates a Cryptokey This method adds a new key to a zone. The key can either
	 * be generated or imported by supplying the content parameter. if content, bits
	 * and algo are null, a key will be generated based on the default-ksk-algorithm
	 * and default-ksk-size settings for a KSK and the default-zsk-algorithm and
	 * default-zsk-size options for a ZSK.
	 * <p>
	 * <b>201</b> - Created
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId  The id of the server to retrieve
	 * @param zoneId    The zoneId parameter
	 * @param cryptokey Add a Cryptokey
	 * @return Cryptokey
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<Cryptokey> createCryptokey(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final Cryptokey cryptokey) throws WebClientResponseException {
		ParameterizedTypeReference<Cryptokey> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return createCryptokeyRequestCreation(serverId, zoneId, cryptokey).bodyToMono(localVarReturnType);
	}

	/**
	 * Creates a Cryptokey This method adds a new key to a zone. The key can either
	 * be generated or imported by supplying the content parameter. if content, bits
	 * and algo are null, a key will be generated based on the default-ksk-algorithm
	 * and default-ksk-size settings for a KSK and the default-zsk-algorithm and
	 * default-zsk-size options for a ZSK.
	 * <p>
	 * <b>201</b> - Created
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId  The id of the server to retrieve
	 * @param zoneId    The zoneId parameter
	 * @param cryptokey Add a Cryptokey
	 * @return ResponseEntity&lt;Cryptokey&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<ResponseEntity<Cryptokey>> createCryptokeyWithHttpInfo(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final Cryptokey cryptokey) throws WebClientResponseException {
		ParameterizedTypeReference<Cryptokey> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return createCryptokeyRequestCreation(serverId, zoneId, cryptokey).toEntity(localVarReturnType);
	}

	/**
	 * Creates a Cryptokey This method adds a new key to a zone. The key can either
	 * be generated or imported by supplying the content parameter. if content, bits
	 * and algo are null, a key will be generated based on the default-ksk-algorithm
	 * and default-ksk-size settings for a KSK and the default-zsk-algorithm and
	 * default-zsk-size options for a ZSK.
	 * <p>
	 * <b>201</b> - Created
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId  The id of the server to retrieve
	 * @param zoneId    The zoneId parameter
	 * @param cryptokey Add a Cryptokey
	 * @return ResponseSpec
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public ResponseSpec createCryptokeyWithResponseSpec(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final Cryptokey cryptokey) throws WebClientResponseException {
		return createCryptokeyRequestCreation(serverId, zoneId, cryptokey);
	}

	/**
	 * This method deletes a key specified by cryptokey_id.
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
	 * @param serverId    The id of the server to retrieve
	 * @param zoneId      The id of the zone to retrieve
	 * @param cryptokeyId The id value of the Cryptokey
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	private ResponseSpec deleteCryptokeyRequestCreation(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final String cryptokeyId) throws WebClientResponseException {
		Object postBody = null;
		// verify the required parameter 'serverId' is set
		if (serverId == null) {
			throw new WebClientResponseException("Missing the required parameter 'serverId' when calling deleteCryptokey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'zoneId' is set
		if (zoneId == null) {
			throw new WebClientResponseException("Missing the required parameter 'zoneId' when calling deleteCryptokey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'cryptokeyId' is set
		if (cryptokeyId == null) {
			throw new WebClientResponseException("Missing the required parameter 'cryptokeyId' when calling deleteCryptokey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// create path and map variables
		final Map<String, Object> pathParams = new HashMap<>();

		pathParams.put("server_id", serverId);
		pathParams.put("zone_id", zoneId);
		pathParams.put("cryptokey_id", cryptokeyId);

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
		return apiClient.invokeAPI("/servers/{server_id}/zones/{zone_id}/cryptokeys/{cryptokey_id}", HttpMethod.DELETE, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
	}

	/**
	 * This method deletes a key specified by cryptokey_id.
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
	 * @param serverId    The id of the server to retrieve
	 * @param zoneId      The id of the zone to retrieve
	 * @param cryptokeyId The id value of the Cryptokey
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<Void> deleteCryptokey(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final String cryptokeyId) throws WebClientResponseException {
		ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return deleteCryptokeyRequestCreation(serverId, zoneId, cryptokeyId).bodyToMono(localVarReturnType);
	}

	/**
	 * This method deletes a key specified by cryptokey_id.
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
	 * @param serverId    The id of the server to retrieve
	 * @param zoneId      The id of the zone to retrieve
	 * @param cryptokeyId The id value of the Cryptokey
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<ResponseEntity<Void>> deleteCryptokeyWithHttpInfo(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final String cryptokeyId) throws WebClientResponseException {
		ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return deleteCryptokeyRequestCreation(serverId, zoneId, cryptokeyId).toEntity(localVarReturnType);
	}

	/**
	 * This method deletes a key specified by cryptokey_id.
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
	 * @param serverId    The id of the server to retrieve
	 * @param zoneId      The id of the zone to retrieve
	 * @param cryptokeyId The id value of the Cryptokey
	 * @return ResponseSpec
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public ResponseSpec deleteCryptokeyWithResponseSpec(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final String cryptokeyId) throws WebClientResponseException {
		return deleteCryptokeyRequestCreation(serverId, zoneId, cryptokeyId);
	}

	/**
	 * Returns all data about the CryptoKey, including the privatekey.
	 * 
	 * <p>
	 * <b>200</b> - Cryptokey
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId    The id of the server to retrieve
	 * @param zoneId      The id of the zone to retrieve
	 * @param cryptokeyId The id value of the CryptoKey
	 * @return Cryptokey
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	private ResponseSpec getCryptokeyRequestCreation(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final String cryptokeyId) throws WebClientResponseException {
		Object postBody = null;
		// verify the required parameter 'serverId' is set
		if (serverId == null) {
			throw new WebClientResponseException("Missing the required parameter 'serverId' when calling getCryptokey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'zoneId' is set
		if (zoneId == null) {
			throw new WebClientResponseException("Missing the required parameter 'zoneId' when calling getCryptokey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'cryptokeyId' is set
		if (cryptokeyId == null) {
			throw new WebClientResponseException("Missing the required parameter 'cryptokeyId' when calling getCryptokey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// create path and map variables
		final Map<String, Object> pathParams = new HashMap<>();

		pathParams.put("server_id", serverId);
		pathParams.put("zone_id", zoneId);
		pathParams.put("cryptokey_id", cryptokeyId);

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

		ParameterizedTypeReference<Cryptokey> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return apiClient.invokeAPI("/servers/{server_id}/zones/{zone_id}/cryptokeys/{cryptokey_id}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
	}

	/**
	 * Returns all data about the CryptoKey, including the privatekey.
	 * 
	 * <p>
	 * <b>200</b> - Cryptokey
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId    The id of the server to retrieve
	 * @param zoneId      The id of the zone to retrieve
	 * @param cryptokeyId The id value of the CryptoKey
	 * @return Cryptokey
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<Cryptokey> getCryptokey(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final String cryptokeyId) throws WebClientResponseException {
		ParameterizedTypeReference<Cryptokey> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return getCryptokeyRequestCreation(serverId, zoneId, cryptokeyId).bodyToMono(localVarReturnType);
	}

	/**
	 * Returns all data about the CryptoKey, including the privatekey.
	 * 
	 * <p>
	 * <b>200</b> - Cryptokey
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId    The id of the server to retrieve
	 * @param zoneId      The id of the zone to retrieve
	 * @param cryptokeyId The id value of the CryptoKey
	 * @return ResponseEntity&lt;Cryptokey&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<ResponseEntity<Cryptokey>> getCryptokeyWithHttpInfo(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final String cryptokeyId) throws WebClientResponseException {
		ParameterizedTypeReference<Cryptokey> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return getCryptokeyRequestCreation(serverId, zoneId, cryptokeyId).toEntity(localVarReturnType);
	}

	/**
	 * Returns all data about the CryptoKey, including the privatekey.
	 * 
	 * <p>
	 * <b>200</b> - Cryptokey
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId    The id of the server to retrieve
	 * @param zoneId      The id of the zone to retrieve
	 * @param cryptokeyId The id value of the CryptoKey
	 * @return ResponseSpec
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public ResponseSpec getCryptokeyWithResponseSpec(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final String cryptokeyId) throws WebClientResponseException {
		return getCryptokeyRequestCreation(serverId, zoneId, cryptokeyId);
	}

	/**
	 * Get all CryptoKeys for a zone, except the privatekey
	 * 
	 * <p>
	 * <b>200</b> - List of Cryptokey objects
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
	 * @return List&lt;Cryptokey&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	private ResponseSpec listCryptokeysRequestCreation(@Nonnull final String serverId, @Nonnull final String zoneId) throws WebClientResponseException {
		Object postBody = null;
		// verify the required parameter 'serverId' is set
		if (serverId == null) {
			throw new WebClientResponseException("Missing the required parameter 'serverId' when calling listCryptokeys", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'zoneId' is set
		if (zoneId == null) {
			throw new WebClientResponseException("Missing the required parameter 'zoneId' when calling listCryptokeys", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

		ParameterizedTypeReference<Cryptokey> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return apiClient.invokeAPI("/servers/{server_id}/zones/{zone_id}/cryptokeys", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
	}

	/**
	 * Get all CryptoKeys for a zone, except the privatekey
	 * 
	 * <p>
	 * <b>200</b> - List of Cryptokey objects
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
	 * @return List&lt;Cryptokey&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Flux<Cryptokey> listCryptokeys(@Nonnull final String serverId, @Nonnull final String zoneId) throws WebClientResponseException {
		ParameterizedTypeReference<Cryptokey> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return listCryptokeysRequestCreation(serverId, zoneId).bodyToFlux(localVarReturnType);
	}

	/**
	 * Get all CryptoKeys for a zone, except the privatekey
	 * 
	 * <p>
	 * <b>200</b> - List of Cryptokey objects
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
	 * @return ResponseEntity&lt;List&lt;Cryptokey&gt;&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<ResponseEntity<List<Cryptokey>>> listCryptokeysWithHttpInfo(@Nonnull final String serverId, @Nonnull final String zoneId) throws WebClientResponseException {
		ParameterizedTypeReference<Cryptokey> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return listCryptokeysRequestCreation(serverId, zoneId).toEntityList(localVarReturnType);
	}

	/**
	 * Get all CryptoKeys for a zone, except the privatekey
	 * 
	 * <p>
	 * <b>200</b> - List of Cryptokey objects
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
	public ResponseSpec listCryptokeysWithResponseSpec(@Nonnull final String serverId, @Nonnull final String zoneId) throws WebClientResponseException {
		return listCryptokeysRequestCreation(serverId, zoneId);
	}

	/**
	 * This method (de)activates a key from zone_name specified by cryptokey_id
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
	 * @param serverId    The id of the server to retrieve
	 * @param zoneId      The zoneId parameter
	 * @param cryptokeyId Cryptokey to manipulate
	 * @param cryptokey   the Cryptokey
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	private ResponseSpec modifyCryptokeyRequestCreation(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final String cryptokeyId, @Nonnull final Cryptokey cryptokey) throws WebClientResponseException {
		Object postBody = cryptokey;
		// verify the required parameter 'serverId' is set
		if (serverId == null) {
			throw new WebClientResponseException("Missing the required parameter 'serverId' when calling modifyCryptokey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'zoneId' is set
		if (zoneId == null) {
			throw new WebClientResponseException("Missing the required parameter 'zoneId' when calling modifyCryptokey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'cryptokeyId' is set
		if (cryptokeyId == null) {
			throw new WebClientResponseException("Missing the required parameter 'cryptokeyId' when calling modifyCryptokey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'cryptokey' is set
		if (cryptokey == null) {
			throw new WebClientResponseException("Missing the required parameter 'cryptokey' when calling modifyCryptokey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// create path and map variables
		final Map<String, Object> pathParams = new HashMap<>();

		pathParams.put("server_id", serverId);
		pathParams.put("zone_id", zoneId);
		pathParams.put("cryptokey_id", cryptokeyId);

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
		return apiClient.invokeAPI("/servers/{server_id}/zones/{zone_id}/cryptokeys/{cryptokey_id}", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
	}

	/**
	 * This method (de)activates a key from zone_name specified by cryptokey_id
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
	 * @param serverId    The id of the server to retrieve
	 * @param zoneId      The zoneId parameter
	 * @param cryptokeyId Cryptokey to manipulate
	 * @param cryptokey   the Cryptokey
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<Void> modifyCryptokey(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final String cryptokeyId, @Nonnull final Cryptokey cryptokey) throws WebClientResponseException {
		ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return modifyCryptokeyRequestCreation(serverId, zoneId, cryptokeyId, cryptokey).bodyToMono(localVarReturnType);
	}

	/**
	 * This method (de)activates a key from zone_name specified by cryptokey_id
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
	 * @param serverId    The id of the server to retrieve
	 * @param zoneId      The zoneId parameter
	 * @param cryptokeyId Cryptokey to manipulate
	 * @param cryptokey   the Cryptokey
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<ResponseEntity<Void>> modifyCryptokeyWithHttpInfo(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final String cryptokeyId, @Nonnull final Cryptokey cryptokey) throws WebClientResponseException {
		ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return modifyCryptokeyRequestCreation(serverId, zoneId, cryptokeyId, cryptokey).toEntity(localVarReturnType);
	}

	/**
	 * This method (de)activates a key from zone_name specified by cryptokey_id
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
	 * @param serverId    The id of the server to retrieve
	 * @param zoneId      The zoneId parameter
	 * @param cryptokeyId Cryptokey to manipulate
	 * @param cryptokey   the Cryptokey
	 * @return ResponseSpec
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public ResponseSpec modifyCryptokeyWithResponseSpec(@Nonnull final String serverId, @Nonnull final String zoneId, @Nonnull final String cryptokeyId, @Nonnull final Cryptokey cryptokey) throws WebClientResponseException {
		return modifyCryptokeyRequestCreation(serverId, zoneId, cryptokeyId, cryptokey);
	}
}
