package blackjack;

public abstract class Participant {
    protected final String name;
    protected final Hand hand = new Hand();

    protected Participant(String name) {
        this.name = name;
    }

    // Каждый участник реализует свой ход сам
    public abstract void makeTurn(Deck deck);

    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public int getValue() {
        return hand.getValue();
    }

    public boolean isBust() {
        return hand.isBust();
    }

    public boolean isBlackjack() {
        return hand.isBlackjack();
    }

    public String describeHand() {
        return name + ": " + hand + " (sum: " + getValue() + ")";
    }
}