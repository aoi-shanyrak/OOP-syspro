package blackjack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {

    private static Card c(Rank r) {
        return new Card(Suit.HEARTS, r);
    }

    @Test
    void emptyHandIsZero() {
        assertEquals(0, new Hand().getValue());
    }

    @Test
    void sumsNumbersAndFaceCards() {
        Hand h = new Hand();
        h.add(c(Rank.FIVE));
        h.add(c(Rank.KING));
        assertEquals(15, h.getValue());
    }

    @Test
    void aceAsElevenWhenSafe() {
        Hand h = new Hand();
        h.add(c(Rank.ACE));
        h.add(c(Rank.NINE));
        assertEquals(20, h.getValue());
    }

    @Test
    void aceDropsTo1OnBust() {
        Hand h = new Hand();
        h.add(c(Rank.ACE));
        h.add(c(Rank.NINE));
        h.add(c(Rank.FIVE));
        assertEquals(15, h.getValue());
    }

    @Test
    void multipleAcesDropAsNeeded() {
        Hand h = new Hand();
        h.add(c(Rank.ACE));
        h.add(c(Rank.ACE));
        h.add(c(Rank.ACE));
        h.add(c(Rank.KING));
        assertEquals(13, h.getValue());
    }

    @Test
    void isBustBranches() {
        Hand safe = new Hand();
        safe.add(c(Rank.ACE));
        safe.add(c(Rank.KING));
        assertFalse(safe.isBust());

        Hand bust = new Hand();
        bust.add(c(Rank.KING));
        bust.add(c(Rank.QUEEN));
        bust.add(c(Rank.TWO));
        assertTrue(bust.isBust());
    }

    @Test
    void isBlackjackBranches() {
        Hand bj = new Hand();
        bj.add(c(Rank.ACE));
        bj.add(c(Rank.KING));
        assertTrue(bj.isBlackjack());

        Hand threeCards = new Hand();
        threeCards.add(c(Rank.SEVEN));
        threeCards.add(c(Rank.SEVEN));
        threeCards.add(c(Rank.SEVEN));
        assertFalse(threeCards.isBlackjack());
    }

    @Test
    void toStringFormatsCards() {
        Hand h = new Hand();
        h.add(new Card(Suit.HEARTS, Rank.ACE));
        h.add(new Card(Suit.SPADES, Rank.KING));
        assertEquals("A ♥, K ♠", h.toString());
    }

    @Test
    void getFirstCardAndSize() {
        Hand h = new Hand();
        Card first = c(Rank.FIVE);
        h.add(first);
        h.add(c(Rank.SIX));
        assertSame(first, h.getFirstCard());
        assertEquals(2, h.size());
        assertEquals(2, h.getCards().size());
    }
}