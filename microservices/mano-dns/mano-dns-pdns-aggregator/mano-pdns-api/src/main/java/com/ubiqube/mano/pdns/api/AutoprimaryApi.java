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
import com.ubiqube.mano.pdns.model.Autoprimary;

import jakarta.annotation.Nonnull;
import reactor.core.publisher.Mono;

@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-05-23T10:34:53.990305454+02:00[Europe/Paris]", comments = "Generator version: 7.13.0")
public class AutoprimaryApi {
	private ApiClient apiClient;

	public AutoprimaryApi() {
		this(new ApiClient());
	}

	@Autowired
	public AutoprimaryApi(final ApiClient apiClient) {
		this.apiClient = apiClient;
	}

	public ApiClient getApiClient() {
		return apiClient;
	}

	public void setApiClient(final ApiClient apiClient) {
		this.apiClient = apiClient;
	}

	/**
	 * Add an autoprimary This methods add a new autoprimary server.
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
	 * @param serverId    The id of the server to manage the list of autoprimaries
	 *                    on
	 * @param autoprimary autoprimary entry to add
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	private ResponseSpec createAutoprimaryRequestCreation(@Nonnull final String serverId, @Nonnull final Autoprimary autoprimary) throws WebClientResponseException {
		Object postBody = autoprimary;
		// verify the required parameter 'serverId' is set
		if (serverId == null) {
			throw new WebClientResponseException("Missing the required parameter 'serverId' when calling createAutoprimary", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'autoprimary' is set
		if (autoprimary == null) {
			throw new WebClientResponseException("Missing the required parameter 'autoprimary' when calling createAutoprimary", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
		final String[] localVarContentTypes = {
				"application/json"
		};
		final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

		String[] localVarAuthNames = { "APIKeyHeader" };

		ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return apiClient.invokeAPI("/servers/{server_id}/autoprimaries", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
	}

	/**
	 * Add an autoprimary This methods add a new autoprimary server.
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
	 * @param serverId    The id of the server to manage the list of autoprimaries
	 *                    on
	 * @param autoprimary autoprimary entry to add
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<Void> createAutoprimary(@Nonnull final String serverId, @Nonnull final Autoprimary autoprimary) throws WebClientResponseException {
		ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return createAutoprimaryRequestCreation(serverId, autoprimary).bodyToMono(localVarReturnType);
	}

	/**
	 * Add an autoprimary This methods add a new autoprimary server.
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
	 * @param serverId    The id of the server to manage the list of autoprimaries
	 *                    on
	 * @param autoprimary autoprimary entry to add
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<ResponseEntity<Void>> createAutoprimaryWithHttpInfo(@Nonnull final String serverId, @Nonnull final Autoprimary autoprimary) throws WebClientResponseException {
		ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return createAutoprimaryRequestCreation(serverId, autoprimary).toEntity(localVarReturnType);
	}

	/**
	 * Add an autoprimary This methods add a new autoprimary server.
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
	 * @param serverId    The id of the server to manage the list of autoprimaries
	 *                    on
	 * @param autoprimary autoprimary entry to add
	 * @return ResponseSpec
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public ResponseSpec createAutoprimaryWithResponseSpec(@Nonnull final String serverId, @Nonnull final Autoprimary autoprimary) throws WebClientResponseException {
		return createAutoprimaryRequestCreation(serverId, autoprimary);
	}

	/**
	 * Delete the autoprimary entry
	 * 
	 * <p>
	 * <b>204</b> - OK, key was deleted
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId   The id of the server to delete the autoprimary from
	 * @param ip         IP address of autoprimary
	 * @param nameserver DNS name of the autoprimary
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	private ResponseSpec deleteAutoprimaryRequestCreation(@Nonnull final String serverId, @Nonnull final String ip, @Nonnull final String nameserver) throws WebClientResponseException {
		Object postBody = null;
		// verify the required parameter 'serverId' is set
		if (serverId == null) {
			throw new WebClientResponseException("Missing the required parameter 'serverId' when calling deleteAutoprimary", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'ip' is set
		if (ip == null) {
			throw new WebClientResponseException("Missing the required parameter 'ip' when calling deleteAutoprimary", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'nameserver' is set
		if (nameserver == null) {
			throw new WebClientResponseException("Missing the required parameter 'nameserver' when calling deleteAutoprimary", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// create path and map variables
		final Map<String, Object> pathParams = new HashMap<>();

		pathParams.put("server_id", serverId);
		pathParams.put("ip", ip);
		pathParams.put("nameserver", nameserver);

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
		return apiClient.invokeAPI("/servers/{server_id}/autoprimaries/{ip}/{nameserver}", HttpMethod.DELETE, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
	}

	/**
	 * Delete the autoprimary entry
	 * 
	 * <p>
	 * <b>204</b> - OK, key was deleted
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId   The id of the server to delete the autoprimary from
	 * @param ip         IP address of autoprimary
	 * @param nameserver DNS name of the autoprimary
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<Void> deleteAutoprimary(@Nonnull final String serverId, @Nonnull final String ip, @Nonnull final String nameserver) throws WebClientResponseException {
		ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return deleteAutoprimaryRequestCreation(serverId, ip, nameserver).bodyToMono(localVarReturnType);
	}

	/**
	 * Delete the autoprimary entry
	 * 
	 * <p>
	 * <b>204</b> - OK, key was deleted
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId   The id of the server to delete the autoprimary from
	 * @param ip         IP address of autoprimary
	 * @param nameserver DNS name of the autoprimary
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<ResponseEntity<Void>> deleteAutoprimaryWithHttpInfo(@Nonnull final String serverId, @Nonnull final String ip, @Nonnull final String nameserver) throws WebClientResponseException {
		ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return deleteAutoprimaryRequestCreation(serverId, ip, nameserver).toEntity(localVarReturnType);
	}

	/**
	 * Delete the autoprimary entry
	 * 
	 * <p>
	 * <b>204</b> - OK, key was deleted
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId   The id of the server to delete the autoprimary from
	 * @param ip         IP address of autoprimary
	 * @param nameserver DNS name of the autoprimary
	 * @return ResponseSpec
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public ResponseSpec deleteAutoprimaryWithResponseSpec(@Nonnull final String serverId, @Nonnull final String ip, @Nonnull final String nameserver) throws WebClientResponseException {
		return deleteAutoprimaryRequestCreation(serverId, ip, nameserver);
	}

	/**
	 * Get a list of autoprimaries
	 * 
	 * <p>
	 * <b>200</b> - OK.
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId The id of the server to manage the list of autoprimaries on
	 * @return Autoprimary
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	private ResponseSpec getAutoprimariesRequestCreation(@Nonnull final String serverId) throws WebClientResponseException {
		Object postBody = null;
		// verify the required parameter 'serverId' is set
		if (serverId == null) {
			throw new WebClientResponseException("Missing the required parameter 'serverId' when calling getAutoprimaries", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

		ParameterizedTypeReference<Autoprimary> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return apiClient.invokeAPI("/servers/{server_id}/autoprimaries", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
	}

	/**
	 * Get a list of autoprimaries
	 * 
	 * <p>
	 * <b>200</b> - OK.
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId The id of the server to manage the list of autoprimaries on
	 * @return Autoprimary
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<Autoprimary> getAutoprimaries(@Nonnull final String serverId) throws WebClientResponseException {
		ParameterizedTypeReference<Autoprimary> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return getAutoprimariesRequestCreation(serverId).bodyToMono(localVarReturnType);
	}

	/**
	 * Get a list of autoprimaries
	 * 
	 * <p>
	 * <b>200</b> - OK.
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId The id of the server to manage the list of autoprimaries on
	 * @return ResponseEntity&lt;Autoprimary&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<ResponseEntity<Autoprimary>> getAutoprimariesWithHttpInfo(@Nonnull final String serverId) throws WebClientResponseException {
		ParameterizedTypeReference<Autoprimary> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return getAutoprimariesRequestCreation(serverId).toEntity(localVarReturnType);
	}

	/**
	 * Get a list of autoprimaries
	 * 
	 * <p>
	 * <b>200</b> - OK.
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId The id of the server to manage the list of autoprimaries on
	 * @return ResponseSpec
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public ResponseSpec getAutoprimariesWithResponseSpec(@Nonnull final String serverId) throws WebClientResponseException {
		return getAutoprimariesRequestCreation(serverId);
	}
}
