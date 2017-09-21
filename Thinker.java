package rockPaperScissors;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Implements an Markov Chain algorithm to select the computer's next move.
 */
public class Thinker {
    /**
     * T[i][j] is the number of times the other player has selected move j given move i
     */
    public int[][] transitionCountMatrix = new int[Move.ALL_MOVES.length()][Move.ALL_MOVES.length()];
    /**
     * T[i][j] stores the probability the other player will play move j given move i
     */
    public double[][] transitionProbabilityMatrix = new double[Move.ALL_MOVES.length()][Move.ALL_MOVES.length()];

    /**
     * Computes the next move in two steps: 
     *
     * Step 1: Aggregate a transitional probability matrix P, where P[i][j] is 
     * the probability of j being the human's next move given that i is the
     * human's last move.
     *
     * Step 2: We can score each of the computer's possible moves by summing the
     * probabilities weighted as follows (loss: -1, tie: 0, win: 1).
     * Finally, the move with the highest score is selected.
     *
     * -------------------------------------------------------------------------
     *
     * Performance: Against more predictable simulated-humans such as the
     * Repeater, or combinations of Cycler and Repeater (human_0), AI reaches
     * 80% to near 100% win-rate within 20-100 rounds. Against less predictable
     * simulated-humans (human_1 is a mixture of Cycler, Repeater, Balancer, and
     * random), AI plateaus at 50% to 60% after many rounds. This is still a
     * drastic improvement over randomly selecting moves, which converges to win-rates
     * around 30% to 40%.
     *
     * Results (Win-rate = Wins / (Ties + Losses))
     *
     * 100 Rounds AI Win-rate: 82.0% against human_0
     *
     * 5000 Rounds AI Win-rate: 87.9% against human_0
     *
     * 100 Rounds AI Win-rate: 52.0% against human_1
     *
     * 5000 Rounds AI Win-rate: 60.2% against human_1
     *
     * 5000 Rounds Random Win-rate 37.9% against human_0
     *
     * 5000 Rounds Random Win-rate 40.0% against human_1
     *
     * -------------------------------------------------------------------------
     *
     * @param humanMoveSequence
     */
    public Move computeMove(ArrayList<Move> humanMoveSequence) {
        // If there is no data, play a random move
        if (humanMoveSequence.size() < 2) {
            Move theMove = MoveThrower.randomMove();
            return theMove;
        } else {
            int size = humanMoveSequence.size();
            int numMoves = Move.ALL_MOVES.length();

            // Retrieve the last move and the move before that
            int currentId = humanMoveSequence.get(size - 1).id;
            int previousId = humanMoveSequence.get(size - 2).id;

            // Step 1: Update the transition probability matrix with new data
            for (int i = 0; i < numMoves; i++) {
                int ithRowSum = 0;
                for (int j = 0; j < numMoves; j++) {
                    ithRowSum += transitionCountMatrix[i][j];
                }
                for (int j = 0; j < numMoves; j++) {
                    transitionProbabilityMatrix[i][j] = (double) transitionCountMatrix[i][j] / ithRowSum;
                }
            }

            // Step 2: The next move is scored with a weighting system.
            // The move with the greatest score is selected.
            double maxScore = 0;
            int maxProbIndex = 0;
            // Looping over all possible computer's moves
            for (int i = 0; i < numMoves; i++) {
                double score = 0;
                // Looping over all possible human moves
                for (int j = 0; j < numMoves; j++) {

                    // We have the two moves
                    Move possibleHumanMove = new Move("" + Move.ALL_MOVES.charAt(j));
                    Move possibleComputerMove = new Move("" + Move.ALL_MOVES.charAt(i));

                    // Create the score based on the outcome of two moves and
                    // the probability of this happening

                    // Calculate the weight
                    int multiplier = 0;
                    // Multiplier is 0 for tie, 1 for win, -1 for loss
                    if (possibleHumanMove.id != possibleComputerMove.id) {
                        multiplier = possibleComputerMove.winsAgainst(possibleHumanMove) ? 1 : -1;
                    }

                    // Sum up all the partial scores (weight * probability) to
                    // the score
                    score += transitionProbabilityMatrix[currentId][j] * multiplier;
                }
                // Finds maximum score
                if (score > maxScore) {
                    maxScore = score;
                    maxProbIndex = i;
                }
            }
            Move computedMove = new Move("" + Move.ALL_MOVES.charAt(maxProbIndex));

            transitionCountMatrix[previousId][currentId]++;

            return computedMove;
        }
    }
}
