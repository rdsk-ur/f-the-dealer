/**
 * Calculates a value for each card (e.g. the probability to be correct when choosing it) and chooses the one with the biggest value.
 */
public abstract class ProbabilisticPlayer extends Player {

    /**
     * @param deck The shared deck in the game
     */
    public ProbabilisticPlayer(Deck deck) {
        super(deck);
    }

    @Override
    public int firstGuess() {
        double maxValue = -1;
        int maxCard = -1;
        double value = 0;
        for (int i = 0; i < deck.differentNumbers(); i++) {
            value = valueCard(i);
            if (value > maxValue) {
                maxValue = value;
                maxCard = i;
            }
        }
        //System.out.println("First guess: " + maxCard);
        return maxCard;
    }

    @Override
    public int secondGuess(int wrongGuess, boolean above) {
        int max = -1;
        int maxCard = -1;
        if (above) {
            for (int i = wrongGuess + 1; i < deck.differentNumbers(); i++) {
                if (max < deck.countCard(i)) {
                    max = deck.countCard(i);
                    maxCard = i;
                }
            }
        } else {
            for (int i = 0; i < wrongGuess; i++) {
                if (max < deck.countCard(i)) {
                    max = deck.countCard(i);
                    maxCard = i;
                }
            }
        }
        //System.out.println("Second guess: " + maxCard);
        return maxCard;
    }

    protected abstract double valueCard(int card);

    protected double probToGetCardFirstTry(int card) {
        return (double) deck.countCard(card) / deck.size();
    }

    protected double probThatCardIsAbove(int card) {
        int cardsAbove = 0;
        for (int i = card + 1; i < deck.differentNumbers(); i++) {
            cardsAbove += deck.countCard(i);
        }
        if (deck.size() - deck.countCard(card) == 0)
            return 0;
        return (double) cardsAbove / (deck.size() - deck.countCard(card));
    }

    protected double probThatCardIsBelow(int card) {
        int cardsBelow = 0;
        for (int i = 0; i < card; i++) {
            cardsBelow += deck.countCard(i);
        }
        if (deck.size() - deck.countCard(card) == 0)
            return 0;
        return (double) cardsBelow / (deck.size() - deck.countCard(card));
    }

    protected double probToGetCardSecondTry(int wrongCard, boolean above) {
        if (above) {
            int max = -1;
            int cardsAbove = 0;
            for (int i = wrongCard + 1; i < deck.differentNumbers(); i++) {
                cardsAbove += deck.countCard(i);
                if (max < deck.countCard(i)) {
                    max = deck.countCard(i);
                }
            }
            if (max == -1 || cardsAbove == 0) {
                return 0;
            } else {
                return (double) max / cardsAbove;
            }
        } else {
            int max = -1;
            int cardsBelow = 0;
            for (int i = 0; i < wrongCard; i++) {
                cardsBelow += deck.countCard(i);
                if (max < deck.countCard(i)) {
                    max = deck.countCard(i);
                }
            }
            if (max == -1 || cardsBelow == 0) {
                return 0;
            } else {
                return (double) max / cardsBelow;
            }
        }
    }
}
