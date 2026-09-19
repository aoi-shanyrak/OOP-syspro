package blackjack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RankTest {

    @Test
    void thirteenRanksExist() {
        assertEquals(13, Rank.values().length);
    }

    @Test
    void numericValues() {
        assertEquals(2, Rank.TWO.getValue());
        assertEquals(10, Rank.TEN.getValue());
    }

    @Test
    void faceCardsAreTen() {
        assertEquals(10, Rank.JACK.getValue());
        assertEquals(10, Rank.QUEEN.getValue());
        assertEquals(10, Rank.KING.getValue());
    }

    @Test
    void aceIsEleven() {
        assertEquals(11, Rank.ACE.getValue());
    }

    @Test
    void symbolsMatch() {
        assertEquals("2", Rank.TWO.getSymbol());
        assertEquals("Q", Rank.QUEEN.getSymbol());
        assertEquals("K", Rank.KING.getSymbol());
    }

    @Test
    void toStringReturnsSymbol() {
        assertEquals("A", Rank.ACE.toString());
        assertEquals("10", Rank.TEN.toString());
    }
}