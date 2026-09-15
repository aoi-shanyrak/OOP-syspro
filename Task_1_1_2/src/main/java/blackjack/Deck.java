package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a standard 52-card deck of playing cards.
 */
public class Deck {

    private final List<Card> cards = new ArrayList<>();

    /**
     * Creates a new deck containing 52 cards in a standard order.
     */
    public Deck() {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
    }

    /**
     * Shuffles the cards in the deck using a random order.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Removes and returns the top card of the deck.
     *
     * @return the drawn card
     * @throws IllegalStateException if the deck is empty
     */
    public Card draw() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Deck is empty");
        }
        return cards.remove(cards.size() - 1);
    }

    /**
     * Returns the number of remaining cards in the deck.
     *
     * @return count of cards left
     */
    public int size() {
        return cards.size();
    }
}