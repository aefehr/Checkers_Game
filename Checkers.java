package org.cis1200.checkers;


import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class is a model for Checkers.
 */
public class Checkers {
    // 2-d array where a BoardSquare object is the element
    public BoardSquare[][] board;

    // current player
    public boolean player1;

    // number of red checkers that have been captured
    public int redCheckersTaken;

    // number of red checkers that have been captured
    public int blueCheckersTaken;

    // number of red checkers that have scored
    public int redCheckersScored;

    // number of blue checkers that have scored
    public int blueCheckersScored;

    private boolean gameOver;

    /**
     * Constructor sets up game state.
     */
    public Checkers() {
        reset();
    }

    /**
     * playTurn allows players to play a turn. Returns true if the move is
     * successful and false if a player tries to play in a location that is
     * taken or after the game has ended. If the turn is successful and the game
     * has not ended, the player is changed. If the turn is unsuccessful or the
     * game has ended, the player is not changed.
     *
     * @param c1 column to play from
     * @param r1 row to play from
     * @param c2 row to play to
     * @param r2 row to play to
     * @return whether the turn was successful
     */
    public boolean playTurn(int r1, int c1, int r2, int c2) {
        BoardSquare squareToMoveFrom = getCell(r1, c1);
        CheckerPiece checkerToMove = squareToMoveFrom.getChecker();

        ArrayList<BoardSquare> legalMoves = getlegalMoves(checkerToMove);

        BoardSquare squareToMoveTo = getCell(r2, c2);

        if (gameOver || !legalMoves.contains(squareToMoveTo)) {
            return false;
        } else {
            boolean jumped = move(squareToMoveFrom, squareToMoveTo);
            // if jumped over a checker, decrease number of checkers
            if (jumped) {
                if (checkerToMove.getColor() == Color.RED) {
                    blueCheckersTaken += 1;
                } else {
                    redCheckersTaken += 1;
                }
            }
            if (checkWinner() == 0) {
                player1 = !player1;
            }
            return true;
        }
    }


    /**
     * checkWinner checks whether the game has reached a win condition.
     * checkWinner only looks for horizontal wins.
     *
     * @return 0 if nobody has won yet, 1 if player 1 has won, and 2 if player 2
     *         has won
     */
    public int checkWinner() {
        // if blue lost all checkers or red scored all checkers, player 1 (red) wins
        if (blueCheckersTaken == 12 || redCheckersScored == 12) {
            gameOver = true;
            return 1;
        }
        // if red lost all checkers or blue scored all checkers, player 2 (blue) wins
        if (redCheckersTaken == 12 || blueCheckersScored == 12) {
            gameOver = true;
            return 2;
        }

        return 0;
    }

    /**
     * reset (re-)sets the game state to start a new game.
     */
    public void reset() {
        BoardSquare[][] newBoard = createBoard();
        setInitialCheckers(newBoard);
        this.board = newBoard;

        this.player1 = true;

        this.redCheckersTaken = 0;
        this.blueCheckersTaken = 0;

        this.redCheckersScored = 0;
        this.blueCheckersScored = 0;

        this.gameOver = false;
    }

