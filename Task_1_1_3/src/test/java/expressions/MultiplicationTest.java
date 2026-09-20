package expressions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Multiplication}.
 */
class MultiplicationTest {

    @Test
    void print() {
        assertEquals("(2*x)",
                TestOutput.capture(new Multiplication(new Number(2), new Variable("x"))));
    }

    @Test
    void evalMultiplies() {
        Expression e = new Multiplication(new Number(2), new Variable("x"));
        assertEquals(20, e.eval("x = 10"));
    }

    @Test
    void derivativeFollowsProductRule() {
        Expression e = new Multiplication(new Number(2), new Variable("x"));
        assertEquals("((0*x)+(2*1))", TestOutput.capture(e.derivative("x")));
    }

    @Test
    void derivativeFullExample() {
        Expression e = new Addition(new Number(3),
                new Multiplication(new Number(2), new Variable("x")));
        assertEquals("(0+((0*x)+(2*1)))", TestOutput.capture(e.derivative("x")));
    }

    @Test
    void equals() {
        assertEquals(
                new Multiplication(new Number(2), new Variable("x")),
                new Multiplication(new Number(2), new Variable("x"))
        );
        assertNotEquals(
                new Multiplication(new Number(2), new Variable("x")),
                new Multiplication(new Variable("x"), new Number(2))
        );
    }
}