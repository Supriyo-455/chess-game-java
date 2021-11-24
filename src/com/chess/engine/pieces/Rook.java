package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Rook extends Piece{

    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-8, -1, 1, 8};

    public Rook(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES) {
            int candidateDestinationCoordinate = this.piecePosition;

            while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {

                if (firstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset) ||
                        eigthColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)
                ) {
                    break;
                }

                candidateDestinationCoordinate += candidateCoordinateOffset;

                if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

                    if (!candidateDestinationTile.isTileOccupied()) {
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                    } else {
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAllianceAtDestination = pieceAtDestination.getPieceAlliance();

                        if (this.pieceAlliance != pieceAllianceAtDestination) {
                            legalMoves.add(
                                    new Move.AttackMove(
                                            board,
                                            this,
                                            candidateDestinationCoordinate,
                                            pieceAtDestination
                                    )
                            );
                        }
                        break;  //further, positions blocked or handover move after capturing enemy piece
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean firstColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1);
    }

    private static boolean eigthColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 1);
    }

    @Override
    public String toString() {
        return PieceType.ROOK.toString();
    }
}
