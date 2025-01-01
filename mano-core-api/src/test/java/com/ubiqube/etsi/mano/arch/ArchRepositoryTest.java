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
				.that().resideInAPackage("com.ubiqube.etsi.mano.service..").and().haveSimpleNameEndingWith("Service")
				.should().accessClassesThat().resideInAPackage("com.ubiqube.etsi.mano.jpa..")
				.andShould().haveSimpleNameNotEndingWith("RepositoryService")
				.because("Services should not access JPA package directly, except those in com.ubiqube.etsi.mano.vnfm.service.repository")
				.check(importedClasses);
	}

	@Test
	void repositoryServicesCanAccessJpaPackage() {
		ArchRuleDefinition.classes()
				.that().resideInAPackage("com.ubiqube.etsi.mano.service.repository..")
				.should().accessClassesThat().resideInAPackage("com.ubiqube.etsi.mano.jpa..")
				.check(importedClasses);
	}
}
