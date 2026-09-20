package expressions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimplifyTest {

    private static String s(Expression e) {
        return TestOutput.capture(e.simplify());
    }

    @Test
    void mulByZeroLeft() {
        assertEquals("0", s(new Multiplication(new Number(0), new Variable("x"))));
    }

    @Test
    void mulByZeroRight() {
        assertEquals("0", s(new Multiplication(new Variable("x"), new Number(0))));
    }

    @Test
    void mulByOneLeft() {
        assertEquals("x", s(new Multiplication(new Number(1), new Variable("x"))));
    }

    @Test
    void mulByOneRight() {
        assertEquals("x", s(new Multiplication(new Variable("x"), new Number(1))));
    }

    @Test
    void addZeroLeft() {
        assertEquals("x", s(new Addition(new Number(0), new Variable("x"))));
    }

    @Test
    void addZeroRight() {
        assertEquals("x", s(new Addition(new Variable("x"), new Number(0))));
    }

    @Test
    void subSameVariable() {
        assertEquals("0", s(new Subtraction(new Variable("x"), new Variable("x"))));
    }

    @Test
    void subSameComplex() {
        Expression inner = new Addition(new Number(1), new Variable("x"));
        assertEquals("0", s(new Subtraction(inner,
                new Addition(new Number(1), new Variable("x")))));
    }

    @Test
    void subDifferentNotZero() {
        assertEquals("(x-y)", s(new Subtraction(new Variable("x"), new Variable("y"))));
    }

    @Test
    void collapseNumericAddMul() {
        Expression e = new Addition(new Number(3),
                new Multiplication(new Number(2), new Number(5)));
        assertEquals("13", s(e));
    }

    @Test
    void collapseNumericDivision() {
        assertEquals("5", s(new Division(new Number(10), new Number(2))));
    }

    @Test
    void collapseNumericSub() {
        assertEquals("7", s(new Subtraction(new Number(10), new Number(3))));
    }

    @Test
    void divZeroByAnything() {
        assertEquals("0", s(new Division(new Number(0), new Variable("x"))));
    }

    @Test
    void divByOne() {
        assertEquals("x", s(new Division(new Variable("x"), new Number(1))));
    }

    @Test
    void divNotSimplifiedIfVariableInDenominator() {
        assertEquals("(x/y)", s(new Division(new Variable("x"), new Variable("y"))));
    }

    @Test
    void simplifyChildrenFirst() {
        Expression e = new Multiplication(
                new Addition(new Number(0), new Number(3)),
                new Variable("x"));
        assertEquals("(3*x)", s(e));
    }

    @Test
    void nestedCollapse() {
        Expression e = new Addition(
                new Multiplication(new Variable("x"), new Number(1)),
                new Number(0));
        assertEquals("x", s(e));
    }

    @Test
    void complexTree() {
        Expression e = new Multiplication(
                new Addition(new Number(0), new Number(3)),
                new Addition(new Variable("x"), new Number(0)));
        assertEquals("(3*x)", s(e));
    }

    @Test
    void zeroBeatsOneInMul() {
        assertEquals("0", s(new Multiplication(new Number(0), new Number(1))));
    }

    @Test
    void addZeroCollapsesNumeric() {
        assertEquals("12", s(new Addition(new Number(0),
                new Multiplication(new Number(3), new Number(4)))));
    }

    @Test
    void originalNotMutated() {
        Expression e = new Multiplication(new Number(0), new Variable("x"));
        e.simplify();
        assertEquals("(0*x)", TestOutput.capture(e));
    }

    @Test
    void simplifyReturnsNewObjectOnChange() {
        Expression e = new Addition(new Number(0), new Variable("x"));
        assertNotSame(e, e.simplify());
    }

    @Test
    void noEvalIfVariablesPresent() {
        Expression e = new Addition(new Variable("x"), new Number(0));
        assertEquals("x", s(e));
    }

    @Test
    void partiallyNumericStillHasVariables() {
        Expression e = new Addition(new Variable("x"),
                new Addition(new Number(2), new Number(3)));
        assertEquals("(x+5)", s(e));
    }
}