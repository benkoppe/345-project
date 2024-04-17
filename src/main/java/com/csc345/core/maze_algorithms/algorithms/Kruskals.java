package com.csc345.core.maze_algorithms.algorithms;

import com.csc345.core.Node;
import com.csc345.core.State;
import com.csc345.core.maze_algorithms.MazeAlgorithm;
import com.csc345.data.UnionFind;
import com.csc345.data.LinkedListSet;
import com.csc345.data.List;

/**
 * This class uses Kruskal's algorithm to generate a maze. It's kind of like building a road system
 * without creating any loops and making sure all the parts are reachable. We use something called a
 * union-find disjoint set to keep track of which parts are connected to which, so we don't accidentally make a loop.
 */
public class Kruskals extends MazeAlgorithm {
    
    private UnionFind<Integer> unionFind;
    private List<Edge> edges;
    private int currIndex;
    private Edge currEdge;

    /**
     * Sets up the maze with the nodes provided and prepares all the tools we'll need,
     * like the union-find and a list of all edges (possible paths between nodes).
     *
     * @param nodes Array of nodes that the maze will consist of.
     */
    public Kruskals(Node[] nodes) {
        super(nodes);

        LinkedListSet<Integer> nodeSet = new LinkedListSet<>();
        for (Node node : nodes) {
            nodeSet.add(node.id);
        }

        unionFind = new UnionFind<>(nodeSet);
        currIndex = 0;
        currEdge = null;

        edges = new List<>();

        for (Node node : nodes) {
            node.neighbors.forEach(neighborId -> {
                edges.append(new Edge(node.id, neighborId));
            });
        }

        edges.shuffle();
    }

    /**
     * In each step of the algorithm here we pick an edge. If it doesn't form a loop with the already
     * picked edges, we add it to our maze. This method figures out one step at a time whether
     * we can add the next edge to our growing maze.
     *
     * @return true if all nodes are connected, false otherwise (meaning there are more steps to do).
     */
    @Override
    protected boolean loopOnceInternal() {
        // extra loop for selecting edges (better visual)
        if (currEdge == null) {
            currEdge = edges.get(currIndex++);
            changeState(currEdge.node1, State.VISITING);
            return false;
        }

        Edge edge = currEdge;
        currEdge = null;

        changeState(edge.node1, State.VISITING);

        // catch already connected nodes
        if (unionFind.inSameSet(edge.node1, edge.node2)) {
            changeState(edge.node1, State.VISITED);
            return false;
        }

        // merge the sets of the two nodes and connect them
        unionFind.union(edge.node1, edge.node2);
        nodes[edge.node1].connect(nodes[edge.node2]);

        changeState(edge.node1, State.VISITED);
        changeState(edge.node2, State.VISITED);

        return unionFind.numberOfSets() == 1;
    }

    /**
     * Represents a connection between two nodes in the maze. This helps us keep the edges in order,
     * which is useful for the union-find structure.
     */
    private class Edge {
        final int node1;
        final int node2;

        /**
         * Create an edge between two nodes. We always store the smaller node ID first to keep things orderly.
         *
         * @param node1 ID of one node.
         * @param node2 ID of the other node.
         */
        public Edge(int node1, int node2) {
            // add nodes in sorted order
            if (node1 < node2) {
                this.node1 = node1;
                this.node2 = node2;
            } else {
                this.node1 = node2;
                this.node2 = node1;
            }
        }
    }
}
