package com.csc345.core.maze_algorithms;

import com.csc345.core.Algorithm;
import com.csc345.core.State;
import com.csc345.core.Node;

public abstract class MazeAlgorithm extends Algorithm {

    public MazeAlgorithm(Node[] nodes) {
        super(nodes);
        for (int i = 0; i < nodes.length; i++) {
            changeState(i, State.UNVISITED);
        }
    }
    
}
