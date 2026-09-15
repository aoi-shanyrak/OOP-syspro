package blackjack;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void newDeckHas52Cards() {
        assertEquals(52, new Deck().size());
    }

    @Test
    void drawReducesSize() {
        Deck d = new Deck();
        assertNotNull(d.draw());
        assertEquals(51, d.size());
    }

    @Test
    void shuffleKeepsDeckUsable() {
        Deck d = new Deck();
        d.shuffle();
        assertEquals(52, d.size());
    }

    @Test
    void drawFromEmptyThrows() {
        Deck d = new Deck();
        for (int i = 0; i < 52; i++) d.draw();
        assertThrows(IllegalStateException.class, d::draw);
    }

    static class GameTest {

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
        void twoRoundsAndQuit() {
            assertDoesNotThrow(() -> run("s\ny\ns\nn\n"));
        }

        @Test
        void hitThenStand() {
            assertDoesNotThrow(() -> run("h\ns\nn\n"));
        }
    }
}