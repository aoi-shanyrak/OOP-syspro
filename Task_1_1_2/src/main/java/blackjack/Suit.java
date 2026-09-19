package blackjack;

/**
 * Represents the four suits of a standard playing card deck.
 */
public enum Suit {
    HEARTS("H"),
    DIAMONDS("D"),
    CLUBS("C"),
    SPADES("S");

    private final String symbol;

    Suit(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the single-letter symbol of the suit.
     *
     * @return suit symbol, {@code "H"} for hearts
     */
    public String getSymbol() {
        return symbol;
    }
}