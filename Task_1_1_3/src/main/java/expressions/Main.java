package expressions;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Repl repl = new Repl(scanner, System.out);
        repl.run();
        scanner.close();
    }
}