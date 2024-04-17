package com.csc345.gui;

import com.csc345.data.LinkedListSet;
import com.csc345.data.List;
import com.csc345.data.functionals.Function;
import com.csc345.core.Algorithm;
import com.csc345.core.Node;
import com.csc345.core.State;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * Represents an image of a maze.
 * 
 * Maze images are internally stored within a Canvas and WritableImage, and can be updated with new maze nodes and states.
 */
public class MazeImage extends Group {

    /**
     * Defines colors for the maze image.
     */
    static enum MazeColor {
        EMPTY("ff17528f"), // solid maze space, including walls
        VISITING("ffadd9ff"), // currently visiting by algorithm
        MAZE("ffffffff"), // open maze space (visited by algorithm)
        SOLUTION("ffad360b"), // solution path
        START("ff33cc33"), // start cell
        END("ffff0000"); // end cell

        private int argb;
        private Color color;

        /**
         * MazeColor constructor. Takes in an argb color string.
         * 
         * @param argb The argb color string
         */
        MazeColor(String argb) {
            this.argb = (int) Long.parseLong(argb, 16);
            this.color = Color.web("#" + argb.substring(2));
        }

        /**
         * Converts a State to a MazeColor.
         * 
         * @param state The State to convert
         * @return The MazeColor equivalent of the State
         */
        static MazeColor fromState(State state) {
            switch (state) {
                case UNVISITED:
                    return MazeColor.EMPTY;
                case VISITED:
                    return MazeColor.MAZE;
                case VISITING:
                    return MazeColor.VISITING;
                default:
                    return MazeColor.EMPTY;
            }
        }
    }
    
    // size of wallsize + cellsize
    private static final int FULL_SIZE = 30;

    private int rows;
    private int cols;
    private double cellWallRatio;

    private MarkedPosition start;
    private MarkedPosition end;
    
    private int wallSize;
    private int cellSize;
    private int width;
    private int height;

    private Canvas canvas;
    private WritableImage image;

    private double scale;
    private Pos origin;

