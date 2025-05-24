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
import com.ubiqube.mano.pdns.model.SearchResult;

import jakarta.annotation.Nonnull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-05-23T10:34:53.990305454+02:00[Europe/Paris]", comments = "Generator version: 7.13.0")
public class SearchApi {
	private ApiClient apiClient;

	public SearchApi() {
		this(new ApiClient());
	}

	@Autowired
	public SearchApi(final ApiClient apiClient) {
		this.apiClient = apiClient;
	}

	public ApiClient getApiClient() {
		return apiClient;
	}

	public void setApiClient(final ApiClient apiClient) {
		this.apiClient = apiClient;
	}

	/**
	 * Search the data inside PowerDNS Search the data inside PowerDNS for
	 * search_term and return at most max_results. This includes zones, records and
	 * comments. The * character can be used in search_term as a wildcard character
	 * and the ? character can be used as a wildcard for a single character.
	 * <p>
	 * <b>200</b> - Returns a JSON array with results
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
	 * @param q          The string to search for
	 * @param max        Maximum number of entries to return
	 * @param objectType Type of data to search for, one of “all”, “zone”, “record”,
	 *                   “comment”
	 * @return List&lt;SearchResult&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	private ResponseSpec searchDataRequestCreation(@Nonnull final String serverId, @Nonnull final String q, @Nonnull final Integer max, @Nullable final String objectType) throws WebClientResponseException {
		Object postBody = null;
		// verify the required parameter 'serverId' is set
		if (serverId == null) {
			throw new WebClientResponseException("Missing the required parameter 'serverId' when calling searchData", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'q' is set
		if (q == null) {
			throw new WebClientResponseException("Missing the required parameter 'q' when calling searchData", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'max' is set
		if (max == null) {
			throw new WebClientResponseException("Missing the required parameter 'max' when calling searchData", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// create path and map variables
		final Map<String, Object> pathParams = new HashMap<>();

		pathParams.put("server_id", serverId);

		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		final HttpHeaders headerParams = new HttpHeaders();
		final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
		final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

		queryParams.putAll(apiClient.parameterToMultiValueMap(null, "q", q));
		queryParams.putAll(apiClient.parameterToMultiValueMap(null, "max", max));
		queryParams.putAll(apiClient.parameterToMultiValueMap(null, "object_type", objectType));

		final String[] localVarAccepts = {
				"application/json"
		};
		final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
		final String[] localVarContentTypes = {};
		final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

		String[] localVarAuthNames = { "APIKeyHeader" };

		ParameterizedTypeReference<SearchResult> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return apiClient.invokeAPI("/servers/{server_id}/search-data", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
	}

	/**
	 * Search the data inside PowerDNS Search the data inside PowerDNS for
	 * search_term and return at most max_results. This includes zones, records and
	 * comments. The * character can be used in search_term as a wildcard character
	 * and the ? character can be used as a wildcard for a single character.
	 * <p>
	 * <b>200</b> - Returns a JSON array with results
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
	 * @param q          The string to search for
	 * @param max        Maximum number of entries to return
	 * @param objectType Type of data to search for, one of “all”, “zone”, “record”,
	 *                   “comment”
	 * @return List&lt;SearchResult&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Flux<SearchResult> searchData(@Nonnull final String serverId, @Nonnull final String q, @Nonnull final Integer max, @Nullable final String objectType) throws WebClientResponseException {
		ParameterizedTypeReference<SearchResult> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return searchDataRequestCreation(serverId, q, max, objectType).bodyToFlux(localVarReturnType);
	}

	/**
	 * Search the data inside PowerDNS Search the data inside PowerDNS for
	 * search_term and return at most max_results. This includes zones, records and
	 * comments. The * character can be used in search_term as a wildcard character
	 * and the ? character can be used as a wildcard for a single character.
	 * <p>
	 * <b>200</b> - Returns a JSON array with results
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
	 * @param q          The string to search for
	 * @param max        Maximum number of entries to return
	 * @param objectType Type of data to search for, one of “all”, “zone”, “record”,
	 *                   “comment”
	 * @return ResponseEntity&lt;List&lt;SearchResult&gt;&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<ResponseEntity<List<SearchResult>>> searchDataWithHttpInfo(@Nonnull final String serverId, @Nonnull final String q, @Nonnull final Integer max, @Nullable final String objectType) throws WebClientResponseException {
		ParameterizedTypeReference<SearchResult> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return searchDataRequestCreation(serverId, q, max, objectType).toEntityList(localVarReturnType);
	}

	/**
	 * Search the data inside PowerDNS Search the data inside PowerDNS for
	 * search_term and return at most max_results. This includes zones, records and
	 * comments. The * character can be used in search_term as a wildcard character
	 * and the ? character can be used as a wildcard for a single character.
	 * <p>
	 * <b>200</b> - Returns a JSON array with results
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
	 * @param q          The string to search for
	 * @param max        Maximum number of entries to return
	 * @param objectType Type of data to search for, one of “all”, “zone”, “record”,
	 *                   “comment”
	 * @return ResponseSpec
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public ResponseSpec searchDataWithResponseSpec(@Nonnull final String serverId, @Nonnull final String q, @Nonnull final Integer max, @Nullable final String objectType) throws WebClientResponseException {
		return searchDataRequestCreation(serverId, q, max, objectType);
	}
}
