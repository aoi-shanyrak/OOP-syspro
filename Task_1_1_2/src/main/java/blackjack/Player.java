package blackjack;

import java.util.Scanner;

/**
 * Human player controlled via console input.
 * Asks the user on each turn whether to hit or stand.
 */
public class Player extends Participant {

    private final Scanner scanner;

    /**
     * Creates a player that reads commands from the given scanner.
     *
     * @param scanner scanner used for console input
     */
    public Player(Scanner scanner) {
        super("You");
        this.scanner = scanner;
    }

    /**
     * Runs the player's turn: repeatedly asks for {@code h} (hit)
     * or {@code s} (stand) until the player stands or busts.
     *
     * @param deck the deck to draw cards from
     */
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