package org.cis1200.checkers;

import java.awt.*;

/**
 * This class creates an object that represents a checker piece.
 */
public class CheckerPiece {
    // board row of the checker
    private int row;

    // board column of the checker
    private int col;

    // checker color, red or black
    private Color color;

    // constructor for a checker piece
    public CheckerPiece(int row, int col, Color color) {
        this.row = row;
        this.col = col;
        this.color = color;
    }

    // get board row of checker
    public int getRow() {
        return this.row;
    }

    // get column of checker
    public int getCol() {
        return this.col;
    }

    // get color of checker
    public Color getColor() {
        return this.color;
    }

    // move checker to new position
    public void movePosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

}
