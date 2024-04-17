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


    /**
     * This method is called by the JavaFX runtime when the application is launched.
     * 
     * @param stage The primary stage for the application
     */
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
            mazeImage.update(nodes, null);
            mazeImage.redraw();
        });
        mazeController.setSetStartEndButtonHandler((startPosition, endPosition) -> {
            setStartEnd(startPosition, endPosition);
        });

        stage.setTitle(APP_NAME);
        stage.show();
    }

    /**
     * Set up a new maze image and adds it to the root pane.
     * 
     * @param rows number or rows in the maze
     * @param cols number of columns in the maze
     * @param cellWallRatio ratio of cell size to wall size
     */
    private void setupMaze(int rows, int cols, double cellWallRatio) {
        mazeImage = new MazeImage(rows, cols, cellWallRatio, DEFAULT_MAZE_WIDTH, DEFAULT_MAZE_HEIGHT);
        root.setCenter(mazeImage);
    }
    
    /**
     * Set up a new maze controller and adds it to the root pane.
     */
    private void setupMazeController() {
        mazeController = new MazeController(DEFAULT_MAZE_ROWS, DEFAULT_MAZE_COLS, DEFAULT_CELL_WALL_RATIO);
        root.setTop(mazeController);
    }

    /**
     * Generates a new maze with the given parameters.
     * 
     * @param rows number of rows in the maze
     * @param cols number of columns in the maze
     * @param cellWallRatio ratio of cell size to wall size
     * @param mazeAlgorithmType type of maze generation algorithm to use
     * @param animate whether or not to animate the maze generation
     */
    private void generateMaze(int rows, int cols, double cellWallRatio, MazeAlgorithmType mazeAlgorithmType,
            boolean animate) {
        if (rows <= 0 || cols <= 0 || cellWallRatio <= 0) {
            return; // catch invalid input
        }

        setupMaze(rows, cols, cellWallRatio); // set up new maze image

        nodes = Maze.createNodes(rows, cols); // create new nodes
        MazeAlgorithm mazeAlgorithm = mazeAlgorithmType.getAlgorithm(nodes); // initialize maze algorithm

        mazeTimer = new MazeTimer(mazeImage, mazeAlgorithm, animate, null); // initialize maze timer
        mazeTimer.start(); // start maze timer
    }
    
    /**
     * Solves the maze using the given algorithm.
     * 
     * @param solveAlgorithmType
     * @param animate
     */
    private void solveMaze(SolveAlgorithmType solveAlgorithmType, boolean animate) {
        if (nodes == null || mazeTimer == null) {
            return; // catch undefined maze
        }
        if (!mazeTimer.isSolved()) {
            return; // catch unsolved maze
        }

        if (!mazeImage.isStartEndSet()) {
            mazeController.runSetStartEndButtonHandler(); // if necessary, set start and end positions
        }

        int cols = mazeImage.getCols();
        BiFunction<Integer, Integer, Double> heuristicFunction = Maze.createHeuristic(cols); // create heuristic function

        SolveAlgorithm solveAlgorithm = solveAlgorithmType.getAlgorithm(nodes, mazeImage.getStartId(), mazeImage.getEndId(), heuristicFunction); // initialize solve algorithm

        solveTimer = new SolveTimer(mazeImage, solveAlgorithm, animate); // initialize solve timer
        solveTimer.start(); // start solve timer
    }

    /**
     * Sets the start and end positions of the maze.
     * 
     * @param startPosition maze start position
     * @param endPosition maze end position
     */
    private void setStartEnd(MazePosition startPosition, MazePosition endPosition) {
        if (nodes == null) {
            return; // catch undefined maze
        }

        if (startPosition == null || endPosition == null) {
            return; // catch invalid input
        }

        // get number of rows and columns in the maze
        int rows = mazeImage.getRows();
        int cols = mazeImage.getCols();

        // get start and end positions
        int startId = startPosition.getId(rows, cols); 
        int endId = endPosition.getId(rows, cols);

        // update start and end positions
        mazeImage.updateStartEnd(startId, endId);
        mazeImage.redraw();
    }

    /**
     * Launches the JavaFX application.
     * 
     * @param args command line argument
     */
    public static void main(String[] args) {
        launch(args);
    }
}