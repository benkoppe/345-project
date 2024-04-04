package core.solve_algorithms.algorithms;

import core.solve_algorithms.SolveAlgorithm;
import data.PriorityQueue;
import data.HashMap;
import data.functionals.BiFunction;
import data.List;
import core.Node;
import core.State;

public class AStar extends SolveAlgorithm {
    
    private HashMap<Integer, Integer> cameFrom = new HashMap<>();
    private HashMap<Integer, Double> gScores = new HashMap<>();
    private HashMap<Integer, Double> fScores = new HashMap<>();

    private PriorityQueue<Integer, Double> openQueue;
    private BiFunction<Integer, Integer, Double> heuristic;

    public AStar(Node[] nodes, int startId, int endId, BiFunction<Integer, Integer, Double> heuristic) {
        super(nodes, startId, endId);
        this.heuristic = heuristic;

        this.openQueue = new PriorityQueue<>(id -> fScores.getOrDefault(id, Double.POSITIVE_INFINITY));

        openQueue.insert(startId);
        gScores.put(startId, 0.0);
        fScores.put(startId, heuristic.apply(startId, endId));
        changeState(startId, State.VISITING);
    }

    @Override
    protected boolean loopOnceInternal() {
        // add check for if openQueue is empty?

        int currentId = openQueue.poll();

        if (currentId == endId) {
            // end is found
            this.path = reconstructPath(cameFrom, endId);
            return true;
        }

        Node current = nodes[currentId];

        current.connections.forEach(connectionId -> {
            double tentativeGScore = gScores.get(currentId) + 1; // 1 is the distance between two nodes

            if (!gScores.containsKey(connectionId) || tentativeGScore < gScores.get(connectionId)) {
                cameFrom.put(connectionId, currentId);
                gScores.put(connectionId, tentativeGScore);
                double fScore = tentativeGScore + heuristic.apply(connectionId, endId);
                fScores.put(connectionId, fScore);

                if (!openQueue.contains(connectionId)) {
                    openQueue.insert(connectionId);
                }
            }
        });

        return false;
    }

    private static List<Integer> reconstructPath(HashMap<Integer, Integer> cameFrom, int firstId) {
        List<Integer> path = new List<>();
        path.add(firstId);

        Integer currentId = firstId;

        do {
            currentId = cameFrom.get(currentId);
            if (currentId == null) break;
            path.add(currentId);
        } while (true);

        // reverse path
        path.reverse();

        return path;
    }
}
