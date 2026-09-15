package blackjack;

import org.junit.jupiter.api.Test;

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
}