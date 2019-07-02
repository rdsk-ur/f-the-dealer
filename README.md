# F The Dealer Simulation

## The game.Game
F the Dealer is a drinking game with poker playing cards. One player starts as the dealer and holds the deck of cards.
The player next to him now has to guess, what number lies on top of the stack.
If he's correct, good, if not, he gets a second chance:
The dealer now tells the current guesser if the number of the correct card is above or below his first guess.
He gets to guess again.
Now depending on his guesses they have to drink:
- The dealer 5 times if the guesser was correct on first try.
- The dealer 2 times if the guesser was correct on second try.
- The guesser 2 times if he was wrong on both tries.

After that each card is put on the table for everyone to see and current guesser changes to the next player.
The dealer doesn't change until two guessers are wrong in a row.

## Motivation to simulate
Of course it would be best to calculate probabilities for each card at each turn, to get the best result.
But because that's not a doable strategy in real life, there is the question whether one can find an easy rule of thumb to help choosing a card.
The simulation focuses on comparing different strategies by counting how often one guesses right/wrong or has to drink.

## Insight in the simulation
The main class is game.Game, it contains the game's logic and keeps track of the scores with a Score object.
The game.Player class is an abstract super class and defines which actions a player must have implemented (firstGuess, secondGuess, reset).
The game.Deck class holds a representation (optimized) of a deck of cards. A player can post requests to it (e.g. how many cards there are etc.).

## Output and generating game data
The current purpose of the main method in the game.Game class is a call to generateDataset() which runs lots of rounds and saves them in a .csv file for each player.
