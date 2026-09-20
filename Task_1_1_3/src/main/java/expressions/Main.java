package expressions;

import java.util.Scanner;

/**
 * Entry point: starts the interactive expression REPL.
 */
public class Main {

    /**
     * Starts the REPL with standard input and output.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        new Repl(scanner, System.out).run();
        scanner.close();
    }
}