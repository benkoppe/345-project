package com.csc345.gui;

import com.csc345.data.functionals.Runnable;
import com.csc345.gui.components.FloatField;
import com.csc345.gui.components.IntegerField;
import com.csc345.core.maze_algorithms.MazeAlgorithmType;
import com.csc345.core.solve_algorithms.SolveAlgorithmType;

import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * JavaFX ToolBar that contains all the controls for the maze.
 * 
 * Buttons have handlers that can be set and used to control the maze.
 */
public class MazeController extends ToolBar {

    private static final String ROWS_FIELD_LABEL = "Rows";
    private static final String COLS_FIELD_LABEL = "Cols";
    private static final String CELL_WALL_RATIO_FIELD_LABEL = "Cell-Wall Ratio";

    private static final String GENERATE_BUTTON_LABEL = "Generate";
    private static final String SOLVE_BUTTON_LABEL = "Solve";
    private static final String CLEAR_SOLUTION_BUTTON_LABEL = "Clear Solution";

    private static final String START_FIELD_LABEL = "Start";
    private static final String END_FIELD_LABEL = "End";
    private static final String SET_START_END_BUTTON_LABEL = "Set Start/End";

    private static final String ANIMATE_CHECKBOX_LABEL = "Animate";

    private static final double FIELD_WIDTH = 60;
    private static final double GRID_HGAP_SIZE = 15;
    private static final double GRID_VGAP_SIZE = 5;

    private static final double CENTER_SPACER_HALF_WIDTH = 20;

    private IntegerField rows;
    private IntegerField cols;
    private FloatField cellWallRatio;

    private ChoiceBox<MazeAlgorithmType> generateType;
    private CheckBox generateAnimate;
    private Button generate;

    private ChoiceBox<SolveAlgorithmType> solveType;
    private CheckBox solveAnimate;
    private Button solve;
    private Button clearSolution;

    private ChoiceBox<MazePosition> startPosition;
    private ChoiceBox<MazePosition> endPosition;
    private Button setStartEnd;

    /**
     * Creates a new MazeController with the given default values.
     * 
     * @param defaultRows default number of maze rows
     * @param defaultCols default number of maze columns
     * @param defaultCellWallRatio default cell-wall ratio
     */
    public MazeController(int defaultRows, int defaultCols, double defaultCellWallRatio) {
        super();

        GridPane mazePanel = makeMazePanel(defaultRows, defaultCols, defaultCellWallRatio);
        GridPane solvePanel = makeSolvePanel();

        HBox spacer = makeCenterSpacer();
        
        this.getItems().addAll(mazePanel, spacer, solvePanel);
    }

    /**
     * Sets the handler for the generate button.
     * 
     * @param handler the handler to set
     */
    public void setGenerateButtonHandler(GenerateButtonHandler handler) {
        generate.setOnAction(e -> handler.handle(rows.getInt(), cols.getInt(), cellWallRatio.getDouble(),
                generateType.getValue(), generateAnimate.isSelected()));
    }

    /**
     * Sets the handler for the solve button.
     * 
     * @param handler the handler to set
     */
    public void setSolveButtonHandler(SolveButtonHandler handler) {
        solve.setOnAction(e -> handler.handle(solveType.getValue(), solveAnimate.isSelected()));
    }

    /**
     * Sets the handler for the clear button.
     * 
     * @param handler the handler to set
     */
    public void setClearSolutionButtonHandler(Runnable handler) {
        clearSolution.setOnAction(e -> handler.run());
    }

    /**
     * Sets the handler for the set start/end button.
     * 
     * @param handler the handler to set
     */
    public void setSetStartEndButtonHandler(SetStartEndButtonHandler handler) {
        setStartEnd.setOnAction(e -> handler.handle(startPosition.getValue(), endPosition.getValue()));
    }

    /**
     * Runs the start/end button handler.
     */
    public void runSetStartEndButtonHandler() {
        setStartEnd.fire();
    }

    /**
     * Creates the center spacer that divides the generate/solve sections.
     * 
     * @return the center spacer
     */
    private HBox makeCenterSpacer() {
        Region leftRegion = new Region();
        leftRegion.setPrefWidth(CENTER_SPACER_HALF_WIDTH);

        Separator separator = new Separator(Orientation.VERTICAL);

        Region rightRegion = new Region();
        rightRegion.setPrefWidth(CENTER_SPACER_HALF_WIDTH);

        HBox spacer = new HBox(leftRegion, separator, rightRegion);
        spacer.setAlignment(Pos.CENTER);
        return spacer;
    }
    
