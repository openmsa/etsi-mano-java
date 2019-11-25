package com.ubiqube.etsi.mano.factory;

import java.time.OffsetDateTime;
import java.util.Map;

import com.ubiqube.etsi.mano.Constants;
import com.ubiqube.etsi.mano.controller.vnf.Linkable;
import com.ubiqube.etsi.mano.model.vnf.sol005.Checksum;
import com.ubiqube.etsi.mano.model.vnf.sol005.PackageOnboardingStateType;
import com.ubiqube.etsi.mano.model.vnf.sol005.PackageOperationalStateType;
import com.ubiqube.etsi.mano.model.vnf.sol005.PackageUsageStateType;
import com.ubiqube.etsi.mano.model.vnf.sol005.PkgmNotificationsFilter;
import com.ubiqube.etsi.mano.model.vnf.sol005.PkgmSubscription;
import com.ubiqube.etsi.mano.model.vnf.sol005.SubscriptionObject;
import com.ubiqube.etsi.mano.model.vnf.sol005.VnfPackageArtifactInfo;
import com.ubiqube.etsi.mano.model.vnf.sol005.VnfPkgInfo;
import com.ubiqube.etsi.mano.model.vnf.sol005.notification.PackageChangeType;
import com.ubiqube.etsi.mano.model.vnf.sol005.notification.VnfPackageChangeNotification;
import com.ubiqube.etsi.mano.model.vnf.sol005.notification.VnfPackageOnboardingNotification;

public class VnfPackageFactory {
	private VnfPackageFactory() {
		// Nothing.
	}

	public static VnfPkgInfo createVnfPkgInfo(final Map<String, Object> userData) {
		final VnfPkgInfo vnfPkgInfo = new VnfPkgInfo();
		vnfPkgInfo.setOnboardingState(PackageOnboardingStateType.CREATED);
		vnfPkgInfo.setUserDefinedData(userData);
		vnfPkgInfo.setOperationalState(PackageOperationalStateType.DISABLED);
		vnfPkgInfo.setUsageState(PackageUsageStateType.NOT_IN_USE);

		return vnfPkgInfo;
	}

	public static VnfPackageArtifactInfo createArtefact(final String _filename, final String _checksum) {
		final VnfPackageArtifactInfo artefact = new VnfPackageArtifactInfo();
		artefact.artifactPath(_filename);
		final Checksum checksum = new Checksum();
		checksum.algorithm(Constants.HASH_ALGORITHM);
		checksum.setHash(_checksum);
		artefact.setChecksum(checksum);
		return artefact;
	}

	public static VnfPackageArtifactInfo createArtefact(final String _filename, final Checksum _checksum) {
		final VnfPackageArtifactInfo artefact = new VnfPackageArtifactInfo();
		artefact.artifactPath(_filename);
		artefact.setChecksum(_checksum);
		return artefact;
	}

	public static VnfPackageChangeNotification createVnfPackageChangeNotification(final String _subscriptionId, final String _vnfPkgId, final String _vnfdId, final Linkable links) {
		final VnfPackageChangeNotification obj = createVnfPackageChangeNotificationVnfPackageChangeNotification(_subscriptionId, _vnfPkgId, _vnfdId, links);
		return obj;
	}

	public static VnfPackageChangeNotification createVnfPackageChangeNotificationVnfPackageChangeNotification(final String _subscriptionId, final String _vnfPkgId, final String _vnfdId, final Linkable links) {
		final VnfPackageChangeNotification ret = new VnfPackageChangeNotification();
		ret.setChangeType(PackageChangeType.OP_STATE_CHANGE);
		ret.setNotificationType("VnfPackageChangeNotification");
		ret.setOperationalState(com.ubiqube.etsi.mano.model.vnf.sol005.notification.PackageOperationalStateType.DISABLED);
		ret.setSubscriptionId(_subscriptionId);
		ret.setTimeStamp(OffsetDateTime.now());
		ret.setVnfdId(_vnfdId);
		ret.setVnfPkgId(_vnfPkgId);
		ret.setLinks(links.createNotificationLink(_vnfPkgId, _subscriptionId));
		return ret;
	}

	public static VnfPackageOnboardingNotification createNotificationVnfPackageOnboardingNotification(final String _subscriptionId, final String _vnfPkgId, final String _vnfdId, final Linkable links) {
		final VnfPackageOnboardingNotification ret = new VnfPackageOnboardingNotification();
		ret.setTimeStamp(OffsetDateTime.now());
		ret.setNotificationType("VnfPackageOnboardingNotification");
		ret.setSubscriptionId(_subscriptionId);
		ret.setVnfPkgId(_vnfPkgId);
		ret.setVnfdId(_vnfdId);
		ret.setLinks(links.createVnfPackageOnboardingNotificationLinks(_vnfPkgId, _subscriptionId));
		return ret;
	}

	public static PkgmSubscription createSubscriptionsPkgmSubscription(final String _callbackUri, final PkgmNotificationsFilter _filter) {
		final PkgmSubscription subscriptionsPkgmSubscription = new PkgmSubscription();
		subscriptionsPkgmSubscription.setCallbackUri(_callbackUri);
		subscriptionsPkgmSubscription.setFilter(_filter);
		return subscriptionsPkgmSubscription;
	}

	public static PkgmSubscription createSubscriptionsPkgmSubscription(final SubscriptionObject subscriptionObject) {
		final PkgmSubscription ret = new PkgmSubscription();
		ret.setCallbackUri(subscriptionObject.getPkgmSubscription().getCallbackUri());
		ret.setId(subscriptionObject.getPkgmSubscription().getId());
		ret.setFilter(subscriptionObject.getPkgmSubscription().getFilter());
		return ret;
	}

}
