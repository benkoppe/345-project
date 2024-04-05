package com.csc345;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import com.csc345.core.Node;
import com.csc345.core.State;
import com.csc345.core.Maze;
import com.csc345.gui.MazeImage;

import com.csc345.core.maze_algorithms.algorithms.Backtracking;
import com.csc345.core.solve_algorithms.algorithms.AStar;
import com.csc345.data.List;
import com.csc345.data.RowCol;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage primaryStage) {
        int rows = 100;
        int cols = 100;
        RowCol mazeSize = new RowCol(rows, cols);

        MazeImage root = new MazeImage(rows, cols, 3.0, 400, 400);

        Node[] nodes = Maze.createNodes(rows, cols);
        Backtracking backtracking = new Backtracking(nodes);

        backtracking.finishImmediately();
        State[] states = backtracking.getStates();

        root.update(nodes, states);
        root.redraw();

        AStar aStar = new AStar(nodes, 0, nodes.length - 1, (startNodeId, endNodeId) -> {
            RowCol startRowCol = RowCol.idToRowCol(startNodeId, mazeSize.getCol());
            RowCol endRowCol = RowCol.idToRowCol(endNodeId, mazeSize.getCol());

            return (double) (Math.abs(startRowCol.getRow() - endRowCol.getRow()) + Math.abs(startRowCol.getCol() - endRowCol.getCol()));
        });

        aStar.finishImmediately();

        List<Integer> path = aStar.getPath();
        root.drawPath(path);

        BorderPane pane = new BorderPane(root);
        scene = new Scene(pane, 640, 480);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}