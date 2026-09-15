package blackjack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void constructorStoresSuitAndRank() {
        Card card = new Card(Suit.HEARTS, Rank.ACE);
        assertEquals(Suit.HEARTS, card.getSuit());
        assertEquals(Rank.ACE, card.getRank());
    }

    @Test
    void getValueDelegatesToRank() {
        assertEquals(11, new Card(Suit.HEARTS, Rank.ACE).getValue());
        assertEquals(10, new Card(Suit.SPADES, Rank.KING).getValue());
        assertEquals(2, new Card(Suit.CLUBS, Rank.TWO).getValue());
    }

    @Test
    void toStringCombinesRankAndSuit() {
        assertEquals("K S", new Card(Suit.SPADES, Rank.KING).toString());
        assertEquals("A H", new Card(Suit.HEARTS, Rank.ACE).toString());
        assertEquals("10 D", new Card(Suit.DIAMONDS, Rank.TEN).toString());
    }
}