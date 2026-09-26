package graph;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies that the three representations encode the same graph
 * identically, and are mutually equal.
 */
class CrossRepresentationTest {

    private static Graph sample(Supplier<Graph> factory) {
        Graph g = factory.get();
        g.addEdge("A", "B", 5);
        g.addEdge("A", "C", 2);
        g.addEdge("B", "D", 1);
        g.addEdge("C", "D", 3);
        return g;
    }

    @Test
    void allThreeAreEqual() {
        Graph list      = sample(AdjacencyListGraph::new);
        Graph matrix    = sample(AdjacencyMatrixGraph::new);
        Graph incidence = sample(IncidenceMatrixGraph::new);

        assertEquals(list, matrix);
        assertEquals(matrix, incidence);
        assertEquals(list, incidence);
    }

    @Test
    void allThreeHaveSameNeighbors() {
        Graph list      = sample(AdjacencyListGraph::new);
        Graph matrix    = sample(AdjacencyMatrixGraph::new);
        Graph incidence = sample(IncidenceMatrixGraph::new);

        for (String v : List.of("A", "B", "C", "D")) {
            assertEquals(list.getNeighbors(v), matrix.getNeighbors(v));
            assertEquals(list.getNeighbors(v), incidence.getNeighbors(v));
        }
    }

    @Test
    void allThreeHaveSameWeights() {
        Graph list      = sample(AdjacencyListGraph::new);
        Graph matrix    = sample(AdjacencyMatrixGraph::new);
        Graph incidence = sample(IncidenceMatrixGraph::new);

        assertEquals(list.getWeight("A", "B").getAsInt(),
                matrix.getWeight("A", "B").getAsInt());
        assertEquals(matrix.getWeight("A", "B").getAsInt(),
                incidence.getWeight("A", "B").getAsInt());
    }

    @Test
    void allThreeProduceValidTopologicalOrder() {
        assertValidOrder(sample(AdjacencyListGraph::new).topologicalSort());
        assertValidOrder(sample(AdjacencyMatrixGraph::new).topologicalSort());
        assertValidOrder(sample(IncidenceMatrixGraph::new).topologicalSort());
    }

    @Test
    void differentEdgesBreakEquality() {
        Graph a = new AdjacencyListGraph();
        Graph b = new IncidenceMatrixGraph();
        a.addEdge("A", "B", 1);
        b.addEdge("A", "B", 2);
        assertNotEquals(a, b);
    }

    private static void assertValidOrder(List<String> order) {
        assertTrue(order.indexOf("A") < order.indexOf("B"));
        assertTrue(order.indexOf("A") < order.indexOf("C"));
        assertTrue(order.indexOf("B") < order.indexOf("D"));
        assertTrue(order.indexOf("C") < order.indexOf("D"));
    }
}