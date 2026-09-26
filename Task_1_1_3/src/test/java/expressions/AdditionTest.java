package expressions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Addition}.
 */
class AdditionTest {

    @Test
    void printWrapsInParentheses() {
        Expression e = new Addition(new Number(3), new Number(5));
        assertEquals("(3+5)", TestOutput.capture(e));
    }

    @Test
    void evalAdds() {
        assertEquals(8, new Addition(new Number(3), new Number(5)).eval(""));
    }

    @Test
    void evalWithVariables() {
        Expression e = new Addition(new Number(3),
                new Multiplication(new Number(2), new Variable("x")));
        assertEquals(23, e.eval("x = 10"));
    }

    @Test
    void derivativeKeepsStructure() {
        Expression e = new Addition(new Number(3),
                new Multiplication(new Number(2), new Variable("x")));
        Expression d = e.derivative("x");
        assertEquals("(0+((0*x)+(2*1)))", TestOutput.capture(d));
    }

    @Test
    void equalsSameStructure() {
        Expression a = new Addition(new Number(5), new Number(6));
        Expression b = new Addition(new Number(5), new Number(6));
        assertEquals(a, b);
    }

    @Test
    void equalsDifferentOrderIsNotEqual() {
        Expression a = new Addition(new Number(5), new Number(6));
        Expression b = new Addition(new Number(6), new Number(5));
        assertNotEquals(a, b);
    }

    @Test
    void equalsDifferentOperation() {
        Expression add = new Addition(new Number(5), new Number(6));
        Expression sub = new Subtraction(new Number(5), new Number(6));
        assertNotEquals(add, sub);
    }

    @Test
    void equalsByValueIsNotEqual() {
        assertNotEquals(new Addition(new Number(3), new Number(5)),
                new Number(8));
    }
}