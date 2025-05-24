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

import jakarta.annotation.Nonnull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-05-23T10:34:53.990305454+02:00[Europe/Paris]", comments = "Generator version: 7.13.0")
public class StatsApi {
	private ApiClient apiClient;

	public StatsApi() {
		this(new ApiClient());
	}

	@Autowired
	public StatsApi(final ApiClient apiClient) {
		this.apiClient = apiClient;
	}

	public ApiClient getApiClient() {
		return apiClient;
	}

	public void setApiClient(final ApiClient apiClient) {
		this.apiClient = apiClient;
	}

	/**
	 * Query statistics. Query PowerDNS internal statistics.
	 * <p>
	 * <b>200</b> - List of Statistic Items
	 * <p>
	 * <b>422</b> - Returned when a non-existing statistic name has been requested.
	 * Contains an error message
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId     The id of the server to retrieve
	 * @param statistic    When set to the name of a specific statistic, only this
	 *                     value is returned. If no statistic with that name exists,
	 *                     the response has a 422 status and an error message.
	 * @param includerings “true” (default) or “false”, whether to include the Ring
	 *                     items, which can contain thousands of log messages or
	 *                     queried domains. Setting this to ”false” may make the
	 *                     response a lot smaller.
	 * @return List&lt;Object&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	private ResponseSpec getStatsRequestCreation(@Nonnull final String serverId, @Nullable final String statistic, @Nullable final Boolean includerings) throws WebClientResponseException {
		Object postBody = null;
		// verify the required parameter 'serverId' is set
		if (serverId == null) {
			throw new WebClientResponseException("Missing the required parameter 'serverId' when calling getStats", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// create path and map variables
		final Map<String, Object> pathParams = new HashMap<>();

		pathParams.put("server_id", serverId);

		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		final HttpHeaders headerParams = new HttpHeaders();
		final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
		final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

		queryParams.putAll(apiClient.parameterToMultiValueMap(null, "statistic", statistic));
		queryParams.putAll(apiClient.parameterToMultiValueMap(null, "includerings", includerings));

		final String[] localVarAccepts = {
				"application/json"
		};
		final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
		final String[] localVarContentTypes = {};
		final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

		String[] localVarAuthNames = { "APIKeyHeader" };

		ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return apiClient.invokeAPI("/servers/{server_id}/statistics", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
	}

	/**
	 * Query statistics. Query PowerDNS internal statistics.
	 * <p>
	 * <b>200</b> - List of Statistic Items
	 * <p>
	 * <b>422</b> - Returned when a non-existing statistic name has been requested.
	 * Contains an error message
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId     The id of the server to retrieve
	 * @param statistic    When set to the name of a specific statistic, only this
	 *                     value is returned. If no statistic with that name exists,
	 *                     the response has a 422 status and an error message.
	 * @param includerings “true” (default) or “false”, whether to include the Ring
	 *                     items, which can contain thousands of log messages or
	 *                     queried domains. Setting this to ”false” may make the
	 *                     response a lot smaller.
	 * @return List&lt;Object&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Flux<Object> getStats(@Nonnull final String serverId, @Nullable final String statistic, @Nullable final Boolean includerings) throws WebClientResponseException {
		ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return getStatsRequestCreation(serverId, statistic, includerings).bodyToFlux(localVarReturnType);
	}

	/**
	 * Query statistics. Query PowerDNS internal statistics.
	 * <p>
	 * <b>200</b> - List of Statistic Items
	 * <p>
	 * <b>422</b> - Returned when a non-existing statistic name has been requested.
	 * Contains an error message
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId     The id of the server to retrieve
	 * @param statistic    When set to the name of a specific statistic, only this
	 *                     value is returned. If no statistic with that name exists,
	 *                     the response has a 422 status and an error message.
	 * @param includerings “true” (default) or “false”, whether to include the Ring
	 *                     items, which can contain thousands of log messages or
	 *                     queried domains. Setting this to ”false” may make the
	 *                     response a lot smaller.
	 * @return ResponseEntity&lt;List&lt;Object&gt;&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<ResponseEntity<List<Object>>> getStatsWithHttpInfo(@Nonnull final String serverId, @Nullable final String statistic, @Nullable final Boolean includerings) throws WebClientResponseException {
		ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return getStatsRequestCreation(serverId, statistic, includerings).toEntityList(localVarReturnType);
	}

	/**
	 * Query statistics. Query PowerDNS internal statistics.
	 * <p>
	 * <b>200</b> - List of Statistic Items
	 * <p>
	 * <b>422</b> - Returned when a non-existing statistic name has been requested.
	 * Contains an error message
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId     The id of the server to retrieve
	 * @param statistic    When set to the name of a specific statistic, only this
	 *                     value is returned. If no statistic with that name exists,
	 *                     the response has a 422 status and an error message.
	 * @param includerings “true” (default) or “false”, whether to include the Ring
	 *                     items, which can contain thousands of log messages or
	 *                     queried domains. Setting this to ”false” may make the
	 *                     response a lot smaller.
	 * @return ResponseSpec
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public ResponseSpec getStatsWithResponseSpec(@Nonnull final String serverId, @Nullable final String statistic, @Nullable final Boolean includerings) throws WebClientResponseException {
		return getStatsRequestCreation(serverId, statistic, includerings);
	}
}
