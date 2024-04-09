package com.csc345.core.maze_algorithms.algorithms;

import com.csc345.core.Node;
import com.csc345.core.State;
import com.csc345.core.maze_algorithms.MazeAlgorithm;
import com.csc345.data.UnionFind;
import com.csc345.data.LinkedListSet;
import com.csc345.data.List;

public class Kruskals extends MazeAlgorithm {
    
    private UnionFind<Integer> unionFind;
    private List<Edge> edges;
    private int currIndex;
    private Edge currEdge;

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
                edges.add(new Edge(node.id, neighborId));
            });
        }

        edges.shuffle();
    }

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
    
    private class Edge {
        final int node1;
        final int node2;
        
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
