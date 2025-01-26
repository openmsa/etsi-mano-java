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
package com.ubiqube.etsi.mano.arch;

import org.junit.jupiter.api.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

class ArchRepositoryTest {
	private static final JavaClasses importedClasses = new ClassFileImporter()
			.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS).importPackages("com.ubiqube.etsi.mano");

	@Test
	void servicesShouldNotAccessJpaPackage() {

		ArchRuleDefinition.noClasses()
				.that().resideInAPackage("com.ubiqube.etsi.mano.nfvo.service..").and().haveSimpleNameEndingWith("Service")
				.should().accessClassesThat().resideInAPackage("com.ubiqube.etsi.mano.nfvo.jpa..")
				.andShould().haveSimpleNameNotEndingWith("RepositoryService")
				.because("Services should not access JPA package directly, except those in com.ubiqube.etsi.mano.vnfm.service.repository")
				.check(importedClasses);
	}

	// @Test
	void repositoryServicesCanAccessJpaPackage() {
		ArchRuleDefinition.classes()
				.that().resideInAPackage("com.ubiqube.etsi.mano.nfvo.service.repository..")
				.should().accessClassesThat().resideInAPackage("com.ubiqube.etsi.mano.nfvo.jpa..")
				.check(importedClasses);
	}

}
