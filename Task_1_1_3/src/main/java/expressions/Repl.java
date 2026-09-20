package expressions;

import java.io.PrintStream;
import java.util.Scanner;

public class Repl {

    private final Scanner scanner;
    private final PrintStream out;

    private Expression current;

    public Repl(Scanner scanner, PrintStream out) {
        this.scanner = scanner;
        this.out = out;
    }

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
            current.print();
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
        d.print();
        out.println();
    }

    private void doSimplify() {
        Expression s = current.simplify();
        s.print();
        out.println();
    }

    private void doPrint() {
        current.print();
        out.println();
    }
}