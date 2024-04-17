package com.csc345.data.types;

/**
 * Represents a row-column pair. Can also be used to store the size of a maze.
 */
public class RowCol {
    private int row;
    private int col;

    /**
     * Initialize a new RowCol at a given row and column.
     * 
     * @param row
     * @param col
     */
    public RowCol(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Returns the pair's row.
     * 
     * @return the pair's row
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the pair's column
     * 
     * @return the pair's column
     */
    public int getCol() {
        return col;
    }

    /**
     * Converts the pair to an id in a maze with a given number of columns.
     * 
     * @param cols number of columns in the maze
     * @return the associated id
     */
    public int toId(int cols) {
        return row * cols + col;
    }

    /**
     * Initializes and returns a new RowCol with a given id 
     * in a maze with a given number of columns.
     * 
     * @param id id of the RowCol instance
     * @param cols number of columns in the maze
     * @return RowCol for given id
     */
    public static RowCol idToRowCol(int id, int cols) {
        return new RowCol(id / cols, id % cols);
    }
}
