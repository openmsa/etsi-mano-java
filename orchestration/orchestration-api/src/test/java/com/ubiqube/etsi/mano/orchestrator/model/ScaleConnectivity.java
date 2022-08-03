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
package com.ubiqube.etsi.mano.orchestrator.model;

import com.ubiqube.etsi.mano.orchestrator.uow.Relation;

public class ScaleConnectivity {

	private ScaleModel source;
	private ScaleModel target;
	private Relation relation;

	public ScaleConnectivity() {
		// Nothing.
	}

	public ScaleConnectivity(final ScaleModel source, final ScaleModel target, final Relation relation) {
		this.source = source;
		this.target = target;
		this.relation = relation;
	}

	public ScaleModel getSource() {
		return source;
	}

	public void setSource(final ScaleModel source) {
		this.source = source;
	}

	public ScaleModel getTarget() {
		return target;
	}

	public void setTarget(final ScaleModel target) {
		this.target = target;
	}

	public Relation getRelation() {
		return relation;
	}

	public void setRelation(final Relation relation) {
		this.relation = relation;
	}

	@Override
	public String toString() {
		return "ConnectivityEdge [source=" + source + ", target=" + target + "]";
	}

}
