package graph;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Behaviour specific to {@link IncidenceMatrixGraph}:
 * the incidence matrix is rebuilt on every mutation and
 * {@code getNeighbors} reads directly from it.
 */
class IncidenceMatrixGraphTest {

    @Test
    void neighborsReadFromMatrix() {
        Graph g = new IncidenceMatrixGraph();
        g.addEdge("A", "B", 1);
        g.addEdge("A", "C", 2);
        g.addEdge("C", "D", 3);

        assertEquals(List.of("B", "C"), g.getNeighbors("A"));
        assertEquals(List.of("D"), g.getNeighbors("C"));
        assertEquals(List.of(), g.getNeighbors("D"));
    }

    @Test
    void matrixRebuildsAfterRemoval() {
        Graph g = new IncidenceMatrixGraph();
        g.addEdge("A", "B", 1);
        g.addEdge("B", "C", 2);
        g.removeEdge("A", "B");

        assertEquals(List.of(), g.getNeighbors("A"));
        assertEquals(List.of("C"), g.getNeighbors("B"));
    }

    @Test
    void addingDuplicateReplacesWeight() {
        Graph g = new IncidenceMatrixGraph();
        g.addEdge("A", "B", 5);
        g.addEdge("A", "B", 9);

        assertEquals(1, g.getEdges().size());
        assertEquals(9, g.getWeight("A", "B").getAsInt());
    }

    @Test
    void twoEdgesModelUndirectedEdge() {
        Graph g = new IncidenceMatrixGraph();
        g.addEdge("A", "B", 1);
        g.addEdge("B", "A", 1);

        assertEquals(List.of("B"), g.getNeighbors("A"));
        assertEquals(List.of("A"), g.getNeighbors("B"));
        assertEquals(2, g.getEdges().size());
    }
}