package blackjack;

/**
 * Common base class for everyone sitting at the blackjack table,
 * such as the player and the dealer.
 * Holds the shared hand and exposes blackjack-related helpers,
 * while forcing subclasses to implement their own turn logic.
 */
public abstract class Participant {

    /** Display name of the participant. */
    protected final String name;

    /** Hand of cards held by the participant. */
    protected final Hand hand = new Hand();

    /**
     * Creates a participant with the given display name.
     *
     * @param name name shown in console output
     */
    protected Participant(String name) {
        this.name = name;
    }

    /**
     * Performs this participant's turn, drawing cards according
     * to the participant's own rules.
     *
     * @param deck the deck to draw cards from
     */
    public abstract void makeTurn(Deck deck);

    /**
     * Returns the participant's name.
     *
     * @return display name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the participant's hand.
     *
     * @return hand object
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * Adds a card to this participant's hand.
     *
     * @param card card to add
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * Returns the total point value of the participant's hand.
     *
     * @return hand value
     */
    public int getValue() {
        return hand.getValue();
    }

    /**
     * Returns whether the participant's hand is bust.
     *
     * @return {@code true} if hand value exceeds 21
     */
    public boolean isBust() {
        return hand.isBust();
    }

    /**
     * Returns whether the participant's hand is a natural blackjack.
     *
     * @return {@code true} if hand is blackjack
     */
    public boolean isBlackjack() {
        return hand.isBlackjack();
    }

    /**
     * Returns a formatted string with the participant's name,
     * cards and total value.
     *
     * @return description of the hand
     */
    public String describeHand() {
        return name + ": " + hand + " (sum: " + getValue() + ")";
    }
}