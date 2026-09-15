package blackjack;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a hand of cards held by a participant.
 * Provides blackjack specific score calculation with flexible aces.
 */
public class Hand {

    private final List<Card> cards = new ArrayList<>();

    /**
     * Adds a card to the hand.
     *
     * @param card the card to add
     */
    public void add(Card card) {
        cards.add(card);
    }

    /**
     * Returns the list of cards currently in the hand.
     *
     * @return mutable list of cards
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * Returns the total blackjack value of the hand.
     * Aces count as 11 while this does not cause a bust,
     * otherwise they drop to 1.
     *
     * @return total point value of the hand
     */
    public int getValue() {
        int sum = 0;
        int aces = 0;

        for (Card card : cards) {
            sum += card.getValue();
            if (card.getRank() == Rank.ACE) {
                aces++;
            }
        }

        while (sum > 21 && aces > 0) {
            sum -= 10;
            aces--;
        }

        return sum;
    }

    /**
     * Returns whether the hand value exceeds 21.
     *
     * @return {@code true} if the hand is bust
     */
    public boolean isBust() {
        return getValue() > 21;
    }

    /**
     * Returns whether the hand is a natural blackjack,
     * i.e. exactly two cards totalling 21.
     *
     * @return {@code true} if the hand is blackjack
     */
    public boolean isBlackjack() {
        return cards.size() == 2 && getValue() == 21;
    }

    /**
     * Returns the first card dealt to the hand.
     * Used to display the dealer's open card.
     *
     * @return first card in the hand
     */
    public Card getFirstCard() {
        return cards.get(0);
    }

    /**
     * Returns the number of cards in the hand.
     *
     * @return card count
     */
    public int size() {
        return cards.size();
    }

    /**
     * Returns a comma-separated representation of the hand,
     * for example {@code "A H, K S"}.
     *
     * @return string with all cards
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cards.size(); i++) {
            sb.append(cards.get(i));
            if (i < cards.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}