    /**
     * Creates the game board.
     */
    public BoardSquare[][] createBoard() {
        board = new BoardSquare[8][8];

        boolean isWhite = true;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (isWhite) {
                    board[row][col] = new BoardSquare(row, col, Color.white);
                } else {
                    board[row][col] = new BoardSquare(row, col, Color.black);
                }
                isWhite = !isWhite;
            }
            isWhite = !isWhite;
        }
            return board;
    }

    /**
     * Puts the starting checkers on the board.
     */
     public void setInitialCheckers(BoardSquare[][] board) {
        // place the red checkers on the top of the board
         for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 8; col++) {
                    BoardSquare current = board[row][col];
                    if (current.getBackgroundColor() == Color.BLACK) {
                        CheckerPiece red = new CheckerPiece(row, col, Color.RED);
                        current.placeChecker(red);
                    }
                }
            }

         // place the blue checkers at the bottom of the board
         for (int row = 5; row < 8; row++) {
             for (int col = 0; col < 8; col++) {
                 if (getCell(row, col).getBackgroundColor() == Color.BLACK) {
                     CheckerPiece blue = new CheckerPiece(row, col, Color.BLUE);
                     getCell(row,col).placeChecker(blue);
                 }
             }
         }
    }

    /**
     * getCurrentPlayer is a getter for the player
     * whose turn it is in the game.
     * 
     * @return true if it's Player 1's turn,
     *         false if it's Player 2's turn.
     */
    public boolean getCurrentPlayer() {
        return player1;
    }

    /**
     * getCell is a getter for the contents of the cell specified by the method
     * arguments.
     *
     *
     * @param row row to retrieve
     * @param col column to retrieve
     * @return a BoardSquare denoting the contents of the corresponding cell on the game board
     */
    public BoardSquare getCell(int row, int col) {
       return board[row][col];
    }

    /**
     * Helper function that checkers whether a spot on the board is in bounds.
     */
    public static boolean isInBounds(int row, int col) {
        if((row >= 0 && row < 8) && (col >= 0 && col < 8)) {
            return true;
        }
        return false;
    }

    /**
     * Finds the legal moves associated with a checker piece.
     */
    public ArrayList<BoardSquare> getlegalMoves(CheckerPiece checker) {
        ArrayList<BoardSquare> legalMoves = new ArrayList<>();

        int cRow = checker.getRow();
        int cCol = checker.getCol();
        Color color = checker.getColor();

        // red checker must move down, check to the left
        if (Checkers.isInBounds(cRow + 1, cCol - 1) && color == Color.RED) {
            // check if square is empty
            if (getCell(cRow + 1, cCol - 1).isEmpty()) {
                legalMoves.add(getCell(cRow + 1, cCol - 1));
            } else {
                // check if a jump can be made
                if (Checkers.isInBounds(cRow + 2, cCol - 2)) {
                    if (getCell(cRow + 2, cCol - 2).isEmpty() &&
                          getCell(cRow + 1, cCol - 1).getChecker().getColor() != color) {
                        legalMoves.add(getCell(cRow + 2, cCol - 2));
                    }
                }
            }
        }

        // check to the right for red checker
        if (Checkers.isInBounds(cRow + 1, cCol + 1) && color == Color.RED) {
            if (getCell(cRow + 1, cCol + 1).isEmpty()) {
                legalMoves.add(getCell(cRow + 1, cCol + 1));
            } else {
                // check if a jump can be made
                if (Checkers.isInBounds(cRow + 2, cCol + 2)) {
                    if (getCell(cRow + 2, cCol + 2).isEmpty() &&
                            getCell(cRow + 1, cCol + 1).getChecker().getColor() != color) {
                        legalMoves.add(getCell(cRow + 2, cCol + 2));
                    }
                }
            }
        }


        // for blue checkers, must move up, check to the left
        if (Checkers.isInBounds(cRow - 1, cCol - 1) && color == Color.BLUE) {
            // check if square is empty
            if (getCell(cRow - 1, cCol - 1).isEmpty()) {
                legalMoves.add(getCell(cRow - 1, cCol - 1));
            } else {
                // check if a jump can be made
                if (Checkers.isInBounds(cRow - 2, cCol - 2)) {
                    if (getCell(cRow - 2, cCol - 2).isEmpty() &&
                            getCell(cRow - 1, cCol - 1).getChecker().getColor() != color) {
                        legalMoves.add(getCell(cRow - 2, cCol - 2));
                    }
                }
            }
        }
        // check to the right
        if (Checkers.isInBounds(cRow - 1, cCol + 1) && color == Color.BLUE) {
            // check if square is empty
            if (getCell(cRow - 1, cCol + 1).isEmpty()) {
                legalMoves.add(getCell(cRow - 1, cCol + 1));
            } else {
                // check if a jump can be made
                if (Checkers.isInBounds(cRow - 2, cCol + 2)) {
                    if (getCell(cRow - 2, cCol + 2).isEmpty() &&
                            getCell(cRow - 1, cCol + 1).getChecker().getColor() != color) {
                        legalMoves.add(getCell(cRow - 2, cCol + 2));
                    }
                }
            }
        }

        return legalMoves;
    }

    /**
     * Moves a checker piece.
     */
     public boolean move(BoardSquare start, BoardSquare end) {
         CheckerPiece toMove = start.getChecker();

         int startRow = start.getRow();
         int startCol = start.getCol();
         int endRow = end.getRow();
         int endCol = end.getCol();

         // if a red checker makes it to the other side, remove it from the board
         // and increase redCheckersScored by 1
         if (endRow == 7 && toMove.getColor() == Color.RED) {
             this.redCheckersScored += 1;
             start.removeChecker();
         } else if (endRow == 0 && toMove.getColor() == Color.BLUE) {
                 this.blueCheckersScored += 1;
                 start.removeChecker();
         } else {
                 // add to new square
                 end.placeChecker(toMove);
                 toMove.movePosition(endRow, endCol);
                 start.removeChecker();
             }

         boolean jump = false;

         if (Math.abs(startRow - endRow) > 1 || Math.abs(startCol - endCol) > 1) {
             // remove checker that was jumped
             jump = true;
             BoardSquare toTakeFrom = getCell((startRow + endRow) / 2, (startCol + endCol) / 2);
             toTakeFrom.removeChecker();
         }
         return jump;
     }

    /**
     * Saves the current game state to a file.
     */
     public void saveGameToFile (String fileName) {
        try {
            FileWriter file = new FileWriter(fileName);
            BufferedWriter writer = new BufferedWriter(file);

            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    BoardSquare current = getCell(row, col);
                    writer.write(Integer.toString(current.getRow()));
                    writer.write(" ");
                    writer.write(Integer.toString(current.getCol()));
                    writer.write(" ");
                    writer.write(current.getBackgroundColor().toString());
                    writer.write(" ");
                    writer.write(Boolean.toString(current.isEmpty()));
                    writer.write(" ");
                     // if the square has a checker, record what color it is
                    if (!current.isEmpty()) {
                        writer.write(current.getChecker().getColor().toString());
                    }
                    writer.newLine();
                }
            }
             // also want to keep track of whose turn it is, etc.
            writer.write(Boolean.toString(player1));
            writer.write(" ");
            writer.write(Integer.toString(redCheckersTaken));
            writer.write(" ");
            writer.write(Integer.toString(blueCheckersTaken));
            writer.write(" ");
            writer.write(Integer.toString(redCheckersScored));
            writer.write(" ");
            writer.write(Integer.toString(blueCheckersScored));

             //overwrite?
            writer.flush();
            writer.close();
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads a game state from a file and updates the board.
     */
    public void loadGameFromFile (String fileName) {
        try {
            FileReader file = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(file);
            FileLineIterator brIterator = new FileLineIterator(reader);

            this.board = new BoardSquare[8][8];
            int r;
            int c;
            Color squareColor;
            Color checkerColor;

            Map<String, Color> colorMap = new TreeMap<>();
            colorMap.put("java.awt.Color[r=255,g=0,b=0]", Color.RED);
            colorMap.put("java.awt.Color[r=0,g=0,b=255]", Color.BLUE);
            colorMap.put("java.awt.Color[r=0,g=0,b=0]", Color.BLACK);
            colorMap.put("java.awt.Color[r=255,g=255,b=255]", Color.WHITE);

            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    String line = brIterator.next();
                    System.out.println(line);
                    String[] splitList = line.split(" ");
                    r = Integer.parseInt(splitList[0]);
                    c = Integer.parseInt(splitList[1]);
                    squareColor = colorMap.get(splitList[2]);
                    board[row][col] = new BoardSquare(r, c, squareColor);

                    if (splitList[3].equals("false")) {
                        checkerColor = colorMap.get(splitList[4]);
                        board[row][col].placeChecker(new CheckerPiece(row, col, checkerColor));
                    }
                }
            }

            while (brIterator.hasNext()) {
                String line = brIterator.next();
                String[] splitList = line.split(" ");
                player1 = Boolean.parseBoolean(splitList[0]);
                redCheckersTaken = Integer.parseInt(splitList[1]);
                blueCheckersTaken = Integer.parseInt(splitList[2]);
                redCheckersScored = Integer.parseInt(splitList[3]);
                blueCheckersScored = Integer.parseInt(splitList[4]);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}




