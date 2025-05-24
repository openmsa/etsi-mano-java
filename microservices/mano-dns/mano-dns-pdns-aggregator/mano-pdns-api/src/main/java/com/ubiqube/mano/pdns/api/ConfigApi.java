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
import com.ubiqube.mano.pdns.model.ConfigSetting;

import jakarta.annotation.Nonnull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-05-23T10:34:53.990305454+02:00[Europe/Paris]", comments = "Generator version: 7.13.0")
public class ConfigApi {
	private ApiClient apiClient;

	public ConfigApi() {
		this(new ApiClient());
	}

	@Autowired
	public ConfigApi(final ApiClient apiClient) {
		this.apiClient = apiClient;
	}

	public ApiClient getApiClient() {
		return apiClient;
	}

	public void setApiClient(final ApiClient apiClient) {
		this.apiClient = apiClient;
	}

	/**
	 * Returns all ConfigSettings for a single server
	 * 
	 * <p>
	 * <b>200</b> - List of config values
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
	 * @return List&lt;ConfigSetting&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	private ResponseSpec getConfigRequestCreation(@Nonnull final String serverId) throws WebClientResponseException {
		Object postBody = null;
		// verify the required parameter 'serverId' is set
		if (serverId == null) {
			throw new WebClientResponseException("Missing the required parameter 'serverId' when calling getConfig", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

		ParameterizedTypeReference<ConfigSetting> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return apiClient.invokeAPI("/servers/{server_id}/config", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
	}

	/**
	 * Returns all ConfigSettings for a single server
	 * 
	 * <p>
	 * <b>200</b> - List of config values
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
	 * @return List&lt;ConfigSetting&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Flux<ConfigSetting> getConfig(@Nonnull final String serverId) throws WebClientResponseException {
		ParameterizedTypeReference<ConfigSetting> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return getConfigRequestCreation(serverId).bodyToFlux(localVarReturnType);
	}

	/**
	 * Returns all ConfigSettings for a single server
	 * 
	 * <p>
	 * <b>200</b> - List of config values
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
	 * @return ResponseEntity&lt;List&lt;ConfigSetting&gt;&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<ResponseEntity<List<ConfigSetting>>> getConfigWithHttpInfo(@Nonnull final String serverId) throws WebClientResponseException {
		ParameterizedTypeReference<ConfigSetting> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return getConfigRequestCreation(serverId).toEntityList(localVarReturnType);
	}

	/**
	 * Returns all ConfigSettings for a single server
	 * 
	 * <p>
	 * <b>200</b> - List of config values
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
	public ResponseSpec getConfigWithResponseSpec(@Nonnull final String serverId) throws WebClientResponseException {
		return getConfigRequestCreation(serverId);
	}

	/**
	 * Returns a specific ConfigSetting for a single server NOT IMPLEMENTED
	 * <p>
	 * <b>200</b> - List of config values
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId          The id of the server to retrieve
	 * @param configSettingName The name of the setting to retrieve
	 * @return ConfigSetting
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	private ResponseSpec getConfigSettingRequestCreation(@Nonnull final String serverId, @Nonnull final String configSettingName) throws WebClientResponseException {
		Object postBody = null;
		// verify the required parameter 'serverId' is set
		if (serverId == null) {
			throw new WebClientResponseException("Missing the required parameter 'serverId' when calling getConfigSetting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// verify the required parameter 'configSettingName' is set
		if (configSettingName == null) {
			throw new WebClientResponseException("Missing the required parameter 'configSettingName' when calling getConfigSetting", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
		}
		// create path and map variables
		final Map<String, Object> pathParams = new HashMap<>();

		pathParams.put("server_id", serverId);
		pathParams.put("config_setting_name", configSettingName);

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

		ParameterizedTypeReference<ConfigSetting> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return apiClient.invokeAPI("/servers/{server_id}/config/{config_setting_name}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
	}

	/**
	 * Returns a specific ConfigSetting for a single server NOT IMPLEMENTED
	 * <p>
	 * <b>200</b> - List of config values
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId          The id of the server to retrieve
	 * @param configSettingName The name of the setting to retrieve
	 * @return ConfigSetting
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<ConfigSetting> getConfigSetting(@Nonnull final String serverId, @Nonnull final String configSettingName) throws WebClientResponseException {
		ParameterizedTypeReference<ConfigSetting> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return getConfigSettingRequestCreation(serverId, configSettingName).bodyToMono(localVarReturnType);
	}

	/**
	 * Returns a specific ConfigSetting for a single server NOT IMPLEMENTED
	 * <p>
	 * <b>200</b> - List of config values
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId          The id of the server to retrieve
	 * @param configSettingName The name of the setting to retrieve
	 * @return ResponseEntity&lt;ConfigSetting&gt;
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public Mono<ResponseEntity<ConfigSetting>> getConfigSettingWithHttpInfo(@Nonnull final String serverId, @Nonnull final String configSettingName) throws WebClientResponseException {
		ParameterizedTypeReference<ConfigSetting> localVarReturnType = new ParameterizedTypeReference<>() {
		};
		return getConfigSettingRequestCreation(serverId, configSettingName).toEntity(localVarReturnType);
	}

	/**
	 * Returns a specific ConfigSetting for a single server NOT IMPLEMENTED
	 * <p>
	 * <b>200</b> - List of config values
	 * <p>
	 * <b>400</b> - The supplied request was not valid
	 * <p>
	 * <b>404</b> - Requested item was not found
	 * <p>
	 * <b>422</b> - The input to the operation was not valid
	 * <p>
	 * <b>500</b> - Internal server error
	 *
	 * @param serverId          The id of the server to retrieve
	 * @param configSettingName The name of the setting to retrieve
	 * @return ResponseSpec
	 * @throws WebClientResponseException if an error occurs while attempting to
	 *                                    invoke the API
	 */
	public ResponseSpec getConfigSettingWithResponseSpec(@Nonnull final String serverId, @Nonnull final String configSettingName) throws WebClientResponseException {
		return getConfigSettingRequestCreation(serverId, configSettingName);
	}
}
