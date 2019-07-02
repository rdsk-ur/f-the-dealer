package players;

import game.Deck;
import game.Player;

import java.util.Random;

/**
 * Doesn't count cards, picks a random available card.
 */
public class RandomPlayer extends Player {
    private Random r;

    /**
     * @param deck The shared deck in the game
     */
    public RandomPlayer(Deck deck) {
        super(deck);
        r = new Random();
    }

    @Override
    public int firstGuess() {
        return deck.getRandom();
    }

    @Override
    public int secondGuess(int wrongGuess, boolean above) {
        int[] cards = deck.getCards();
        if (above) {
            int countCardsAbove = 0;
            for (int i = wrongGuess + 1; i < cards.length; i++) {
                countCardsAbove += cards[i];
            }
            int pick = r.nextInt(countCardsAbove);
            int index = wrongGuess + 1;
            while (pick >= 0) {
                pick -= cards[index];
                index++;
            }
            index--;
            return index;
        } else {
            int countCardsBelow = 0;
            for (int i = 0; i < wrongGuess; i++) {
                countCardsBelow += cards[i];
            }
            int pick = r.nextInt(countCardsBelow);
            int index = 0;
            while (pick >= 0) {
                pick -= cards[index];
                index++;
            }
            index--;
            return index;
        }
    }

    @Override
    public String getStrategyName() {
        return "Random";
    }
}
