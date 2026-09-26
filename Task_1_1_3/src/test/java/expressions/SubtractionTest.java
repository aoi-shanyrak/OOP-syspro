package expressions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Subtraction}.
 */
class SubtractionTest {

    @Test
    void print() {
        assertEquals("(10-3)",
                TestOutput.capture(new Subtraction(new Number(10), new Number(3))));
    }

    @Test
    void evalSubtracts() {
        assertEquals(7, new Subtraction(new Number(10), new Number(3)).eval(""));
    }

    @Test
    void derivative() {
        Expression e = new Subtraction(new Number(10), new Variable("x"));
        assertEquals("(0-1)", TestOutput.capture(e.derivative("x")));
    }

    @Test
    void equals() {
        assertEquals(
                new Subtraction(new Number(5), new Number(6)),
                new Subtraction(new Number(5), new Number(6))
        );
        assertNotEquals(
                new Subtraction(new Number(5), new Number(6)),
                new Subtraction(new Number(6), new Number(5))
        );
    }
}