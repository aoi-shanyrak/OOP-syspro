package expressions;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Interactive read-eval-print loop for working with expressions.
 * <p>
 * The first non-empty input line is parsed as an expression.
 * Subsequent lines are treated as commands: {@code eval},
 * {@code derivative}, {@code simplify}, {@code print}, {@code new},
 * {@code exit}. A line that is not a command is parsed as a new
 * expression and replaces the current one.
 */
public class Repl {

    private final Scanner scanner;
    private final PrintStream out;

    private Expression current;

    /**
     * Creates a REPL that reads from the given scanner and writes
     * to the given stream.
     *
     * @param scanner source of input lines
     * @param out     destination for output
     */
    public Repl(Scanner scanner, PrintStream out) {
        this.scanner = scanner;
        this.out = out;
    }

    /**
     * Runs the loop until {@code exit}/{@code quit} is entered
     * or the input ends.
     */
    public void run() {
        out.println("-=<{[ Expression REPL ]}>=-");
        out.print("""
                Commands: eval <assign>,
                          derivative <var>,
                          simplify,
                          print,
                          new,
                          exit
                """);

        while (scanner.hasNextLine()) {
            out.print("> ");
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;
            if (isExit(line)) break;
            handle(line);
        }
    }

    private boolean isExit(String line) {
        return line.equals("exit") || line.equals("quit");
    }

    private void handle(String line) {
        if (current == null) {
            loadExpression(line);
            return;
        }

        String[] parts = line.split("\\s+", 2);
        String command = parts[0];
        String arg = parts.length > 1 ? parts[1].trim() : "";

        switch (command) {
            case "eval":       doEval(arg);       break;
            case "derivative": doDerivative(arg); break;
            case "simplify":   doSimplify();      break;
            case "print":      doPrint();         break;
            case "new":        current = null;    out.println("Enter a new expression:"); break;
            default:           loadExpression(line);
        }
    }

    private void loadExpression(String line) {
        try {
            current = Parser.parse(line);
            out.print("parsed: ");
            current.print(out);
            out.println();
        } catch (RuntimeException e) {
            out.println("parse error: " + e.getMessage());
        }
    }

    private void doEval(String arg) {
        try {
            out.println(current.eval(arg));
        } catch (RuntimeException e) {
            out.println("eval error: " + e.getMessage());
        }
    }

    private void doDerivative(String arg) {
        if (arg.isEmpty()) {
            out.println("usage: derivative <var>");
            return;
        }
        Expression d = current.derivative(arg);
        d.print(out);
        out.println();
    }

    private void doSimplify() {
        Expression s = current.simplify();
        s.print(out);
        out.println();
    }

    private void doPrint() {
        current.print(out);
        out.println();
    }
}