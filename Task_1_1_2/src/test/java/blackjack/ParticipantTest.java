package blackjack;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ParticipantTest {

    private static Player player() {
        return new Player(new Scanner(new ByteArrayInputStream("".getBytes())));
    }

    @Test
    void nameAndHandWork() {
        Player p = player();
        assertEquals("Вы", p.getName());
        assertNotNull(p.getHand());
    }

    @Test
    void addCardUpdatesValueAndFlags() {
        Player p = player();
        p.addCard(new Card(Suit.HEARTS, Rank.ACE));
        p.addCard(new Card(Suit.HEARTS, Rank.KING));
        assertEquals(21, p.getValue());
        assertTrue(p.isBlackjack());
        assertFalse(p.isBust());
    }
}