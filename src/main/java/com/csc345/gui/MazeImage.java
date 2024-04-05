package com.csc345.gui;

import com.csc345.core.Maze;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class MazeImage extends Canvas {

    static enum MazeColor {
        EMPTY("ff1c5188"),
        VISITING("ffadd9ff"),
        MAZE("ffffffff"),
        SOLUTION("ffad360b"),
        START("ff06d6a0"),
        END("ffaf2bbf");

        private String argbStr;
        private int argb;
        private Color color;

        MazeColor(String argb) {
            this.argbStr = argb;
            this.argb = (int) Long.parseLong(argb, 16);
            this.color = Color.web("#" + argb.substring(2));
        }
    }
    
    // size of wallsize + cellsize
    private static final int FULL_SIZE = 30;
    
    private int wallSize;
    private int cellSize;

    private WritableImage image;

    private double scale;

    
    public MazeImage(int rows, int cols, double cellWallRatio) {
        super(500, 500);

    }
}
