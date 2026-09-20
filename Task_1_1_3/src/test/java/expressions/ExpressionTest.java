package expressions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Expression}.
 */
class ExpressionTest {

    @Test
    void equalsDeepStructure() {
        Expression a = new Addition(
                new Number(3),
                new Multiplication(new Number(2), new Variable("x")));
        Expression b = new Addition(
                new Number(3),
                new Multiplication(new Number(2), new Variable("x")));
        assertEquals(a, b);
    }

    @Test
    void equalsCatchesDifferentVariables() {
        Expression a = new Addition(
                new Number(3),
                new Multiplication(new Number(2), new Variable("x")));
        Expression b = new Addition(
                new Number(3),
                new Multiplication(new Number(2), new Variable("y")));
        assertNotEquals(a, b);
    }

    @Test
    void derivativeConstantIsZero() {
        Expression e = new Addition(new Number(3), new Number(5));
        Expression d = e.derivative("x");
        assertEquals("(0+0)", TestOutput.capture(d));
        assertEquals(0, d.eval(""));
    }

    @Test
    void evalMultiVariable() {
        Expression e = new Addition(new Variable("x"), new Variable("y"));
        assertEquals(5, e.eval("x = 2; y = 3"));
    }
}