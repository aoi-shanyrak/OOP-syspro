package blackjack;

/**
 * Represents a single playing card with a suit and a rank.
 */
public class Card {

    private final Suit suit;
    private final Rank rank;

    /**
     * Creates a card with the given suit and rank.
     *
     * @param suit the card suit
     * @param rank the card rank
     */
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * Returns the suit of this card.
     *
     * @return the suit
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Returns the rank of this card.
     *
     * @return the rank
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Returns the blackjack point value of this card.
     *
     * @return point value based on the rank
     */
    public int getValue() {
        return rank.getValue();
    }

    /**
     * Returns a human-readable representation of the card,
     * for example {@code "K S"} or {@code "10 H"}.
     *
     * @return string combining rank and suit symbol
     */
    @Override
    public String toString() {
        return rank + " " + suit.getSymbol();
    }
}