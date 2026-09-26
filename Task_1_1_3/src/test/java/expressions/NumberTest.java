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

    @Test
    void isZeroTrueForZero() {
        assertTrue(new Number(0).isZero());
        assertFalse(new Number(1).isZero());
    }

    @Test
    void isOneTrueForOne() {
        assertTrue(new Number(1).isOne());
        assertFalse(new Number(0).isOne());
        assertFalse(new Number(2).isOne());
    }

    @Test
    void numberHasNoVariables() {
        assertFalse(new Number(5).hasVariables());
    }
}