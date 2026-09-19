package blackjack;

import java.util.Scanner;

/**
 * Coordinates a full game of blackjack: creates the deck,
 * deals cards, asks players for input, and resolves each round.
 */
public class Game {

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Starts the main game loop: plays rounds until the user
     * chooses to stop, then closes the input scanner.
     */
    public void play() {
        System.out.println("-=<{( BLACKJACK )}>=-");

        boolean playAgain = true;
        while (playAgain) {
            playRound();
            playAgain = askPlayAgain();
        }

        System.out.println("bye.");
        scanner.close();
    }

    /**
     * Plays a single round: shuffles the deck, deals two cards to
     * each participant, runs the player's and dealer's turns,
     * and prints the result.
     */
    private void playRound() {
        Deck deck = new Deck();
        deck.shuffle();

        Player player = new Player(scanner);
        Dealer dealer = new Dealer();

        player.addCard(deck.draw());
        dealer.addCard(deck.draw());
        player.addCard(deck.draw());
        dealer.addCard(deck.draw());

        System.out.println("\n" + dealer.describeHiddenHand());
        System.out.println(player.describeHand());

        if (player.isBlackjack() || dealer.isBlackjack()) {
            resolveBlackjack(player, dealer);
            return;
        }

        player.makeTurn(deck);

        if (player.isBust()) {
            System.out.println("You lost.");
            return;
        }

        System.out.println("\nDealer's turn. Showing cards:");
        System.out.println(dealer.describeHand());
        dealer.makeTurn(deck);

        resolveWinner(player, dealer);
    }

    /**
     * Handles the case where at least one participant has a
     * natural blackjack and prints the outcome.
     *
     * @param player the player
     * @param dealer the dealer
     */
    private void resolveBlackjack(Player player, Dealer dealer) {
        System.out.println("\nShowing Dealer's cards:");
        System.out.println(dealer.describeHand());

        if (player.isBlackjack() && dealer.isBlackjack()) {
            System.out.println("all have a blackjack! tie.");
        } else if (player.isBlackjack()) {
            System.out.println("You have a blackjack! You won!");
        } else {
            System.out.println("Dealer have a blackjack. You lost.");
        }
    }

    /**
     * Compares the player's and dealer's hands and prints the
     * final round result.
     *
     * @param player the player
     * @param dealer the dealer
     */
    private void resolveWinner(Player player, Dealer dealer) {
        if (dealer.isBust()) {
            System.out.println("Dealer got too much! You won!");
            return;
        }

        int p = player.getValue();
        int d = dealer.getValue();

        System.out.println("\nResult: you " + p + ", dealer " + d);

        if (p > d) {
            System.out.println("You won!");
        } else if (p < d) {
            System.out.println("You lost.");
        } else {
            System.out.println("tie.");
        }
    }

    /**
     * Asks the user whether to play another round.
     *
     * @return {@code true} if the user entered {@code "y"} or {@code "yes"}
     */
    private boolean askPlayAgain() {
        System.out.print("\nPlay again? (y/n): ");
        String again = scanner.nextLine().trim().toLowerCase();
        return again.equals("y") || again.equals("yes");
    }
}