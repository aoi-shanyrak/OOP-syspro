package expressions;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Repl}.
 */
class ReplTest {

    private static String runRepl(String input) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(buffer);
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        new Repl(scanner, out).run();
        return buffer.toString();
    }

    @Test
    void parsesAndPrintsExpression() {
        String output = runRepl("3+2*x\nexit\n");
        assertTrue(output.contains("parsed: (3+(2*x))"));
    }

    @Test
    void derivativeCommand() {
        String output = runRepl("3+2*x\nderivative x\nexit\n");
        assertTrue(output.contains("(0+((0*x)+(2*1)))"));
    }

    @Test
    void evalCommand() {
        String output = runRepl("3+2*x\neval x = 10\nexit\n");
        assertTrue(output.contains("23"));
    }

    @Test
    void evalMultipleVariables() {
        String output = runRepl("x+y\neval x = 2; y = 3\nexit\n");
        assertTrue(output.contains("5"));
    }

    @Test
    void printCommand() {
        String output = runRepl("3+5\nprint\nexit\n");
        int first = output.indexOf("(3+5)");
        int second = output.indexOf("(3+5)", first + 1);
        assertTrue(first >= 0 && second > first);
    }

    @Test
    void newCommandResetsExpression() {
        String output = runRepl("3+5\nnew\n2*4\nexit\n");
        assertTrue(output.contains("parsed: (3+5)"));
        assertTrue(output.contains("parsed: (2*4)"));
    }

    @Test
    void replaceExpressionWithoutNew() {
        String output = runRepl("3+5\n2*4\nexit\n");
        assertTrue(output.contains("parsed: (3+5)"));
        assertTrue(output.contains("parsed: (2*4)"));
    }

    @Test
    void invalidExpressionShowsError() {
        String output = runRepl("???\nexit\n");
        assertTrue(output.contains("parse error"));
    }

    @Test
    void evalUnknownVariableShowsError() {
        String output = runRepl("x\neval y = 10\nexit\n");
        assertTrue(output.contains("eval error"));
    }

    @Test
    void derivativeWithoutArgShowsUsage() {
        String output = runRepl("3+x\nderivative\nexit\n");
        assertTrue(output.contains("usage: derivative"));
    }

    @Test
    void exitStopsProcessing() {
        String output = runRepl("3+5\nexit\n2*4\n");
        assertFalse(output.contains("parsed: (2*4)"));
    }

    @Test
    void emptyLinesIgnored() {
        String output = runRepl("\n\n3+5\n\nexit\n");
        assertTrue(output.contains("parsed: (3+5)"));
    }

    @Test
    void endOfInputStopsLoop() {
        String output = runRepl("3+5\n");
        assertTrue(output.contains("parsed: (3+5)"));
    }
}