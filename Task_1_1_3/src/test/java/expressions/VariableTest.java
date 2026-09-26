package expressions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Variable}.
 */
public class VariableTest {

    @Test
    void printOutputsName() {
        assertEquals("x", TestOutput.capture(new Variable("x")));
    }

    @Test
    void derivativeBySameNameIsOne() {
        Expression d = new Variable("x").derivative("x");
        assertEquals(1, d.eval(""));
    }

    @Test
    void derivativeByOtherNameIsZero() {
        Expression d = new Variable("x").derivative("y");
        assertEquals(0, d.eval(""));
    }

    @Test
    void evalReadsAssignment() {
        assertEquals(10, new Variable("x").eval("x = 10"));
    }

    @Test
    void evalMultipleAssignments() {
        String a = "x = 10; y = 13";
        assertEquals(10, new Variable("x").eval(a));
        assertEquals(13, new Variable("y").eval(a));
    }

    @Test
    void evalWithoutSpaces() {
        assertEquals(7, new Variable("x").eval("x=7"));
    }

    @Test
    void evalUnknownVariableThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new Variable("z").eval("x = 10"));
    }

    @Test
    void equalsSameName() {
        assertEquals(new Variable("x"), new Variable("x"));
    }

    @Test
    void equalsDifferentName() {
        assertNotEquals(new Variable("x"), new Variable("y"));
    }

    @Test
    void equalsDifferentType() {
        assertNotEquals(new Variable("x"), new Number(1));
    }

    @Test
    void evalRejectsAssignmentWithoutEquals() {
        Variable x = new Variable("x");
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> x.eval("x = 10; garbage")
        );
        assertTrue(ex.getMessage().contains("garbage"));
    }

    @Test
    void evalRejectsEmptyValue() {
        Variable x = new Variable("x");
        assertThrows(
                IllegalArgumentException.class,
                () -> x.eval("x = 10; = 5")
        );
    }

    @Test
    void evalRejectsTooManyEquals() {
        Variable x = new Variable("x");
        assertThrows(
                IllegalArgumentException.class,
                () -> x.eval("x = 10; y = 5 = 6")
        );
    }

    @Test
    void evalRejectsEmptySegment() {
        Variable x = new Variable("x");
        assertThrows(
                IllegalArgumentException.class,
                () -> x.eval("x = 10;; y = 5")
        );
    }

    @Test
    void evalRejectsNonNumericValue() {
        Variable x = new Variable("x");
        assertThrows(
                NumberFormatException.class,
                () -> x.eval("x = abc")
        );
    }

    @Test
    void variableHasVariables() {
        assertTrue(new Variable("x").hasVariables());
    }
}