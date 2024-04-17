package com.csc345.core;

/**
 * An abstract base class for a maze-generating maze-solving algorithm.
 * 
 * All algorithms store nodes and states, as well as a boolean flag to indicate if the algorithm is finished.
 * All algorithms can bew looped once, or finished immediately.
 */
public abstract class Algorithm {

    protected Node[] nodes;
    protected State[] states;

    private boolean isFinished = false;

    /**
     * Initialize a new Algorithm for a given set of nodes that represents a maze.
     * 
     * @param nodes the nodes that represent the maze
     */
    public Algorithm(Node[] nodes) {
        this.nodes = nodes;
        this.states = new State[nodes.length];
    }

    /**
     * Returns whether the algorithm is finished.
     * 
     * @return whether the algorithm is finished
     */
    public boolean isFinished() {
        return isFinished;
    }

    /**
     * Loop the algorithm once.
     */
    public void loopOnce() {
        assert !isFinished : "Cannot loop once after algorithm is finished";
        isFinished = loopOnceInternal();
    }

    /**
     * Finish the algorithm immediately.
     */
    public void finishImmediately() {
        do {
            loopOnce();
        } while (!isFinished);
    }

    /**
     * Loop the algorithm once. This method should be implemented by subclasses.
     * 
     * @return whether the algorithm is finished
     */
    abstract protected boolean loopOnceInternal();

    /**
     * Change the state of a node.
     * 
     * @param id id of the node
     * @param state new state of the node
     */
    protected void changeState(int id, State state) {
        this.states[id] = state;
    }

    /**
     * Returns all States for the nodes in the maze.
     * 
     * @return array of States for the nodes in the maze
     */
    public State[] getStates() {
        return states;
    }

    /**
     * Returns all Nodes in the maze.
     * 
     * @return array of Nodes in the maze
     */
    public Node[] getNodes() {
        return nodes;
    }
}
