package blackjack;

/**
 * Represents the thirteen ranks of a playing card and their blackjack values.
 */
public enum Rank {
    TWO(2, "2"),
    THREE(3, "3"),
    FOUR(4, "4"),
    FIVE(5, "5"),
    SIX(6, "6"),
    SEVEN(7, "7"),
    EIGHT(8, "8"),
    NINE(9, "9"),
    TEN(10, "10"),
    JACK(10, "J"),
    QUEEN(10, "Q"),
    KING(10, "K"),
    ACE(11, "A");

    private final int value;
    private final String symbol;

    Rank(int value, String symbol) {
        this.value = value;
        this.symbol = symbol;
    }

    /**
     * Returns the blackjack point value of this rank.
     *
     * @return point value (2-11)
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the short string representation of this rank.
     *
     * @return symbol such as {@code "2"}, {@code "10"}, {@code "K"} or {@code "A"}
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Returns the symbol of this rank.
     *
     * @return symbol of the rank
     */
    @Override
    public String toString() {
        return symbol;
    }
}