package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;

/**
 * Graph stored as an adjacency matrix with weights:
 * for each vertex, a map from neighbor to edge weight.
 */
public class AdjacencyMatrixGraph extends AbstractGraph {

    private static final int INITIAL_CAP = 16;

    private final List<String> slots = new ArrayList<>();
    private final Map<String, Integer> index = new HashMap<>();
    private Integer[][] matrix = new Integer[INITIAL_CAP][INITIAL_CAP];

    @Override
    public void addVertex(String v) {
        if (index.containsKey(v)) {
            return;
        }
        int slot = firstFreeSlot();
        if (slot == -1) {
            slot = slots.size();
            slots.add(v);
        } else {
            slots.set(slot, v);
        }
        index.put(v, slot);
        ensureCap(slot + 1);
    }

    private int firstFreeSlot() {
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i) == null) {
                return i;
            }
        }
        return -1;
    }

    private void ensureCap(int size) {
        if (size <= matrix.length) {
            return;
        }
        int newCap = Math.max(matrix.length * 2, size);
        Integer[][] larger = new Integer[newCap][newCap];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, larger[i], 0, matrix[i].length);
        }
        matrix = larger;
    }

    @Override
    public void removeVertex(String v) {
        Integer slot = index.remove(v);
        if (slot == null) {
            return;
        }
        slots.set(slot, null);
        for (int i = 0; i < matrix.length; i++) {
            matrix[i][slot] = null;
            matrix[slot][i] = null;
        }
    }

    @Override
    public void addEdge(String from, String to, int weight) {
        addVertex(from);
        addVertex(to);
        matrix[index.get(from)][index.get(to)] = weight;
    }

    @Override
    public void removeEdge(String from, String to) {
        Integer f = index.get(from);
        Integer t = index.get(to);
        if (f == null || t == null) {
            return;
        }
        matrix[f][t] = null;
    }

    @Override
    public boolean hasVertex(String v) {
        return index.containsKey(v);
    }

    @Override
    public boolean hasEdge(String from, String to) {
        return getWeight(from, to).isPresent();
    }

    @Override
    public OptionalInt getWeight(String from, String to) {
        Integer f = index.get(from);
        Integer t = index.get(to);
        if (f == null || t == null || matrix[f][t] == null) {
            return OptionalInt.empty();
        }
        return OptionalInt.of(matrix[f][t]);
    }

    @Override
    public List<String> getNeighbors(String v) {
        Integer slot = index.get(v);
        if (slot == null) {
            return List.of();
        }
        List<String> neighbours = new ArrayList<>();
        for (int i = 0; i < slots.size(); i++) {
            String neigh = slots.get(i);
            if (neigh == null || matrix[slot][i] == null) {
                continue;
            }
            neighbours.add(neigh);
        }
        return neighbours;
    }

    @Override
    public Set<String> getVertices() {
        Set<String> vs = new LinkedHashSet<>();
        for (String v : slots) {
            if (v != null) {
                vs.add(v);
            }
        }
        return vs;
    }

    @Override
    public List<Edge> getEdges() {
        List<Edge> res = new ArrayList<>();
        for (int i = 0; i < slots.size(); i++) {
            String f = slots.get(i);
            if (f == null) {
                continue;
            }
            for (int j = 0; j < slots.size(); j++) {
                String t = slots.get(j);
                if (t == null || matrix[i][j] == null) {
                    continue;
                }
                res.add(new Edge(f, t, matrix[i][j]));
            }
        }
        return res;
    }
}