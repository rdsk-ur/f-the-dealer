package game;

import java.util.Random;

public class Deck {
    private int[] cards;
    private int size;
    private final int colors;
    private Random r;

    /**
     * @param cards  How many cards of each color the deck contains.
     * @param colors How many colors the deck contains.
     */
    public Deck(int cards, int colors) {
        this.cards = new int[cards];
        this.colors = colors;
        r = new Random();
        reset();
    }

    /**
     * Reset to new-deck-state.
     */
    public void reset() {
        for (int i = 0; i < cards.length; i++) {
            cards[i] = colors;
        }
        size = cards.length * colors;
    }

    /**
     * @return True, when there is still at least one card in the deck.
     */
    public boolean hasNext() {
        return size > 0;
    }

    public int size() {
        return size;
    }

    /**
     * Returns a random card number from the cards in the deck. Doesn't remove it.
     *
     * @return The card number.
     */
    public int getRandom() {
        if (!hasNext())
            throw new RuntimeException("game.Deck is empty");
        int pick = r.nextInt(size);
        int index = 0;
        while (pick >= 0) {
            pick -= cards[index];
            index++;
        }
        index--;
        return index;
    }

    /**
     * Remove given number from deck.
     *
     * @param number
     */
    public void remove(int number) {
        if (cards[number] <= 0)
            throw new RuntimeException("This one can't be removed.");
        cards[number]--;
        size--;
    }

    public int[] getCards() {
        return cards;
    }

    public int getColors() {
        return colors;
    }

    public int countCard(int card) {
        return cards[card];
    }

    public int differentNumbers() {
        return cards.length;
    }
}
