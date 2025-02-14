package com.ubiqube.etsi.mano.service.graph.vnfm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.jgrapht.event.GraphEdgeChangeEvent;
import org.jgrapht.event.GraphVertexChangeEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.orchestrator.nodes.ConnectivityEdge;

public class EdgeListenerTest {

    private EdgeListener<String> edgeListener;

    @BeforeEach
    public void setUp() {
        edgeListener = new EdgeListener<>();
    }

    @Test
    public void testEdgeAdded() {
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
    public void testVertexAdded() {
        GraphVertexChangeEvent<String> event = mock(GraphVertexChangeEvent.class);
        edgeListener.vertexAdded(event);
        // No assertion needed as the method does nothing
    }

    @Test
    public void testVertexRemoved() {
        GraphVertexChangeEvent<String> event = mock(GraphVertexChangeEvent.class);
        edgeListener.vertexRemoved(event);
        // No assertion needed as the method does nothing
    }

    @Test
    public void testEdgeRemoved() {
        GraphEdgeChangeEvent<String, ConnectivityEdge<String>> event = mock(GraphEdgeChangeEvent.class);
        edgeListener.edgeRemoved(event);
        // No assertion needed as the method does nothing
    }
}
