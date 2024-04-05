package com.csc345.gui;

import com.csc345.core.Maze;

import javafx.scene.canvas.Canvas;

public class MazeImage extends Canvas {
    
    // size of wallsize + cellsize
    private static final float FULL_SIZE = 30;
    


    
    public MazeImage(int rows, int cols) {
        super(1000, 100);
    }
}
