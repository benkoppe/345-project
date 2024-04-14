package com.csc345.core.maze_algorithms;

import com.csc345.core.Algorithm;
import com.csc345.core.State;
import com.csc345.core.Node;

/**
 * This abstract class serves as a starting point for all maze algorithms. It extends the Algorithm class,
 * which means it inherits all its properties and methods, but here we're specifically focusing on mazes.
 * When we create a new maze algorithm, we start by setting all nodes to UNVISITED, so we know we haven't 
 * been there yet.
 */

/**
 * It initializes a new MazeAlgorithm with a given array of nodes.
 * Sets each node's state to UNVISITED to indicate that none of the nodes have been explored yet.
 * This setup is crucial for starting the maze generation process properly.
 *
 * @param nodes An array of nodes to be used in the maze. Each node should already be instantiated before it is passed here.
 *              The constructor marks all these nodes as UNVISITED.
 */
public abstract class MazeAlgorithm extends Algorithm {

    public MazeAlgorithm(Node[] nodes) {
        super(nodes);
        for (int i = 0; i < nodes.length; i++) {
            changeState(i, State.UNVISITED);
        }
    }
    
}
