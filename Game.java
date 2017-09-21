package rockPaperScissors;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Game class manages rounds, round statisitcs, and flow of control in general
 */
public class Game {
    private int wins = 0;
    private int losses = 0;
    private int ties = 0;
    private int rounds = 0;
    private ArrayList<String> winLossHistory = new ArrayList<String>();

    private MoveThrower humanThrower = new MoveThrower();
    private MoveThrower computerThrower = new MoveThrower();

    /**
     * Overloaded Constructor calls the playRound method a many times (number
     * specified by 'rounds') When the rounds are complete, the constructor
     * prints out statistics.
     *
     * @param rounds
     * @param humanSimType
     * @param smartAI
     * @param quiet
     */
    public Game(int rounds, int humanSimType, boolean smartAI, boolean quiet) {
        for (int i = 0; i < rounds; i++) {
            playRound(i, humanSimType, smartAI, quiet);
            this.rounds++;
        }
        Talker.printResults(this.wins, this.ties, this.losses, this.rounds);
    }

    /**
     * Plays a single round of the game
     */
    private void playRound(int round, int humanSimType, boolean smartAI, boolean quiet) {
        String result = "";
        String reason = "";

        // Select the AI to move either randomly or intelligently
        Move computerMove = computerThrower.computerMove(smartAI, humanThrower.moveSequence);
        computerThrower.moveSequence.add(computerMove);
        computerThrower.updateMoveDistribution();

        // Select the move based on the type of human to simulate update
        Move humanMove = humanThrower.simulatedHumanMove(humanSimType, round);
        humanThrower.moveSequence.add(humanMove);
        humanThrower.updateMoveDistribution();

        // Updates the fields based on results of the round
        String interaction = " " + Move.interaction(humanMove, computerMove) + " ";
        if (humanMove.winsAgainst(computerMove)) {
            this.wins++;
            result = "Win";
            reason = humanMove.name + interaction + computerMove.name;
            winLossHistory.add("W");
        } else {
            if (humanMove.initial.equals(computerMove.initial)) {
                this.ties++;
                result = "Tie";
                reason = computerMove.name + interaction + humanMove.name;
                winLossHistory.add("T");
            } else {
                this.losses++;
                result = "Loss";
                reason = computerMove.name + interaction + humanMove.name;
                winLossHistory.add("L");
            }
        }

        if(!quiet) {
            Talker.printRoundResult(humanMove, computerMove, result, reason);
        }
    }

    /**
     * Creates a manual instance of the game
     */
    public Game() {
        Talker.printWelcomeText();
        // While playRound method returns true, continues playing more rounds.
        while (playRound()) {
            rounds++;
        }
        Talker.printResults(this.wins, this.ties, this.losses, this.rounds);
        Talker.printEndgameInfo();
    }

    /**
     * Plays a round and prompts user whether to play another
     */
    private boolean playRound() {
        String humanMoveString = Talker.promptInput();

        if (humanMoveString.charAt(0) == 'Z' || humanMoveString.charAt(0) == 'z')
            return false;

        String result = "";
        String reason = "";

        Move humanMove = new Move(humanMoveString);
        humanThrower.moveSequence.add(humanMove);
        humanThrower.updateMoveDistribution();

        Move computerMove = MoveThrower.randomMove();
        computerThrower.moveSequence.add(computerMove);
        computerThrower.updateMoveDistribution();

        String interaction = " " + Move.interaction(humanMove, computerMove) + " ";

        if (humanMove.winsAgainst(computerMove)) {
            this.wins++;
            result = "Win";
            reason = humanMove.name + interaction + computerMove.name;
            winLossHistory.add("W");
        } else {
            if (humanMove.initial.equals(computerMove.initial)) {
                this.ties++;
                result = "Tie";
                reason = computerMove.name + interaction + humanMove.name;
                winLossHistory.add("T");
            } else {
                this.losses++;
                result = "Loss";
                reason = computerMove.name + interaction + humanMove.name;
                winLossHistory.add("L");
            }
        }

        Talker.printRoundResult(humanMove, computerMove, result, reason);
        return true;
    }
}
