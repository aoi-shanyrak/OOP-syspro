package graph;

import java.util.*;

/**
 * Graph stored as an incidence matrix: rows are vertices, columns
 * are edges. For a directed edge {@code u -> v}, the cell is
 * {@code -1} in row {@code u}, {@code +1} in row {@code v},
 * and {@code 0} in all other rows.
 *
 * <p>Weights are kept alongside the edges; the incidence matrix
 * itself carries only the topology.
 */
public class IncidenceMatrixGraph extends AbstractGraph {

    private final Set<String> vertices = new LinkedHashSet<>();
    private final List<Edge> edges = new ArrayList<>();

    private List<String> ordered = new ArrayList<>();
    private int[][] incidence = new int[0][0];

    @Override
    public void addVertex(String vertex) {
        if (vertices.add(vertex)) {
            rebuild();
        }
    }

    @Override
    public void removeVertex(String vertex) {
        if (!vertices.remove(vertex)) {
            return;
        }
        edges.removeIf(e -> e.from().equals(vertex) || e.to().equals(vertex));
        rebuild();
    }

    @Override
    public void addEdge(String from, String to, int weight) {
        edges.removeIf(e -> e.from().equals(from) && e.to().equals(to));
        vertices.add(from);
        vertices.add(to);
        edges.add(new Edge(from, to, weight));
        rebuild();
    }

    @Override
    public void removeEdge(String from, String to) {
        if (edges.removeIf(e -> e.from().equals(from) && e.to().equals(to))) {
            rebuild();
        }
    }

    @Override
    public boolean hasVertex(String vertex) {
        return vertices.contains(vertex);
    }

    @Override
    public boolean hasEdge(String from, String to) {
        return getWeight(from, to).isPresent();
    }

    @Override
    public OptionalInt getWeight(String from, String to) {
        for (Edge e : edges) {
            if (e.from().equals(from) && e.to().equals(to)) {
                return OptionalInt.of(e.weight());
            }
        }
        return OptionalInt.empty();
    }

    @Override
    public List<String> getNeighbors(String vertex) {
        List<String> result = new ArrayList<>();
        int v = ordered.indexOf(vertex);
        if (v < 0) {
            return result;
        }
        for (int e = 0; e < edges.size(); e++) {
            if (incidence[v][e] == -1) {
                result.add(edges.get(e).to());
            }
        }
        return result;
    }

    @Override
    public Set<String> getVertices() {
        return new LinkedHashSet<>(vertices);
    }

    @Override
    public List<Edge> getEdges() {
        return new ArrayList<>(edges);
    }

    private void rebuild() {
        ordered = new ArrayList<>(vertices);
        incidence = new int[ordered.size()][edges.size()];
        for (int v = 0; v < ordered.size(); v++) {
            for (int e = 0; e < edges.size(); e++) {
                if (edges.get(e).from().equals(ordered.get(v))) {
                    incidence[v][e] = -1;
                } else if (edges.get(e).to().equals(ordered.get(v))) {
                    incidence[v][e] = 1;
                }
            }
        }
    }
}