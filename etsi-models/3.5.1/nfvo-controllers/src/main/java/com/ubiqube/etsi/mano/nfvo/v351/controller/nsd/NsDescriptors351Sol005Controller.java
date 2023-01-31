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
package com.ubiqube.etsi.mano.nfvo.v351.controller.nsd;

import static com.ubiqube.etsi.mano.uri.ManoWebMvcLinkBuilder.linkTo;
import static com.ubiqube.etsi.mano.uri.ManoWebMvcLinkBuilder.methodOn;

import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ubiqube.etsi.mano.controller.nsd.NsDescriptorGenericFrontController;
import com.ubiqube.etsi.mano.em.v351.model.lcmcoord.Link;
import com.ubiqube.etsi.mano.nfvo.v351.model.nsd.CreateNsdInfoRequest;
import com.ubiqube.etsi.mano.nfvo.v351.model.nsd.NsdInfo;
import com.ubiqube.etsi.mano.nfvo.v351.model.nsd.NsdInfoLinks;
import com.ubiqube.etsi.mano.nfvo.v351.model.nsd.NsdInfoModifications;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@RestController
public class NsDescriptors351Sol005Controller implements NsDescriptors351Sol005Api {
	private final NsDescriptorGenericFrontController nsDescriptorGenericFrontController;

	public NsDescriptors351Sol005Controller(final NsDescriptorGenericFrontController nsDescriptorGenericFrontController) {
		super();
		this.nsDescriptorGenericFrontController = nsDescriptorGenericFrontController;
	}

	@Override
	public ResponseEntity<String> nsDescriptorsGet(final MultiValueMap<String, String> requestParams) {
		return nsDescriptorGenericFrontController.search(requestParams, NsdInfo.class, NsDescriptors351Sol005Controller::makeLinks);
	}

	@Override
	public ResponseEntity<Void> nsDescriptorsNsdInfoIdDelete(final String nsdInfoId) {
		return nsDescriptorGenericFrontController.delete(nsdInfoId);
	}

	@Override
	public ResponseEntity<NsdInfo> nsDescriptorsNsdInfoIdGet(final String nsdInfoId) {
		return nsDescriptorGenericFrontController.finsById(nsdInfoId, NsdInfo.class, NsDescriptors351Sol005Controller::makeLinks);
	}

	@Override
	public ResponseEntity<Resource> nsDescriptorsNsdInfoIdNsdContentGet(final String nsdInfoId, final String accept, final String range) {
		return nsDescriptorGenericFrontController.getNsdContent(nsdInfoId, accept);
	}

	@Override
	public ResponseEntity<Void> nsDescriptorsNsdInfoIdNsdContentPut(final String nsdInfoId, final String accept, final MultipartFile file) {
		return nsDescriptorGenericFrontController.putNsdContent(nsdInfoId, accept, file);
	}

	@Override
	public ResponseEntity<NsdInfoModifications> nsDescriptorsNsdInfoIdPatch(final String nsdInfoId, @Valid final String body, final String ifMatch) {
		nsDescriptorGenericFrontController.modify(nsdInfoId, body, ifMatch, NsdInfo.class, NsDescriptors351Sol005Controller::makeLinks);
		final NsdInfoModifications modif = new NsdInfoModifications();
		return ResponseEntity.ok(modif);
	}

	@Override
	public ResponseEntity<NsdInfo> nsDescriptorsPost(@Valid final CreateNsdInfoRequest body) {
		return nsDescriptorGenericFrontController.create("", body.getUserDefinedData(), NsdInfo.class, NsDescriptors351Sol005Controller::makeLinks, NsDescriptors351Sol005Controller::makeSelfLink);
	}

	@Override
	public ResponseEntity<Resource> nsDescriptorsNsdInfoIdArtifactsArtifactPathGet(final String nsdInfoId, final String artifactPath, final String range, @Valid final String includeSignatures) {
		return nsDescriptorGenericFrontController.getArtifact(nsdInfoId, artifactPath, includeSignatures);
	}

	@Override
	public ResponseEntity<Resource> nsDescriptorsNsdInfoIdManifestGet(final String nsdInfoId, @Valid final String includeSignatures) {
		return nsDescriptorGenericFrontController.getManifest(nsdInfoId, includeSignatures);
	}

	@Override
	public ResponseEntity<Resource> nsDescriptorsNsdInfoIdNsdGet(final String nsdInfoId, @Valid final String includeSignatures) {
		return nsDescriptorGenericFrontController.getNsd(nsdInfoId, includeSignatures);
	}

	private static void makeLinks(@Nonnull final NsdInfo nsdInfo) {
		final String id = nsdInfo.getId();
		final NsdInfoLinks ret = new NsdInfoLinks();
		final Link nsdSelf = new Link();
		final String _self = makeSelfLink(nsdInfo);
		nsdSelf.setHref(_self);
		ret.setSelf(nsdSelf);

		final String _nsdContent = linkTo(methodOn(NsDescriptors351Sol005Api.class).nsDescriptorsNsdInfoIdNsdContentGet(id, "", "")).withSelfRel().getHref();
		final Link nsdContent = new Link();
		nsdContent.setHref(_nsdContent);
		ret.setNsdContent(nsdContent);

		nsdInfo.setLinks(ret);
	}

	private static String makeSelfLink(final NsdInfo nsdInfo) {
		return linkTo(methodOn(NsDescriptors351Sol005Api.class).nsDescriptorsNsdInfoIdGet(nsdInfo.getId())).withSelfRel().getHref();
	}

}
