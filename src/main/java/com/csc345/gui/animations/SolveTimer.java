package com.csc345.gui.animations;

import com.csc345.core.solve_algorithms.SolveAlgorithm;
import com.csc345.data.List;
import com.csc345.gui.MazeImage;

import javafx.animation.AnimationTimer;

/**
 * AnimationTimer for visually solving a maze
 */
public class SolveTimer extends AnimationTimer {
    private MazeImage mazeImage;
    private SolveAlgorithm solveAlgorithm;
    private boolean animate;

    private PathTimer pathTimer;

    /**
     * Initialize a new SolveTimer
     * 
     * @param mazeImage the MazeImage to animate
     * @param solveAlgorithm the SolveAlgorithm to animate
     * @param animate whether or not to animate the solution
     */
    public SolveTimer(MazeImage mazeImage, SolveAlgorithm solveAlgorithm, boolean animate) {
        this.mazeImage = mazeImage;
        this.solveAlgorithm = solveAlgorithm;
        this.animate = animate;
    }

    /**
     * Start the SolveTimer
     */
    @Override
    public void start() {
        if (animate) {
            super.start();
        } else {
            solveAlgorithm.finishImmediately();
            mazeImage.update(solveAlgorithm);
            redraw();
            this.stop();
        }
    }

    /**
     * Stop the SolveTimer
     */
    @Override
    public void stop() {
        super.stop();
        pathTimer = new PathTimer();
        pathTimer.start();
    }

    /**
     * Handles the animation of the solve algorithm by looping once
     */
    @Override
    public void handle(long now) {
        mazeImage.update(solveAlgorithm);
        if (solveAlgorithm.isFinished()) {
            this.stop();
            return;
        }
        redraw();

        new Thread((() -> {
            solveAlgorithm.loopOnce();
        })).start();
    }

    /**
     * Redraw the maze
     */
    private void redraw() {
        redraw(true);
    }

    /**
     * Redraw the maze
     * 
     * @param drawPath whether the path should be drawn
     */
    private void redraw(boolean drawPath) {
        mazeImage.redraw();

        if (drawPath && solveAlgorithm.getPath().size() > 1) {
            mazeImage.drawPath(solveAlgorithm.getPath());
        }
    }

    /**
     * AnimationTimer for visually walking the path of a maze
     */
    private class PathTimer extends AnimationTimer {
        private int currPathIndex = 0;
        private int maxPathIndex;

        /**
         * Initialize a new PathTimer
         */
        private PathTimer() {
            this.maxPathIndex = solveAlgorithm.getPath().size() - 1;
        }

        /**
         * Start the PathTimer
         */
        @Override
        public void start() {
            if (animate) {
                super.start();
            } else {
                this.stop();
                redrawPath(maxPathIndex);
            }
        }

        /**
         * Handles the animation of the path by drawing to the path index, and incrementing
         */
        @Override
        public void handle(long now) {
            if (currPathIndex >= maxPathIndex) {
                this.stop();
                return;
            } else {
                redrawPath(++currPathIndex);
            }
        }

        /**
         * Redraws the path up to the given index
         * 
         * @param pathIndex the index to draw the path up to
         */
        private void redrawPath(int pathIndex) {
            mazeImage.redraw();
            mazeImage.updateStartEnd(solveAlgorithm.getStartId(), solveAlgorithm.getEndId());
            if (pathIndex <= maxPathIndex) {
                List<Integer> subPath = solveAlgorithm.getPath().subList(0, pathIndex + 1);
                mazeImage.drawPath(subPath);
            }
        }
    }
}
