package com.csc345;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import com.csc345.data.functionals.BiFunction;
import com.csc345.core.maze_algorithms.MazeAlgorithmType;
import com.csc345.core.solve_algorithms.SolveAlgorithmType;
import com.csc345.core.solve_algorithms.SolveAlgorithm;
import com.csc345.core.maze_algorithms.MazeAlgorithm;
import com.csc345.core.Node;

import com.csc345.core.Maze;
import com.csc345.gui.MazeController;
import com.csc345.gui.MazeImage;
import com.csc345.gui.MazeController.MazePosition;
import com.csc345.gui.animations.MazeTimer;
import com.csc345.gui.animations.SolveTimer;

/**
 * JavaFX App
 */
public class App extends Application {

    private static final String APP_NAME = "Maze Solver";

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
        mazeController.setClearSolutionButtonHandler(() -> {
            mazeImage.redraw();
        });
        mazeController.setSetStartEndButtonHandler((startPosition, endPosition) -> {
            setStartEnd(startPosition, endPosition);
        });

        stage.setTitle(APP_NAME);
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
        MazeAlgorithm mazeAlgorithm = mazeAlgorithmType.getAlgorithm(nodes);

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

        if (!mazeImage.isStartEndSet()) {
            mazeController.runSetStartEndButtonHandler();
        }

        int cols = mazeImage.getCols();
        BiFunction<Integer, Integer, Double> heuristicFunction = Maze.createHeuristic(cols);

        SolveAlgorithm solveAlgorithm = solveAlgorithmType.getAlgorithm(nodes, mazeImage.getStartId(), mazeImage.getEndId(), heuristicFunction);

        solveTimer = new SolveTimer(mazeImage, solveAlgorithm, animate);
        solveTimer.start();
    }

    private void setStartEnd(MazePosition startPosition, MazePosition endPosition) {
        if (nodes == null) {
            return;
        }

        if (startPosition == null || endPosition == null) {
            return;
        }

        int rows = mazeImage.getRows();
        int cols = mazeImage.getCols();

        int startId = startPosition.getId(rows, cols);
        int endId = endPosition.getId(rows, cols);

        mazeImage.updateStartEnd(startId, endId);
        mazeImage.redraw();
    }

    public static void main(String[] args) {
        launch(args);
    }
}