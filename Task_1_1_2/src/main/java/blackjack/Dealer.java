package blackjack;

/**
 * Dealer controlled by fixed casino rules: draws cards until
 * the hand value reaches at least 17.
 */
public class Dealer extends Participant {

    /**
     * Creates a dealer with the default name.
     */
    public Dealer() {
        super("Dealer");
    }

    /**
     * Runs the dealer's turn: keeps drawing while the hand is
     * below 17 and stops once 17 or more is reached.
     *
     * @param deck the deck to draw cards from
     */
    @Override
    public void makeTurn(Deck deck) {
        while (getValue() < 17) {
            addCard(deck.draw());
            System.out.println("Dealer takes a card: " + describeHand());
        }
    }

    /**
     * Returns a string showing only the dealer's first card,
     * keeping the second card hidden from the player.
     *
     * @return description with one visible card
     */
    public String describeHiddenHand() {
        return name + ": " + hand.getFirstCard() + ", [closed card]";
    }
}