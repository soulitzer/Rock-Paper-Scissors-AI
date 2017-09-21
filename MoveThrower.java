package rockPaperScissors;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * This class remembers moves that have been thrown and throws moves.The class
 * contains different methods of generating the next move.
 */
public class MoveThrower {
    /**
     * Move sequence records all the moves played
     */
    public ArrayList<Move> moveSequence = new ArrayList<Move>();
    /**
     * Thinker helper class instance
     */
    public Thinker myThinker = new Thinker();
    /**
     * Distribution of moves categorized by move type
     */
    public int[] moveCountArray = new int[Move.ALL_MOVES.length()];

    /**
     * Computes the next move using the Thinker class
     * 
     * @return
     */
    public Move computerMove(boolean smartAI, ArrayList<Move> humanMoveSequence) {
        if (smartAI)
            return myThinker.computeMove(humanMoveSequence);
        else
            return MoveThrower.randomMove();
    }

    /**
     * Returns a random Move
     * 
     * @return a new Move instance of random type
     */
    public static Move randomMove() {
        int randomNum = ((int) Math.floor(Math.random() * Move.ALL_MOVES.length()));
        Move randomMove = new Move("" + Move.ALL_MOVES.charAt(randomNum));

        return randomMove;
    }

    /**
     * Returns the next Move in the sequence "RPSRPSRPS"
     * 
     * @return a new Move instance
     */
    public Move cycleMove() {
        if (moveSequence.size() == 0)
            return MoveThrower.randomMove();
        else {
            String currentMove = moveSequence.get(moveSequence.size() - 1).initial;
            int index = Move.ALL_MOVES.indexOf(currentMove); // Index of current
                                                                // move
            // Create a new move with the type of the move next in the cycle
            Move theMove = new Move("" + Move.ALL_MOVES.charAt((index + 1) % Move.ALL_MOVES.length()));

            return theMove;
        }
    }

    /**
     * Returns the last move thrown, if there are no moves thrown, throw a
     * random move.
     * 
     * @return a new Move instance with the chosen type
     */
    public Move repeatMove() {
        if (moveSequence.size() == 0)
            return MoveThrower.randomMove();
        else
            return moveSequence.get(moveSequence.size() - 1);
    }

    /**
     * Uses the move distribution to see which move has been used the least,
     * then, return that move.
     * 
     * @return a new Move instance with the chosen type
     */
    public Move rebalancer() {
        if (moveSequence.size() == 0) {
            return MoveThrower.randomMove();
        } else {
            int indexOfLeast = 0;

            for (int i = 0; i < moveCountArray.length; i++)
                if (moveCountArray[i] < (moveCountArray[indexOfLeast]))
                    indexOfLeast = i;

            Move theMove = new Move("" + Move.ALL_MOVES.charAt(indexOfLeast));

            return theMove;
        }
    }

    /**
     * Generates a move simulating the human.
     * 
     * @param humanSimType
     *        Represents the type of human to simulate. This could either be
     *        0 or 1, simple or complex. Complex is harder for AI to beat
     * @param round
     *        The current round, the human throws different moves depending
     *        on the round
     * @return
     */
    public Move simulatedHumanMove(int humanSimType, int round) {
        // Select the the human move using one of two set strategies
        Move humanMove;
        switch (humanSimType) {
        case 0:
            // Human_0's move is a mixture of The Repeater and The Rotator if
            // round is divisible by 5 the human repeats its previous move,
            // otherwise it cycles to the next move in the sequence
            if (round % 5 == 0) {
                humanMove = this.repeatMove();
            } else {
                humanMove = this.cycleMove();
            }
            break;
        case 1:
        default:
            // Human_1's move is a mixture of The Rotator, The Repeater,
            // The Rebalancer. If the round is divisible by 3 it cycles to the
            // next move in the sequence. If its not divisible by 3 but
            // divisible by 5, it repeats the previous move. If round is not
            // divisible by 3 or 5, but divisible by 7 or 12, the human tries to
            // "rebalance". Finally if round is not divisible by 3, 5, 7, or 13,
            // a random move is played.
            if (round % 3 == 0) {
                humanMove = this.cycleMove();
            } else if (round % 5 == 0) {
                humanMove = this.repeatMove();
            } else if (round % 7 == 0 || round % 13 == 0) {
                humanMove = this.rebalancer();
            } else {
                humanMove = MoveThrower.randomMove();
            }
            break;
        }
        return humanMove;
    }

    /**
     * Prints out contents of the corresponding field
     */
    public void printMoveSequence() {
        for (int i = 0; i < moveSequence.size(); i++)
            System.out.print(moveSequence.get(i).initial + " ");
    }

    /**
     * How many each move type has been used by the human returned in an integer
     * array
     */
    public void updateMoveDistribution() {
        for (int i = 0; i < moveCountArray.length; i++)
            if (Move.ALL_MOVES.indexOf(moveSequence.get(moveSequence.size() - 1).initial) == i)
                moveCountArray[i]++;
    }
}
