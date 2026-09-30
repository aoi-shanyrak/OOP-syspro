package graph;

import java.util.List;

/**
 * Strategy for ordering graph vertices so that every edge
 * goes from an earlier vertex to a later one.
 */
public interface TopologicalSort {

    /**
     * Returns a topological ordering of the graph vertices.
     *
     * @param graph graph to sort
     * @return vertices in topological order
     * @throws IllegalStateException if the graph contains a cycle
     */
    List<String> sort(Graph graph);
}