    /**
     * Creates the maze solve button panel section.
     * 
     * @return the solve panel
     */
    private GridPane makeSolvePanel() {
        solveType = new ChoiceBox<>();
        solveType.getItems().addAll(SolveAlgorithmType.values());
        solveType.setValue(SolveAlgorithmType.defaultValue());

        solveAnimate = new CheckBox(ANIMATE_CHECKBOX_LABEL);
        solveAnimate.setSelected(true);

        solve = new Button(SOLVE_BUTTON_LABEL);

        clearSolution = new Button(CLEAR_SOLUTION_BUTTON_LABEL);

        GridPane solvePanel = new GridPane();

        solvePanel.add(solveType, 0, 0);

        solvePanel.add(solveAnimate, 1, 0);

        solvePanel.add(solve, 2, 0);
        solvePanel.add(clearSolution, 3, 0);

        startPosition = new ChoiceBox<>();
        HBox startBox = makePositionBox(START_FIELD_LABEL, startPosition, MazePosition.TOP_LEFT);

        endPosition = new ChoiceBox<>();
        HBox endBox = makePositionBox(END_FIELD_LABEL, endPosition, MazePosition.BOTTOM_RIGHT);

        setStartEnd = new Button(SET_START_END_BUTTON_LABEL);

        HBox startEndBox = new HBox(startBox, endBox, setStartEnd);
        startEndBox.setAlignment(Pos.CENTER);
        startEndBox.setSpacing(GRID_HGAP_SIZE);

        solvePanel.add(startEndBox, 0, 1, 5, 1);

        solvePanel.setHgap(GRID_HGAP_SIZE);
        solvePanel.setVgap(GRID_VGAP_SIZE);
        solvePanel.setAlignment(Pos.CENTER);

        return solvePanel;
    }

    /**
     * Creates the maze generate button section.
     * 
     * @param defaultRows default number of maze rows
     * @param defaultCols default number of maze columns
     * @param defaultCellWallRatio default cell-wall ratio
     * @return
     */
    private GridPane makeMazePanel(int defaultRows, int defaultCols, double defaultCellWallRatio) {
        rows = new IntegerField(defaultRows);
        rows.setPrefWidth(FIELD_WIDTH);
        Label rowsLabel = new Label(ROWS_FIELD_LABEL);

        cols = new IntegerField(defaultCols);
        cols.setPrefWidth(FIELD_WIDTH);
        Label colsLabel = new Label(COLS_FIELD_LABEL);

        cellWallRatio = new FloatField(defaultCellWallRatio);
        cellWallRatio.setPrefWidth(FIELD_WIDTH);
        Label cellWallRatioLabel = new Label(CELL_WALL_RATIO_FIELD_LABEL);

        generate = new Button(GENERATE_BUTTON_LABEL);

        generateType = new ChoiceBox<>();
        generateType.getItems().addAll(MazeAlgorithmType.values());
        generateType.setValue(MazeAlgorithmType.defaultValue());

        generateAnimate = new CheckBox(ANIMATE_CHECKBOX_LABEL);
        generateAnimate.setSelected(true);

        GridPane mazePanel = new GridPane();

        mazePanel.add(rowsLabel, 0, 0);
        mazePanel.add(rows, 1, 0);

        mazePanel.add(colsLabel, 0, 1);
        mazePanel.add(cols, 1, 1);

        mazePanel.add(cellWallRatioLabel, 2, 0, 1, 1);
        mazePanel.add(cellWallRatio, 3, 0, 1, 1);

        mazePanel.add(generateType, 2, 1, 2, 1);

        mazePanel.add(generateAnimate, 4, 0);

        mazePanel.add(generate, 4, 1);

        mazePanel.setHgap(GRID_HGAP_SIZE);
        mazePanel.setVgap(GRID_VGAP_SIZE);
        mazePanel.setAlignment(Pos.CENTER);

        GridPane.setHalignment(generateType, HPos.CENTER);

        return mazePanel;
    }

    private HBox makePositionBox(String label, ChoiceBox<MazePosition> position, MazePosition defaultPosition) {
        position.getItems().addAll(MazePosition.values());
        position.setValue(defaultPosition);

        Label positionLabel = new Label(label);
        HBox positionBox = new HBox(positionLabel, position);
        positionBox.setAlignment(Pos.CENTER);
        positionBox.setSpacing(GRID_HGAP_SIZE / 2);
        return positionBox;
    }

    public enum MazePosition {
        TOP_LEFT("Top left"),
        TOP_RIGHT("Top right"),
        BOTTOM_LEFT("Bot left"),
        BOTTOM_RIGHT("Bot right"),
        RANDOM("Random");

        private final String label;

        MazePosition(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }

        public int getId(int rows, int cols) {
            switch (this) {
                case TOP_LEFT:
                    return 0;
                case TOP_RIGHT:
                    return cols - 1;
                case BOTTOM_LEFT:
                    return (rows - 1) * cols;
                case BOTTOM_RIGHT:
                    return rows * cols - 1;
                case RANDOM:
                    return (int) (Math.random() * (rows * cols));
                default:
                    return -1;
            }
        }
    }

    @FunctionalInterface
    public interface GenerateButtonHandler {
        void handle(int rows, int cols, double cellWallRatio, MazeAlgorithmType type, boolean animate);
    }

    @FunctionalInterface
    public interface SolveButtonHandler {
        void handle(SolveAlgorithmType type, boolean animate);
    }

    @FunctionalInterface
    public interface SetStartEndButtonHandler {
        void handle(MazePosition start, MazePosition end);
    }
}
