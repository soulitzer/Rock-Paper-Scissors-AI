package rockPaperScissors;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Move class represents the "move" that the user throws in RPS / RPSLK.
*/
public class Move {
    public final static String ALL_MOVES = "RPSLK";
    public final static String[] NAMES = { "Rock", "Paper", "Scissors", "Lizard", "Spock" };
    public final static String[] WINS_AGAINST_ARRAY = { "SL", "RK", "PL", "PK", "RS" };
    public final static String[][] INTERACTIONS = { { "crushes", "crushes" }, { "covers", "disproves" },
            { "cuts", "decapitates" }, { "eats", "poisons" }, { "vaporizes", "smashes" } };

    public String initial = "";
    public int id = 0;
    public String name = "";

    public Move(String initial) {
        switch (initial.charAt(0)) {
        case 'R':
            this.id = 0;
            break;
        case 'P':
            this.id = 1;
            break;
        case 'S':
            this.id = 2;
            break;
        case 'L':
            this.id = 3;
            break;
        case 'K':
            this.id = 4;
            break;
        }

        this.name = this.NAMES[id];
        this.initial = initial;
    }

    /**
     * Compare moves and decide winner
     *
     * @param m
     */
    public boolean winsAgainst(Move m) {
        if (WINS_AGAINST_ARRAY[this.id].indexOf(m.initial) != -1)
            return true;
        else
            return false;
    }

    /**
     * Checks the interaction between two moves
     *
     * @param x
     * @param y
     */
    public static String interaction(Move x, Move y) {
        //Check the constant string array for the right verb to return
        if (x.winsAgainst(y)) {
            int index = WINS_AGAINST_ARRAY[x.id].indexOf(y.initial);
            return INTERACTIONS[x.id][index];
        } else if (y.winsAgainst(x)) {
            int index = WINS_AGAINST_ARRAY[y.id].indexOf(x.initial);
            return INTERACTIONS[y.id][index];
        } else {
            return "ties";
        }
    }
}
