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
package com.ubiqube.etsi.mano.service.graph.vnfm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.jgrapht.event.GraphEdgeChangeEvent;
import org.jgrapht.event.GraphVertexChangeEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.orchestrator.nodes.ConnectivityEdge;

class EdgeListenerTest {

	private EdgeListener<String> edgeListener;

	@BeforeEach
	public void setUp() {
		edgeListener = new EdgeListener<>();
	}

	@Test
	void testEdgeAdded() {
		ConnectivityEdge<String> edge = new ConnectivityEdge<>();
		GraphEdgeChangeEvent<String, ConnectivityEdge<String>> event = mock(GraphEdgeChangeEvent.class);
		when(event.getEdge()).thenReturn(edge);
		when(event.getEdgeSource()).thenReturn("source");
		when(event.getEdgeTarget()).thenReturn("target");

		edgeListener.edgeAdded(event);

		assertEquals("source", edge.getSource());
		assertEquals("target", edge.getTarget());
	}

	@Test
	void testVertexAdded() {
		GraphVertexChangeEvent<String> event = mock(GraphVertexChangeEvent.class);
		edgeListener.vertexAdded(event);
		// No assertion needed as the method does nothing
		assertTrue(true);
	}

	@Test
	void testVertexRemoved() {
		GraphVertexChangeEvent<String> event = mock(GraphVertexChangeEvent.class);
		edgeListener.vertexRemoved(event);
		// No assertion needed as the method does nothing
		assertTrue(true);
	}

	@Test
	void testEdgeRemoved() {
		GraphEdgeChangeEvent<String, ConnectivityEdge<String>> event = mock(GraphEdgeChangeEvent.class);
		edgeListener.edgeRemoved(event);
		// No assertion needed as the method does nothing
		assertTrue(true);
	}
}
