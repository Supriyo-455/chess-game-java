package com.tests.chess.engine.board;

import com.chess.engine.board.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    @Test
    public void initialBoard(){
        final Board board = Board.createStandardBoard();
        assertEquals(board.currentPlayer().getLegalMoves().size(), 20);
        assertEquals(board.currentPlayer().getOpponent().getLegalMoves().size(), 20);
        assertFalse(board.currentPlayer().isInCheck());
        assertFalse(board.currentPlayer().isInCheckMate());
        assertFalse(board.currentPlayer().isCastled());
//        assertFalse(board.currentPlayer().isKingSideCastleCapable());
//        assertFalse(board.currentPlayer().isQueenSideCastleCapable());
        assertFalse(board.currentPlayer().getOpponent().isInCheck());
        assertFalse(board.currentPlayer().getOpponent().isInCheckMate());
        assertFalse(board.currentPlayer().getOpponent().isCastled());
//        assertFalse(board.currentPlayer().getOpponent().isKingSideCastleCapable());
//        assertFalse(board.currentPlayer().getOpponent().isQueenSideCastleCapable());
    }
}