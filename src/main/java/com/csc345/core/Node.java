package com.csc345.core;

import com.csc345.data.LinkedListSet;

/**
 * Represents a Node in a maze. Each Node contains its neighbors and connections in sets.
 * All nodes have an id within the maze. If the maze is a 1D array, this is the Node's index in the array.
 * All neighbors and connections are also stored as ids.
 */
public class Node {
    public int id;
    public LinkedListSet<Integer> neighbors;
    public LinkedListSet<Integer> connections;

    /**
     * Initializes a new node with a given id and set of neighbors.
     * 
     * @param id node's id
     * @param neighbors node's neighbors
     */
    public Node(int id, LinkedListSet<Integer> neighbors) {
        this.id = id;
        this.neighbors = neighbors;
        this.connections = new LinkedListSet<>();
    }

    /**
     * Connect the Node to another Node. 
     * This will add a connection to itself and the other Node.
     * 
     * @param other the Node to connect to
     */
    public void connect(Node other) {
        assert neighbors.contains(other.id) : "Cannot connect nodes that are not neighbors";
        assert other.neighbors.contains(id) : "Cannot connect nodes that are not neighbors";

        connections.add(other.id);
        other.connections.add(id);
    }

    /**
     * Disconnect from another Node. Both Nodes must already be connected.
     * This will remove the connection from both itself and the other Node.
     * 
     * @param other the Node to disconnect from
     */
    public void disconnect(Node other) {
        assert neighbors.contains(other.id) : "Cannot connect nodes that are not neighbors";
        assert other.neighbors.contains(id) : "Cannot connect nodes that are not neighbors";

        connections.remove(other.id);
        other.connections.remove(id);
    }
}
