package game;

import java.util.List;

import static game.Util.*;

/**
 * This Class is meant to keep track of events in a simulation.
 * It can be extended for saving different simulation features.
 */
public class Scores {
    private Integer[] chugsDrunk;
    private Integer[] chugsDealt;
    private Integer[] guessesRight;
    private Integer[] guessesWrong;

    public Scores(int players) {
        chugsDrunk = new Integer[players];
        chugsDealt = new Integer[players];
        guessesRight = new Integer[players];
        guessesWrong = new Integer[players];
        for (int i = 0; i < players; i++) {
            chugsDrunk[i] = 0;
            chugsDealt[i] = 0;
            guessesRight[i] = 0;
            guessesWrong[i] = 0;
        }
    }

    public Integer[][] getScores() {
        return new Integer[][]{chugsDrunk, chugsDealt, guessesRight, guessesWrong};
    }

    public void newChugs(int giver, int victim, int n) {
        chugsDealt[giver] += n;
        chugsDrunk[victim] += n;
    }

    public void guessedRight(int player) {
        guessesRight[player] += 1;
    }

    public void guessedWrong(int player) {
        guessesWrong[player] += 1;
    }

    /**
     * @return index of player with the most chugs dealt
     */
    public List<Integer> bestChugsDealt() {
        return maxIndices(chugsDealt);
    }

    /**
     * @return index of player with the least chugs drunk
     */
    public List<Integer> bestChugsDrunk() {
        return minIndices(chugsDrunk);
    }

    /**
     * @return index of player with the best dealt-drunk score
     */
    public List<Integer> bestChugsDifference() {
        return maxIndicesDiff(chugsDealt, chugsDrunk);
    }


    /**
     * @return index of player with the most right guesses
     */
    public List<Integer> bestGuessesRight() {
        return maxIndices(guessesRight);
    }

    /**
     * @return index of player with the least wrong guesses
     */
    public List<Integer> bestGuessesWrong() {
        return minIndices(guessesWrong);
    }

    /**
     * @return index of player with the best right-wrong guess score
     */
    public List<Integer> bestGuessesDifference() {
        return maxIndicesDiff(guessesRight, guessesWrong);
    }

    //Generated, for debugging
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("game.Scores{");
        sb.append("chugsDrunk=");
        if (chugsDrunk == null) sb.append("null");
        else {
            sb.append('[');
            for (int i = 0; i < chugsDrunk.length; ++i)
                sb.append(i == 0 ? "" : ", ").append(chugsDrunk[i]);
            sb.append(']');
        }
        sb.append(", chugsDealt=");
        if (chugsDealt == null) sb.append("null");
        else {
            sb.append('[');
            for (int i = 0; i < chugsDealt.length; ++i)
                sb.append(i == 0 ? "" : ", ").append(chugsDealt[i]);
            sb.append(']');
        }
        sb.append(", guessesRight=");
        if (guessesRight == null) sb.append("null");
        else {
            sb.append('[');
            for (int i = 0; i < guessesRight.length; ++i)
                sb.append(i == 0 ? "" : ", ").append(guessesRight[i]);
            sb.append(']');
        }
        sb.append(", guessesWrong=");
        if (guessesWrong == null) sb.append("null");
        else {
            sb.append('[');
            for (int i = 0; i < guessesWrong.length; ++i)
                sb.append(i == 0 ? "" : ", ").append(guessesWrong[i]);
            sb.append(']');
        }
        sb.append('}');
        return sb.toString();
    }
}