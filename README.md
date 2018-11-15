# PongGame
Basic pong game with two different "AI" computers playing against each other.

Made using JavaFX.

Noteable Features:

The left and right paddles have been programmed with slightly different "AI"'s (Left paddle tracks the ball from a farther range than the right, which means the left will be more likely to win every round.

Human players may be enabled by commenting out the AI code and uncommenting the human player code.
	
The scoreboard updates after a player has won the round.

After reaching the game winning point(3), there is an end screen, which indicates which player has won.
	
Upon the end of a round, there is an intentional brief pause to allow users to know that the round has ended, and to prepare for the next round.
	
The ball accelerates until reaching an acceleration cap as the round progresses.
	
The ball bounces off the paddles at a random trajectory within a set range.
	
The ball bounces off the top and bottom walls in the appropriate x,y velocities to continue its path while staying in bounds.
	
A ping pong sound is triggered every time the ball bounces off a paddle.
