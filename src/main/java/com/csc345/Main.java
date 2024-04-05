package com.csc345;

import com.csc345.core.Maze;
import com.csc345.core.Node;
import com.csc345.core.maze_algorithms.algorithms.Backtracking;
import com.csc345.core.solve_algorithms.algorithms.AStar;

import com.csc345.data.RowCol;
import com.csc345.data.List;

public class Main {
    public static void main(String[] args) {
        // later, this will be the main entry point, but for now, we're just testing
        
        RowCol mazeSize = new RowCol(10, 10);
        Node[] maze = Maze.createNodes(mazeSize.getRow(), mazeSize.getCol());
        
        Maze.printMaze(maze, mazeSize.getCol());

        Backtracking backtracking = new Backtracking(maze);
        backtracking.finishImmediately();

        Maze.printMaze(maze, mazeSize.getCol());

        AStar aStar = new AStar(maze, 0, maze.length - 1, (startNodeId, endNodeId) -> {
            RowCol startRowCol = RowCol.idToRowCol(startNodeId, mazeSize.getCol());
            RowCol endRowCol = RowCol.idToRowCol(endNodeId, mazeSize.getCol());

            return (double) (Math.abs(startRowCol.getRow() - endRowCol.getRow()) + Math.abs(startRowCol.getCol() - endRowCol.getCol()));
        });

        aStar.finishImmediately();

        List<Integer> path = aStar.getPath();
        
        Maze.printMazeSolution(path, maze, mazeSize.getCol());

    }
}
