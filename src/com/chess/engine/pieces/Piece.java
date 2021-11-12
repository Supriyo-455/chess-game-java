package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.google.common.collect.ImmutableList;

public abstract class Piece {
    protected final int piecePosition;
    protected final Alliance pieceAlliance;

    public Piece(final int piecePosition,final Alliance pieceAlliance) {
        this.piecePosition = piecePosition;
        this.pieceAlliance = pieceAlliance;
    }

    public Alliance getPieceAlliance(){
        return this.pieceAlliance;
    }

    public abstract ImmutableList<Move> calculateLegalMoves(final Board board);

}
