package expressions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Number}.
 */
class NumberTest {

    @Test
    void printOutputsValue() {
        assertEquals("5", TestOutput.capture(new Number(5)));
    }

    @Test
    void derivativeIsZero() {
        Expression d = new Number(5).derivative("x");
        assertTrue(d instanceof Number);
        assertEquals(0, d.eval(""));
    }

    @Test
    void evalReturnsValueIgnoringAssignments() {
        assertEquals(42, new Number(42).eval(""));
        assertEquals(42, new Number(42).eval("x = 100"));
    }

    @Test
    void equalsSameValue() {
        assertEquals(new Number(5), new Number(5));
    }

    @Test
    void equalsDifferentValue() {
        assertNotEquals(new Number(5), new Number(6));
    }

    @Test
    void equalsDifferentType() {
        assertNotEquals(new Number(5), new Variable("x"));
    }
}