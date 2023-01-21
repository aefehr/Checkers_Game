package org.cis1200.checkers;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.*;

/**
 * You can use this file (and others) to test your
 * implementation.
 */

public class GameTest {
    private Checkers checkersGame;

    @BeforeEach
    public void setUp() {
        checkersGame = new Checkers();
    }

    @Test
    public void testGetCell() {
        BoardSquare current = checkersGame.getCell(0, 1);
        assertFalse(current.isEmpty());
        assertEquals(Color.red, current.getChecker().getColor());
    }

    @Test
    public void testPlayTurn() {
        BoardSquare toMoveFrom = checkersGame.getCell(2, 1);
        assertFalse(toMoveFrom.isEmpty());
        BoardSquare before = checkersGame.getCell(3, 0);
        assertTrue(before.isEmpty());
        boolean test = checkersGame.playTurn(2,1,3,0);
        BoardSquare after = checkersGame.getCell(3, 0);
        assertFalse(after.isEmpty());
        assertEquals(Color.RED, after.getChecker().getColor());
        assertTrue(toMoveFrom.isEmpty());
    }

    @Test
    public void testPlayTurnJump() {
        boolean test = checkersGame.playTurn(2,1,3,2);
        boolean test2 = checkersGame.playTurn(5,4,4,3);
        boolean jump = checkersGame.playTurn(3,2,5,4);
        assertFalse(checkersGame.getCell(5, 4).isEmpty());
        assertFalse(checkersGame.getCell(5, 4).getChecker() == null);
        assertEquals(Color.RED, checkersGame.getCell(5, 4).getChecker().getColor());
        assertEquals(1, checkersGame.blueCheckersTaken);
    }

    @Test
    public void testSaveAndLoadGame() {
        boolean r1 = checkersGame.playTurn(2, 1, 3, 2);
        checkersGame.saveGameToFile("test.txt");
        boolean b1 = checkersGame.playTurn(5, 4, 4, 5);
        assertTrue(checkersGame.getCell(5, 4).isEmpty());
        checkersGame.loadGameFromFile("test.txt");
        assertFalse(checkersGame.getCell(5, 4).isEmpty());
    }

    @Test
    public void testRedCheckerScores() {
        boolean r1 = checkersGame.playTurn(2, 5, 3, 4);
        boolean b1 = checkersGame.playTurn(5, 4, 4, 3);
        boolean r2 = checkersGame.playTurn(3, 4, 4, 5);
        boolean b2 = checkersGame.playTurn(5, 6, 4, 7);
        boolean r3 = checkersGame.playTurn(4, 5, 5, 6);
        boolean b3 = checkersGame.playTurn(6, 5, 5, 4);
        boolean r4 = checkersGame.playTurn(5, 6, 6, 5);
        boolean b4 = checkersGame.playTurn(6, 7, 5, 6);
        boolean r5 = checkersGame.playTurn(2, 7, 3, 6);
        boolean b5 = checkersGame.playTurn(7, 6, 6, 7);
        boolean r6 = checkersGame.playTurn(6, 5, 7, 6);
        assertEquals(1, checkersGame.redCheckersScored);
        assertTrue(checkersGame.getCell(6,5).isEmpty());
        assertTrue(checkersGame.getCell(7,6).isEmpty());
    }


}
