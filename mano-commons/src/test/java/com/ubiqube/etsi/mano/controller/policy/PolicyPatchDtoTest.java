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
package com.ubiqube.etsi.mano.controller.policy;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class PolicyPatchDtoTest {

	@Test
	void test() {
		final PolicyPatchDto p = new PolicyPatchDto();
		p.setActivationStatus(null);
		p.setAddAssociations(null);
		p.setRemoveAllAssociations(false);
		p.setRemoveAssociations(null);
		p.setSelectedVersion(null);
		p.getActivationStatus();
		p.getAddAssociations();
		p.getRemoveAssociations();
		p.getSelectedVersion();
		assertNotNull(p.toString());
	}

}
