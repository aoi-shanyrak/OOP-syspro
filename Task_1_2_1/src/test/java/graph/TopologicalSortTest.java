package graph;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link DfsTopologicalSort}, the default strategy.
 *
 * <p>The {@link TopologicalSort} interface stays pluggable: adding
 * another algorithm only requires implementing it and passing it
 * through {@link Graph#setTopologicalSort(TopologicalSort)}.
 */
class TopologicalSortTest {

    private static Graph graph() {
        return new AdjacencyListGraph();
    }

    @Test
    void emptyGraph() {
        assertTrue(new DfsTopologicalSort().sort(graph()).isEmpty());
    }

    @Test
    void singleVertex() {
        Graph g = graph();
        g.addVertex("A");
        assertEquals(List.of("A"), new DfsTopologicalSort().sort(g));
    }

    @Test
    void linearChain() {
        Graph g = graph();
        g.addEdge("A", "B", 1);
        g.addEdge("B", "C", 1);
        g.addEdge("C", "D", 1);
        assertEquals(List.of("A", "B", "C", "D"), new DfsTopologicalSort().sort(g));
    }

    @Test
    void diamondHasValidOrder() {
        Graph g = graph();
        g.addEdge("A", "B", 1);
        g.addEdge("A", "C", 1);
        g.addEdge("B", "D", 1);
        g.addEdge("C", "D", 1);

        List<String> order = new DfsTopologicalSort().sort(g);
        assertTrue(order.indexOf("A") < order.indexOf("B"));
        assertTrue(order.indexOf("A") < order.indexOf("C"));
        assertTrue(order.indexOf("B") < order.indexOf("D"));
        assertTrue(order.indexOf("C") < order.indexOf("D"));
    }

    @Test
    void cycleThrows() {
        Graph g = graph();
        g.addEdge("A", "B", 1);
        g.addEdge("B", "C", 1);
        g.addEdge("C", "A", 1);
        assertThrows(IllegalStateException.class, () -> new DfsTopologicalSort().sort(g));
    }

    @Test
    void selfLoopThrows() {
        Graph g = graph();
        g.addEdge("A", "A", 1);
        assertThrows(IllegalStateException.class, () -> new DfsTopologicalSort().sort(g));
    }

    @Test
    void isolatedVerticesAreIncluded() {
        Graph g = graph();
        g.addVertex("X");
        g.addEdge("A", "B", 1);

        List<String> order = new DfsTopologicalSort().sort(g);
        assertEquals(3, order.size());
        assertTrue(order.contains("X"));
        assertTrue(order.indexOf("A") < order.indexOf("B"));
    }
}