package blackjack;

public class Dealer extends Participant {

    public Dealer() {
        super("Dealer");
    }

    @Override
    public void makeTurn(Deck deck) {
        while (getValue() < 17) {
            addCard(deck.draw());
            System.out.println("Dealer takes a card: " + describeHand());
        }
    }

    public String describeHiddenHand() {
        return name + ": " + hand.getFirstCard() + ", [closed card]";
    }
}