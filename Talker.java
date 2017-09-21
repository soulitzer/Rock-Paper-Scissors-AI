package rockPaperScissors;

import java.util.Scanner;

/**
 * This class handles i/o: prompts for user input, generates output based on
 * parameters
 */

public final class Talker {
    /**
     * Method prompts for user to enter in a character and handles errors in
     * user input. (multiple characters could be entered but only the first
     * matters.) If an invalid character is entered the method prompts the user
     * again and again until a valid character is entered.
     *
     * @return The character is returned as a String
     */
    public static String promptInput() {
        // variable for user input
        Scanner keyboard = new Scanner(System.in);
        boolean proceed = false;
        String moveString = "";

        while (!proceed) {
            try {
                // Prompt for input and store said input in a variable
                System.out.print("Throw either 'R', 'P', 'S', 'L', or 'K' (enter 'Z' to end): ");
                moveString = keyboard.next();

                moveString = filter(moveString);
                proceed = true;

            } catch (IllegalArgumentException e) {
                System.out.println("Your input is invalid, try again.");
                proceed = false;
            }
        }

        return moveString;
    }

    /**
     * prints out welcome text and instructions
     */
    public static void printWelcomeText() {
        System.out.println("Welcome to Rock, Paper, Scissors, Lizard, Spock");
        System.out.println("\nHere are the rules:\n");
        System.out.println("1. Rock beats scissors and Lizard");
        System.out.println("2. Paper beats spock and Rock");
        System.out.println("3. Scissors beats paper and lizard");
        System.out.println("4. Lizard beats spock and paper");
        System.out.println("5. Spock beats rock and scissors");
        System.out.println("6. If two of the same move are played, its a tie");
        System.out.println("\nHave fun!\n");
    }

    public static void printEndgameInfo() {
        System.out.println("----------------------------------------");
        System.out.println("----------------------------------------");
        System.out.println("\nThanks for playing.\n");
        System.out.println("----------------------------------------");
    }

    /**
     * Prints output based on the outcome of a particular round
     *
     * @param humanMove
     * @param computerMove
     * @param result
     * @param reason
     */
    public static void printRoundResult(Move humanMove, Move computerMove, String result, String reason) {
        // Output player's moves and the result, move histories, and overall
        // win-ties-loss
        System.out.println("YOUR MOVE: " + humanMove.name + "; AI MOVE: " + computerMove.name + "; RESULT: " + result);
        System.out.println("Reason: " + reason);
        System.out.println("----------------------------------------------------");
    }

    /**
     * Prints out results based on results
     *
     * @param wins
     * @param ties
     * @param losses
     * @param rounds
     */
    public static void printResults(int wins, int ties, int losses, double rounds) {
        // Results
        System.out.println("----------------------------------------");
        System.out.println("ROUNDS PLAYED: " + rounds);
        System.out.println("WINS: " + wins + " TIES: " + ties + " LOSSES: " + losses);
        System.out.println("WINS/ROUNDS: " + ((double) wins / rounds) * 100 + "%");
        System.out.println("TIES/ROUNDS: " + ((double) ties / rounds) * 100 + "%");
        System.out.println("AI WIN-RATE: " + ((double) losses / rounds) * 100 + "%");

    }

    /**
     * Converts the string entered to uppercase
     *
     * @param s
     */
    public static String filter(String s) {
        int firstCharToInt = (int) s.charAt(0);

        // Check if its a letter
        if (!(firstCharToInt >= 97 && firstCharToInt <= 122) && !(firstCharToInt >= 65 && firstCharToInt <= 90))
            throw new IllegalArgumentException();

        // If its a lowercase letter convert it to uppercase
        if (firstCharToInt >= 97 && firstCharToInt <= 122)
            firstCharToInt -= 32;

        String firstCharAsString = "" + (char) firstCharToInt;
        if (!firstCharAsString.equals("Z") && !Move.ALL_MOVES.contains(firstCharAsString))
            throw new IllegalArgumentException();
        // convert back to String
        return "" + (char) firstCharToInt;
    }

}
