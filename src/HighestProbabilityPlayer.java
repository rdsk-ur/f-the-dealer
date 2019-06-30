public class HighestProbabilityPlayer extends ProbabilisticPlayer {
    /**
     * @param deck The shared deck in the game
     */
    public HighestProbabilityPlayer(Deck deck) {
        super(deck);
    }

    @Override
    public double valueCard(int card) {
        double first = probToGetCardFirstTry(card);
        return first + (1 - first) *
                (probThatCardIsAbove(card) * probToGetCardSecondTry(card, true)
                        + probThatCardIsBelow(card) * probToGetCardSecondTry(card, false));
    }

    @Override
    public String getStrategyName() {
        return "HighestProbability";
    }
}
