package org.cis1200.checkers;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.*;

/**
 * This class instantiates a Checkers object, which is the model for the game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 *
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private Checkers checkersGame; // model for the game
    private JLabel status; // current status text

    // point of the first click
    private Point p1;

    // point of the second click
    private Point p2;

    // Game constants
    public static final int BOARD_WIDTH = 720;
    public static final int BOARD_HEIGHT = 720;



    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit) {
        // Creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        checkersGame = new Checkers(); // initializes model for the game
        status = statusInit; // initializes the status JLabel
        p1 = null;
        p2 = null;

        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (p1 == null) {
                    p1 = e.getPoint();
                    // check that the correct player is playing, otherwise set = null
                    if (checkersGame.getCurrentPlayer()) {
                        if (checkersGame.getCell(p1.y / 90, p1.x / 90).getChecker().getColor()
                                != Color.RED) {
                            p1 = null;
                        }
                    }

                        if (!checkersGame.getCurrentPlayer()) {
                            if (checkersGame.getCell(p1.y / 90, p1.x / 90).getChecker().getColor()
                                    != Color.BLUE) {
                                p1 = null;
                            }
                        }

                } else {
                    p2 = e.getPoint();

                    // check that the square clicked by p2 is a legal move, if not set p2 = null
                    ArrayList<BoardSquare> legalMoves = checkersGame.getlegalMoves(checkersGame.getCell(p1.y / 90,
                                    p1.x / 90).getChecker());
                    if (!legalMoves.contains(checkersGame.getCell(p2.y / 90, p2.x / 90))) {
                        p2 = null;
                    }
                }

                // updates the model given the coordinates of the mouseclick
                if (p1 != null && p2 != null) {
                    checkersGame.playTurn(p1.y / 90, p1.x / 90, p2.y / 90, p2.x / 90);
                    p1 = null;
                    p2 = null;

                    updateStatus(); // updates the status JLabel
                    repaint(); // repaints the game board
                }


            }
        });
    }


    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        checkersGame.reset();
        status.setText("Player 1's Turn   " + "Red Scored: 0   " +
                 "Red Captured: 0   "+ "Blue Scored: 0    " +
                "Blue Captured: 0");
        repaint();

        requestFocusInWindow();
    }


    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        if (checkersGame.getCurrentPlayer()) {
            status.setText("Player 1's Turn   " + "Red Scored: " +
                    checkersGame.redCheckersScored + "   Red Captured: "
                    + checkersGame.redCheckersTaken + "   Blue Scored: " +
                    checkersGame.blueCheckersScored + "   Blue Captured: "
                    + checkersGame.blueCheckersTaken);
        } else {
            status.setText("Player 2's Turn   " + "Red Scored: " +
                    checkersGame.redCheckersScored + "    Red Captured: "
                    + checkersGame.redCheckersTaken + "   Blue Scored: " +
                    checkersGame.blueCheckersScored + "   Blue Captured: "
                    + checkersGame.blueCheckersTaken);
        }

        int winner = checkersGame.checkWinner();

        if (winner == 1) {
            status.setText("Player 1 wins!!!");
        } else if  (winner == 2) {
            status.setText("Player 2 wins!!!");
        }

    }

    // save game state to file
    public void saveGame (String fileName) {
        checkersGame.saveGameToFile(fileName);
    }

    public void loadGame (String fileName) {
        checkersGame.loadGameFromFile(fileName);
        repaint();
    }

    /**
     * Draws the game board.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // for each square on the board, paint it
         for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                BoardSquare current = checkersGame.getCell(row, col);
                current.paint(g);
            }
          }

    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }


}
