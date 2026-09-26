package graph;

import java.util.List;
import java.util.OptionalInt;
import java.util.Set;

/**
 * A directed weighted graph with string-labeled vertices.
 *
 * <p>Implementations may store the graph as an adjacency matrix,
 * an incidence matrix, or an adjacency list. All implementations
 * must be mutually comparable: two graphs are equal if they have
 * the same vertices and the same weighted edges, regardless of the
 * underlying representation.
 *
 * <p>The topological sort algorithm is pluggable: use
 * {@link #setTopologicalSort(TopologicalSort)} to swap in a different one.
 */
public interface Graph {

    /**
     * Adds a vertex to the graph. If it already exists, does nothing.
     *
     * @param vertex vertex name
     */
    void addVertex(String vertex);

    /**
     * Removes a vertex and all edges incident to it.
     *
     * @param vertex vertex name
     */
    void removeVertex(String vertex);

    /**
     * Adds a weighted directed edge. Missing endpoints are created.
     * If the edge already exists, replaces its weight.
     *
     * @param from   source vertex
     * @param to     target vertex
     * @param weight edge weight
     */
    void addEdge(String from, String to, int weight);

    /**
     * Adds an unweighted edge with default weight 1.
     *
     * @param from source vertex
     * @param to   target vertex
     */
    default void addEdge(String from, String to) {
        addEdge(from, to, 1);
    }

    /**
     * Removes the edge {@code from -> to}, if it exists.
     *
     * @param from source vertex
     * @param to   target vertex
     */
    void removeEdge(String from, String to);

    /**
     * Checks whether the graph contains the given vertex.
     *
     * @param vertex vertex name
     * @return {@code true} if the vertex exists
     */
    boolean hasVertex(String vertex);

    /**
     * Checks whether the graph contains the edge {@code from -> to}.
     *
     * @param from source vertex
     * @param to   target vertex
     * @return {@code true} if the edge exists
     */
    boolean hasEdge(String from, String to);

    /**
     * Returns the weight of the edge {@code from -> to}, if present.
     *
     * @param from source vertex
     * @param to   target vertex
     * @return weight wrapped in {@link OptionalInt}, or empty if the edge is absent
     */
    OptionalInt getWeight(String from, String to);

    /**
     * Returns outgoing neighbors of the given vertex.
     *
     * @param vertex vertex name
     * @return list of neighbors reachable by one edge
     */
    List<String> getNeighbors(String vertex);

    /**
     * Returns all vertices of the graph.
     *
     * @return set of vertex names
     */
    Set<String> getVertices();

    /**
     * Returns all edges of the graph.
     *
     * @return list of weighted edges
     */
    List<Edge> getEdges();

    /**
     * Replaces the topological sort strategy.
     *
     * @param sorter new strategy; must not be {@code null}
     */
    void setTopologicalSort(TopologicalSort sorter);

    /**
     * Returns a topological ordering using the current strategy.
     *
     * @return list of vertices in topological order
     * @throws IllegalStateException if the graph contains a cycle
     */
    List<String> topologicalSort();

    /**
     * Reads a file and build a graph.
     */
    void readFromFile(String path);
}