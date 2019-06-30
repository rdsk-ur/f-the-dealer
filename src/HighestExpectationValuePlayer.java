public class HighestExpectationValuePlayer extends ProbabilisticPlayer {
    /**
     * @param deck The shared deck in the game
     */
    public HighestExpectationValuePlayer(Deck deck) {
        super(deck);
    }

    @Override
    public double valueCard(int card) {
        double first = probToGetCardFirstTry(card);
        double secondRight = probThatCardIsAbove(card) * probToGetCardSecondTry(card, true)
                + probThatCardIsBelow(card) * probToGetCardSecondTry(card, false);
        double secondWrong = probThatCardIsAbove(card) * (1 - probToGetCardSecondTry(card, true))
                + probThatCardIsBelow(card) * (1 - probToGetCardSecondTry(card, false));
        return first + (1 - first) * (secondRight * Constants.SECOND_RIGHT - secondWrong * Constants.SECOND_WRONG);
    }

    @Override
    public String getStrategyName() {
        return "HighestExpectationValue";
    }
}
