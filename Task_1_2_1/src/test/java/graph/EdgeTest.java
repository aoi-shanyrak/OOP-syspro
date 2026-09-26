package graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Edge}.
 */
class EdgeTest {

    @Test
    void accessorsReturnValues() {
        Edge e = new Edge("A", "B", 5);
        assertEquals("A", e.from());
        assertEquals("B", e.to());
        assertEquals(5, e.weight());
    }

    @Test
    void equalsComparesAllFields() {
        assertEquals(new Edge("A", "B", 5), new Edge("A", "B", 5));
    }

    @Test
    void notEqualWhenWeightDiffers() {
        assertNotEquals(new Edge("A", "B", 5), new Edge("A", "B", 6));
    }

    @Test
    void notEqualWhenDirectionDiffers() {
        assertNotEquals(new Edge("A", "B", 5), new Edge("B", "A", 5));
    }

    @Test
    void hashCodeConsistentWithEquals() {
        assertEquals(new Edge("A", "B", 5).hashCode(),
                     new Edge("A", "B", 5).hashCode());
    }
}