package expressions;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

final class TestOutput {

    private TestOutput() {}   // нельзя создать экземпляр

    static String capture(Expression e) {
        PrintStream orig = System.out;
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buf));
        try {
            e.print();
        } finally {
            System.setOut(orig);
        }
        return buf.toString();
    }
}