package com.csc345.data;

public class RowCol {
    // represenets a row-column pair

    private int row;
    private int col;

    public RowCol(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int toId(int cols) {
        return row * cols + col;
    }

    public static RowCol idToRowCol(int id, int cols) {
        return new RowCol(id / cols, id % cols);
    }
}
