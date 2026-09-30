package graph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * Base class providing behaviour shared by all graph representations:
 * equality, hashing, string output, file input, and a pluggable
 * topological sort strategy.
 */
public abstract class AbstractGraph implements Graph {

    private TopologicalSort sorter = new DfsTopologicalSort();

    @Override
    public void setTopologicalSort(TopologicalSort sorter) {
        this.sorter = Objects.requireNonNull(sorter, "sorter");
    }

    @Override
    public List<String> topologicalSort() {
        return sorter.sort(this);
    }

    @Override
    public void readFromFile(String path) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot read file: " + path, e);
        }

        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                continue;
            }
            String[] tokens = trimmed.split("\\s+");
            if (tokens.length == 1) {
                addVertex(tokens[0]);
            } else if (tokens.length == 3) {
                addEdge(tokens[0], tokens[1], Integer.parseInt(tokens[2]));
            } else if (tokens.length == 2) {
                addEdge(tokens[0], tokens[1]);
            } else {
                throw new IllegalArgumentException("Invalid line: " + line);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Graph)) {
            return false;
        }
        Graph other = (Graph) o;
        return getVertices().equals(other.getVertices())
                && new HashSet<>(getEdges()).equals(new HashSet<>(other.getEdges()));
    }

    @Override
    public int hashCode() {
        return getVertices().hashCode() * 31 + new HashSet<>(getEdges()).hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: ").append(getVertices());
        sb.append(System.lineSeparator()).append("Edges: ");
        for (Edge e : getEdges()) {
            sb.append("(").append(e.from()).append(" -> ")
                    .append(e.to()).append(", w=").append(e.weight()).append(") ");
        }
        return sb.toString();
    }
}