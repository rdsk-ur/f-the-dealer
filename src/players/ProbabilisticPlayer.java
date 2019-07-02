package players;

import game.Deck;
import game.Player;

import java.util.ArrayList;
import java.util.Random;

/**
 * Calculates a value for each card (e.g. the probability to be correct when choosing it) and chooses the one with the biggest value.
 */
public abstract class ProbabilisticPlayer extends Player {
    Random r;

    /**
     * @param deck The shared deck in the game
     */
    public ProbabilisticPlayer(Deck deck) {
        super(deck);
        r = new Random();
    }

    @Override
    public int firstGuess() {
        ArrayList<Integer> out = new ArrayList<>();
        double max = Integer.MIN_VALUE;
        double value;
        for (int i = 0; i < deck.differentNumbers(); i++) {
            value = this.valueCard(i);
            if (value == max) {
                out.add(i);
            } else if (value > max) {
                max = value;
                out = new ArrayList<>();
                out.add(i);
            }
        }
        if (!out.isEmpty())
            return out.get(r.nextInt(out.size()));
        else
            throw new RuntimeException("This shouldn't have happened...");
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
