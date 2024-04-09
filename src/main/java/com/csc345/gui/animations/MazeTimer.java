package com.csc345.gui.animations;

import com.csc345.core.maze_algorithms.MazeAlgorithm;
import com.csc345.gui.MazeImage;

import javafx.animation.AnimationTimer;

public class MazeTimer extends AnimationTimer {
    private MazeImage mazeImage;
    private MazeAlgorithm mazeAlgorithm;
    private boolean animate;
    private SolveTimer solveTimer;

    private boolean solved = false;

    public MazeTimer(MazeImage mazeImage, MazeAlgorithm mazeAlgorithm, boolean animate, SolveTimer solveTimer) {
        this.mazeImage = mazeImage;
        this.mazeAlgorithm = mazeAlgorithm;
        this.animate = animate;
        this.solveTimer = solveTimer;
    }

    @Override
    public void start() {
        if (animate) {
            super.start();
        } else {
            this.stop();
            mazeAlgorithm.finishImmediately();
            mazeImage.update(mazeAlgorithm);
            mazeImage.redraw();
        }
    }

    @Override
    public void handle(long now) {
        mazeImage.update(mazeAlgorithm);
        mazeImage.redraw();
        if (mazeAlgorithm.isFinished()) {
            this.stop();
            return;
        }
        new Thread(() -> {
            mazeAlgorithm.loopOnce();
        }).start();
    }

    @Override
    public void stop() {
        super.stop();
        solved = true;
        if (solveTimer != null)
            solveTimer.start();
    }

    public boolean isSolved() {
        return solved;
    }
}