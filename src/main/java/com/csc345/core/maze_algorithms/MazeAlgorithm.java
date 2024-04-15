package com.csc345.core.maze_algorithms;

import com.csc345.core.Algorithm;
import com.csc345.core.State;
import com.csc345.core.Node;

/**
 * This is an abstract class for all the maze algorithms we're gonna write. It's like a template that
 * helps us set everything up for making mazes. It already handles some of the repetitive stuff,
 * like making sure all the nodes are ready to go by setting them to 'UNVISITED'.
 */
public abstract class MazeAlgorithm extends Algorithm {
    /**
     * Constructor that preps up all the nodes for a new maze. It goes through each node and sets its state
     * to 'UNVISITED' because, in the beginning, we haven't gone through any of them yet.
     *
     * @param nodes This is the bunch of nodes we'll be using to create the maze. Think of each node like
     *              a room or space in the maze that we need to visit.
     */

    public MazeAlgorithm(Node[] nodes) {
        super(nodes);
        for (int i = 0; i < nodes.length; i++) {
            changeState(i, State.UNVISITED);
        }
    }
    
}
