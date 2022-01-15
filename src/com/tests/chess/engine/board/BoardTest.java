package com.tests.chess.engine.board;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.player.MoveTransition;
import com.chess.engine.player.ai.Minimax;
import com.chess.engine.player.ai.MoveStrategy;
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

    @Test
    public void testAI(){
        final Board board = Board.createStandardBoard();
        final MoveStrategy moveStrategy = new Minimax(4);
        final Move move = moveStrategy.execute(board);
        System.out.println(move);
    }


    @Test
    public void testMove(){
        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer().makeMove(
                Move.MoveFactory.createMove(
                        board,
                        BoardUtils.getCoordinateAtPosition("f2"),
                        BoardUtils.getCoordinateAtPosition("f3")
                )
        );
        assertTrue(t1.getMoveStatus().isDone());

        System.out.println(board);

        final MoveTransition t2 = t1.getTransitionBoard()
                .currentPlayer()
                .makeMove(
                  Move.MoveFactory.createMove(
                          t1.getTransitionBoard(),
                          BoardUtils.getCoordinateAtPosition("e7"),
                          BoardUtils.getCoordinateAtPosition("e5"))
                );

        final MoveTransition t3 = t2.getTransitionBoard()
                .currentPlayer()
                .makeMove(
                        Move.MoveFactory.createMove(
                                t2.getTransitionBoard(),
                                BoardUtils.getCoordinateAtPosition("g2"),
                                BoardUtils.getCoordinateAtPosition("g4"))
                );
        assertTrue(t3.getMoveStatus().isDone());

        System.out.println(t3.getTransitionBoard());

        final MoveStrategy strategy = new Minimax(4);

        final Move aiMove = strategy.execute(t3.getTransitionBoard());

        final Move bestMove = Move.MoveFactory.createMove(
                t3.getTransitionBoard(),
                BoardUtils.getCoordinateAtPosition("d8"),
                BoardUtils.getCoordinateAtPosition("h4")
        );

        assertEquals(aiMove, bestMove);
    }
}