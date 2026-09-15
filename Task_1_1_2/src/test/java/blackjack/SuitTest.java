package blackjack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SuitTest {

    @Test
    void allFourSuitsExist() {
        assertEquals(4, Suit.values().length);
    }

    @Test
    void symbolsMatch() {
        assertEquals("H", Suit.HEARTS.getSymbol());
        assertEquals("D", Suit.DIAMONDS.getSymbol());
        assertEquals("C", Suit.CLUBS.getSymbol());
        assertEquals("S", Suit.SPADES.getSymbol());
    }
}