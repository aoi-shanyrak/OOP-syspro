package graph;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Graph#readFromFile(String)} across all implementations.
 */
class FileInputTest {

    @TempDir
    Path tempDir;

    static Stream<Arguments> implementations() {
        return Stream.of(
                Arguments.of("list",      (java.util.function.Supplier<Graph>) AdjacencyListGraph::new),
                Arguments.of("matrix",    (java.util.function.Supplier<Graph>) AdjacencyMatrixGraph::new),
                Arguments.of("incidence", (java.util.function.Supplier<Graph>) IncidenceMatrixGraph::new)
        );
    }

    private Path write(String content) throws IOException {
        Path file = tempDir.resolve("graph.txt");
        Files.writeString(file, content);
        return file;
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("implementations")
    void readsVerticesAndWeightedEdges(String name, java.util.function.Supplier<Graph> factory) throws IOException {
        Path file = write("""
                # sample
                A
                B
                C
                A B 5
                B C 2
                """);

        Graph g = factory.get();
        g.readFromFile(file.toString());

        assertTrue(g.hasVertex("A"));
        assertTrue(g.hasVertex("B"));
        assertTrue(g.hasVertex("C"));
        assertEquals(5, g.getWeight("A", "B").getAsInt());
        assertEquals(2, g.getWeight("B", "C").getAsInt());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("implementations")
    void edgeWithoutWeightDefaultsToOne(String name, java.util.function.Supplier<Graph> factory) throws IOException {
        Path file = write("A B\n");
        Graph g = factory.get();
        g.readFromFile(file.toString());
        assertEquals(1, g.getWeight("A", "B").getAsInt());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("implementations")
    void ignoresBlankLinesAndComments(String name, java.util.function.Supplier<Graph> factory) throws IOException {
        Path file = write("""
                # header

                A

                A B 5
                # trailing
                """);
        Graph g = factory.get();
        g.readFromFile(file.toString());
        assertEquals(1, g.getEdges().size());
    }

    @Test
    void missingFileThrows() {
        Graph g = new IncidenceMatrixGraph();
        assertThrows(IllegalArgumentException.class,
                () -> g.readFromFile("does-not-exist.txt"));
    }

    @Test
    void rejectsInvalidLine() throws IOException {
        Path file = write("A B C D\n");
        Graph g = new IncidenceMatrixGraph();
        assertThrows(IllegalArgumentException.class,
                () -> g.readFromFile(file.toString()));
    }
}