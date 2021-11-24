package com.chess.engine.board;

import com.chess.engine.Alliance;
import com.chess.engine.pieces.*;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Board {

    private final List<Tile> gameBoard;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;

    private Board(Builder builder) {
        this.gameBoard = createGameBoard(builder);
        this.whitePieces = calculateActivePieces(this.gameBoard, Alliance.WHITE);
        this.blackPieces = calculateActivePieces(this.gameBoard, Alliance.BLACK);
    }

    private Collection<Piece> calculateActivePieces(final List<Tile> gameBoard, final Alliance alliance) {
        final List<Piece> activePieces = new ArrayList<>();

        for (final Tile tile : gameBoard) {
            if (tile.isTileOccupied()) {
                final Piece piece = tile.getPiece();
                if (piece.getPieceAlliance() == alliance) {
                    activePieces.add(piece);
                }
            }
        }

        return ImmutableList.copyOf(activePieces);
    }

    public static List<Tile> createGameBoard(final Builder builder) {
        final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
        }
        return ImmutableList.copyOf(tiles);
    }

    public static Board createStandardBoard() {
        final Builder builder = new Builder();

        //Black layout
        builder.setPiece(new Rook(0, Alliance.BLACK));
        builder.setPiece(new Knight(1, Alliance.BLACK));
        builder.setPiece(new Bishop(2, Alliance.BLACK));
        builder.setPiece(new Queen(3, Alliance.BLACK));
        builder.setPiece(new King(4, Alliance.BLACK));
        builder.setPiece(new Bishop(5, Alliance.BLACK));
        builder.setPiece(new Knight(6, Alliance.BLACK));
        builder.setPiece(new Rook(7, Alliance.BLACK));
        builder.setPiece(new Pawn(8, Alliance.BLACK));
        builder.setPiece(new Pawn(9, Alliance.BLACK));
        builder.setPiece(new Pawn(10, Alliance.BLACK));
        builder.setPiece(new Pawn(11, Alliance.BLACK));
        builder.setPiece(new Pawn(12, Alliance.BLACK));
        builder.setPiece(new Pawn(13, Alliance.BLACK));
        builder.setPiece(new Pawn(14, Alliance.BLACK));
        builder.setPiece(new Pawn(15, Alliance.BLACK));

        //White layout
        builder.setPiece(new Rook(63 - 0, Alliance.WHITE));
        builder.setPiece(new Knight(63 - 1, Alliance.WHITE));
        builder.setPiece(new Bishop(63 - 2, Alliance.WHITE));
        builder.setPiece(new Queen(63 - 3, Alliance.WHITE));
        builder.setPiece(new King(63 - 4, Alliance.WHITE));
        builder.setPiece(new Bishop(63 - 5, Alliance.WHITE));
        builder.setPiece(new Knight(63 - 6, Alliance.WHITE));
        builder.setPiece(new Rook(63 - 7, Alliance.WHITE));
        builder.setPiece(new Pawn(63 - 8, Alliance.WHITE));
        builder.setPiece(new Pawn(63 - 9, Alliance.WHITE));
        builder.setPiece(new Pawn(63 - 10, Alliance.WHITE));
        builder.setPiece(new Pawn(63 - 11, Alliance.WHITE));
        builder.setPiece(new Pawn(63 - 12, Alliance.WHITE));
        builder.setPiece(new Pawn(63 - 13, Alliance.WHITE));
        builder.setPiece(new Pawn(63 - 14, Alliance.WHITE));
        builder.setPiece(new Pawn(63 - 15, Alliance.WHITE));

        //white to move
        builder.setMoveMaker(Alliance.WHITE);

        return builder.build();
    }

    public Tile getTile(int coordinate) {

        return this.gameBoard.get(coordinate);
    }

    public static class Builder {

        Map<Integer, Piece> boardConfig;
        Alliance nextMoveMaker;

        public Builder() {
        }

        public Builder setPiece(final Piece piece) {
            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }

        public Builder setMoveMaker(final Alliance nextMoveMaker) {
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

        public Board build() {
            return new Board(this);
        }
    }
}
