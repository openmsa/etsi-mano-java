/**
 *     Copyright (C) 2019-2024 Ubiqube.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import lombok.experimental.UtilityClass;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.dao.mano.Instance;
import com.ubiqube.etsi.mano.dao.mano.InstantiationState;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.dao.mano.NsdPackage;
import com.ubiqube.etsi.mano.dao.mano.OnboardingStateType;
import com.ubiqube.etsi.mano.dao.mano.PackageBase;
import com.ubiqube.etsi.mano.dao.mano.PackageOperationalState;
import com.ubiqube.etsi.mano.dao.mano.UsageStateEnum;
import com.ubiqube.etsi.mano.dao.mano.v2.Blueprint;
import com.ubiqube.etsi.mano.dao.mano.v2.OperationStatusType;
import com.ubiqube.etsi.mano.exception.ConflictException;
import com.ubiqube.etsi.mano.exception.GenericException;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

@UtilityClass
public final class Constants {

	private static final String THE_NSD_PACKAGE = "The NSD Package ";

	private static final String THE_VNF_INSTANCE = "The VNF Instance ";

	private static final String THE_VNF_PACKAGE = "The VNF Package ";

	public static final String HASH_ALGORITHM = "SHA-512";

	public static final String VNF_SEARCH_DEFAULT_EXCLUDE_FIELDS = "softwareImages,additionalArtifacts,userDefinedData,checksum";

	public static final Set<String> VNF_SEARCH_MANDATORY_FIELDS =
            Set.of("id", "onboardingState", "operationalState", "usageState", "_links.self.href",
                    "_links.vnfd.href", "_links.packageContent.href");

	public static final String VNFPMJOB_SEARCH_DEFAULT_EXCLUDE_FIELDS = "reports";
	@Nonnull
	public static final Set<String> VNFPMJOB_SEARCH_MANDATORY_FIELDS =
            Set.of("id", "criteria.collectionPeriod", "criteria.reportingPeriod",
                    "objectInstanceIds");
	@Nonnull
	public static final Set<String> VNFTHR_SEARCH_MANDATORY_FIELDS = Set.of("id");

	// XXX:
	public static final String VNFTHR_SEARCH_DEFAULT_EXCLUDE_FIELDS = "";

	// XXX:
	public static final String ALARM_SEARCH_DEFAULT_EXCLUDE_FIELDS = "";
	@Nonnull
	public static final Set<String> ALARM_SEARCH_MANDATORY_FIELDS =
            Set.of("id", "managedObjectId", "rootCauseFaultyResource", "alarmRaisedTime",
                    "ackState", "perceivedSeverity", "eventTime", "eventType", "probableCause",
                    "isRootCause", "_links.self.href");
	@Nonnull
	public static final Set<String> VNFLCMOPOCC_SEARCH_MANDATORY_FIELDS =
            Set.of("id", "operationState", "stateEnteredTime", "isAutomaticInvocation",
                    "operationParams", "isCancelPending", "startTime", "vnfInstanceId",
                    "operation");
	@Nonnull
	public static final String VNFLCMOPOCC_SEARCH_DEFAULT_EXCLUDE_FIELDS = "error,resourceChanges,changedInfo,changedExtConnectivity";

	@Nonnull
	public static final Set<String> VNFLCM_SEARCH_MANDATORY_FIELDS =
            Set.of("id", "vnfProvider", "vnfProductName", "vnfSoftwareVersion", "vnfdVersion",
                    "instantiationState", "vnfdId");

	public static final String VNFLCM_SEARCH_DEFAULT_EXCLUDE_FIELDS = "error,resourceChanges,changedInfo,changedExtConnectivity";

	public static final String REPOSITORY_FILENAME_MANIFEST = "manifest";

	/**
	 * This file contain only descriptors.
	 */
	public static final String REPOSITORY_FILENAME_VNFD = "vnfd";

	/**
	 * This file contains the whole package.
	 */
	public static final String REPOSITORY_FILENAME_PACKAGE = "vnf-package";

	/**
	 * Path where artifacts are stored on onboarding.
	 */
	public static final String REPOSITORY_FOLDER_ARTIFACTS = "artifacts";

	/**
	 * Artifact archive name.
	 */
	public static final @NotNull String REPOSITORY_ZIP_ARTIFACT = "artifacts.zip";

	/**
	 * Uniq value around the cluster.
	 */
	public static final String CONF_CLUSTER_ID = "clusterId";

	public static final String MANO_VERSIONS = "mano-versions.json";

	@Nullable
	public static String getSingleField(@Nullable final MultiValueMap<String, String> bag, final String parameter) {
		if (null == bag) {
			return null;
		}
		final List<String> params = bag.get(parameter);
		if ((null == params) || params.isEmpty()) {
			return null;
		}
		if (params.size() > 1) {
			throw new GenericException("Parameter: " + parameter + " could not have multiple occurences.");
		}
		return params.getFirst();
	}

	public static void ensureDisabled(final PackageBase vnfPackage) {
		if (PackageOperationalState.DISABLED != vnfPackage.getOperationalState()) {
			throw new ConflictException(THE_VNF_PACKAGE + vnfPackage.getId() + " is ENABLED.");
		}
	}

	public static void ensureIsEnabled(final PackageBase vnfackage) {
		if (PackageOperationalState.ENABLED != vnfackage.getOperationalState()) {
			throw new ConflictException(THE_VNF_PACKAGE + vnfackage.getId() + " is not in ENABLED state.");
		}
	}

	public static void ensureNotInUse(final PackageBase vnfPackqge) {
		if (UsageStateEnum.NOT_IN_USE != vnfPackqge.getUsageState()) {
			throw new ConflictException(THE_VNF_PACKAGE + vnfPackqge.getId() + " is Not In Use State.");
		}
	}

	public static void ensureIsOnboarded(final PackageBase vnfPackqge) {
		if (OnboardingStateType.ONBOARDED != vnfPackqge.getOnboardingState()) {
			throw new ConflictException(THE_VNF_PACKAGE + vnfPackqge.getId() + " is not in ONBOARDED state.");
		}
	}

	public static void ensureNotOnboarded(final PackageBase vnfPackage) {
		if (OnboardingStateType.ONBOARDED == vnfPackage.getOnboardingState()) {
			throw new ConflictException(THE_VNF_PACKAGE + vnfPackage.getId() + " is already ONBOARDED");
		}
	}

	public static void ensureNotProcessing(final PackageBase vnfPackage) {
		if (OnboardingStateType.PROCESSING == vnfPackage.getOnboardingState()) {
			throw new ConflictException(THE_VNF_PACKAGE + vnfPackage.getId() + " is already PROCESSING");
		}
	}

	public static void ensureInstantiated(final Instance vnfInstance) {
		if (InstantiationState.INSTANTIATED != vnfInstance.getInstantiationState()) {
			throw new ConflictException(THE_VNF_INSTANCE + vnfInstance.getId() + " is not in INSTANTIATED state.");
		}
	}

	public static void ensureNotInstantiated(final Instance vnfInstance) {
		if (InstantiationState.INSTANTIATED == vnfInstance.getInstantiationState()) {
			throw new ConflictException(THE_VNF_INSTANCE + vnfInstance.getId() + " is already in INSTANTIATED state.");
		}
	}

	public static void ensureNotInUse(final NsdPackage nsd) {
		if (UsageStateEnum.NOT_IN_USE != nsd.getNsdUsageState()) {
			throw new ConflictException(THE_NSD_PACKAGE + nsd.getId() + " Should be in NOT_IN_USE state.");
		}
	}

	public static void ensureDisabled(final NsdPackage nsd) {
		if (PackageOperationalState.DISABLED != nsd.getNsdOperationalState()) {
			throw new ConflictException(THE_NSD_PACKAGE + nsd.getId() + " Should be in disabled state.");
		}
	}

	public static void ensureIsEnabled(final NsdPackage nsd) {
		if (PackageOperationalState.ENABLED != nsd.getNsdOperationalState()) {
			throw new ConflictException(THE_NSD_PACKAGE + nsd.getId() + " is not in ENABLED state.");
		}
	}

	public static void ensureIsOnboarded(final NsdPackage nsd) {
		if (OnboardingStateType.ONBOARDED != nsd.getNsdOnboardingState()) {
			throw new ConflictException(THE_NSD_PACKAGE + nsd.getId() + " is not in ONBOARDED state.");
		}
	}

	public static void ensureNotOnboarded(final NsdPackage nsd) {
		if (OnboardingStateType.ONBOARDED == nsd.getNsdOnboardingState()) {
			throw new ConflictException(THE_NSD_PACKAGE + nsd.getId() + "is already ONBOARDED state.");
		}
	}

	public static void ensureNotInstantiated(final NsdInstance nsInstance) {
		if (InstantiationState.INSTANTIATED == nsInstance.getInstantiationState()) {
			throw new ConflictException("The Ns instance " + nsInstance.getId() + " is instantiated.");
		}
	}

	public static void ensureInstantiated(final NsdInstance nsInstance) {
		if (InstantiationState.INSTANTIATED != nsInstance.getInstantiationState()) {
			throw new ConflictException("The Ns Instance " + nsInstance.getId() + " is not instantiated.");
		}
	}

	public static void ensureNotLocked(final Instance vnfInstance) {
		if (vnfInstance.getLockedBy() != null) {
			throw new ConflictException(THE_VNF_INSTANCE + vnfInstance.getId() + " is locked by LCMopOcc: " + vnfInstance.getLockedBy() + ".");
		}
	}

	public static void ensureLockedByMyself(final Instance vnfInstance, final UUID lcmOpOccsId) {
		if ((vnfInstance.getLockedBy() != null) && vnfInstance.getLockedBy().equals(lcmOpOccsId)) {
			throw new ConflictException(THE_VNF_INSTANCE + vnfInstance.getId() + " is locked by LCMopOcc: " + vnfInstance.getLockedBy() + ".");
		}
	}

	public static void ensureFailedTemp(final Blueprint<?, ?> vnfInstance) {
		if (vnfInstance.getOperationStatus() != OperationStatusType.FAILED_TEMP) {
			throw new ConflictException("The LCM OP OCCS  " + vnfInstance.getId() + " must be in FAILED_TEMP status.");
		}
	}

	public static UUID getSafeUUID(final String uuid) {
		return Objects.requireNonNull(UUID.fromString(uuid));
	}
}
