# TSP:
Travelling salesman problem using heiuristics
TSP involves finding the shortest path that visits n specified locations, starting and ending at the same place and visiting the other n-1 destinations exactly once.
Suppose we have started at city 1 and after visiting some cities now we are in city j. Hence, this is a partial tour. We certainly need to know j, since this will determine which cities are most convenient to visit next. We also need to know all the cities visited so far, so that we don't repeat any of them. Hence, this is an appropriate sub-problem.
The opimal path to node j from node i is found using MST.

Code is in tsp_heuristic.py
Code ouput can be found in Tsp_output.txt

# GA :-

Given number of students and generate random marks for them and then and then define 3 defaults number of groups to be formed. Then using GA we  define accordingly which group should contain how many students ....The algo creates cluster of students  that is the gorup of similar objects, we have 3 cluster by default and final fitness value would decide which student belongs to which group aaccording to their marks.
The process of natural selection starts with the selection of fittest individuals from a population. They produce offspring which inherit the characteristics of the parents and will be added to the next generation. If parents have better fitness, their offspring will be better than parents and have a better chance at surviving.
Five phases are considered in a genetic algorithm.
* Initial population
* Fitness function
* Selection
* Crossover
* Mutation
Code is in GA.cpp
Code ouput can be found in genetic_output.txt

# min_maX (game of sticks):-
Mini-max algorithm is a recursive or backtracking algorithm which is used in decision-making and game theory. It provides an optimal move for the player assuming that opponent is also playing optimally.
Mini-Max algorithm uses recursion to search through the game-tree.
Here we have used this concept to create game of sticks...
The game can normally be represented as a tree where the nodes represent the current status of the game and the edges represent the moves. The game tree consists of all possible moves for the current players starting at the root and all possible moves for the next player as the children of these nodes, and so forth, as far into the future of the game as desired. The leaves of the game tree represent terminal positions as one where the outcome of the game is clear (a win, a loss, a draw, a payoff).The number of sticks that can be pickup is 1-3. Whoever will be the last player to pick the last stick would be the loser.
The game consist of 3 modes:
1. Human vs Human
2. Human vs AI
3. AI vs AI
Code is in min_max.cpp 
Code ouput can be found in game_of_sticks__output.txt
