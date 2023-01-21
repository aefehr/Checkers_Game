package org.cis1200.checkers;

import java.awt.*;

/**
 * This class creates an object that represents a square on the checkerboard.
 */
public class BoardSquare {

    // Board row of the square
    private int row;

    // Board column of the square
    private int col;

    // x coordinate of the square
    private int px;

    // y coordinate of the square
    private int py;

    // Background color of the square
    private Color backgroundColor;

    // Variable that indicates whether the square has a checker on it
    private boolean empty;

    // The checker piece on the square, if not empty
    private CheckerPiece checkerOnSquare;

    /**
     * Constructor sets up a square on the board.
     */
    public BoardSquare(int row, int col, Color c) {

        this.row = row;
        this.col = col;
        this.px = col * 90;
        this.py = row * 90;
        this.backgroundColor = c;

        this.empty = true;
        this.checkerOnSquare = null;
    }

    // get the row of the square on the board
    public int getRow() {
        return this.row;
    }

    // get the column of the square on the board
    public int getCol() {
        return this.col;
    }

    // get the background color of the square
    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    // check whether a square is empty
    public boolean isEmpty() {
        return this.empty;
    }

    // get the checker on the square
    public CheckerPiece getChecker() {
        // may need to add if else w/ is empty
        return this.checkerOnSquare;

    }

    // place a checker on the square
    public void placeChecker(CheckerPiece checker) {
        this.checkerOnSquare = checker;
        this.empty = false;
    }

    // remove a checker from a square
    public void removeChecker() {
        this.checkerOnSquare = null;
        this.empty = true;
    }

    public int getPx() {
        return this.px;
    }

    public int getPy() {
        return this.py;
    }

    // have the square draw itself
    public void paint(Graphics g) {
        g.setColor(this.backgroundColor);
        g.fillRect(this.getPx(), this.getPy(), 90, 90);

        if (!this.isEmpty()) {
            g.setColor(this.checkerOnSquare.getColor());
            g.fillOval(this.getPx() + 10, this.getPy() + 10, 70, 70);
        }

    }

}
