package com.ubiqube.etsi.mano.model.vnf.sol005;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubiqube.etsi.mano.controller.vnf.ApiTypesEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "This type represents a subscription related to notifications about VNF package management. This one is stored in the repository")
public class SubscriptionObject {

	@ApiModelProperty(value = "")
	@Valid
	private SubscriptionAuthentication subscriptionsAuthentication = null;

	@ApiModelProperty(value = "")
	@Valid
	private PkgmSubscription pkgmSubscription = null;

	private ApiTypesEnum api;

	public SubscriptionObject() {
		// Nothing.
	}

	public SubscriptionObject(final SubscriptionAuthentication _subscriptionAuthentication, final PkgmSubscription _PkgmSubscription) {
		super();
		subscriptionsAuthentication = _subscriptionAuthentication;
		pkgmSubscription = _PkgmSubscription;
	}

	@JsonProperty("auth")
	public SubscriptionAuthentication getSubscriptionAuthentication() {
		return subscriptionsAuthentication;
	}

	public void setSubscriptionAuthentication(final SubscriptionAuthentication _subscriptionsPkgmSubscriptionRequestAuthentication) {
		subscriptionsAuthentication = _subscriptionsPkgmSubscriptionRequestAuthentication;
	}

	@JsonProperty("subscriptionsPkgmSubscription")
	public PkgmSubscription getPkgmSubscription() {
		return pkgmSubscription;
	}

	public void setSubscriptionsPkgmSubscription(final PkgmSubscription _subscriptionsPkgmSubscription) {
		pkgmSubscription = _subscriptionsPkgmSubscription;
	}

	@JsonProperty("api")
	public ApiTypesEnum getApi() {
		return api;
	}

	public void setApi(final ApiTypesEnum api) {
		this.api = api;
	}

}
