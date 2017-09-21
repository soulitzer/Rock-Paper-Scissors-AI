# RPSLK-AI
A project for Honors Intro to Computer Science

Implements a rudimentary AI algorithm (markov chain transition matrix) to play Rock Paper Scissors Lizard Spock (An extension of Rock Paper Scissors).

Aggregates a probability distribution of human player's next move given their previous move. Then, select the move that would perform the best
against the distribution.

A two-dimensional array is used to keep track of the user's "habits," specifically the number of times the user transitions from a particular
move to another.
