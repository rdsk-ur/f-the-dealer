package game;

public abstract class Player {
    protected Deck deck;

    /**
     * @param deck The shared deck in the game
     */
    public Player(Deck deck) {
        this.deck = deck;
    }

    /**
     * Gives a first guess for a card.
     *
     * @return The first guess
     */
    public abstract int firstGuess();

    /**
     * Gives a second guess for a card.
     *
     * @param wrongGuess The last guess that was given (and which was wrong).
     * @param above      If the correct card is above the wrong guess.
     * @return The second guess
     */
    public abstract int secondGuess(int wrongGuess, boolean above);

    /**
     * Resets internal state.
     */
    public void reset() {
    }

    ;

    public abstract String getStrategyName();
}
