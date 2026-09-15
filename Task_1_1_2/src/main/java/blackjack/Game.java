package blackjack;

import java.util.Scanner;

public class Game {
    private final Scanner scanner = new Scanner(System.in);

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

        // Проверка блэкджеков
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

    private boolean askPlayAgain() {
        System.out.print("\nPlay again? (y/n): ");
        String again = scanner.nextLine().trim().toLowerCase();
        return again.equals("y") || again.equals("yes");
    }
}