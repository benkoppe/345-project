package com.csc345;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import com.csc345.core.maze_algorithms.MazeAlgorithmType;
import com.csc345.core.solve_algorithms.SolveAlgorithmType;
import com.csc345.core.solve_algorithms.SolveAlgorithm;
import com.csc345.core.solve_algorithms.algorithms.AStar;
import com.csc345.core.maze_algorithms.algorithms.Backtracking;
import com.csc345.core.maze_algorithms.MazeAlgorithm;
import com.csc345.core.Node;
import com.csc345.core.Maze;
import com.csc345.gui.MazeController;
import com.csc345.gui.MazeImage;
import com.csc345.gui.animations.MazeTimer;
import com.csc345.gui.animations.SolveTimer;

/**
 * JavaFX App
 */
public class App extends Application {

    private static final double DEFAULT_WIDTH = 800;
    private static final double DEFAULT_HEIGHT = 800;

    private static final double DEFAULT_MAZE_WIDTH = 700;
    private static final double DEFAULT_MAZE_HEIGHT = 700;
    private static final int DEFAULT_MAZE_ROWS = 20;
    private static final int DEFAULT_MAZE_COLS = 20;
    private static final double DEFAULT_CELL_WALL_RATIO = 3.0;

    private BorderPane root;
    private Scene scene;

    private MazeImage mazeImage;
    private MazeController mazeController;
    private Node[] nodes;

    private MazeTimer mazeTimer;
    private SolveTimer solveTimer;


    @SuppressWarnings("exports")
    @Override
    public void start(Stage stage) {
        root = new BorderPane();
        scene = new Scene(root, DEFAULT_HEIGHT, DEFAULT_WIDTH);
        stage.setScene(scene);

        // SET UP EVERYTHING HERE
        setupMazeController();
        setupMaze(DEFAULT_MAZE_ROWS, DEFAULT_MAZE_COLS, DEFAULT_CELL_WALL_RATIO);

        mazeController.setGenerateButtonHandler((rows, cols, cellWallRatio, mazeAlgorithmType, animate) -> {
            generateMaze(rows, cols, cellWallRatio, mazeAlgorithmType, animate);
        });
        mazeController.setSolveButtonHandler((solveAlgorithmType, animate) -> {
            solveMaze(solveAlgorithmType, animate);
        });

        stage.show();
    }

    private void setupMaze(int rows, int cols, double cellWallRatio) {
        mazeImage = new MazeImage(rows, cols, cellWallRatio, DEFAULT_MAZE_WIDTH, DEFAULT_MAZE_HEIGHT);
        root.setCenter(mazeImage);
    }
    
    private void setupMazeController() {
        mazeController = new MazeController(DEFAULT_MAZE_ROWS, DEFAULT_MAZE_COLS, DEFAULT_CELL_WALL_RATIO);
        root.setTop(mazeController);
    }

    private void generateMaze(int rows, int cols, double cellWallRatio, MazeAlgorithmType mazeAlgorithmType,
            boolean animate) {
        if (rows <= 0 || cols <= 0 || cellWallRatio <= 0) {
            return;
        }

        setupMaze(rows, cols, cellWallRatio);

        nodes = Maze.createNodes(rows, cols);
        MazeAlgorithm mazeAlgorithm = getMazeAlgorithm(mazeAlgorithmType, nodes);

        mazeTimer = new MazeTimer(mazeImage, mazeAlgorithm, animate, null);
        mazeTimer.start();
    }
    
    private void solveMaze(SolveAlgorithmType solveAlgorithmType, boolean animate) {
        if (nodes == null || mazeTimer == null) {
            return;
        }
        if (!mazeTimer.isSolved()) {
            return;
        }

        SolveAlgorithm solveAlgorithm = getSolveAlgorithm(solveAlgorithmType, nodes, 0, nodes.length - 1);

        solveTimer = new SolveTimer(mazeImage, solveAlgorithm, animate);
        solveTimer.start();
    }


    private MazeAlgorithm getMazeAlgorithm(MazeAlgorithmType mazeAlgorithmType, Node[] nodes) {
        switch (mazeAlgorithmType) {
            default:
                return new Backtracking(nodes);
        }
    }

    private SolveAlgorithm getSolveAlgorithm(SolveAlgorithmType solveAlgorithmType, Node[] nodes, int startId, int endId) {
        switch (solveAlgorithmType) {
            default:
                return new AStar(nodes, startId, endId, Maze.createHeuristic(mazeImage.getCols()));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}