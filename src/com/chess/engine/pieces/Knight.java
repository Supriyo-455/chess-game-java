package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    private final static int[] CANDIDATE_MOVES_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};

    public Knight(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public ImmutableList<Move> calculateLegalMoves(Board board) {

        int candidateDestinationCoordinate;
        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateCoordinate : CANDIDATE_MOVES_COORDINATES) {

            candidateDestinationCoordinate = this.piecePosition + currentCandidateCoordinate;

            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if (isFirstColumnExclusion(this.piecePosition, currentCandidateCoordinate) ||
                        isSecondColumnExclusion(this.piecePosition, currentCandidateCoordinate) ||
                        isSeventhColumnExclusion(this.piecePosition, currentCandidateCoordinate) ||
                        isEightColumnExclusion(this.piecePosition, currentCandidateCoordinate)
                ) {
                    continue;
                }

                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if (!candidateDestinationTile.isTileOccupied()) {
                    legalMoves.add(new Move());
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAllianceAtDestination = pieceAtDestination.getPieceAlliance();

                    if (this.pieceAlliance == pieceAllianceAtDestination) {
                        legalMoves.add(new Move());
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (
                candidateOffset == -17 || candidateOffset == -10 || candidateOffset == 6 || candidateOffset == 15
        );
    }

    private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.SECOND_COLUMN[currentPosition] && (
                candidateOffset == -10 || candidateOffset == 6
        );
    }

    private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && (
                candidateOffset == 10 || candidateOffset == -6
        );
    }

    private static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (
                candidateOffset == 17 || candidateOffset == 10 || candidateOffset == -6 || candidateOffset == -15
        );
    }
}
