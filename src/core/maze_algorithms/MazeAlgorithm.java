package core.maze_algorithms;

import core.Algorithm;
import core.State;
import core.Node;

public abstract class MazeAlgorithm extends Algorithm {

    public MazeAlgorithm(Node[] nodes) {
        super(nodes);
        for (int i = 0; i < nodes.length; i++) {
            changeState(i, State.UNVISITED);
        }
    }
    
}
