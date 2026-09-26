package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Topological sort via depth-first search.
 * Marks vertices grey while visiting and black on exit;
 * a grey neighbor means a cycle.
 */
public class DfsTopologicalSort implements TopologicalSort {

    private static final int WHITE = 0;
    private static final int GREY = 1;
    private static final int BLACK = 2;

    @Override
    public List<String> sort(Graph graph) {
        List<String> vertices = new ArrayList<>(graph.getVertices());
        int n = vertices.size();

        Map<String, Integer> index = new HashMap<>();
        for (int i = 0; i < n; i++) {
            index.put(vertices.get(i), i);
        }

        List<List<Integer>> adjacency = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adjacency.add(new ArrayList<>());
        }
        for (Edge edge : graph.getEdges()) {
            adjacency.get(index.get(edge.from())).add(index.get(edge.to()));
        }

        int[] color = new int[n];
        int[] tout = new int[n];
        int[] time = { 0 };

        for (int v = 0; v < n; v++) {
            if (color[v] == WHITE && !dfs(v, adjacency, color, tout, time)) {
                throw new IllegalStateException("Graph contains a cycle");
            }
        }

        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) {
            order[i] = i;
        }
        Arrays.sort(order, (a, b) -> Integer.compare(tout[b], tout[a]));

        List<String> result = new ArrayList<>();
        for (int i : order) {
            result.add(vertices.get(i));
        }
        return result;
    }

    private boolean dfs(int v, List<List<Integer>> adjacency,
                        int[] color, int[] tout, int[] time) {
        color[v] = GREY;
        for (int to : adjacency.get(v)) {
            if (color[to] == WHITE) {
                if (!dfs(to, adjacency, color, tout, time)) {
                    return false;
                }
            } else if (color[to] == GREY) {
                return false;
            }
        }
        color[v] = BLACK;
        tout[v] = time[0];
        time[0]++;
        return true;
    }
}