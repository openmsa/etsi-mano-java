/**
 *     Copyright (C) 2019-2020 Ubiqube.
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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.em.v281.controller.vnfpm;

import static com.ubiqube.etsi.mano.uri.ManoWebMvcLinkBuilder.linkTo;
import static com.ubiqube.etsi.mano.uri.ManoWebMvcLinkBuilder.methodOn;

import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.em.v281.model.vnfind.CreatePmJobRequest;
import com.ubiqube.etsi.mano.em.v281.model.vnfind.PerformanceReport;
import com.ubiqube.etsi.mano.em.v281.model.vnfind.PmJob;
import com.ubiqube.etsi.mano.em.v281.model.vnfind.PmJobLinks;
import com.ubiqube.etsi.mano.em.v281.model.vnfind.PmJobModifications;
import com.ubiqube.etsi.mano.em.v281.model.vnflcm.Link;
import com.ubiqube.etsi.mano.vnfm.fc.vnfpm.VnfmPmGenericFrontController;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@RestController
public class PmJobs281Sol002Controller implements PmJobs281Sol002Api {
	private final VnfmPmGenericFrontController vnfmPmGenericFrontController;

	public PmJobs281Sol002Controller(final VnfmPmGenericFrontController vnfmPmGenericFrontController) {
		super();
		this.vnfmPmGenericFrontController = vnfmPmGenericFrontController;
	}

	@Override
	public ResponseEntity<String> pmJobsGet(final MultiValueMap<String, String> requestParams, @Valid final String nextpageOpaqueMarker) {
		return vnfmPmGenericFrontController.search(requestParams, PmJob.class, PmJobs281Sol002Controller::makeLinks);
	}

	@Override
	public ResponseEntity<Void> pmJobsPmJobIdDelete(final String pmJobId) {
		return vnfmPmGenericFrontController.deleteById(UUID.fromString(pmJobId));
	}

	@Override
	public ResponseEntity<PmJob> pmJobsPmJobIdGet(final String pmJobId) {
		return vnfmPmGenericFrontController.findById(UUID.fromString(pmJobId), PmJob.class, PmJobs281Sol002Controller::makeLinks);
	}

	@Override
	public ResponseEntity<PerformanceReport> pmJobsPmJobIdReportsReportIdGet(final String pmJobId, final String reportId) {
		return vnfmPmGenericFrontController.findReportById(pmJobId, reportId, PerformanceReport.class);
	}

	@Override
	public ResponseEntity<PmJob> pmJobsPost(@Valid final CreatePmJobRequest createPmJobRequest) {
		return vnfmPmGenericFrontController.pmJobsPost(createPmJobRequest, PmJob.class, PmJobs281Sol002Controller::makeLinks, PmJobs281Sol002Controller::makeSelf);
	}

	@Override
	public ResponseEntity<PmJobModifications> pmJobsPmJobIdPatch(final String pmJobId, @Valid final PmJobModifications pmJobModifications, final String ifMatch) {
		return vnfmPmGenericFrontController.pmJobsPmJobIdPatch(UUID.fromString(pmJobId), pmJobModifications);
	}

	private static String makeSelf(final PmJob x) {
		return linkTo(methodOn(PmJobs281Sol002Api.class).pmJobsPmJobIdGet(x.getId())).withSelfRel().getHref();
	}

	private static void makeLinks(final PmJob x) {
		final PmJobLinks links = new PmJobLinks();
		Link link = new Link();
		link.setHref(linkTo(methodOn(PmJobs281Sol002Api.class).pmJobsPmJobIdGet(x.getId())).withSelfRel().getHref());
		links.setSelf(link);

		link = new Link();
		link.setHref("");
		// links.setObjects(link);

		x.setLinks(links);
	}

}
