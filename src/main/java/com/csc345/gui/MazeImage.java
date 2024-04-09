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

public class MazeImage extends Group {

    static enum MazeColor {
        EMPTY("ff17528f"),
        VISITING("ffadd9ff"),
        MAZE("ffffffff"),
        SOLUTION("ffad360b"),
        START("ff33cc33"),
        END("ffff0000");

        private int argb;
        private Color color;

        MazeColor(String argb) {
            this.argb = (int) Long.parseLong(argb, 16);
            this.color = Color.web("#" + argb.substring(2));
        }

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

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public double getCellWallRatio() {
        return cellWallRatio;
    }

    public boolean isStartEndSet() {
        return start != null && end != null;
    }

    public int getStartId() {
        return start.id;
    }

    public int getEndId() {
        return end.id;
    }

    public void update(Algorithm algorithm) {
        update(algorithm.getNodes(), algorithm.getStates());
    }

    public void update(Node[] nodes, State[] states) {
        int id = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                IntPos topLeft = getCellTopLeftPos(id);
                int cellArgb = MazeColor.fromState(states[id]).argb;

                updateCell(cellArgb, topLeft);

                LinkedListSet<Integer> connections = nodes[id].connections;
                LinkedListSet<Integer> neighbors = nodes[id].neighbors;

                Function<Integer, Integer> getWallColor = neighborId -> {
                    if (connections.contains(neighborId)) {
                        return (states[neighborId] == State.UNVISITED) ? MazeColor.EMPTY.argb : cellArgb;
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

    public void updateStartEnd() {
        if (start == null || end == null) {
            return;
        }
        start.mark();
        end.mark();
    }
    
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

    public void redraw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0.0, 0.0, canvas.getWidth(), canvas.getHeight());
        gc.drawImage(image, origin.x, origin.y, scale * width, scale * height);
    }

    private IntPos getCellTopLeftPos(int id) {
        int row = id / cols;
        int col = id % cols;
        return new IntPos(FULL_SIZE * col + wallSize, FULL_SIZE * row + wallSize);
    }

    private int getCellColor(IntPos topLeft) {
        return image.getPixelReader().getArgb(topLeft.x, topLeft.y);
    }

    private void updateCell(int argb, IntPos topLeft) {
        colorRect(argb, topLeft.x, topLeft.y, cellSize, cellSize);
    }

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

    private void colorRect(int argb, int x, int y, int width, int height) {
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                image.getPixelWriter().setArgb(i, j, argb);
            }
        }
    }

    private void updateScaleAndOrigin() {
        ScaleAndOrigin scaleAndOrigin = getScaleAndOrigin();
        this.scale = scaleAndOrigin.scale;
        this.origin = scaleAndOrigin.origin;
    }

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

    private class Pos {
        double x;
        double y;

        Pos(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    private class IntPos {
        int x;
        int y;

        IntPos(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private class ScaleAndOrigin {
        double scale;
        Pos origin;

        ScaleAndOrigin(double scale, Pos origin) {
            this.scale = scale;
            this.origin = origin;
        }
    }

    private class MarkedPosition {
        int id;
        int regArgb;
        MazeColor markColor;

        MarkedPosition(int id, MazeColor markColor) {
            this.id = id;
            this.regArgb = getCellColor(getCellTopLeftPos(id));
            this.markColor = markColor;
        }

        void mark() {
            updateCell(markColor.argb, getCellTopLeftPos(id));
        }

        void unmark() {
            updateCell(regArgb, getCellTopLeftPos(id));
        }
    }

    private enum Side {
        TOP,
        RIGHT,
        BOTTOM,
        LEFT
    }
}
