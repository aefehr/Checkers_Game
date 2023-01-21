package org.cis1200.checkers;


import javax.swing.*;
import java.awt.*;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 */
public class RunCheckers implements Runnable {
    public void run() {

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Checkers");
        frame.setLocation(400, 400);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);


        // Game board
        final GameBoard board = new GameBoard(status);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> board.reset());
        control_panel.add(reset);

        final JButton instructions = new JButton("Instructions");
        String text = "Get ready to play Checkers! This is a slightly " +
                "modified version of checkers in which kings do \n"
                + "not" + "exist. Instead, a player scores points by making" +
                "it to the other side of the board. \n" +
                "As with normal" + "checkers, a player can jump other " +
                "players and take away their checker. A \n" +
                "player wins by getting all " + "their checker pieces to the " +
                "other side of the board or by \n" +
                "capturing " + "all the checker" + "pieces of the other" +
                "player. Checker pieces can only move \n" +
                "to black squares and must move in the " + "direction away from their " +
                "" + "starting position. If \n" +
                "you try to make an illegal move, the game will wait for you to " +
                "make a legal move. " + "Player 1 \n" +
                "is red checkers and Player 2 is blue checkers. " +
                "To play, click on the checker you to move and \n" +
                " " + "then click on the square you want to move it to.";
        instructions.addActionListener(e ->
                JOptionPane.showMessageDialog(frame, text));
        control_panel.add(instructions);


        final JButton save = new JButton("Save");
        save.addActionListener(e ->
            board.saveGame("files/test.txt")
        );
        control_panel.add(save);


        final JButton load = new JButton("Load");
        load.addActionListener(e ->
                board.loadGame("files/test.txt"));
        control_panel.add(load);


        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.reset();
    }

}