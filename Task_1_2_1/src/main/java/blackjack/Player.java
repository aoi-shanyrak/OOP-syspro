package blackjack;

import java.util.Scanner;

public class Player extends Participant {
    private final Scanner scanner;

    public Player(Scanner scanner) {
        super("You");
        this.scanner = scanner;
    }

    @Override
    public void makeTurn(Deck deck) {
        while (true) {
            System.out.print("Take a card (h) or stop (s)? ");
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("h")) {
                addCard(deck.draw());
                System.out.println(describeHand());

                if (isBust()) {
                    System.out.println("too much!");
                    return;
                }
            } else if (choice.equals("s")) {
                return;
            } else {
                System.out.println("enter h or s.");
            }
        }
    }
}