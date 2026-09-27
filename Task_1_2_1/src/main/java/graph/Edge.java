package graph;

/**
 * A weighted directed edge.
 *
 * @param from   source vertex
 * @param to     target vertex
 * @param weight edge weight
 */
public record Edge(String from, String to, int weight) {
}