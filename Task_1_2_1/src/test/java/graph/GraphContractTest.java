package graph;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contract tests that every {@link Graph} implementation must satisfy.
 */
class GraphContractTest {

    static Stream<Arguments> implementations() {
        Supplier<Graph> list      = AdjacencyListGraph::new;
        Supplier<Graph> matrix    = AdjacencyMatrixGraph::new;
        Supplier<Graph> incidence = IncidenceMatrixGraph::new;
        return Stream.of(
                Arguments.of("list",      list),
                Arguments.of("matrix",    matrix),
                Arguments.of("incidence", incidence)
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("implementations")
    void newGraphIsEmpty(String name, java.util.function.Supplier<Graph> factory) {
        Graph g = factory.get();
        assertTrue(g.getVertices().isEmpty());
        assertTrue(g.getEdges().isEmpty());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("implementations")
    void addVertex(String name, java.util.function.Supplier<Graph> factory) {
        Graph g = factory.get();
        g.addVertex("A");
        assertTrue(g.hasVertex("A"));
        assertEquals(Set.of("A"), g.getVertices());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("implementations")
    void addVertexTwiceIsNoOp(String name, java.util.function.Supplier<Graph> factory) {
        Graph g = factory.get();
        g.addVertex("A");
        g.addVertex("A");
        assertEquals(1, g.getVertices().size());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("implementations")
    void removeVertex(String name, java.util.function.Supplier<Graph> factory) {
        Graph g = factory.get();
        g.addVertex("A");
        g.removeVertex("A");
        assertFalse(g.hasVertex("A"));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("implementations")
    void addEdgeCreatesEndpoints(String name, java.util.function.Supplier<Graph> factory) {
        Graph g = factory.get();
        g.addEdge("A", "B", 5);
        assertTrue(g.hasVertex("A"));
        assertTrue(g.hasVertex("B"));
        assertTrue(g.hasEdge("A", "B"));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("implementations")
    void addEdgeStoresWeight(String name, java.util.function.Supplier<Graph> factory) {
        Graph g = factory.get();
        g.addEdge("A", "B", 7);
        assertEquals(7, g.getWeight("A", "B").getAsInt());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("implementations")
    void addingSameEdgeReplacesWeight(String name, java.util.function.Supplier<Graph> factory) {
        Graph g = factory.get();
        g.addEdge("A", "B", 5);
        g.addEdge("A", "B", 9);
        assertEquals(1, g.getEdges().size());
        assertEquals(9, g.getWeight("A", "B").getAsInt());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("implementations")
    void defaultWeightIsOne(String name, java.util.function.Supplier<Graph> factory) {
        Graph g = factory.get();
        g.addEdge("A", "B");
        assertEquals(1, g.getWeight("A", "B").getAsInt());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("implementations")
    void removeEdge(String name, java.util.function.Supplier<Graph> factory) {
        Graph g = factory.get();
        g.addEdge("A", "B", 5);
        g.removeEdge("A", "B");
        assertFalse(g.hasEdge("A", "B"));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("implementations")
    void directionIsRespected(String name, java.util.function.Supplier<Graph> factory) {
        Graph g = factory.get();
        g.addEdge("A", "B", 5);
        assertTrue(g.hasEdge("A", "B"));
        assertFalse(g.hasEdge("B", "A"));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("implementations")
    void neighborsAreOutgoing(String name, java.util.function.Supplier<Graph> factory) {
        Graph g = factory.get();
        g.addEdge("A", "B", 1);
        g.addEdge("A", "C", 2);
        g.addEdge("B", "C", 3);

        assertEquals(List.of("B", "C"), g.getNeighbors("A"));
        assertEquals(List.of("C"), g.getNeighbors("B"));
        assertEquals(List.of(), g.getNeighbors("C"));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("implementations")
    void neighborsOfMissingVertexAreEmpty(String name, java.util.function.Supplier<Graph> factory) {
        Graph g = factory.get();
        assertEquals(List.of(), g.getNeighbors("Z"));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("implementations")
    void removingVertexRemovesIncidentEdges(String name, java.util.function.Supplier<Graph> factory) {
        Graph g = factory.get();
        g.addEdge("A", "B", 1);
        g.addEdge("A", "C", 2);
        g.addEdge("C", "D", 3);

        g.removeVertex("A");

        assertFalse(g.hasVertex("A"));
        assertEquals(1, g.getEdges().size());
        assertTrue(g.hasEdge("C", "D"));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("implementations")
    void removingVertexRemovesIncomingEdges(String name, java.util.function.Supplier<Graph> factory) {
        Graph g = factory.get();
        g.addEdge("A", "B", 1);
        g.addEdge("C", "B", 2);
        g.removeVertex("B");
        assertTrue(g.getEdges().isEmpty());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("implementations")
    void equalsReflexive(String name, java.util.function.Supplier<Graph> factory) {
        Graph g = factory.get();
        g.addEdge("A", "B", 5);
        assertEquals(g, g);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("implementations")
    void equalsSameStructure(String name, java.util.function.Supplier<Graph> factory) {
        Graph a = factory.get();
        Graph b = factory.get();
        a.addEdge("A", "B", 5);
        b.addEdge("A", "B", 5);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("implementations")
    void notEqualWhenWeightDiffers(String name, java.util.function.Supplier<Graph> factory) {
        Graph a = factory.get();
        Graph b = factory.get();
        a.addEdge("A", "B", 5);
        b.addEdge("A", "B", 9);
        assertNotEquals(a, b);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("implementations")
    void topologicalSortLinear(String name, java.util.function.Supplier<Graph> factory) {
        Graph g = factory.get();
        g.addEdge("A", "B", 1);
        g.addEdge("B", "C", 1);
        assertEquals(List.of("A", "B", "C"), g.topologicalSort());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("implementations")
    void topologicalSortThrowsOnCycle(String name, java.util.function.Supplier<Graph> factory) {
        Graph g = factory.get();
        g.addEdge("A", "B", 1);
        g.addEdge("B", "A", 1);
        assertThrows(IllegalStateException.class, g::topologicalSort);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("implementations")
    void defaultSorterIsDfs(String name, java.util.function.Supplier<Graph> factory) {
        Graph g = factory.get();
        g.addEdge("A", "B", 1);
        // Не важно, какой именно — важно, что topologicalSort работает
        // без явной установки стратегии.
        assertEquals(List.of("A", "B"), g.topologicalSort());
    }
}