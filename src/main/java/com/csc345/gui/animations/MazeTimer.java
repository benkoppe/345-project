package com.csc345.gui.animations;

import com.csc345.core.maze_algorithms.MazeAlgorithm;
import com.csc345.gui.MazeImage;

import javafx.animation.AnimationTimer;

/**
 * AnimationTimer for visually generating a maze
 
 */
public class MazeTimer extends AnimationTimer {
    private MazeImage mazeImage;
    private MazeAlgorithm mazeAlgorithm;
    private boolean animate;
    private SolveTimer solveTimer;

    private boolean solved = false;

    /**
     * Initialize a new MazeTimer.
     * 
     * @param mazeImage the MazeImage to animate
     * @param mazeAlgorithm the MazeAlgorithm to animate
     * @param animate whether or not to animate the maze generation
     * @param solveTimer the SolveTimer to start when the maze is finished, or null if no SolveTimer should be started
     */
    public MazeTimer(MazeImage mazeImage, MazeAlgorithm mazeAlgorithm, boolean animate, SolveTimer solveTimer) {
        this.mazeImage = mazeImage;
        this.mazeAlgorithm = mazeAlgorithm;
        this.animate = animate;
        this.solveTimer = solveTimer;
    }

    /**
     * Start the MazeTimer.
     */
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

    /**
     * Handles the animation of the maze algorithm by looping once.
     */
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

    /**
     * Stop the MazeTimer.
     *
     * Will start the SolveTimer if not null.
     */
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