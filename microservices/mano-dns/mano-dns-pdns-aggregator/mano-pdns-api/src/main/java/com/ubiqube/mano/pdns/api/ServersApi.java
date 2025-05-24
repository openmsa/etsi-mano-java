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
import com.ubiqube.mano.pdns.model.CacheFlushResult;
import com.ubiqube.mano.pdns.model.Server;

import jakarta.annotation.Nonnull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-05-23T10:34:53.990305454+02:00[Europe/Paris]", comments = "Generator version: 7.13.0")
public class ServersApi {
	private ApiClient apiClient;

	public ServersApi() {
		this(new ApiClient());
	}

	@Autowired
	public ServersApi(final ApiClient apiClient) {
		this.apiClient = apiClient;
	}

	public ApiClient getApiClient() {
		return apiClient;
	}

	public void setApiClient(final ApiClient apiClient) {
		this.apiClient = apiClient;
	}

	/**
	 * Flush a cache-entry by name
	 * 
	 * <p>
	 * <b>200</b> - Flush successful
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
	 * @param domain   The domain name to flush from the cache
	 * @return CacheFlushResult
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	private ResponseSpec cacheFlushByNameRequestCreation(@Nonnull final String serverId, @Nonnull final String domain) throws WebClientResponseException {
		Object postBody = null;
		// verify the required parameter 'serverId' is set
		if (serverId == null) {
			throw new WebClientResponseException("Missing the required parameter 'serverId' when calling cacheFlushByName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'domain' is set
		if (domain == null) {
			throw new WebClientResponseException("Missing the required parameter 'domain' when calling cacheFlushByName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// create path and map variables
		final Map<String, Object> pathParams = new HashMap<>();

		pathParams.put("server_id", serverId);

		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		final HttpHeaders headerParams = new HttpHeaders();
		final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
		final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

		queryParams.putAll(apiClient.parameterToMultiValueMap(null, "domain", domain));

		final String[] localVarAccepts = {
				"application/json"
		};
		final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
		final String[] localVarContentTypes = {};
		final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

		String[] localVarAuthNames = { "APIKeyHeader" };

		ParameterizedTypeReference<CacheFlushResult> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return apiClient.invokeAPI("/servers/{server_id}/cache/flush", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
	}

	/**
	 * Flush a cache-entry by name
	 * 
	 * <p>
	 * <b>200</b> - Flush successful
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
	 * @param domain   The domain name to flush from the cache
	 * @return CacheFlushResult
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<CacheFlushResult> cacheFlushByName(@Nonnull final String serverId, @Nonnull final String domain) throws WebClientResponseException {
		ParameterizedTypeReference<CacheFlushResult> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return cacheFlushByNameRequestCreation(serverId, domain).bodyToMono(localVarReturnType);
	}

	/**
	 * Flush a cache-entry by name
	 * 
	 * <p>
	 * <b>200</b> - Flush successful
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
	 * @param domain   The domain name to flush from the cache
	 * @return ResponseEntity&lt;CacheFlushResult&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<ResponseEntity<CacheFlushResult>> cacheFlushByNameWithHttpInfo(@Nonnull final String serverId, @Nonnull final String domain) throws WebClientResponseException {
		ParameterizedTypeReference<CacheFlushResult> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return cacheFlushByNameRequestCreation(serverId, domain).toEntity(localVarReturnType);
	}

	/**
	 * Flush a cache-entry by name
	 * 
	 * <p>
	 * <b>200</b> - Flush successful
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
	 * @param domain   The domain name to flush from the cache
	 * @return ResponseSpec
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public ResponseSpec cacheFlushByNameWithResponseSpec(@Nonnull final String serverId, @Nonnull final String domain) throws WebClientResponseException {
		return cacheFlushByNameRequestCreation(serverId, domain);
	}

	/**
	 * List a server
	 * 
	 * <p>
	 * <b>200</b> - An server
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
	 * @return Server
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	private ResponseSpec listServerRequestCreation(@Nonnull final String serverId) throws WebClientResponseException {
		Object postBody = null;
		// verify the required parameter 'serverId' is set
		if (serverId == null) {
			throw new WebClientResponseException("Missing the required parameter 'serverId' when calling listServer", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// create path and map variables
		final Map<String, Object> pathParams = new HashMap<>();

		pathParams.put("server_id", serverId);

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

		ParameterizedTypeReference<Server> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return apiClient.invokeAPI("/servers/{server_id}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
	}

	/**
	 * List a server
	 * 
	 * <p>
	 * <b>200</b> - An server
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
	 * @return Server
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<Server> listServer(@Nonnull final String serverId) throws WebClientResponseException {
		ParameterizedTypeReference<Server> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return listServerRequestCreation(serverId).bodyToMono(localVarReturnType);
	}

	/**
	 * List a server
	 * 
	 * <p>
	 * <b>200</b> - An server
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
	 * @return ResponseEntity&lt;Server&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<ResponseEntity<Server>> listServerWithHttpInfo(@Nonnull final String serverId) throws WebClientResponseException {
		ParameterizedTypeReference<Server> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return listServerRequestCreation(serverId).toEntity(localVarReturnType);
	}

	/**
	 * List a server
	 * 
	 * <p>
	 * <b>200</b> - An server
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
	 * @return ResponseSpec
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public ResponseSpec listServerWithResponseSpec(@Nonnull final String serverId) throws WebClientResponseException {
		return listServerRequestCreation(serverId);
	}

	/**
	 * List all servers
	 * 
	 * <p>
	 * <b>200</b> - An array of servers
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @return List&lt;Server&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	private ResponseSpec listServersRequestCreation() throws WebClientResponseException {
		Object postBody = null;
		// create path and map variables
		final Map<String, Object> pathParams = new HashMap<>();

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

		ParameterizedTypeReference<Server> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return apiClient.invokeAPI("/servers", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
	}

	/**
	 * List all servers
	 * 
	 * <p>
	 * <b>200</b> - An array of servers
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @return List&lt;Server&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Flux<Server> listServers() throws WebClientResponseException {
		ParameterizedTypeReference<Server> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return listServersRequestCreation().bodyToFlux(localVarReturnType);
	}

	/**
	 * List all servers
	 * 
	 * <p>
	 * <b>200</b> - An array of servers
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @return ResponseEntity&lt;List&lt;Server&gt;&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<ResponseEntity<List<Server>>> listServersWithHttpInfo() throws WebClientResponseException {
		ParameterizedTypeReference<Server> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return listServersRequestCreation().toEntityList(localVarReturnType);
	}

	/**
	 * List all servers
	 * 
	 * <p>
	 * <b>200</b> - An array of servers
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @return ResponseSpec
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public ResponseSpec listServersWithResponseSpec() throws WebClientResponseException {
		return listServersRequestCreation();
	}
}
