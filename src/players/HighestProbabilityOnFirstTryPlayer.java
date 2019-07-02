package players;

import game.Deck;

public class HighestProbabilityOnFirstTryPlayer extends ProbabilisticPlayer {

    /**
     * @param deck The shared deck in the game
     */
    public HighestProbabilityOnFirstTryPlayer(Deck deck) {
        super(deck);
    }

    @Override
    protected double valueCard(int card) {
        return deck.countCard(card);
    }

    @Override
    public String getStrategyName() {
        return "HighestProbabilityOnFirstTry";
    }
}
