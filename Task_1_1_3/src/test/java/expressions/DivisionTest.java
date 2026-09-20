package expressions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DivisionTest {

    @Test
    void print() {
        assertEquals("(10/2)",
                TestOutput.capture(new Division(new Number(10), new Number(2))));
    }

    @Test
    void evalIntegerDivision() {
        assertEquals(5, new Division(new Number(10), new Number(2)).eval(""));
        assertEquals(3, new Division(new Number(10), new Number(3)).eval(""));
    }

    @Test
    void evalWithVariable() {
        Expression e = new Division(new Number(20), new Variable("x"));
        assertEquals(2, e.eval("x = 10"));
    }

    @Test
    void derivativeFollowsQuotientRule() {
        Expression e = new Division(new Variable("x"), new Number(2));
        String out = TestOutput.capture(e.derivative("x"));
        assertTrue(out.contains("/"));
        assertTrue(out.contains("(2*2)"));
    }

    @Test
    void equals() {
        assertEquals(
                new Division(new Number(10), new Number(2)),
                new Division(new Number(10), new Number(2))
        );
        assertNotEquals(
                new Division(new Number(10), new Number(2)),
                new Multiplication(new Number(10), new Number(2))
        );
    }
}