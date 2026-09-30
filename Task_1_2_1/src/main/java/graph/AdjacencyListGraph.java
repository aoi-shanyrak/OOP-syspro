package graph;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;

/**
 * Graph stored as an adjacency list with weights:
 * for each vertex a map from neighbor to edge weight.
 */
public class AdjacencyListGraph extends AbstractGraph {

    private final Map<String, Map<String, Integer>> adjacency = new LinkedHashMap<>();

    @Override
    public void addVertex(String v) {
        adjacency.putIfAbsent(v, new LinkedHashMap<>());
    }

    @Override
    public void removeVertex(String v) {
        if (adjacency.remove(v) == null) {
            return;
        }
        for (Map<String, Integer> neighbours : adjacency.values()) {
            neighbours.remove(v);
        }
    }

    @Override
    public void addEdge(String from, String to, int weight) {
        addVertex(from);
        addVertex(to);
        adjacency.get(from).put(to, weight);
    }

    @Override
    public void removeEdge(String from, String to) {
        Map<String, Integer> neighbours = adjacency.get(from);
        if (neighbours == null) {
            return;
        }
        neighbours.remove(to);
    }

    @Override
    public boolean hasVertex(String v) {
        return adjacency.containsKey(v);
    }

    @Override
    public boolean hasEdge(String from, String to) {
        Map<String, Integer> neighbors = adjacency.get(from);
        return neighbors != null && neighbors.containsKey(to);
    }

    @Override
    public OptionalInt getWeight(String from, String to) {
        Map<String, Integer> neighbors = adjacency.get(from);
        if (neighbors == null) {
            return OptionalInt.empty();
        }
        Integer w = neighbors.get(to);
        return w == null ? OptionalInt.empty() : OptionalInt.of(w);
    }

    @Override
    public List<String> getNeighbors(String v) {
        Map<String, Integer> neighbors = adjacency.get(v);
        return neighbors == null ? List.of() : new ArrayList<>(neighbors.keySet());
    }

    @Override
    public Set<String> getVertices() {
        return new LinkedHashSet<>(adjacency.keySet());
    }

    @Override
    public List<Edge> getEdges() {
        List<Edge> edges = new ArrayList<>();
        for (Map.Entry<String, Map<String, Integer>> entry : adjacency.entrySet()) {
            for (Map.Entry<String, Integer> e : entry.getValue().entrySet()) {
                edges.add(new Edge(entry.getKey(), e.getKey(), e.getValue()));
            }
        }
        return edges;
    }
}