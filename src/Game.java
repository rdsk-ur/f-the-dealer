import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Game {
    public static void main(String[] args) {
        Deck deck = new Deck(13, 4);
        new Game(new Player[]{
                new HighestProbabilityPlayer(deck),
                new HighestExpectationValuePlayer(deck)
        }, deck).generateDataset("simulations", 1_000_000, 10 * 60);
    }

    private Player[] players;
    private Deck deck;
    private Scores scores;
    private int starter;

    public Game(Player[] players, Deck deck) {
        this.players = players;
        this.deck = deck;
        this.scores = new Scores(players.length);
    }

    public Scores run() {
        int currentDealer = new Random().nextInt(players.length);
        while (deck.hasNext()) {
            int currentGuesser = (currentDealer + 1) % players.length;
            int wrongsForDealerChange = 2;
            while (deck.hasNext() && wrongsForDealerChange > 0) {
                int correct = deck.getRandom();
                //System.out.println("\nDrawn: " + correct);
                int guess = players[currentGuesser].firstGuess();
                if (guess == correct) {
                    scores.newChugs(currentGuesser, currentDealer, Constants.FIRST);
                    scores.guessedRight(currentGuesser);
                    wrongsForDealerChange = 2;
                    //System.out.println("Correct! First");
                } else {
                    guess = players[currentGuesser].secondGuess(guess, correct > guess);
                    if (guess == correct) {
                        scores.newChugs(currentGuesser, currentDealer, Constants.SECOND_RIGHT);
                        scores.guessedRight(currentGuesser);
                        wrongsForDealerChange = 2;
                        //System.out.println("Correct! Second");
                    } else {
                        scores.newChugs(currentDealer, currentGuesser, Constants.SECOND_WRONG);
                        scores.guessedWrong(currentGuesser);
                        wrongsForDealerChange--;
                    }
                }
                deck.remove(correct);
                currentGuesser = (currentGuesser + 1) % players.length;
                if (currentGuesser == currentDealer) {
                    currentGuesser = (currentGuesser + 1) % players.length;
                }
            }
            currentDealer = (currentDealer + 1) % players.length;
        }
        return scores;
    }

    public void runAndResetNTimes(int n) {
        int[] winnerChugs = new int[players.length];
        int[] winnerGuess = new int[players.length];
        for (int i = 0; i < n; i++) {
            scores = run();
            for (Integer p : scores.bestChugsDrunk()) {
                winnerChugs[p]++;
            }
            for (Integer p : scores.bestGuessesRight()) {
                winnerGuess[p]++;
            }
            for (int j = 0; j < players.length; j++) {
                players[j].reset();
            }
            deck.reset();
            scores = new Scores(players.length);
        }
        System.out.println("Least chugged: " + Arrays.toString(winnerChugs) + " times");
        System.out.println("Best guessed:  " + Arrays.toString(winnerGuess) + " times");
    }

    /**
     * Generate a dataset to be saved in folderpath and to be analyzed seperately (greetings from Python).
     * Each player gets its own file.
     * Naming scheme: .../folderpath/gameid/playerid.csv.
     * Folder will contain a info.json with information about the game.
     *
     * @param folderpath the folder where to save the data
     * @param n          how many rounds to simulate
     * @param time       after how many seconds the simulation should stop
     */
    public void generateDataset(String folderpath, int n, long time) {
        long start = System.currentTimeMillis();
        int round = 0;

        ArrayList<Integer[][]> data = new ArrayList<>();

        while (round < n && System.currentTimeMillis() - start < time * 1000) {
            System.out.println(round);
            data.add(run().getScores());

            //RESET
            for (int j = 0; j < players.length; j++) {
                players[j].reset();
            }
            deck.reset();
            scores = new Scores(players.length);
            round++;
        }
        System.out.println("Stopped simulation. Now saving...");

        String gameid = "game" + Long.toHexString(start);
        File folder = new File(folderpath);
        folder.mkdir();
        folder = new File(folder, gameid);
        folder.mkdir();
        for (int p = 0; p < players.length; p++) {
            StringBuilder sb = new StringBuilder();
            sb.append("Round,chugsDrunk,chugsDealt,guessesRight,guessesWrong\n");
            for (int r = 0; r < round; r++) {
                sb.append(r);
                for (int c = 0; c < 4; c++) {
                    sb.append(",");
                    sb.append(data.get(r)[c][p]);
                }
                sb.append("\n");
            }
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File(folder, "player" + p + ".csv")));
                writer.write(sb.toString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //info.json
        StringBuilder json = new StringBuilder();
        json.append("{\"players\":[\"");
        json.append(players[0].getStrategyName());
        json.append("\"");
        for (int i = 1; i < players.length; i++) {
            json.append(",\"");
            json.append(players[0].getStrategyName());
            json.append("\"");
        }
        json.append("],\"rounds\":");
        json.append(round);
        json.append(",");
        json.append("\"deck\":{\"colors\":");
        json.append(deck.getColors());
        json.append(",\"numbers\":");
        json.append(deck.differentNumbers());
        json.append("},");
        json.append("\"constants\":{\"first\":");
        json.append(Constants.FIRST);
        json.append(",\"second_right\":");
        json.append(Constants.SECOND_RIGHT);
        json.append(",\"second_wrong\":");
        json.append(Constants.SECOND_WRONG);
        json.append("}");

        //extensible

        json.append("}");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(folder, "info.json")));
            writer.write(json.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
