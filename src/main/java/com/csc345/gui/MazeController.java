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

public class MazeController extends ToolBar {

    private static final String ROWS_FIELD_LABEL = "Rows";
    private static final String COLS_FIELD_LABEL = "Cols";
    private static final String CELL_WALL_RATIO_FIELD_LABEL = "Cell-Wall Ratio";

    private static final String GENERATE_BUTTON_LABEL = "Generate";
    private static final String SOLVE_BUTTON_LABEL = "Solve";
    private static final String CLEAR_SOLUTION_BUTTON_LABEL = "Clear Solution";

    private static final String ANIMATE_CHECKBOX_LABEL = "Animate";

    private static final double FIELD_WIDTH = 60;
    private static final double GRID_HGAP_SIZE = 15;
    private static final double GRID_VGAP_SIZE = 5;

    private static final double CENTER_SPACER_HALF_WIDTH = 30;

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

    public MazeController(int defaultRows, int defaultCols, double defaultCellWallRatio) {
        super();

        GridPane mazePanel = makeMazePanel(defaultRows, defaultCols, defaultCellWallRatio);
        GridPane solvePanel = makeSolvePanel();

        HBox spacer = makeCenterSpacer();
        
        this.getItems().addAll(mazePanel, spacer, solvePanel);
    }

    public void setGenerateButtonHandler(GenerateButtonHandler handler) {
        generate.setOnAction(e -> handler.handle(rows.getInt(), cols.getInt(), cellWallRatio.getDouble(),
                generateType.getValue(), generateAnimate.isSelected()));
    }

    public void setSolveButtonHandler(SolveButtonHandler handler) {
        solve.setOnAction(e -> handler.handle(solveType.getValue(), solveAnimate.isSelected()));
    }

    public void setClearSolutionButtonHandler(Runnable handler) {
        clearSolution.setOnAction(e -> handler.run());
    }

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

        solvePanel.setHgap(GRID_HGAP_SIZE);
        solvePanel.setVgap(GRID_VGAP_SIZE);
        solvePanel.setAlignment(Pos.CENTER);

        return solvePanel;
    }

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

    @FunctionalInterface
    public interface GenerateButtonHandler {
        void handle(int rows, int cols, double cellWallRatio, MazeAlgorithmType type, boolean animate);
    }

    @FunctionalInterface
    public interface SolveButtonHandler {
        void handle(SolveAlgorithmType type, boolean animate);
    }
}
