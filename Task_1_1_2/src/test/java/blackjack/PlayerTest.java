package blackjack;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private static Player player(String input) {
        return new Player(new Scanner(new ByteArrayInputStream(input.getBytes())));
    }

    private static void silent(Runnable r) {
        PrintStream orig = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        try { r.run(); } finally { System.setOut(orig); }
    }

    @Test
    void standDoesNotDraw() {
        Player p = player("s\n");
        DeterminedDeck deck = new DeterminedDeck(new Card(Suit.HEARTS, Rank.TWO));
        silent(() -> p.makeTurn(deck));
        assertEquals(1, deck.size());
    }

    @Test
    void hitDrawsThenStands() {
        Player p = player("h\ns\n");
        DeterminedDeck deck = new DeterminedDeck(
                new Card(Suit.HEARTS, Rank.FIVE),
                new Card(Suit.HEARTS, Rank.SIX));
        silent(() -> p.makeTurn(deck));
        assertEquals(5, p.getValue());
        assertEquals(1, deck.size());
    }

    @Test
    void invalidInputIsRetried() {
        Player p = player("xyz\ns\n");
        DeterminedDeck deck = new DeterminedDeck(new Card(Suit.HEARTS, Rank.TWO));
        silent(() -> p.makeTurn(deck));
        assertEquals(1, deck.size());
    }
}