    /**
     * MazeImage constructor. Creates a new MazeImage with the given parameters.
     * 
     * @param rows number of rows in the maze
     * @param cols number of columns in the maze
     * @param cellWallRatio ratio of cell size to wall size
     * @param canvasWidth width of the canvas
     * @param canvasHeight height of the canvas
     */
    public MazeImage(int rows, int cols, double cellWallRatio, double canvasWidth, double canvasHeight) {
        super();
        this.wallSize = (int) Math.round(FULL_SIZE / (cellWallRatio + 1));
        this.cellSize = FULL_SIZE - wallSize;

        this.rows = rows;
        this.cols = cols;
        this.cellWallRatio = cellWallRatio;

        this.width = FULL_SIZE * cols + wallSize;
        this.height = FULL_SIZE * rows + wallSize;

        this.canvas = new Canvas(canvasWidth, canvasHeight);
        this.image = new WritableImage(width, height);

        updateScaleAndOrigin();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.getPixelWriter().setArgb(x, y, MazeColor.EMPTY.argb);
            }
        }

        redraw();

        this.getChildren().add(canvas);
    }

    /**
     * Get the number of rows in the maze.
     * 
     * @return The number of rows in the maze
     */
    public int getRows() {
        return rows;
    }

    /**
     * Get the number of columns in the maze.
     * 
     * @return The number of columns in the maze
     */
    public int getCols() {
        return cols;
    }

    /**
     * Get the cell-wall ratio of the maze.
     * 
     * @return The cell-wall ratio of the maze
     */
    public double getCellWallRatio() {
        return cellWallRatio;
    }

    /**
     * Returns whether the start and end positions have been set.
     * 
     * @return Whether the start and end positions have been set
     */
    public boolean isStartEndSet() {
        return start != null && end != null;
    }

    /**
     * Get the start position of the maze.
     * 
     * @return The start position of the maze
     */
    public int getStartId() {
        return start.id;
    }

    /**
     * Get the end position of the maze.
     * 
     * @return The end position of the maze
     */
    public int getEndId() {
        return end.id;
    }

    /**
     * Draws the given algorithm on the maze image.
     * 
     * @param algorithm The algorithm to update the maze with
     */
    public void update(Algorithm algorithm) {
        update(algorithm.getNodes(), algorithm.getStates());
    }

    /**
     * Draws the given maze nodes and states on the maze image.
     * 
     * @param nodes The nodes to update the maze with
     * @param states The states to update the maze with
     */
    public void update(Node[] nodes, State[] states) {
        int id = 0;

        if (nodes == null) {
            return;
        }

        final State[] mazeStates = new State[nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            mazeStates[i] = (states != null) ? states[i] : State.VISITED;
        }

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                IntPos topLeft = getCellTopLeftPos(id);
                int cellArgb = MazeColor.fromState(mazeStates[id]).argb;

                updateCell(cellArgb, topLeft);

                LinkedListSet<Integer> connections = nodes[id].connections;
                LinkedListSet<Integer> neighbors = nodes[id].neighbors;

                Function<Integer, Integer> getWallColor = neighborId -> {
                    if (connections.contains(neighborId)) {
                        return (mazeStates[neighborId] == State.UNVISITED) ? MazeColor.EMPTY.argb : cellArgb;
                    } else {
                        return MazeColor.EMPTY.argb;
                    }
                };

                int rightId = id + 1;
                if (col < cols - 1 && neighbors.contains(rightId)) {
                    updateWall(getWallColor.apply(rightId), topLeft, Side.RIGHT);
                }

                int bottomId = id + cols;
                if (row < rows - 1 && neighbors.contains(bottomId)) {
                    updateWall(getWallColor.apply(bottomId), topLeft, Side.BOTTOM);
                }

                id++;
            }
        }
        
        updateStartEnd();
    }

    /**
     * Draws new start and end positions on the maze.
     * 
     * @param newStartId The new start position
     * @param newEndId The new end position
     */
    public void updateStartEnd(int newStartId, int newEndId) {
        if (start != null) {
            start.unmark();
        }

        if (end != null) {
            end.unmark();
        }

        start = new MarkedPosition(newStartId, MazeColor.START);
        end = new MarkedPosition(newEndId, MazeColor.END);

        updateStartEnd();
    }

    /**
     * Draws the start and end positions on the maze.
     */
    public void updateStartEnd() {
        if (start == null || end == null) {
            return;
        }
        start.mark();
        end.mark();
    }
    
    /**
     * Draws the given path on the maze.
     * 
     * @param path The path to draw
     */
    public void drawPath(List<Integer> path) {
        if (path.isEmpty())
            return;

        double halfCellSize = cellSize / 2.0;

        Function<Integer, Pos> getPathPos = id -> {
            IntPos topLeft = getCellTopLeftPos(id);
            return new Pos(
                origin.x + scale * (topLeft.x + halfCellSize),
                origin.y + scale * (topLeft.y + halfCellSize)
            );
        };

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.beginPath();
        
        int firstId = path.get(0);
        Pos start = getPathPos.apply(firstId);
        gc.moveTo(start.x, start.y);

        path.forEach(id -> {
            if (id != firstId) {
                Pos pos = getPathPos.apply(id);
                gc.lineTo(pos.x, pos.y);
                gc.moveTo(pos.x, pos.y);
            }
        });

        gc.closePath();
        gc.setStroke(MazeColor.SOLUTION.color);
        gc.setLineWidth(scale * cellSize * 0.3);
        gc.stroke();
    }

    /**
     * Redraws the maze image on the Canvas.
     */
    public void redraw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0.0, 0.0, canvas.getWidth(), canvas.getHeight());
        gc.drawImage(image, origin.x, origin.y, scale * width, scale * height);
    }

    /**
     * Converts a cell id to the top-left position on the maze image.
     * 
     * @param id The cell id
     * @return The top-left position of the cell
     */
    private IntPos getCellTopLeftPos(int id) {
        int row = id / cols;
        int col = id % cols;
        return new IntPos(FULL_SIZE * col + wallSize, FULL_SIZE * row + wallSize);
    }

    /**
     * Gets the current color of a cell on the maze image.
     * 
     * @param topLeft The top left position of the cell
     * @return
     */
    private int getCellColor(IntPos topLeft) {
        return image.getPixelReader().getArgb(topLeft.x, topLeft.y);
    }

    /**
     * Updates a cell on the maze image.
     * 
     * @param argb The new color of the cell
     * @param topLeft The top-left position of the cell
     */
    private void updateCell(int argb, IntPos topLeft) {
        colorRect(argb, topLeft.x, topLeft.y, cellSize, cellSize);
    }

    /**
     * Updates a cell's wall on the maze image.
     * 
     * @param argb The new color of the wall
     * @param topLeft The top-left position of the cell
     * @param side The side of the cell's wall
     */
    private void updateWall(int argb, IntPos topLeft, Side side) {
        int x = topLeft.x;
        int y = topLeft.y;

        switch (side) {
            case TOP:
                colorRect(argb, x, y - wallSize, cellSize, wallSize);
                break;

            case RIGHT:
                colorRect(argb, x + cellSize, y, wallSize, cellSize);
                break;

            case BOTTOM:
                colorRect(argb, x, y + cellSize, cellSize, wallSize);
                break;

            case LEFT:
                colorRect(argb, x - wallSize, y, wallSize, cellSize);
                break;
        }
    }

    /**
     * Colors a rectangle on the maze image.
     * 
     * @param argb The color to fill the rectangle with
     * @param x The x-coordinate of the top-left corner of the rectangle
     * @param y The y-coordinate of the top-left corner of the rectangle
     * @param width The width of the rectangle
     * @param height The height of the rectangle
     */
    private void colorRect(int argb, int x, int y, int width, int height) {
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                image.getPixelWriter().setArgb(i, j, argb);
            }
        }
    }

    /**
     * Updates the scale and origin of the maze image.
     */
    private void updateScaleAndOrigin() {
        ScaleAndOrigin scaleAndOrigin = getScaleAndOrigin();
        this.scale = scaleAndOrigin.scale;
        this.origin = scaleAndOrigin.origin;
    }

    /**
     * Gets the scale and origin of the maze image.
     * 
     * @return The scale and origin of the maze image
     */
    private ScaleAndOrigin getScaleAndOrigin() {
        double mazeRatio = ((double) width) / height;
        double canvasRatio = canvas.getWidth() / canvas.getHeight();

        double scale;
        if (mazeRatio < canvasRatio) {
            scale = canvas.getHeight() / height;
        } else {
            scale = canvas.getWidth() / width;
        }

        double tempX = (canvas.getWidth() - width) / 2;
        double tempY = (canvas.getHeight() - height) / 2;
        double centerX = canvas.getWidth() / 2;
        double centerY = canvas.getHeight() / 2;
        Pos origin = new Pos(
            centerX - (centerX - tempX) * scale,
            centerY - (centerY - tempY) * scale
        );

        return new ScaleAndOrigin(scale, origin);
    }

    /**
     * Represents a position on the Canvas.
     */
    private class Pos {
        double x;
        double y;

        /**
         * Pos constructor. Takes in x and y coordinates.
         * 
         * @param x the x-coordinate
         * @param y the y-coordinate
         */
        Pos(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Represents an integer position on the maze image.
     */
    private class IntPos {
        int x;
        int y;

        /**
         * IntPos constructor. Takes in x and y coordinates.
         * 
         * @param x the x-coordinate
         * @param y the y-coordinate
         */
        IntPos(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Represents a scale and origin of the maze image.
     */
    private class ScaleAndOrigin {
        double scale;
        Pos origin;

        /**
         * ScaleAndOrigin constructor. Takes in a scale and origin.
         * 
         * @param scale The scale
         * @param origin The origin position
         */
        ScaleAndOrigin(double scale, Pos origin) {
            this.scale = scale;
            this.origin = origin;
        }
    }

    /**
     * Represents a marked position on the maze image.
     * Marked positions are colored differently than regular maze cells, but can be reverted to their original colors.
     */
    private class MarkedPosition {
        int id; // cell id
        int regArgb; // regular argb color
        MazeColor markColor; // color to mark the cell with

        /**
         * MarkedPosition constructor. Takes in the cell id and the mark color.
         * 
         * @param id
         * @param markColor
         */
        MarkedPosition(int id, MazeColor markColor) {
            this.id = id;
            this.regArgb = getCellColor(getCellTopLeftPos(id));
            this.markColor = markColor;
        }

        /**
         * Marks the cell with the mark color.
         */
        void mark() {
            updateCell(markColor.argb, getCellTopLeftPos(id));
        }

        /**
         * Unmarks the cell, reverting to the orginal color.
         */
        void unmark() {
            updateCell(regArgb, getCellTopLeftPos(id));
        }
    }

    /**
     * Represents the side of a cell.
     */
    private enum Side {
        TOP,
        RIGHT,
        BOTTOM,
        LEFT
    }
}
