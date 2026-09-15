package blackjack;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private static void run(String input) {
        InputStream origIn = System.in;
        PrintStream origOut = System.out;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        try {
            new Game().play();
        } finally {
            System.setIn(origIn);
            System.setOut(origOut);
        }
    }

    @Test
    void oneRoundThenQuit() {
        assertDoesNotThrow(() -> run("s\nn\n"));
    }

    @Test
    void hitThenStand() {
        assertDoesNotThrow(() -> run("h\ns\nn\n"));
    }
}