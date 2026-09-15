package blackjack;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class DealerTest {

    private static void silent(Runnable r) {
        PrintStream orig = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        try { r.run(); } finally { System.setOut(orig); }
    }

    @Test
    void drawsUntil17() {
        Dealer d = new Dealer();
        d.addCard(new Card(Suit.HEARTS, Rank.TWO));
        d.addCard(new Card(Suit.HEARTS, Rank.THREE));
        DeterminedDeck deck = new DeterminedDeck(
                new Card(Suit.SPADES, Rank.KING),
                new Card(Suit.SPADES, Rank.FIVE));
        silent(() -> d.makeTurn(deck));
        assertEquals(20, d.getValue());
    }

    @Test
    void stopsAt17() {
        Dealer d = new Dealer();
        d.addCard(new Card(Suit.HEARTS, Rank.KING));
        d.addCard(new Card(Suit.HEARTS, Rank.SEVEN));
        DeterminedDeck deck = new DeterminedDeck(new Card(Suit.SPADES, Rank.ACE));
        silent(() -> d.makeTurn(deck));
        assertEquals(17, d.getValue());
        assertEquals(1, deck.size());
    }
}