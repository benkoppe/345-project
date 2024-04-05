package com.csc345;

import com.csc345.core.Maze;
import com.csc345.core.maze_algorithms.algorithms.Backtracking;
import com.csc345.core.solve_algorithms.algorithms.AStar;

import com.csc345.data.RowCol;
import com.csc345.data.List;

public class Main {
    public static void main(String[] args) {
        // later, this will be the main entry point, but for now, we're just testing
        
        Maze maze = new Maze(10, 10);
        maze.printMaze();
        Backtracking backtracking = new Backtracking(maze.nodes);
        backtracking.finishImmediately();

        maze.printMaze();

        AStar aStar = new AStar(maze.nodes, 0, maze.nodes.length - 1, (startNodeId, endNodeId) -> {
            RowCol startRowCol = RowCol.idToRowCol(startNodeId, maze.getCols());
            RowCol endRowCol = RowCol.idToRowCol(endNodeId, maze.getCols());

            return (double) (Math.abs(startRowCol.getRow() - endRowCol.getRow()) + Math.abs(startRowCol.getCol() - endRowCol.getCol()));
        });

        aStar.finishImmediately();

        List<Integer> path = aStar.getPath();
        
        maze.printMazeSolution(path);

    }
}
