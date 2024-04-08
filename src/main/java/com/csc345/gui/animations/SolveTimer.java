package com.csc345.gui.animations;

import com.csc345.core.solve_algorithms.SolveAlgorithm;
import com.csc345.data.List;
import com.csc345.gui.MazeImage;

import javafx.animation.AnimationTimer;

public class SolveTimer extends AnimationTimer {
    private MazeImage mazeImage;
    private SolveAlgorithm solveAlgorithm;
    private boolean animate;

    private PathTimer pathTimer;

    public SolveTimer(MazeImage mazeImage, SolveAlgorithm solveAlgorithm, boolean animate) {
        this.mazeImage = mazeImage;
        this.solveAlgorithm = solveAlgorithm;
        this.animate = animate;
    }

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

    @Override
    public void stop() {
        super.stop();
        pathTimer = new PathTimer();
        pathTimer.start();
    }

    @Override
    public void handle(long now) {
        mazeImage.update(solveAlgorithm);
        if (solveAlgorithm.isFinished()) {
            this.stop();
            return;
        }
        redraw();
        solveAlgorithm.loopOnce();
    }

    private void redraw() {
        mazeImage.updateStartEnd(solveAlgorithm.getStartId(), solveAlgorithm.getEndId());
        mazeImage.redraw();
    }

    private class PathTimer extends AnimationTimer {
        private int currPathIndex = 0;
        private int maxPathIndex;

        private PathTimer() {
            this.maxPathIndex = solveAlgorithm.getPath().size() - 1;
        }

        @Override
        public void start() {
            if (animate) {
                super.start();
            } else {
                this.stop();
                redrawPath(maxPathIndex);
            }
        }

        @Override
        public void handle(long now) {
            if (currPathIndex >= maxPathIndex) {
                this.stop();
                return;
            } else {
                redrawPath(++currPathIndex);
            }
        }

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
