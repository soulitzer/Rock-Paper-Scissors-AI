package rockPaperScissors;

import java.util.Scanner;

/**
 * Runs several AI/Random vs simulated human games under
 * different settings
 */
public class HW1Runner {
    public static void main(String[] args) {
        final long startTime = System.currentTimeMillis();

        System.out.println("\n========================================\n"+
                            "========== RANDOM VS HUMAN_0 ===========\n"+
                            "========================================");
        Game myGame10 = new Game(5000, 0, false, true);

        System.out.println("\n========================================\n"+
                            "========== RANDOM VS HUMAN_1 ===========\n"+
                            "========================================");
        Game myGame11 = new Game(5000, 1, false, true);


        System.out.println("\n========================================\n"+
                            "============ AI VS HUMAN_0 =============\n"+
                            "========================================");
        Game myGame = new Game(10, 0, true, true);
        Game myGame1 = new Game(20, 0, true, true);
        Game myGame2 = new Game(50, 0, true, true);
        Game myGame3 = new Game(100, 0, true, true);
        Game myGame3_5 = new Game(1000, 0, true, true);
        Game myGame4 = new Game(5000, 0, true, true);

        System.out.println("\n========================================\n"+
                            "============ AI VS HUMAN_1 =============\n"+
                            "========================================");
        Game myGame5 = new Game(10, 1, true, true);
        Game myGame6 = new Game(20, 1, true, true);
        Game myGame7 = new Game(50, 1, true, true);
        Game myGame8 = new Game(100, 1, true, true);
        Game myGame8_5 = new Game(1000, 1, true, true);
        Game myGame9 = new Game(5000, 1, true, true);

        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime));

        Scanner keyboard = new Scanner(System.in);

        System.out.println("----------------------------------------------------");
        System.out.print("Play a game? Y/N ");

        String yesNoInputString = keyboard.nextLine();

        if (yesNoInputString.charAt(0) == 'Y' || yesNoInputString.charAt(0) == 'y') {
            System.out.println("----------------------------------------------------");
            Game myManualGame = new Game();
        }

        System.out.println("\n\nDone.");
    }
}
