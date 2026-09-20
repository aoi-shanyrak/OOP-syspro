package expressions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Parser}.
 */
class ParserTest {

    private static String getParsed(String input) {
        return TestOutput.capture(Parser.parse(input));
    }

    @Test
    void simpleNumber() {
        assertEquals("42", getParsed("42"));
    }

    @Test
    void simpleVariable() {
        assertEquals("x", getParsed("x"));
    }

    @Test
    void multiLetterVariable() {
        assertEquals("abc", getParsed("abc"));
    }

    @Test
    void simpleAddition() {
        assertEquals("(3+5)", getParsed("3+5"));
    }

    @Test
    void simpleMultiplication() {
        assertEquals("(2*x)", getParsed("2*x"));
    }

    @Test
    void priorityMulOverAdd() {
        assertEquals("(3+(2*x))", getParsed("3+2*x"));
    }

    @Test
    void priorityMulOverSub() {
        assertEquals("(10-(2*x))", getParsed("10-2*x"));
    }

    @Test
    void leftAssociativityAdd() {
        assertEquals("((1+2)+3)", getParsed("1+2+3"));
    }

    @Test
    void leftAssociativityMul() {
        assertEquals("((2*3)*4)", getParsed("2*3*4"));
    }

    @Test
    void mixedPriority() {
        assertEquals("((1+(2*3))-(4/2))", getParsed("1+2*3-4/2"));
    }

    @Test
    void stripOuterParens() {
        assertEquals("(3+5)", getParsed("(3+5)"));
    }

    @Test
    void doesNotStripNonGlobalParens() {
        assertEquals("(3+5)", getParsed("(3)+(5)"));
    }

    @Test
    void nestedParens() {
        assertEquals("((1+2)*3)", getParsed("(1+2)*3"));
    }

    @Test
    void nestedInsideParens() {
        assertEquals("(3+(2*x))", getParsed("(3+(2*x))"));
    }

    @Test
    void withWhitespace() {
        assertEquals("(3+(2*x))", getParsed("  3 + 2 * x  "));
    }

    @Test
    void division() {
        assertEquals("(10/2)", getParsed("10/2"));
    }

    @Test
    void roundTripEvalMatches() {
        Expression a = Parser.parse("3+2*x");
        Expression b = Parser.parse("(3+(2*x))");
        assertEquals(a, b);
        assertEquals(a.eval("x = 10"), b.eval("x = 10"));
    }

    @Test
    void parseDerivative() {
        Expression e = Parser.parse("3+2*x");
        Expression d = e.derivative("x");
        assertEquals("(0+((0*x)+(2*1)))", TestOutput.capture(d));
    }

    @Test
    void invalidAtomThrows() {
        assertThrows(RuntimeException.class, () -> Parser.parse(""));
        assertThrows(RuntimeException.class, () -> Parser.parse("3x"));
    }
}