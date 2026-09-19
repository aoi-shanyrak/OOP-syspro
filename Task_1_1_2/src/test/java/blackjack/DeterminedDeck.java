package blackjack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Replaces deck to make it determined for testing.
 */
class DeterminedDeck extends Deck {
    private final Deque<Card> queue = new ArrayDeque<>();

    DeterminedDeck(Card... cards) {
        this.queue.addAll(Arrays.asList(cards));
    }

    @Override
    public Card draw() {
        if (queue.isEmpty()) {
            throw new IllegalStateException("Deck is empty");
        }
        return queue.poll();
    }

    @Override
    public int size() {
        return queue.size();
    }
}